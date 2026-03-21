package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.interview_sim_backend.dto.EstimateAnswerRequestDto;

public interface AnswerEstimateSender {
    void sendEstimateAnswer(EstimateAnswerRequestDto estimateAnswerRequestDto);
}
