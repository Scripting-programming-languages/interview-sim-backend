package com.github.scripting.programming.language.interview_sim_backend.listener;

import com.github.scripting.programming.language.interview_sim_backend.dto.AnswerEstimationMsg;
import com.github.scripting.programming.language.interview_sim_backend.service.AnswerService;
import com.github.scripting.programming.language.interview_sim_backend.service.AttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerEstimationListener {
    private final AnswerService answerService;
    private final AttemptService attemptService;

    @KafkaListener(
            id = "answerEstimationListener",
            topics = "${kafka.ml.topics}",
            groupId = "${kafka.ml.groupId}",
            concurrency = "${kafka.ml.concurrency}"
    )
    public void listenAnswerEstimationTopic(@Payload AnswerEstimationMsg msg) {
        var attemptId = answerService.updateAnswerByEstimation(msg);
        attemptService.estimateOverallStat(attemptId);
    }
}
