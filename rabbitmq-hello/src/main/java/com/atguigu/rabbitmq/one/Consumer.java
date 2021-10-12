package com.atguigu.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.1.83.22");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection = factory.newConnection();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        };
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息中断");
        };
        Channel channel = connection.createChannel();
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
