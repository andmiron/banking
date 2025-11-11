package com.example.banking.common.exception;

import org.springframework.http.HttpStatus;

public final class AccountNotFound extends BankingException {

    public AccountNotFound() {
        super(HttpStatus.BAD_REQUEST, "Account not found");
    }
}
