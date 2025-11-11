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
        Objects.requireNonNull(accountId, "accountId must not be null");
        Objects.requireNonNull(direction, "direction must not be null");
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(currencyCode, "currencyCode must not be null");
        Objects.requireNonNull(description, "description must not be null");
        Objects.requireNonNull(balanceAfter, "balanceAfter must not be null");
        Objects.requireNonNull(createdAt, "createdAt must not be null");
    }
}
