package com.mohai.one.springbootamqp.p2p;//package com.mohai.one.springbootjms.p2p;
//
///**
// * @Auther: moerhai@qq.com
// * @Date: 2020/10/31 03:58
// */
//public class AsynCustomer implements MessageListener{
//    private static final String DEST = "com.zzj.dest.01";
//
//    private static Connection connection;
//    private static Session session;
//    private static MessageConsumer consumer;
//
//    public static void main(String[] args) throws Exception {
//        ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
//                ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
//        connection = factory.createConnection();
//        connection.start();
//        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queue = session.createQueue(DEST);
//        consumer = session.createConsumer(queue);
//        consumer.setMessageListener(new AsynCustomer());
//    }
//
//    @Override
//    public void onMessage(Message message) {
//        TextMessage tm = (TextMessage) message;
//        try {
//            System.out.println(tm.getText());
//        } catch (JMSException e) {
//            e.printStackTrace();
//            close(); // 关闭资源
//        }
//    }
//
//    /**
//     * 关闭资源
//     */
//    private static void close(){
//        try {
//            consumer.close();
//        } catch (JMSException e1) {
//            e1.printStackTrace();
//        }
//        try {
//            session.close();
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//        try {
//            connection.close();
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
