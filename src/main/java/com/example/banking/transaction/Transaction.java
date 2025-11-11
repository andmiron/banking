package com.example.banking.transaction;

import com.example.banking.balance.BalanceCurrencyCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long id;
    private Long accountId;
    private Direction direction;
    private BigDecimal amount;
    private BalanceCurrencyCode currencyCode;
    private String description;
    private BigDecimal balanceAfter;
    private Instant createdAt;
}
