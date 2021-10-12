package com.atguigu.rabbitmq.two;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Worker01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接受到的消息：" + new String(message.getBody()));
        };
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println(consumerTag + "   消息中断");
        };
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C3 消费者启动等待消费.................. ");

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
