package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.interview_sim_backend.dto.SummaryResponseDto;

import java.util.List;

public interface FeedbackSummarizer {
    SummaryResponseDto summarize(List<String> feedbacks);
}
