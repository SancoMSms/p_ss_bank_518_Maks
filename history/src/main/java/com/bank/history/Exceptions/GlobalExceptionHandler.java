package com.bank.history.Exceptions;

import com.bank.history.DTO.ErrorResponseDto;
import com.bank.history.configs.KafkaTopicConfig;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.UUID;
@Service
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ConsumerAwareListenerErrorHandler {

    private final KafkaTemplate<String, ErrorResponseDto> kafkaTemplate;
    private final KafkaTopicConfig kafkaTopicConfig;

    private String determineErrorType(Throwable ex) {
        if (ex instanceof EntityNotFoundException) {
            return "EntityNotFound";
        } else if (ex instanceof ValidationException) {
            return "ValidationError";
        } else {
            return "GeneralError";
        }
    }

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        Throwable rootCause = exception.getCause() != null ? exception.getCause() : exception;

        String errorType = determineErrorType(rootCause);
        String requestId = UUID.randomUUID().toString();

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                errorType,
                rootCause.getMessage(),
                Instant.now().toString(),
                requestId
        );

        log.error("Глобальная ошибка обработки Kafka-сообщения: {}", errorResponse, exception);

        kafkaTemplate.send(kafkaTopicConfig.getAuditHistoryErrorsTopic(), requestId, errorResponse);

        return null;
    }
}
