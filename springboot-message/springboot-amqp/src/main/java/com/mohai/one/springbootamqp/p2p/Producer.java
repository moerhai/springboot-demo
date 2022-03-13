package com.mohai.one.springbootamqp.p2p;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/31 03:56
 */
public class Producer {

    private static final String DEST = "com.zzj.dest.01";

    public static void main(String[] args) {
//        ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
//                ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
//        Connection connection = factory.createConnection();
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queue = session.createQueue(DEST);
//        MessageProducer producer = session.createProducer(queue);
//        Message message = session.createTextMessage("Hello world!");
//        producer.send(message);
//        System.out.println("发送成功！");
//        // 关闭资源
//        producer.close();
//        session.close();
//        connection.close();

    }

}
