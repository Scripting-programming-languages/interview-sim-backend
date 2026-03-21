package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.model.AttemptDetail;
import com.github.scripting.programming.language.model.AttemptStartResponse;
import com.github.scripting.programming.language.model.AttemptSummary;
import com.github.scripting.programming.language.model.UserAnswerResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttemptService {
    AttemptStartResponse startAttempt(Long userId, Long courseId);

    void answerQuestion(Long attemptId, Long questionId, Long userId, Integer audioDuration, MultipartFile file);

    AttemptDetail getAttemptDetail(Long attemptId, Long userId);

    List<AttemptSummary> getUserAttemptsSummary(Long userId);

    AttemptDetail finishAttempt(Long attemptId, Long userId);
}
