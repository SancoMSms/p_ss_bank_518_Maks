package com.bank.publicinfo.exception;

import com.bank.publicinfo.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component("globalExceptionHandler")
public class GlobalExceptionHandler implements KafkaListenerErrorHandler {

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        Throwable cause = exception.getCause() != null ? exception.getCause() : exception;
        ErrorResponse errorResponse;
        if (cause instanceof EntityNotFoundException) {
            errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), cause.getMessage());
        } else if (cause instanceof ValidationException) {
            errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), cause.getMessage());
        } else {
            errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
        }
        return errorResponse;
    }
}
