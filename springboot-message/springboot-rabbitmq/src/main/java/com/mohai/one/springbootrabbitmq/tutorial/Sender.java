package com.mohai.one.springbootrabbitmq.tutorial;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/31 15:39
 */
public class Sender {

    private final static String HOST_NAME = "localhost";
    private final static String QUEUE_NAME = "demo-queue-mohai";

    public static void main(String[] args) throws Exception {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST_NAME);
        try (final Connection connection = connectionFactory.newConnection();
             final Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            for (int i = 0; i < 10; i++) {
                final String message = "Hello world! The Num=" + i;
                System.out.println("Sending the following message to the queue: " + message);
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            }
        }
    }
}