package com.github.scripting.programming.language.interview_sim_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SummaryResponseDto(@JsonProperty("summary_feedback") String summaryFeedback) {
}
