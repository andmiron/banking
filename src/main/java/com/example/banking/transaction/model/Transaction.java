package com.example.banking.transaction.model;

import com.example.banking.balance.model.BalanceCurrency;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record Transaction(
        Long id,
        Long accountId,
        Direction direction,
        BigDecimal amount,
        BalanceCurrency currencyCode,
        String description,
        BigDecimal balanceAfter,
        Instant createdAt
) {
    public Transaction {
        Objects.requireNonNull(accountId, "Transaction accountId must not be null");
        Objects.requireNonNull(direction, "Transaction direction must not be null");
        Objects.requireNonNull(amount, "Transaction amount must not be null");
        Objects.requireNonNull(currencyCode, "Transaction currencyCode must not be null");
        Objects.requireNonNull(description, "Transaction description must not be null");
        Objects.requireNonNull(balanceAfter, "Transaction balanceAfter must not be null");
        Objects.requireNonNull(createdAt, "Transaction createdAt must not be null");
    }
}
