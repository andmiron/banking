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
        AccountEvent event = new AccountEvent(
                account.getId(),
                account.getCustomerId(),
                account.getCountry(),
                account.getAccountStatus(),
                balances,
                AccountEventType.CREATED,
                Instant.now()
        );
        eventPublisher.publishEvent(event);
    }
}
