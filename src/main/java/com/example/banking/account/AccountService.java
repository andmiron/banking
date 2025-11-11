package com.example.banking.account;

import com.example.banking.balance.Balance;
import com.example.banking.balance.BalanceDto;
import com.example.banking.balance.BalanceMapper;
import com.example.banking.balance.BalanceCurrencyCode;
import com.example.banking.common.exception.AccountNotFoundException;
import com.example.banking.common.exception.InvalidCurrencyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class AccountService {

    private final AccountMapper accountMapper;
    private final BalanceMapper balanceMapper;

    public AccountService(AccountMapper accountMapper, BalanceMapper balanceMapper) {
        this.accountMapper = accountMapper;
        this.balanceMapper = balanceMapper;
    }

    @Transactional
    public GetAccountDto createAccount(CreateAccountDto request) {
        Long customerId = request.customerId();
        String country = request.country();
        Instant now = Instant.now();

        Account account = new Account(null, customerId, AccountStatus.ACTIVE, country, now);
        accountMapper.insert(account);

        Long accountId = account.getId();

        List<BalanceCurrencyCode> currencies = request.currencies().stream()
                .map(this::parseCurrency)
                .toList();

        List<Balance> balances = currencies.stream()
                .distinct()
                .map(currency -> new Balance(accountId, AccountStatus.ACTIVE, currency, BigDecimal.ZERO, now, now))
                .toList();

        balances.forEach(balanceMapper::insert);

        return new GetAccountDto(
                accountId,
                account.getCustomerId(),
                balances.stream()
                        .map(balance -> new BalanceDto(balance.getBalanceCurrencyCode(), balance.getAvailableAmount()))
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public GetAccountDto getAccount(Long accountId) {
        Account account = accountMapper.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        List<BalanceDto> balances = balanceMapper.findByAccountId(accountId).stream()
                .map(balance -> new BalanceDto(balance.getBalanceCurrencyCode(), balance.getAvailableAmount()))
                .toList();

        return new GetAccountDto(account.getId(), account.getCustomerId(), balances);
    }

    private BalanceCurrencyCode parseCurrency(String value) {
        if (value == null) {
            throw new InvalidCurrencyException("null");
        }
        try {
            return BalanceCurrencyCode.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidCurrencyException(value);
        }
    }
}
