package com.example.banking.account;

import com.example.banking.balance.BalanceDto;

import java.util.List;
import java.util.UUID;

public record GetAccountResponse(
        Long accountId,
        UUID customerId,
        List<BalanceDto> balances
) {

}
