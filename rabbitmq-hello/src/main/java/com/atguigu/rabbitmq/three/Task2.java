package com.atguigu.rabbitmq.three;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Task2 {
    public static final String TASK_QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        boolean durable=true;
        channel.queueDeclare(TASK_QUEUE_NAME,durable,false,false,null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String messge = scanner.next();
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,messge.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息"+messge);
        }
    }
}
