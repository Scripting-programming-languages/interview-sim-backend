package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.dto.AnswerEvaluation;
import com.github.scripting.programming.language.interview_sim_backend.service.AnswerEstimatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MachineLearningAnswerEstimatorService implements AnswerEstimatorService {
    @Async
    @Override
    public AnswerEvaluation estimateAnswer(MultipartFile audioFile) {
        // mock
        return new AnswerEvaluation("test text", 3, "good");
    }
}
