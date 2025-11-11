package com.example.banking.transaction;

import com.example.banking.account.AccountMapper;
import com.example.banking.balance.BalanceMapper;
import com.example.banking.balance.BalanceCurrencyCode;
import com.example.banking.common.exception.AccountMissingException;
import com.example.banking.common.exception.AccountNotFoundException;
import com.example.banking.common.exception.DescriptionMissingException;
import com.example.banking.common.exception.InsufficientFundsException;
import com.example.banking.common.exception.InvalidAmountException;
import com.example.banking.common.exception.InvalidCurrencyException;
import com.example.banking.common.exception.InvalidDirectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class TransactionService {

    private final AccountMapper accountMapper;
    private final BalanceMapper balanceMapper;
    private final TransactionMapper transactionMapper;

    public TransactionService(AccountMapper accountMapper,
                              BalanceMapper balanceMapper,
                              TransactionMapper transactionMapper) {
        this.accountMapper = accountMapper;
        this.balanceMapper = balanceMapper;
        this.transactionMapper = transactionMapper;
    }

    @Transactional
    public GetTransactionDto createTransaction(CreateTransactionDto request) {
        Long accountId = request.accountId();

        BalanceCurrencyCode currency;
        try {
            currency = BalanceCurrencyCode.valueOf(request.currency());
        } catch (IllegalArgumentException ex) {
            throw new InvalidCurrencyException(request.currency());
        }

        Direction direction;
        try {
            direction = Direction.valueOf(request.direction());
        } catch (IllegalArgumentException ex) {
            throw new InvalidDirectionException(request.direction());
        }

        String description = request.description();
        if (!StringUtils.hasText(description)) {
            throw new DescriptionMissingException();
        }

        BigDecimal amount = request.amount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidAmountException(amount);
        }

        var balance = balanceMapper.findByAccountIdAndCurrency(accountId, currency);
        if (balance.isEmpty()) {
            if (accountMapper.findById(accountId).isEmpty()) {
                throw new AccountNotFoundException(accountId);
            }
            throw new InvalidCurrencyException(request.currency());
        }

        BigDecimal newBalance = switch (direction) {
            case IN -> balance.get().getAvailableAmount().add(amount);
            case OUT -> {
                if (balance.get().getAvailableAmount().compareTo(amount) < 0) {
                    throw new InsufficientFundsException(amount, balance.get().getAvailableAmount());
                }
                yield balance.get().getAvailableAmount().subtract(amount);
            }
        };

        balanceMapper.updateAmount(newBalance, accountId, currency);

        Transaction transaction = new Transaction(
                null,
                accountId,
                direction,
                amount,
                currency,
                description.trim(),
                newBalance,
                Instant.now()
        );

        transactionMapper.insert(transaction);

        return new GetTransactionDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getCurrencyCode(),
                transaction.getDirection(),
                transaction.getDescription(),
                transaction.getBalanceAfter()
        );
    }

    @Transactional(readOnly = true)
    public List<GetTransactionDto> getTransactions(Long accountId) {
        if (accountId == null) {
            throw new AccountMissingException();
        }

        if (accountMapper.findById(accountId).isEmpty()) {
            throw new AccountNotFoundException(accountId);
        }

        return transactionMapper.findByAccountId(accountId).stream()
                .map(tx -> new GetTransactionDto(
                        tx.getId(),
                        tx.getAccountId(),
                        tx.getAmount(),
                        tx.getCurrencyCode(),
                        tx.getDirection(),
                        tx.getDescription(),
                        tx.getBalanceAfter()
                ))
                .toList();
    }
}
