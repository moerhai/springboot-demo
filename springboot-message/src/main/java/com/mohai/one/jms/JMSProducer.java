package com.mohai.one.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 23:22
 */
public class JMSProducer {

    public static void main (String[] args) throws Exception {
        // 定义JMS-ActiveMQ连接信息
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616", "username", "password");
        Session session = null;
        Destination sendQueue;
        Connection connection = null;

        //进行连接
        connection = connectionFactory.createConnection();
        connection.start();

        //建立会话
        session = connection.createSession(true, Session.SESSION_TRANSACTED);
        //建立queue（当然如果有了就不会重复建立）
        sendQueue = session.createQueue("");
        //建立消息发送者对象
        MessageProducer sender = session.createProducer(sendQueue);
        TextMessage outMessage = session.createTextMessage();
        outMessage.setText("这是发送的消息内容");

        //发送（JMS是支持事务的）
        sender.send(outMessage);
        session.commit();

        //关闭
        sender.close();
        connection.close();
    }

}