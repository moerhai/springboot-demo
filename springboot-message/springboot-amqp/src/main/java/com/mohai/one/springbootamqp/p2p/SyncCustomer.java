package com.mohai.one.springbootamqp.p2p;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/31 03:57
 */
public class SyncCustomer {
    private static final String DEST = "com.zzj.dest.01";

    public static void main(String[] args) {
//        ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
//                ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
//        Connection connection = factory.createConnection();
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queue = session.createQueue(DEST);
//        MessageConsumer consumer = session.createConsumer(queue);
//        TextMessage message = (TextMessage) consumer.receive(); // 阻塞方法，直到接收到消息
//        System.out.println(message.getText());
//        // 关闭资源
//        consumer.close();
//        session.close();
//        connection.close();


    }

}
