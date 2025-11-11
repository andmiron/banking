package com.example.banking.transaction;

import com.example.banking.balance.BalanceCurrencyCode;
import jakarta.validation.constraints.DecimalMin;
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
