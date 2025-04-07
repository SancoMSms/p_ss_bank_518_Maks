package com.bank.profile.Kafka;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaErrorProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaErrorProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topics.profile-errors:profile.errors}")
    private String errorTopic;

    private final Counter errorMessagesCounter;

    public KafkaErrorProducer(KafkaTemplate<String, String> kafkaTemplate, MeterRegistry meterRegistry) {
        this.kafkaTemplate = kafkaTemplate;
        this.errorMessagesCounter = meterRegistry.counter("profile.errors.send.count");
    }

    public void sendError(String message) {
        try {
            kafkaTemplate.send(errorTopic, message);
            logger.info("Sent error message to Kafka: {}", message);
            errorMessagesCounter.increment();
        } catch (Exception e) {
            logger.error("Failed to send error message to Kafka: {}", message, e);
        }
    }
}
