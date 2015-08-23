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
		return receive(strategyName, (Destination) null);
	}

	@Override
	public <T> T receive(String strategyName, String destinationName) {
		return selectReceive(strategyName, destinationName, null);
	}

	@Override
	public <T> T receive(String strategyName, Destination destination) {
		return selectReceive(strategyName, destination, null);
	}

	@Override
	public <T> T selectReceive(String strategyName, String messageSelector) {
		return selectReceive(strategyName, (Destination) null, messageSelector);
	}

	@Override
	public <T> T selectReceive(String strategyName, String destinationName, String messageSelector) {
		return doReceive(strategyName,destinationName, null, messageSelector);
	}

	@Override
	public <T> T selectReceive(String strategyName, Destination destination, String messageSelector) {
		return doReceive(strategyName, null, destination, messageSelector);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T doReceive(String strategyName, String destinationName, Destination destination, String messageSelector) {
		ConsumeStrategy cs = getStrategy(strategyName);
		AssertUtils.assertNotNull(cs, "Can not found consume strategy of [" + strategyName + "].");
		
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
		
		T result = null;
		try {
			connection = createConnection();
			session = createSession(connection, cs, true);
			
			/* 创建消费者 */
			if (destinationName != null) 
				consumer = createSelectableConsumer(session, cs, destinationName, messageSelector);
			else
				consumer = createSelectableConsumer(session, cs, destination, messageSelector);
			
			MessageListener listener = cs.getMessageListener();
			if (listener != null) {
				/* 绑定异步消费监听 */
				if (listener instanceof ConsumeMessageListener) {
					((ConsumeMessageListener) listener).setConnectionFactory(getConnectionFactory());
					((ConsumeMessageListener) listener).setConnection(connection);
					((ConsumeMessageListener) listener).setSession(session);
					((ConsumeMessageListener) listener).setConsumer(consumer);
					((ConsumeMessageListener) listener).setStrategy(cs);
				}
				consumer.setMessageListener(listener);
			} else  {
				/* 直接进行同步消费 */
				Message message = consumer.receive();
				result = (T) cs.getMessageConverter().fromMessage(message);
				
				if (hasTransactional(session, cs))
					JmsUtils.commitIfNecessary(session);
				
				if (org.workin.jms.util.JmsUtils.isClientAcknowledge(session))
					message.acknowledge();
			}
		} catch (JMSException e) {
			e.printStackTrace();
			if (!isAsynConsume(consumer))
				try {
					autoRollback(session, cs);
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
		} finally {
			/* 如果不是异步消费，则接收完成后立即进行关闭操作 ，否则需在消息监听器中完成 */
			if (!isAsynConsume(consumer)) {
				JmsUtils.closeMessageConsumer(consumer);
				JmsUtils.closeSession(session);
				ConnectionFactoryUtils.releaseConnection(connection, getConnectionFactory(), true);
			}
		}
		return result;
	}

}
