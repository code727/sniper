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
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection conn = null;
		Session session = null;
		MessageConsumer consumer = null;
		try {
			// 产生连接
			conn = cf.createConnection();
			conn.start();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
			// 设置消息目的地类型
			
			Destination destination = new ActiveMQQueue("A");
			consumer = session.createConsumer(destination);
			Message message = consumer.receive();
			TextMessage textMessage = (TextMessage) message;
			System.out.println(textMessage.getText());
			
			destination = new ActiveMQQueue("B");
			consumer = session.createConsumer(destination);
			message = consumer.receive();
			textMessage = (TextMessage) message;
			System.out.println(textMessage.getText());
			
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				if (consumer != null)
					consumer.close();
				if (session != null) {
					session.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (JMSException ex) {
				ex.printStackTrace();
			}
		}
	}
}