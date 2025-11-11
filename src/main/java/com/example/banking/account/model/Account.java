package com.example.banking.account.model;

import java.time.Instant;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public record Account(
        Long id,
        UUID customerId,
        AccountStatus accountStatus,
        String country,
        Instant createdAt
) {
    private static final Set<String> VALID_COUNTRIES = Set.copyOf(Arrays.asList(Locale.getISOCountries()));

    public Account {
        Objects.requireNonNull(customerId, "Account customerId must not be null");
        Objects.requireNonNull(accountStatus, "Account status must not be null");
        Objects.requireNonNull(country, "Account country must not be null");
        Objects.requireNonNull(createdAt, "Account createdAt must not be null");
        if (!VALID_COUNTRIES.contains(country)) {
            throw new IllegalArgumentException("Invalid country code: " + country + ". Must be a valid ISO 3166-1 alpha-2 code.");
        }
    }
}
