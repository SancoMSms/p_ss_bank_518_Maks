package com.bank.profile.Exceptions;

import com.bank.profile.Kafka.KafkaErrorProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerUnitTest {

    @Mock
    private final KafkaErrorProducer kafkaErrorProducer = null;

    @InjectMocks
    private final GlobalExceptionHandler exceptionHandler = null;

    @Test
    void handleEntityNotFoundException_shouldReturn404AndSendToKafka() {
        var ex = new EmptyResultDataAccessException("Profile not found", 1);

        var response = exceptionHandler.handleEntityNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(kafkaErrorProducer).sendError(contains("Profile not found"));
    }

    @Test
    void handleValidationException_shouldReturn400AndSendToKafka() {
        final BindingResult bindingResult = mock(BindingResult.class);
        final ObjectError error1 = new ObjectError("field", "must not be null");
        final ObjectError error2 = new ObjectError("anotherField", "must be valid");

        when(bindingResult.getAllErrors()).thenReturn(List.of(error1, error2));

        final MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        var response = exceptionHandler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(kafkaErrorProducer).sendError(contains("must not be null"));
    }

    @Test
    void handleGenericException_shouldReturn500AndSendToKafka() {
        final Exception ex = new RuntimeException("Something went wrong");

        var response = exceptionHandler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(kafkaErrorProducer).sendError(contains("Something went wrong"));
    }
}
