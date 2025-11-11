package com.example.banking.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BankingExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BankingExceptionHandler.class);

    @ExceptionHandler(BankingException.class)
    public ResponseEntity<ApiError> handleBankingException(BankingException ex) {
        log.warn("Domain error: {}", ex.getMessage());
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex) {
        log.error("Unhandled error", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("Unexpected error. Please try again later."));
    }

    public record ApiError(String message) {
    }
}
