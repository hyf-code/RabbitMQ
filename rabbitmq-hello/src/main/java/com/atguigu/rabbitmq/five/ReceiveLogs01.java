package com.atguigu.rabbitmq.five;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class ReceiveLogs01 {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" 接收队列:" + "Q2" + " 绑定键:" + delivery.getEnvelope().getRoutingKey() + ",消息:" + message);
        };
        CancelCallback cancelCallback = (message) -> {
            System.out.println("接受失败：" + message);
        };
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare("Q2", false, false, false, null);

        String routeKey = "*.*.rabbit";
        String routeKey2 = "lazy.#";
        channel.queueBind("Q2", EXCHANGE_NAME, routeKey);
        channel.queueBind("Q2", EXCHANGE_NAME, routeKey2);

        System.out.println("等待接受消息。。。。");
        channel.basicConsume("Q2", true, deliverCallback, cancelCallback);
    }
}
