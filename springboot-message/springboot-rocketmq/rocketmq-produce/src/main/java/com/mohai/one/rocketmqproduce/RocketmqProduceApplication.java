package com.mohai.one.rocketmqproduce;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class RocketmqProduceApplication implements CommandLineRunner {

	@Autowired
	private RocketMQTemplate rocketMQTemplate;

//	@Value("${demo.rocketmq.transTopic}")
//	private String springTransTopic;
//	@Value("${demo.rocketmq.topic}")
//	private String springTopic;
//	@Value("${demo.rocketmq.stringRequestTopic}")
//	private String stringRequestTopic;

	@Value("${mohai.rocketmq.topic}")
	private String topic;
	@Value("${mohai.rocketmq.msgExtTopic}")
	private String msgExtTopic;

//	@Resource(name = "extRocketMQTemplate")
//	private RocketMQTemplate extRocketMQTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RocketmqProduceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

        // 发送消息
		rocketMQTemplate.send(topic, MessageBuilder.withPayload("Hello World! I'm from spring message").build());

        //发送OneWay消息，只管发送，不管发送的结果
		rocketMQTemplate.sendOneWay(topic,"Hello, a OneWay message!");

        // 同步发送消息，等待收到反馈结果以后才会结束
		SendResult sendResult = rocketMQTemplate.syncSend(topic, "Hello, The synchronized message!");
		System.out.printf("同步发送到 topic %s 发送结果为: %s %n", topic, sendResult);

		// 使用标签发送消息
		rocketMQTemplate.convertAndSend(msgExtTopic + ":tag0", "I'm from tag0");
		System.out.printf("发送带标签的消息到 topic %s tag %s %n", msgExtTopic, "tag0");
		// 发送带标签的消息
		rocketMQTemplate.convertAndSend(topic + ":tag0", "Hello, World!");

		// 异步发送消息，发送即结束，但会回调处理消息发送的结果
		// 第一个参数是发送的目的地，一般是topic，也可以是topic:tag；
		// 第二个参数是消息内容；
		// 第三个参数是异步消息发送结果的回调；
		rocketMQTemplate.asyncSend(topic, "Hello, The asynchronized message!", new SendCallback() {
			@Override
			public void onSuccess(SendResult sendResult) {
				System.out.println("异步消息发送成功，发送结果："+sendResult);
			}
			@Override
			public void onException(Throwable throwable) {
				System.out.println("异步消息发送失败，消息回调");
			}
		});

		// 以异步模式发送请求并接收字符串类型的答复。
		rocketMQTemplate.sendAndReceive("stringRequestTopic", "request string", new RocketMQLocalRequestCallback<String>() {
			@Override public void onSuccess(String message) {
				System.out.printf("发送： %s and 接收： %s %n", "request string", message);
			}
			@Override public void onException(Throwable e) {
				e.printStackTrace();
			}
		});

        // 发送事务消息
		Message msg = MessageBuilder.withPayload("rocketMQTemplate transactional message ").
				setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + 0).build();
		sendResult = rocketMQTemplate.sendMessageInTransaction("spring-transaction-topic",msg,null);
		System.out.printf("rocketMQTemplate send Transactional msg body = %s , sendResult=%s %n",
				msg.getPayload(), sendResult.getSendStatus());


//		// Send a batch of strings
//		testBatchMessages();
//
//		// Send transactional messages using rocketMQTemplate
//		testRocketMQTemplateTransaction();
//


		// Use the extRocketMQTemplate
//		SendResult sendResult = extRocketMQTemplate.syncSend(springTopic, MessageBuilder.withPayload("Hello, World!2222".getBytes()).build());
//		System.out.printf("extRocketMQTemplate.syncSend1 to topic %s sendResult=%s %n", springTopic, sendResult);

	}

//	private void testExtRocketMQTemplateTransaction() throws MessagingException {
//		for (int i = 0; i < 10; i++) {
//			try {
//				Message msg = MessageBuilder.withPayload("extRocketMQTemplate transactional message " + i).
//						setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + i).build();
//				SendResult sendResult = extRocketMQTemplate.sendMessageInTransaction(
//						springTransTopic, msg, null);
//				System.out.printf("------ExtRocketMQTemplate send Transactional msg body = %s , sendResult=%s %n",
//						msg.getPayload(), sendResult.getSendStatus());
//
//				Thread.sleep(10);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

//	private void testBatchMessages() {
//		List<Message> msgs = new ArrayList<Message>();
//		for (int i = 0; i < 10; i++) {
//			msgs.add(MessageBuilder.withPayload("Hello RocketMQ Batch Msg#" + i).
//					setHeader(RocketMQHeaders.KEYS, "KEY_" + i).build());
//		}
//
//		SendResult sr = rocketMQTemplate.syncSend(springTopic, msgs, 60000);
//
//		System.out.printf("--- Batch messages send result :" + sr);
//	}

//	private void testRocketMQTemplateTransaction() throws MessagingException {
//		String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
//		for (int i = 0; i < 10; i++) {
//			try {
//
//				Message msg = MessageBuilder.withPayload("rocketMQTemplate transactional message " + i).
//						setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + i).build();
//				SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(
//						springTransTopic + ":" + tags[i % tags.length], msg, null);
//				System.out.printf("------rocketMQTemplate send Transactional msg body = %s , sendResult=%s %n",
//						msg.getPayload(), sendResult.getSendStatus());
//
//				Thread.sleep(10);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}



	// 使用注解@RocketMQTransactionListener定义事务侦听器
	@RocketMQTransactionListener
	class TransactionListenerImpl implements RocketMQLocalTransactionListener {

		private AtomicInteger transactionIndex = new AtomicInteger(0);

		private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<String, Integer>();

		@Override
		public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
			// 实现执行本地事务的逻辑,并返回本地事务执行状态,可以返回:COMMIT,ROLLBACK,UNKNOWN三种状态
			// 获取事务Id
			String transId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
			System.out.printf("本地事务正在执行, 当前事务Id=%s %n",transId);
			int value = transactionIndex.getAndIncrement();
			int status = value % 3;
			localTrans.put(transId, status);
			if (status == 0) {
				// Return local transaction with success(commit), in this case,
				// this message will not be checked in checkLocalTransaction()
				System.out.printf("# COMMIT # 模拟消息为: %s , 相关本地事务执行成功! %n", msg.getPayload());
				return RocketMQLocalTransactionState.COMMIT;
			}

			if (status == 1) {
				// Return local transaction with failure(rollback) , in this case,
				// this message will not be checked in checkLocalTransaction()
				System.out.printf("# ROLLBACK # 模拟 %s , 相关本地事务执行失败! %n", msg.getPayload());
				return RocketMQLocalTransactionState.ROLLBACK;
			}

			System.out.printf("# UNKNOW # 模拟 %s 相关本地事务执行未知! \n");
			return RocketMQLocalTransactionState.UNKNOWN;
		}

		@Override
		public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
			// 检查本地事务状态,并返回:COMMIT,ROLLBACK,UNKNOWN三种状态
			String transId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
			RocketMQLocalTransactionState retState = RocketMQLocalTransactionState.COMMIT;
			Integer status = localTrans.get(transId);
			if (null != status) {
				switch (status) {
					case 0:
						retState = RocketMQLocalTransactionState.COMMIT;
						break;
					case 1:
						retState = RocketMQLocalTransactionState.ROLLBACK;
						break;
					case 2:
						retState = RocketMQLocalTransactionState.UNKNOWN;
						break;
				}
			}
			System.out.printf("checkLocalTransaction执行一次," +
							" msgTransactionId=%s, TransactionState=%s status=%s %n",
					transId, retState, status);
			return retState;
		}
	}

//	@RocketMQTransactionListener(rocketMQTemplateBeanName = "extRocketMQTemplate")
//	class ExtTransactionListenerImpl implements RocketMQLocalTransactionListener {
//		@Override
//		public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//			System.out.printf("ExtTransactionListenerImpl executeLocalTransaction and return UNKNOWN. \n");
//			return RocketMQLocalTransactionState.UNKNOWN;
//		}
//
//		@Override
//		public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
//			System.out.printf("ExtTransactionListenerImpl checkLocalTransaction and return COMMIT. \n");
//			return RocketMQLocalTransactionState.COMMIT;
//		}
//	}

}