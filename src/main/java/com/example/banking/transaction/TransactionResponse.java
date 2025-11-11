package com.example.banking.transaction;

import com.example.banking.balance.BalanceCurrency;

import java.math.BigDecimal;

public record TransactionResponse(
        Long transactionId,
        Long accountId,
        BigDecimal amount,
        BalanceCurrency currency,
        Direction direction,
        String description,
        BigDecimal balanceAfter
) {
}
