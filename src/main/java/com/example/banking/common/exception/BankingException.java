package com.example.banking.common.exception;

import org.springframework.http.HttpStatus;

public abstract class BankingException extends RuntimeException {

    private final HttpStatus status;

    protected BankingException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    protected BankingException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
