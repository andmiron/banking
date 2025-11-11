package com.example.banking.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "banking.events.account")
public record AccountEventsProperties(
        String exchange,
        String queue,
        RoutingKeys routingKey
) {
    public record RoutingKeys(String created, String updated) {
    }
}
