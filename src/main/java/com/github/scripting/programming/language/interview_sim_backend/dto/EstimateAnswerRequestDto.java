package com.github.scripting.programming.language.interview_sim_backend.dto;

import org.springframework.core.io.Resource;

public record EstimateAnswerRequestDto(Resource audio, String referenceText) {
}
