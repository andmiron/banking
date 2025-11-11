package com.example.banking.balance;

import java.math.BigDecimal;

public record BalanceDto(
        BalanceCurrencyCode currency,
        BigDecimal availableAmount
) {
}
