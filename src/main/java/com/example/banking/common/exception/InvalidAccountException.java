package com.example.banking.common.exception;

import org.springframework.http.HttpStatus;

public final class InvalidAccountException extends BankingException {

    public InvalidAccountException(Long accountId) {
        super(HttpStatus.BAD_REQUEST, "Invalid account: " + accountId);
    }
}
