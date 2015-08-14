package org.workin.jms.demo.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description ActiveMQ 实现的JMS消息生产者示例
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JmsProducerDemo {
	
	private static Logger logger = LoggerFactory.getLogger(JmsProducerDemo.class);
	
	public static void main(String[] args) {
		
		
		// 产生连接工厂
		/*ConnectionFactory cf = new ActiveMQConnectionFactory("failover://(tcp://localhost:61616,tcp://localhost:61617)");*/
		ConnectionFactory cf = new ActiveMQConnectionFactory("nio://localhost:61616");
		Connection conn = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			// 产生连接
			conn = cf.createConnection();
//			conn.start();
			logger.info("Successful create connection.");
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
			// 设置消息目的地类型（ActiveMQTopic为订阅目的地）
			Destination destination = new ActiveMQTopic("daniele");
			producer = session.createProducer(destination);
			producer.setTimeToLive(999999);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			TextMessage message = session.createTextMessage();
			message.setText("Just a simaple JMS Testing.");
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				if (producer != null)
					producer.close();
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