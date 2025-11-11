package com.example.banking.balance;

import com.example.banking.account.AccountStatus;

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
        Objects.requireNonNull(accountId, "Balance accountId must not be null");
        Objects.requireNonNull(accountStatus, "Balance accountStatus must not be null");
        Objects.requireNonNull(balanceCurrencyCode, "Balance balanceCurrencyCode must not be null");
        Objects.requireNonNull(availableAmount, "Balance availableAmount must not be null");
        Objects.requireNonNull(createdAt, "Balance createdAt must not be null");
        Objects.requireNonNull(updatedAt, "Balance updatedAt must not be null");
    }
}
