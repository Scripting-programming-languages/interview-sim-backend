package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.dto.EstimateAnswerRequestDto;
import com.github.scripting.programming.language.interview_sim_backend.exception.MLHttpException;
import com.github.scripting.programming.language.interview_sim_backend.service.AnswerEstimateSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpStatus.ACCEPTED;

@Service
@RequiredArgsConstructor
public class MachineLearningAnswerEstimateService implements AnswerEstimateSender {
    private final RestClient mlRestClient;

    private String getOperationName() {
        return "/estimate-answer";
    }

    @Override
    public void sendEstimateAnswer(EstimateAnswerRequestDto estimateAnswerRequestDto) {
        var body = toMultiValueMap(estimateAnswerRequestDto);

        mlRestClient.post()
                .uri(getOperationName())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .onStatus(status -> status != ACCEPTED, (request, response) -> {
                    throw new MLHttpException(response.getStatusCode());
                })
                .toBodilessEntity();
    }

    private MultiValueMap<String, Object> toMultiValueMap(EstimateAnswerRequestDto estimateAnswerRequestDto) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("audio", estimateAnswerRequestDto.audio());
        map.add("reference_text", estimateAnswerRequestDto.referenceText());
        map.add("answer_id", estimateAnswerRequestDto.answerId());
        return map;
    }
}
