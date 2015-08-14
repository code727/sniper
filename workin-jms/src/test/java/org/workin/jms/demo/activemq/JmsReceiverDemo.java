package org.workin.jms.demo.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class JmsReceiverDemo {
	public static void main(String[] args) {
		// 产生连接工厂
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61617");
		Connection conn = null;
		Session session = null;
		MessageConsumer consumer = null;
		try {
			// 产生连接
			conn = cf.createConnection();
			conn.start();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// 设置消息目的地类型
			Destination destination = new ActiveMQQueue("daniele");
			consumer = session.createConsumer(destination);
			session.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println(textMessage.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (consumer != null)
//					consumer.close();
//				if (session != null) {
//					session.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (JMSException ex) {
//				ex.printStackTrace();
//			}
		}
	}
}