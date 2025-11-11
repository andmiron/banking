package com.example.banking.account;

import com.example.banking.balance.BalanceDto;

import java.util.List;


public record GetAccountDto(
        Long accountId,
        Long customerId,
        List<BalanceDto> balances
) {
}
