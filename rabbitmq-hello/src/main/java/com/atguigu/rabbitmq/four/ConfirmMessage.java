package com.atguigu.rabbitmq.four;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ConfirmMessage {
    public static final String QUEUE_NAME = "hello";

    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception {
        publishMessageAsynBatch();
    }

    public static void publishMessageIndividually() throws Exception {
        String queue_name = UUID.randomUUID().toString();


        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare("queue_name", true, false, false, null);

        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            boolean flag = channel.waitForConfirms();
            if (flag == true) {
                System.out.println("消息发送成功");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "个单独确认消息,耗时" + (end - begin) + "ms");
    }


    public static void publishMessageBatch() throws Exception {
        String queue_name = UUID.randomUUID().toString();
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare("queue_name", true, false, false, null);

        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        int batchSize = 100;
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            if (i % batchSize == 0) {
                boolean flag = channel.waitForConfirms();
                if (flag == true) {
                    System.out.println("消息发送成功");
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "个单独确认消息,耗时" + (end - begin) + "ms");
    }


    public static void publishMessageAsynBatch() throws Exception {
        String queue_name = UUID.randomUUID().toString();
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare("queue_name", true, false, false, null);

        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        int batchSize = 100;
        //消息成功回调函数
        ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
            System.out.println("确认的消息" + deliveryTag);
        };
        //消息确认失败回调函数
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            System.out.println("未确认的消息" + deliveryTag);
        };
        channel.addConfirmListener(ackCallback, nackCallback);
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "个单独确认消息,耗时" + (end - begin) + "ms");
    }

}
