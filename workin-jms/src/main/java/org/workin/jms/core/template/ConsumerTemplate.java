/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Create Date : 2015-8-16
 */

package org.workin.jms.core.template;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.jms.support.JmsUtils;
import org.workin.commons.util.AssertUtils;
import org.workin.jms.core.ConsumerService;
import org.workin.jms.core.listener.ConsumeMessageListener;
import org.workin.jms.core.strategy.ConsumeStrategy;
import org.workin.jms.support.ConsumerServiceSupport;

/**
 * @description 消费者服务模板
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ConsumerTemplate extends ConsumerServiceSupport implements ConsumerService {

	@Override
	public <T> T receive(String strategyName) {
		return doReceive(strategyName, null, null);
	}

	@Override
	public <T> T receive(String strategyName, String destinationName) {
		return doReceive(strategyName, null, destinationName);
	}

	@Override
	public <T> T receive(String strategyName, Destination destination) {
		return doReceive(strategyName, destination, null);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T doReceive(String strategyName, Destination destination, String destinationName) {
		ConsumeStrategy cs = getStrategy(strategyName);
		AssertUtils.assertNotNull(cs, "Can not found consume strategy of [" + strategyName + "].");
		
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
		
		T result = null;
		MessageListener listener = cs.getMessageListener();
		try {
			connection = createConnection();
			session = createSession(connection, cs, true);
			
			if (destination != null)
				consumer = createConsumer(session, cs, destination);
			else
				consumer = createConsumer(session, cs, destinationName);
			
			if (listener != null) {
				if (listener instanceof ConsumeMessageListener) {
					((ConsumeMessageListener) listener).setConnection(connection);
					((ConsumeMessageListener) listener).setSession(session);
					((ConsumeMessageListener) listener).setConsumer(consumer);
					((ConsumeMessageListener) listener).setStrategy(cs);
				}
				consumer.setMessageListener(listener);
			} else  {
				Message message = consumer.receive();
				result = (T) cs.getMessageConverter().fromMessage(consumer.receive());
				if (session.getAcknowledgeMode() == Session.CLIENT_ACKNOWLEDGE)
					message.acknowledge();
			}
			
			if (session.getTransacted() && isSessionLocallyTransacted(session, cs))
				JmsUtils.commitIfNecessary(session);
			
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			/* 如果是同步接收，则接收完成后立即进行关闭操作 ，否则需在消息监听器中完成 */
			if (listener == null) {
				JmsUtils.closeMessageConsumer(consumer);
				JmsUtils.closeSession(session);
				ConnectionFactoryUtils.releaseConnection(connection, getConnectionFactory(), true);
			}
		}
		return result;
	}

}
