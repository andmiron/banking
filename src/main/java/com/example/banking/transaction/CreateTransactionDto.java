package com.example.banking.transaction;

import com.example.banking.balance.BalanceCurrency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransactionDto(
        @NotNull Long accountId,
        @NotNull @DecimalMin(value = "0.00", inclusive = false) BigDecimal amount,
        @NotNull BalanceCurrency currency,
        @NotNull Direction direction,
        @NotBlank String description
) {
}
