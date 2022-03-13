package com.mohai.one.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * https://blog.csdn.net/noaman_wgs/article/details/78443560
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 23:36
 */
public class RabbitMQProducer {

    private static final String QUEUE_NAME = "RABBITMQ_HELLO";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //在本地机器创建socket连接
        connectionFactory.setHost("localhost");
        //建立socket连接
        Connection connection = connectionFactory.newConnection();

        //创建Channel，含有处理信息的大部分API
        Channel channel = connection.createChannel();
        //声明一个Queue，用来存放消息
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //消息内容
        String message = "hello, little qute rabbitmq!";
        //发布消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        //发布消息成功提示信息
        System.out.println("RABBITMQ客户端成功发送信息：" +  message);

        //关闭连接
        channel.close();
        connection.close();
    }

}
