package com.github.scripting.programming.language.interview_sim_backend.controller;

import com.github.scripting.programming.language.controller.AttemptsApi;
import com.github.scripting.programming.language.interview_sim_backend.exception.BaseApiException;
import com.github.scripting.programming.language.interview_sim_backend.service.AttemptService;
import com.github.scripting.programming.language.interview_sim_backend.service.AuthUtil;
import com.github.scripting.programming.language.model.AttemptDetail;
import com.github.scripting.programming.language.model.AttemptStartResponse;
import com.github.scripting.programming.language.model.AttemptSummary;
import com.github.scripting.programming.language.model.UserAnswerResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequiredArgsConstructor
public class AttemptControllerImpl implements AttemptsApi {
    private final AttemptService attemptService;
    private final AuthUtil authUtil;

    @Override
    public ResponseEntity<AttemptDetail> attemptsAttemptIdFinishPatch(Long attemptId) {
        var userId = authUtil.getCurrentUserId();
        return ResponseEntity.ok(attemptService.finishAttempt(attemptId, userId));
    }

    @Override
    public ResponseEntity<AttemptDetail> attemptsAttemptIdGet(Long attemptId) {
        var userId = authUtil.getCurrentUserId();
        return ResponseEntity.ok(attemptService.getAttemptDetail(attemptId, userId));
    }

    @Override
    public ResponseEntity<UserAnswerResult> attemptsAttemptIdQuestionsQuestionIdAnswerPost(Long attemptId, Long questionId, MultipartFile userAudio, Integer audioDuration) {
        var userId = authUtil.getCurrentUserId();
        var UserAnswerResult = attemptService.answerQuestion(attemptId, questionId, userId, audioDuration, userAudio);
        return ResponseEntity.ok(UserAnswerResult);
    }

    /**
     * Начала прохождения курса
     * @param courseId  (required)
     * @return
     */
    @Override
    public ResponseEntity<AttemptStartResponse> coursesCourseIdAttemptPost(Long courseId) {
        var userId = authUtil.getCurrentUserId();
        return ResponseEntity.ok(attemptService.startAttempt(userId, courseId));
    }

    @Override
    public ResponseEntity<List<AttemptSummary>> usersUserIdAttemptsGet(Long userId) {
        var jwtUserId = authUtil.getCurrentUserId();
        if (!jwtUserId.equals(userId)) {
            throw new BaseApiException(FORBIDDEN, "Недостаточно прав");
        }
        return ResponseEntity.ok(attemptService.getUserAttemptsSummary(userId));
    }
}
