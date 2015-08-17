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
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.jms.support.JmsUtils;
import org.workin.commons.util.AssertUtils;
import org.workin.jms.core.ConsumerService;
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
		T message = null;
		
		try {
			connection = createConnection();
			session = createSession(connection, cs, true);
			if (destination != null)
				consumer = createConsumer(session, cs, destination);
			else
				consumer = createConsumer(session, cs, destinationName);
			
			MessageListener listener = cs.getMessageListener();
			if (listener != null)
				// 异步接收
				consumer.setMessageListener(listener);
			else 
				message = (T) cs.getMessageConverter().fromMessage(consumer.receive());
			
			if (session.getTransacted() && isSessionLocallyTransacted(session, cs))
				JmsUtils.commitIfNecessary(session);
			
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			JmsUtils.closeMessageConsumer(consumer);
			JmsUtils.closeSession(session);
			// 只关闭而不停止连接
			ConnectionFactoryUtils.releaseConnection(connection, getConnectionFactory(), false);
		}
		return message;
	}

}
