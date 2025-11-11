package com.example.banking.balance;

import com.example.banking.balance.BalanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Balance{
        Long accountId;
        BalanceStatus balanceStatus;
        BalanceCurrencyCode balanceCurrencyCode;
        BigDecimal availableAmount;
        Instant createdAt;
        Instant updatedAt;
}
