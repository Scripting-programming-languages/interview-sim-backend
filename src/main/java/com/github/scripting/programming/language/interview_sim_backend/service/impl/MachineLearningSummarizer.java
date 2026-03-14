package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.dto.SummaryRequestDto;
import com.github.scripting.programming.language.interview_sim_backend.dto.SummaryResponseDto;
import com.github.scripting.programming.language.interview_sim_backend.service.FeedbackSummarizer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineLearningSummarizer implements FeedbackSummarizer {
    private final RestClient mlRestClient;

    @Override
    public SummaryResponseDto summarize(List<String> feedbacks) {
        var body = new SummaryRequestDto(feedbacks);

        return mlRestClient.post()
                .uri(getOperationName())
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(SummaryResponseDto.class);
    }

    private String getOperationName() {
        return "/finalize-feedback";
    }
}
