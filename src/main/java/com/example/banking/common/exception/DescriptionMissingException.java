package com.example.banking.common.exception;

import org.springframework.http.HttpStatus;

public final class DescriptionMissingException extends BankingException {

    public DescriptionMissingException() {
        super(HttpStatus.BAD_REQUEST, "Description missing");
    }
}
