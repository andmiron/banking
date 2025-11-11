package com.example.banking.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AccountEventsProperties.class)
public class RabbitConfig {

    @Bean
    public TopicExchange accountExchange(AccountEventsProperties properties) {
        return new TopicExchange(properties.exchange(), true, false);
    }

    @Bean
    public Queue accountQueue(AccountEventsProperties properties) {
        return new Queue(properties.queue(), true);
    }

    @Bean
    public Binding accountCreatedBinding(Queue accountQueue,
                                         TopicExchange accountExchange,
                                         AccountEventsProperties properties) {
        return BindingBuilder
                .bind(accountQueue)
                .to(accountExchange)
                .with(properties.routingKey().created());
    }

    @Bean
    public Binding accountUpdatedBinding(Queue accountQueue,
                                         TopicExchange accountExchange,
                                         AccountEventsProperties properties) {
        return BindingBuilder
                .bind(accountQueue)
                .to(accountExchange)
                .with(properties.routingKey().updated());
    }

    @Bean
    public MessageConverter jacksonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
