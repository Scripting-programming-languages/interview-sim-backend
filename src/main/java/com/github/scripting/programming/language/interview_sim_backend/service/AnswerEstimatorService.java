package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.interview_sim_backend.dto.AnswerEvaluation;
import org.springframework.web.multipart.MultipartFile;

public interface AnswerEstimatorService {
    AnswerEvaluation estimateAnswer(MultipartFile audioFile);
}
