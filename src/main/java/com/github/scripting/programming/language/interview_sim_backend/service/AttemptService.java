package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.grpc.NextQuestionRequest;
import com.github.scripting.programming.language.interview_sim_backend.entity.Attempt;
import com.github.scripting.programming.language.interview_sim_backend.entity.Question;
import com.github.scripting.programming.language.model.AttemptDetail;
import com.github.scripting.programming.language.model.AttemptSummary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AttemptService {
    Attempt startAttempt(Long userId, Long courseId);

    void answerQuestion(Long attemptId, Long questionId, Long userId, Integer audioDuration, MultipartFile file);

    AttemptDetail getAttemptDetail(Long attemptId, Long userId);

    List<AttemptSummary> getUserAttemptsSummary(Long userId);

    AttemptDetail finishAttempt(Long attemptId, Long userId);

    Optional<Question> getNextQuestion(NextQuestionRequest request);
}
