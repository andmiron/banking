package com.example.banking.common.exception;

import org.springframework.http.HttpStatus;

public final class InvalidDirectionException extends BankingException {

    public InvalidDirectionException(String direction) {
        super(HttpStatus.BAD_REQUEST, "Invalid direction: " + direction);
    }
}
