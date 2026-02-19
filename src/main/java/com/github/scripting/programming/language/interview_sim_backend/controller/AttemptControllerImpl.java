package com.github.scripting.programming.language.interview_sim_backend.controller;

import com.github.scripting.programming.language.controller.AttemptsApi;
import com.github.scripting.programming.language.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttemptControllerImpl implements AttemptsApi {
    @Override
    public ResponseEntity<AttemptDetail> attemptsAttemptIdFinishPatch(Long attemptId) {
        return null;
    }

    @Override
    public ResponseEntity<AttemptDetail> attemptsAttemptIdGet(Long attemptId) {
        return null;
    }

    @Override
    public ResponseEntity<UserAnswerResponse> attemptsAttemptIdQuestionsQuestionIdAnswerPost(Long attemptId, Long questionId, UserAnswerRequest userAnswerRequest) {
        return null;
    }

    /**
     * Начала прохождения курса
     * @param courseId  (required)
     * @return
     */
    @Override
    public ResponseEntity<AttemptStartResponse> coursesCourseIdAttemptsPost(Long courseId) {
        return null;
    }

    @Override
    public ResponseEntity<List<AttemptSummary>> usersUserIdAttemptsGet(Integer userId) {
        return null;
    }
}
