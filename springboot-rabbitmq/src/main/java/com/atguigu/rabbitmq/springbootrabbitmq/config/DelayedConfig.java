package com.atguigu.rabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedConfig {


    public static final String DELAYED_QUEUE="delayedQueue";

    public static final String DELAYED_EXCHANGE="delayedExchange";

    public static final String DELAYED_ROUTING_KEY="delayedRoutingKey";


    @Bean
    public CustomExchange delayedExchange(){
        Map<String, Object> array = new HashMap<>();
        array.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false,array);
    }

    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYED_QUEUE);
    }

    @Bean
    public Binding delayedBingExchange(@Qualifier("delayedQueue") Queue delayedQueue,
                                       @Qualifier("delayedExchange") Exchange delayedExchange){
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

}
