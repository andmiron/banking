package com.example.banking.transaction;

import com.example.banking.balance.BalanceCurrencyCode;

import java.math.BigDecimal;

public record GetTransactionDto(
        Long transactionId,
        Long accountId,
        BigDecimal amount,
        BalanceCurrencyCode currency,
        Direction direction,
        String description,
        BigDecimal balanceAfter
) {
}
