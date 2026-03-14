package com.github.scripting.programming.language.interview_sim_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnswerEvaluation(Integer score,
                               @JsonProperty("speech_score") Integer speechScore,
                               @JsonProperty("text_feedback") String textFeedback,
                               @JsonProperty("speech_feedback") String speechFeedback,
                               @JsonProperty("transcribed_text") String transcribedText) {
}
