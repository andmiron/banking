package com.example.banking.account;

import com.example.banking.common.AccountEventsProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AccountEventsListener {

    private final RabbitTemplate rabbitTemplate;
    private final AccountEventsProperties properties;

    public AccountEventsListener(RabbitTemplate rabbitTemplate, AccountEventsProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAccountEvent(AccountEvent event) {
        String routingKey = switch (event.type()) {
            case CREATED -> properties.routingKey().created();
            case UPDATED -> properties.routingKey().updated();
        };

        rabbitTemplate.convertAndSend(
                properties.exchange(),
                routingKey,
                event
        );
    }
}
