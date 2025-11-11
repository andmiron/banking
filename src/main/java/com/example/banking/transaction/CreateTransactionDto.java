package com.example.banking.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransactionDto(
        @NotNull Long accountId,
        @NotNull BigDecimal amount,
        @NotNull String currency,
        @NotNull String direction,
        @NotBlank String description
) {
}
