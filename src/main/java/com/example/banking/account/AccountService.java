package com.example.banking.account;

import com.example.banking.balance.Balance;
import com.example.banking.balance.BalanceDto;
import com.example.banking.balance.BalanceMapper;
import com.example.banking.common.exception.AccountNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
        UUID customerId = request.customerId();
        String country = request.country();
        Instant now = Instant.now();

        Account account = new Account(null, customerId, AccountStatus.ACTIVE, country, now);
        accountMapper.insert(account);

        Long accountId = account.id();
        List<Balance> balances = request.currencies().stream()
                .distinct()
                .map(currency -> new Balance(accountId, AccountStatus.ACTIVE, currency, BigDecimal.ZERO, now, now))
                .toList();

        balances.forEach(balanceMapper::insert);

        return new GetAccountDto(
                accountId,
                customerId,
                balances.stream()
                        .map(b -> new BalanceDto(b.balanceCurrencyCode(), b.availableAmount()))
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public GetAccountDto getAccount(Long accountId) {
        Account account = accountMapper.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        List<BalanceDto> balances = balanceMapper.findByAccountId(accountId).stream()
                .map(balance -> new BalanceDto(balance.balanceCurrencyCode(), balance.availableAmount()))
                .toList();

        return new GetAccountDto(account.id(), account.customerId(), balances);
    }
}
