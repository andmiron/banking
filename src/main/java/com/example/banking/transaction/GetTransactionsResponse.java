package com.example.banking.transaction;

import java.util.List;

public record GetTransactionsResponse(
        Long accountId,
        List<TransactionResponse> transactions
) {}
