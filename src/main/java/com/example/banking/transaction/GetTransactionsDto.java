package com.example.banking.transaction;

import java.util.List;

public record GetTransactionsDto(
        Long accountId,
        List<GetTransactionDto> transactions
) {}
