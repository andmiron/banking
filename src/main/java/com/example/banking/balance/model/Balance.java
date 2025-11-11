package com.example.banking.balance.model;

import com.example.banking.account.model.AccountStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record Balance(
        Long accountId,
        AccountStatus accountStatus,
        BalanceCurrency balanceCurrencyCode,
        BigDecimal availableAmount,
        Instant createdAt,
        Instant updatedAt
) {

    public Balance {
        Objects.requireNonNull(accountId, "accountId must not be null");
        Objects.requireNonNull(accountStatus, "accountStatus must not be null");
        Objects.requireNonNull(balanceCurrencyCode, "balanceCurrencyCode must not be null");
        Objects.requireNonNull(availableAmount, "availableAmount must not be null");
        Objects.requireNonNull(createdAt, "createdAt must not be null");
        Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }
}
