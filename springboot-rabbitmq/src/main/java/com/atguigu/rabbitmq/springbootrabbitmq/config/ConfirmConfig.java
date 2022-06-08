package com.atguigu.rabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmConfig {

    public static final String CONFIRM_EXCHANGE="confirmExchange";

    public static final String CONFIRM_QUEUE="confirmQueue";

    public static final String CONFIRM_ROUTING_KEY="confirmRoutingKey";


    @Bean
    public Queue confirmQueue(){
        return  QueueBuilder.durable(CONFIRM_QUEUE).build();
    }
    @Bean
    public Exchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE).build();
    }

    @Bean
    public Binding confirmBingExchange(@Qualifier("confirmQueue") Queue confirmQueue,@Qualifier("confirmExchange") Exchange confirmExchange){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY).noargs();
    }
}
