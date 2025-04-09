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

@RequiredArgsConstructor
@Service("globalExceptionHandler")
@Slf4j
public class GlobalExceptionHandler implements ConsumerAwareListenerErrorHandler {

    private final KafkaTemplate<String, ErrorResponseDto> errorResponseKafkaTemplate;
    private final KafkaTopicConfig kafkaTopicConfig;

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

        log.error("Kafka Global Error [{}]: {}", errorType, errorResponse, exception);
        errorResponseKafkaTemplate.send(kafkaTopicConfig.getAuditHistoryErrorsTopic(), requestId, errorResponse);
        return null;
    }

    private String determineErrorType(Throwable ex) {
        if (ex instanceof EntityNotFoundException) return "EntityNotFound";
        if (ex instanceof ValidationException) return "ValidationError";
        if (ex instanceof MessageProcessingException) return "ProcessingError";
        return "GeneralError";
    }
}