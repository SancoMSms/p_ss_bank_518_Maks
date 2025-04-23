package com.bank.profile.Kafka;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

class KafkaErrorProducerTest {

    private final KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);
    private final MeterRegistry meterRegistry = mock(MeterRegistry.class);
    private final Counter errorMessagesCounter = mock(Counter.class);

    private KafkaErrorProducer kafkaErrorProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(meterRegistry.counter("profile.errors.send.count")).thenReturn(errorMessagesCounter);
        kafkaErrorProducer = new KafkaErrorProducer(kafkaTemplate, meterRegistry);
        ReflectionTestUtils.setField(kafkaErrorProducer, "errorTopic", "profile.errors");
    }

    @Test
    void sendError_shouldSendMessageAndIncrementCounter() {
        String message = "Test error message";

        kafkaErrorProducer.sendError(message);

        verify(kafkaTemplate).send("profile.errors", message);
        verify(errorMessagesCounter).increment();
    }

    @Test
    void sendError_shouldHandleKafkaException() {
        String message = "Kafka send failed";

        doThrow(new RuntimeException("Kafka is down"))
                .when(kafkaTemplate).send("profile.errors", message);

        kafkaErrorProducer.sendError(message);

        verify(kafkaTemplate).send("profile.errors", message);
        verify(errorMessagesCounter, never()).increment();
    }
}
