package com.example.banking.common.exception;

import org.springframework.http.HttpStatus;

public final class AccountNotFoundException extends BankingException {

    public AccountNotFoundException() {
        this(null);
    }

    public AccountNotFoundException(Long accountId) {
        super(HttpStatus.NOT_FOUND, message(accountId));
    }

    private static String message(Long accountId) {
        return accountId == null
                ? "Account not found"
                : "Account not found: " + accountId;
    }
}
