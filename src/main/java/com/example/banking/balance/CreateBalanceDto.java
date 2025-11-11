package com.example.banking.balance;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateBalanceDto(
        @NotNull Long accountId,
        @NotNull BalanceCurrency currency,
        @NotNull @DecimalMin(value = "0.00") BigDecimal initialAmount
) {}
