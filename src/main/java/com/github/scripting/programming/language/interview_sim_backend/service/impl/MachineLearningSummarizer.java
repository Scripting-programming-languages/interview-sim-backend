package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.service.FeedbackSummarizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineLearningSummarizer implements FeedbackSummarizer {
    @Override
    public String summarize(List<String> feedbacks) {
        // mock
        return "test summarize";
    }
}
