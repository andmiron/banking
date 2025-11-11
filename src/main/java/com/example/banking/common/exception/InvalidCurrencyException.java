package com.example.banking.common.exception;

import org.springframework.http.HttpStatus;

public final class InvalidCurrencyException extends BankingException {

    public InvalidCurrencyException(String currency) {
        super(HttpStatus.BAD_REQUEST, "Invalid currency: " + currency);
    }
}
