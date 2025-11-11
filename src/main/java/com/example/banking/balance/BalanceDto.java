package com.example.banking.balance;

import java.math.BigDecimal;

public record BalanceDto(
        BalanceCurrency currency,
        BigDecimal availableAmount
) {
}
