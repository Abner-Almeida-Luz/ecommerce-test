package com.AbnerTest.ecommerce_test.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        log.error("Bean validation error: ", e);
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        return ResponseEntity.status(e.getStatusCode())
                .body(new ErrorResponse(e.getStatusCode().value(), errors));
    }

    @ExceptionHandler(Exceptions.ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(Exceptions.ResourceNotFoundException e) {
        log.error("Resource not found error: ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, List.of(e.getMessage())));
    }

    @ExceptionHandler(Exceptions.InvalidTokenCredenceException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenCredence(Exceptions.InvalidTokenCredenceException e) {
        log.error("Invalid token credence error: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(e.getMessage())));
    }

    @ExceptionHandler(Exceptions.OutOfStockException.class)
    public ResponseEntity<ErrorResponse> handleOutOfStock(Exceptions.OutOfStockException e) {
        log.error("Out of stock error: ", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(409, List.of(e.getMessage())));
    }
    @ExceptionHandler(Exceptions.InvalidCartItemPrice.class)
    public ResponseEntity<ErrorResponse> handleInvalidCartItemPrice(Exceptions.InvalidCartItemPrice e) {
        log.error("Invalid cart item error: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(e.getMessage())));
    }
    @ExceptionHandler(Exceptions.DuplicateLoginException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateLoginException(Exceptions.DuplicateLoginException e) {
        log.error("Duplicate login error: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(e.getMessage())));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(401, Collections.singletonList("Invalid login or password")));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        log.error("Unexpected error: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500, List.of("An unexpected error occurred")));
    }
}
