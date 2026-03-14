package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.interview_sim_backend.dto.AnswerEvaluation;
import com.github.scripting.programming.language.interview_sim_backend.dto.EstimateAnswerRequestDto;

public interface AnswerEstimatorService {
    AnswerEvaluation estimateAnswer(EstimateAnswerRequestDto estimateAnswerRequestDto);
}
