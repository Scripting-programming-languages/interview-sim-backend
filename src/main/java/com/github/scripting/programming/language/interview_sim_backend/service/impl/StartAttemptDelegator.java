package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.mapper.AttemptMapper;
import com.github.scripting.programming.language.interview_sim_backend.service.AttemptService;
import com.github.scripting.programming.language.model.AttemptStartResponse;
import com.github.scripting.programming.language.model.AttemptStartResponseV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartAttemptDelegator {
    private final AttemptMapper attemptMapper;
    private final AttemptService attemptService;

    public AttemptStartResponse startAttempt(Long courseId, Long userId) {
        var attempt = attemptService.startAttempt(userId, courseId);

        return attemptMapper.toAttemptStartResponse(attempt);
    }

    public AttemptStartResponseV2 startAttemptV2(Long courseId, Long userId) {
        var attempt = attemptService.startAttempt(userId, courseId);

        return attemptMapper.toAttemptStartResponseV2(attempt);
    }
}
