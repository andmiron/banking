package com.example.banking.account;

import com.example.banking.balance.BalanceDto;

import java.time.Instant;
import java.util.List;

public record AccountEvent(
        Long accountId,
        Long customerId,
        String country,
        AccountStatus status,
        List<BalanceDto> balances,
        AccountEventType type,
        Instant occurredAt
) {
}
