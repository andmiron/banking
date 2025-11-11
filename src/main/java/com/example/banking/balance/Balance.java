package com.example.banking.balance;

import com.example.banking.account.AccountStatus;
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
        AccountStatus accountStatus;
        BalanceCurrencyCode balanceCurrencyCode;
        BigDecimal availableAmount;
        Instant createdAt;
        Instant updatedAt;
}
