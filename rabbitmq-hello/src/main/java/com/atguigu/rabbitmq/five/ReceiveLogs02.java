package com.atguigu.rabbitmq.five;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.UTFDataFormatException;
import java.nio.charset.StandardCharsets;

public class ReceiveLogs02 {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" 接收队列:" + "Q1" + " 绑定键:" + delivery.getEnvelope().getRoutingKey() + ",消息:" + message);
        };
        CancelCallback cancelCallback = (message) -> {
            System.out.println("接受失败：" + message);
        };
        String routeKey = "*.orange.*";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare("Q1", false, false, false, null);
        channel.queueBind("Q1", EXCHANGE_NAME, routeKey);

        System.out.println("等待接受消息。。。。");
        channel.basicConsume("Q1", true, deliverCallback, cancelCallback);
    }
}
