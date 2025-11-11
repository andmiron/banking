package com.example.banking.account;

import java.time.Instant;
import java.util.UUID;

public record AccountDto(
        Long id,
        UUID customerId,
        AccountStatus status,
        String country,
        Instant createdAt
) {
}