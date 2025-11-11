package com.example.banking.account;

import com.example.banking.balance.BalanceDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class AccountEventsPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public AccountEventsPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishAccountCreated(Account account, List<BalanceDto> balances) {
        publish(account, balances, AccountEventType.CREATED);
    }

    public void publishAccountUpdated(Account account, List<BalanceDto> balances) {
        publish(account, balances, AccountEventType.UPDATED);
    }

    private void publish(Account account, List<BalanceDto> balances, AccountEventType type) {
        AccountEvent event = new AccountEvent(
                account.getId(),
                account.getCustomerId(),
                account.getCountry(),
                account.getAccountStatus(),
                balances,
                type,
                Instant.now()
        );
        eventPublisher.publishEvent(event);
    }
}
