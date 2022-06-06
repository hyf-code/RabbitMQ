package com.atguigu.rabbitmq.springbootrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/ttl")
@Slf4j
public class SendMessageController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //开始发消息
    @GetMapping("/sendMsg/{message}")
    public void sendMessage(@PathVariable String message){
        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自 ttl 为 10S 的队列: " + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自 ttl 为 40S 的队列: " + message);
    }


    //开始发消息
    @GetMapping("/sendMsg/{message}/{ttlTime}")
    public void sendMessage(@PathVariable String message,@PathVariable String ttlTime){
        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        //rabbitTemplate.convertAndSend("X", "XA", "消息来自 ttl 为 10S 的队列: " + message);
        //rabbitTemplate.convertAndSend("X", "XB", "消息来自 ttl 为 40S 的队列: " + message);
        rabbitTemplate.convertAndSend("X", "XC", "消息来自 ttl 为 "+ttlTime+" S的队列: " + message,
                message1 -> {
                    message1.getMessageProperties().setExpiration(ttlTime);
                    return message1;
                }
        );

        rabbitTemplate.convertAndSend("X", "XC", "消息来自 ttl 为 "+1000+" ms的队列: " + message,
                message1 -> {
                    message1.getMessageProperties().setExpiration("1000");
                    return message1;
                }
        );

        rabbitTemplate.convertAndSend("X", "XC", "消息来自 ttl 为 "+3000+" ms的队列: " + message,
                message1 -> {
                    message1.getMessageProperties().setExpiration("3000");
                    return message1;
                }
        );

        rabbitTemplate.convertAndSend("X", "XC", "消息来自 ttl 为 "+60000+" ms的队列: " + message,
                message1 -> {
                    message1.getMessageProperties().setExpiration("60000");
                    return message1;
                }
        );
    }



    public static void main(String[] args) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
    }
}
