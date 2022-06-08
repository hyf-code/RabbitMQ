package com.atguigu.rabbitmq.springbootrabbitmq.consumer;


import com.atguigu.rabbitmq.springbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfirmQueueComsumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE)
    public void confirmReciverMessage(Message message){
        String msg = new String(message.getBody());
        log.info("接收到的消息是{}", msg);
    }
}
