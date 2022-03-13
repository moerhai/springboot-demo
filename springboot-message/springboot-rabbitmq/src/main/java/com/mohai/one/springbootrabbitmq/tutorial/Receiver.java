package com.mohai.one.springbootrabbitmq.tutorial;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/31 15:40
 */
public class Receiver {

    private final static String HOST_NAME = "localhost";
    private final static String QUEUE_NAME = "demo-queue-mohai";

    public static void main(String[] args) throws Exception {

        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST_NAME);

        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            final String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Received from message from the queue: " + message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }

}