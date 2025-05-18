package com.advancedbackend.module_one.handler;

import com.advancedbackend.module_one.domain.dto.Error;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Log4j2
public class ErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolationException(ConstraintViolationException exception) {
        var message = extractExceptionMessage(exception);
        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Error> handleConversionException(HttpMessageConversionException exception) {
        var message = extractExceptionMessage(exception);
        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Error> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        var message = extractExceptionMessage(exception);
        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    public ResponseEntity<Error> handleJpaObjectRetrievalFailure(JpaObjectRetrievalFailureException exception) {
        var message = extractExceptionMessage(exception);
        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Error> handleResponseStatusNotFoundException(ResponseStatusException exception) {
        if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
            var message = exception.getReason();
            log.error("Handle not found exception during service chain call, {}", message, exception);
            return buildErrorResponse(HttpStatus.NOT_FOUND.value(), message);
        }
        throw exception;
    }

    private String extractExceptionMessage(Exception exception) {
        var message = exception.getMessage();
        log.error("Handle exception during service chain call, {}", message, exception);
        return message;
    }

    public ResponseEntity<Error> buildErrorResponse(int status, String message) {
        log.error("Error occurred during service chain call, {}", message);
        var errorResponse = new Error();
        errorResponse.setCode(status);
        errorResponse.setErrorMessage(message);
        return ResponseEntity.status(errorResponse.getCode()).body(errorResponse);
    }
}
