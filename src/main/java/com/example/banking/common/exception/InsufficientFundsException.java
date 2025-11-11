package com.example.banking.common.exception;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public final class InsufficientFundsException extends BankingException {

    public InsufficientFundsException(BigDecimal requested, BigDecimal available) {
        super(
                HttpStatus.BAD_REQUEST,
                "Insufficient funds: requested " + requested + " but available " + available
        );
    }
}
