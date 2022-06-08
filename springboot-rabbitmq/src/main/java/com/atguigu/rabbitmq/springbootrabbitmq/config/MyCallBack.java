package com.atguigu.rabbitmq.springbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String reason) {
        String id = correlationData.getId() != null ? correlationData.getId() : "";
        if (ack){
            log.info("收到确认回复了,id={}",id);
        }else {
            log.info("没有收到确认回复,id={},原因={}",id,reason);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息{}未被队列接收，错误码{}，错误原因{}，交换机{},routingKey{}",
                new String(returnedMessage.getMessage().getBody()),
                returnedMessage.getReplyCode(),
                returnedMessage.getReplyText(),
                returnedMessage.getExchange(),
                returnedMessage.getRoutingKey());
    }
}
