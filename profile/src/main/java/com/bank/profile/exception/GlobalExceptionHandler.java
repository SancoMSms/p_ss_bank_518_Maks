//package com.bank.profile.exception;
//
//import com.bank.profile.enums.ErrorType;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.persistence.EntityNotFoundException;
//import javax.validation.ValidationException;
//
//@RestControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//
//    private final KafkaTemplate<String, ErrorResponse> kafkaTemplate;
//
//    public GlobalExceptionHandler(KafkaTemplate<String, ErrorResponse> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
//        return handleException(ex, HttpStatus.NOT_FOUND, ErrorType.NOT_FOUND);
//    }
//
//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
//        return handleException(ex, HttpStatus.BAD_REQUEST, ErrorType.VALIDATION);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
//        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.GENERAL);
//    }
//
//    private ResponseEntity<ErrorResponse> handleException(Exception ex, HttpStatus status, ErrorType type) {
//        log.error("Exception caught: {}", ex.getMessage(), ex);
//        ErrorResponse errorResponse = new ErrorResponse(status.value(), type, ex.getMessage());
//        kafkaTemplate.send("profile.errors", errorResponse);
//        return ResponseEntity.status(status).body(errorResponse);
//    }
//}
