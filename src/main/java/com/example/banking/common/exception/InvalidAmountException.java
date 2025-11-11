package com.example.banking.common.exception;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public final class InvalidAmountException extends BankingException {

    public InvalidAmountException(BigDecimal amount) {
        super(HttpStatus.BAD_REQUEST, "Invalid amount: " + amount);
    }
}
