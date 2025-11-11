package com.example.banking.transaction;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public GetTransactionDto createTransaction(@Valid @RequestBody CreateTransactionDto request) {
        return transactionService.createTransaction(request);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public List<GetTransactionDto> getTransactions(@PathVariable Long accountId) {
        return transactionService.getTransactions(accountId);
    }
}
