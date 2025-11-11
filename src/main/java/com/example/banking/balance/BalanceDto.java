package com.example.banking.balance;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record BalanceDto(
        BalanceCurrencyCode currency,
        BigDecimal availableAmount
) {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.UNNECESSARY;

    public BalanceDto {
        if (availableAmount != null) {
            availableAmount = availableAmount.setScale(SCALE, ROUNDING_MODE);
        }
    }
}
