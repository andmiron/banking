package com.example.banking.account.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Account(
        Long id,
        UUID customerId,
        AccountStatus accountStatus,
        String country,
        Instant createdAt
) {

    public Account {
        Objects.requireNonNull(customerId, "Account customerId must not be null");
        Objects.requireNonNull(accountStatus, "Account status must not be null");
        Objects.requireNonNull(country, "Account country must not be null");
        Objects.requireNonNull(createdAt, "Account createdAt must not be null");
    }
}
