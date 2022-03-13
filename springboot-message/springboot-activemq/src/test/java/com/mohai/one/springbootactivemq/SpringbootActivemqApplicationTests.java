package com.mohai.one.springbootactivemq;

import com.mohai.one.springbootactivemq.sender.ActiveMQSender;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jms.*;

@SpringBootTest
class SpringbootActivemqApplicationTests {

    @Autowired
    private ActiveMQSender activeMQSender;

    @Test
    void contextLoads() {
        activeMQSender.sendQueue("hello,mohai");
    }

    @Test
    void testQueueProducer() throws Exception {
        //1、创建工厂连接对象，指定brokerURL
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2、创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、打开连接
        connection.start();
        //4、创建会话对象
        //第一个参数：不使用本地事务
        //第二个参数：自动应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、创建目标对象，包含queue和topic（一对一和一对多）
        //设置队列名称，消费者根据队列名称接收数据
        Queue queue = session.createQueue("test-queue");
        //6、创建生产者对象
        MessageProducer producer = session.createProducer(queue);
        //7、创建一个文本消息对象
        TextMessage textMessage = session.createTextMessage("hello,This is test-queue!");
        //8、发送消息
        producer.send(textMessage);
        //9、关闭消息生产者
        producer.close();
        //10、关闭会话
        session.close();
        //11、关闭连接
        connection.close();
    }

    @Test
    void testQueueConsumer() throws Exception {
        //1、创建工厂连接对象，指定brokerURL
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2、创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、打开连接
        connection.start();
        //4、创建会话对象
        //第一个参数：不使用本地事务
        //第二个参数：自动应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、创建目标对象，包含queue和topic（一对一和一对多）
        //设置队列名称，消费者根据队列名称接收数据
        Queue queue = session.createQueue("test-queue");
        //6、创建消费者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7、向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //8、获取消息并打印
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
    }

    @Test
    void testQueueConsumer2() throws Exception {
        //1、创建工厂连接对象，指定brokerURL
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2、创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、打开连接
        connection.start();
        //4、创建会话对象
        //第一个参数：不使用本地事务
        //第二个参数：自动应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、创建目标对象，包含queue和topic（一对一和一对多）
        //设置队列名称，消费者根据队列名称接收数据
        Queue queue = session.createQueue("test-queue");
        //6、创建消费者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7、向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //8、获取消息并打印
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
    }

    @Test
    void testTopicProducer() throws Exception {
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Topic topic = session.createTopic("test-topic");
        //6、使用会话对象创建生产者对象
        MessageProducer producer = session.createProducer(topic);
        //7、使用会话对象创建一个消息对象
        TextMessage textMessage = session.createTextMessage("hello, test-topic!");
        //8、发送消息
        producer.send(textMessage);
        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    void testTopicConsumer() throws Exception {
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Topic topic = session.createTopic("test-topic");
        //6、使用会话对象创建生产者对象
        MessageConsumer consumer = session.createConsumer(topic);
        //7、向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //8、程序等待接收用户消息
        System.in.read();
        //9、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    void testTopicConsumer2() throws Exception {
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Topic topic = session.createTopic("test-topic");
        //6、使用会话对象创建生产者对象
        MessageConsumer consumer = session.createConsumer(topic);
        //7、向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //8、程序等待接收用户消息
        System.in.read();
        //9、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}