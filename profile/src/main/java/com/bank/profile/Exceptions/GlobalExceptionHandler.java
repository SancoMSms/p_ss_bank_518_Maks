package com.bank.profile.Exceptions;

import com.bank.profile.Kafka.KafkaErrorProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final KafkaErrorProducer kafkaErrorProducer;

    public GlobalExceptionHandler(KafkaErrorProducer kafkaErrorProducer) {
        this.kafkaErrorProducer = kafkaErrorProducer;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EmptyResultDataAccessException ex) {
        String errorMessage = String.format("Entity not found: %s", ex.getMessage());
        kafkaErrorProducer.sendError(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        String errorMessage = "Validation errors: " + String.join(", ", errorMessages);
        kafkaErrorProducer.sendError(errorMessage);

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        String errorMessage = String.format("An error occurred: %s", ex.getMessage());
        kafkaErrorProducer.sendError(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
