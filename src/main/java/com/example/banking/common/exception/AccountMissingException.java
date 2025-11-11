package com.example.banking.common.exception;

import org.springframework.http.HttpStatus;

public final class AccountMissingException extends BankingException {

    public AccountMissingException() {
        super(HttpStatus.BAD_REQUEST, "Account missing");
    }
}
