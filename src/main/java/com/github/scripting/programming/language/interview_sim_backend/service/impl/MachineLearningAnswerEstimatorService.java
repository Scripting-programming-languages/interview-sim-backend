package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.dto.AnswerEvaluation;
import com.github.scripting.programming.language.interview_sim_backend.dto.EstimateAnswerRequestDto;
import com.github.scripting.programming.language.interview_sim_backend.exception.MLHttpException;
import com.github.scripting.programming.language.interview_sim_backend.service.AnswerEstimatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class MachineLearningAnswerEstimatorService implements AnswerEstimatorService {
    private final RestClient mlRestClient;

    private String getOperationName() {
        return "/estimate-answer";
    }

    @Override
    public AnswerEvaluation estimateAnswer(EstimateAnswerRequestDto estimateAnswerRequestDto) {
        var body = toMultiValueMap(estimateAnswerRequestDto);

        return mlRestClient.post()
                .uri(getOperationName())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new MLHttpException(response.getStatusCode());
                })
                .body(AnswerEvaluation.class);
    }

    private MultiValueMap<String, Object> toMultiValueMap(EstimateAnswerRequestDto estimateAnswerRequestDto) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("audio", estimateAnswerRequestDto.audio());
        map.add("reference_text", estimateAnswerRequestDto.referenceText());
        return map;
    }
}
