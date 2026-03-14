package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.dto.EstimateAnswerRequestDto;
import com.github.scripting.programming.language.interview_sim_backend.dto.FeedbackScore;
import com.github.scripting.programming.language.interview_sim_backend.entity.Attempt;
import com.github.scripting.programming.language.interview_sim_backend.entity.AttemptStatus;
import com.github.scripting.programming.language.interview_sim_backend.exception.BaseApiException;
import com.github.scripting.programming.language.interview_sim_backend.mapper.AttemptMapper;
import com.github.scripting.programming.language.interview_sim_backend.repository.AttemptRepository;
import com.github.scripting.programming.language.interview_sim_backend.repository.CourseRepository;
import com.github.scripting.programming.language.interview_sim_backend.repository.QuestionRepository;
import com.github.scripting.programming.language.interview_sim_backend.repository.UserRepository;
import com.github.scripting.programming.language.interview_sim_backend.service.AnswerEstimatorService;
import com.github.scripting.programming.language.interview_sim_backend.service.AnswerService;
import com.github.scripting.programming.language.interview_sim_backend.service.AttemptService;
import com.github.scripting.programming.language.interview_sim_backend.service.FeedbackSummarizer;
import com.github.scripting.programming.language.model.AttemptDetail;
import com.github.scripting.programming.language.model.AttemptStartResponse;
import com.github.scripting.programming.language.model.AttemptSummary;
import com.github.scripting.programming.language.model.UserAnswerResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AttemptServiceImpl implements AttemptService {
    private final AttemptRepository attemptRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AttemptMapper attemptMapper;
    private final AnswerEstimatorService answerEstimatorService;
    private final AnswerService answerService;
    private final FeedbackSummarizer feedbackSummarizer;

    private static int getOverallScore(List<FeedbackScore> feedbackScores) {
        return (int) Math.round(
                feedbackScores.stream()
                        .collect(Collectors.averagingInt(FeedbackScore::score))
        );
    }

    @Override
    @Transactional
    public AttemptStartResponse startAttempt(Long userId, Long courseId) {
        var course = courseRepository.findWithQuestionsById(courseId)
                .orElseThrow(() -> new BaseApiException(NOT_FOUND, "Такого курса не существует"));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseApiException(NOT_FOUND, "Такого пользователя не существует"));
        Attempt attempt = Attempt.builder()
                .course(course)
                .user(user)
                .build();

        attempt = attemptRepository.save(attempt);
        return attemptMapper.toAttemptStartResponse(attempt);
    }

    @Override
    public UserAnswerResult answerQuestion(Long attemptId, Long questionId, Long userId, Integer audioDuration, MultipartFile file) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseApiException(NOT_FOUND, "Такого пользователя не существует"));
        var attempt = attemptRepository.findWithCourseByIdAndUserId(attemptId, userId)
                .orElseThrow(() -> new BaseApiException(NOT_FOUND, "Такой попытки не существует"));
        if (!attempt.getStatus().equals(AttemptStatus.IN_PROGRESS)) {
            throw new BaseApiException(BAD_REQUEST, "Попытка уже завершена");
        }
        var question = questionRepository.findQuestionByCourseIdAndQuestionId(questionId, attempt.getCourse().getId())
                .orElseThrow(() -> new BaseApiException(NOT_FOUND, "Такой вопроса не существует"));
        if (answerService.existAnswer(attempt, question)) {
            throw new BaseApiException(BAD_REQUEST, "Ответ уже записан");
        }

        // request to LLM
        var request = new EstimateAnswerRequestDto(file.getResource(), question.getCorrectAnswer());
        var evaluation = answerEstimatorService.estimateAnswer(request);

        var answer = answerService.save(attempt, question, evaluation.transcribedText(), evaluation.score(), evaluation.textFeedback());

        var response = new UserAnswerResult();
        response.setQuestionId(questionId);
        response.setFeedback(evaluation.textFeedback());
        response.setCreatedAt(answer.getCreatedAt().toOffsetDateTime());
        response.setScore(evaluation.score());
        response.setUserAnswer(evaluation.transcribedText());
        return response;
    }

    @Override
    public AttemptDetail getAttemptDetail(Long attemptId, Long userId) {
        var attemptWithAnswers = attemptRepository.findWithAnswersByIdAndUserId(attemptId, userId)
                .orElseThrow(() -> new BaseApiException(NOT_FOUND, "Попытки не существует"));

        return attemptMapper.toAttemptDetail(attemptWithAnswers);
    }

    @Override
    public List<AttemptSummary> getUserAttemptsSummary(Long userId) {
        var attemptList = attemptRepository.findAllWithCourseByUserId(userId);
        return attemptList.stream()
                .map(attemptMapper::toAttemptSummary)
                .toList();
    }

    @Override
    @Transactional
    public AttemptDetail finishAttempt(Long attemptId, Long userId) {
        /**
         * 1. Получить Attempt с проверкой по attemptId и userId
         * 2. Взять все фидбеки по всем ответам, очистить от null. (проверить, если список будет пустым, то сразу отдать)
         * 3. Список этого фидбека закинуть в мл на суммаризацию
         * 4. После этого обновить статус на FINISHED, проставить фидбек
         * 5. Отдать
         */
        var currentTime = ZonedDateTime.now();
        var attempt = attemptRepository.findWithAnswersByIdAndUserId(attemptId, userId)
                .orElseThrow(() -> new BaseApiException(NOT_FOUND, "Такой попытки не существует"));
        if (!attempt.getStatus().equals(AttemptStatus.IN_PROGRESS)) {
            throw new BaseApiException(BAD_REQUEST, "Попытка уже завершена");
        }
        var attemptAnswers = attempt.getAnswers();
        List<FeedbackScore> feedbackScores = attemptAnswers.stream()
                .filter(ans -> StringUtils.isNotEmpty(ans.getFeedback()) && ans.getScore() != null)
                .map(ans -> new FeedbackScore(ans.getFeedback(), ans.getScore()))
                .toList();
        if (feedbackScores.isEmpty()) {
            attempt.setStatus(AttemptStatus.FINISHED);
            attempt.setTimestampEnd(currentTime);
            attemptRepository.save(attempt);
            return attemptMapper.toAttemptDetail(attempt);
        }

        Integer meanScore = getOverallScore(feedbackScores);
        var summarize = feedbackSummarizer.summarize(
                feedbackScores.stream()
                        .map(FeedbackScore::feedback)
                        .toList()
        );
        attempt.setOverallScore(meanScore);
        attempt.setOverallFeedback(summarize.summaryFeedback());
        attempt.setStatus(AttemptStatus.FINISHED);
        attempt.setTimestampEnd(currentTime);
        attemptRepository.save(attempt);

        return attemptMapper.toAttemptDetail(attempt);
    }
}
