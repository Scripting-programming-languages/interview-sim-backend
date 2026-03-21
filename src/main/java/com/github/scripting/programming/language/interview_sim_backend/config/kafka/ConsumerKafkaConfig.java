package com.github.scripting.programming.language.interview_sim_backend.config.kafka;

import com.github.scripting.programming.language.interview_sim_backend.dto.AnswerEstimationMsg;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

@Configuration
@EnableKafka
public class ConsumerKafkaConfig {

    @Bean
    public ConsumerFactory<String, AnswerEstimationMsg> answerEstimationConsumerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(),
                new StringDeserializer(),
                new JacksonJsonDeserializer<>());
    }

    @Bean
    public CommonErrorHandler commonErrorHandler() {
        return new CommonLoggingErrorHandler();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AnswerEstimationMsg> answerEstimationListenerContainerFactory(
            ConsumerFactory<String, AnswerEstimationMsg> factory,
            CommonErrorHandler commonErrorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, AnswerEstimationMsg> containerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(factory);
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        containerFactory.setCommonErrorHandler(commonErrorHandler);

        return containerFactory;
    }
}
