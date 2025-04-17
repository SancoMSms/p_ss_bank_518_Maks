package com.bank.profile.Exceptions;

import com.bank.profile.Kafka.KafkaErrorProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerUnitTest {

    @Mock
    private KafkaErrorProducer kafkaErrorProducer;

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleEntityNotFoundException_shouldReturn404AndSendToKafka() {
        EmptyResultDataAccessException exception = new EmptyResultDataAccessException("Profile not found", 1);

        ResponseEntity<String> response = exceptionHandler.handleEntityNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("Profile not found"));
        verify(kafkaErrorProducer, times(1)).sendError(contains("Profile not found"));
    }

    @Test
    void handleValidationException_shouldReturn400AndSendToKafka() {
        BindingResult bindingResult = mock(BindingResult.class);
        ObjectError error1 = new ObjectError("field", "must not be null");
        ObjectError error2 = new ObjectError("anotherField", "must be valid");
        when(bindingResult.getAllErrors()).thenReturn(List.of(error1, error2));

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<List<String>> response = exceptionHandler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(List.of("must not be null", "must be valid"), response.getBody());
        verify(kafkaErrorProducer).sendError(contains("must not be null"));
    }

    @Test
    void handleGenericException_shouldReturn500AndSendToKafka() {
        Exception exception = new RuntimeException("Something went wrong");

        ResponseEntity<String> response = exceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Something went wrong"));
        verify(kafkaErrorProducer).sendError(contains("Something went wrong"));
    }
}
