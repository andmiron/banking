package com.example.banking.common.exception;

import org.springframework.http.HttpStatus;

public final class AccountNotFoundException extends BankingException {

    public AccountNotFoundException(Long accountId) {
        super(HttpStatus.NOT_FOUND, "Account not found: " + accountId);
    }
}
