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
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.jms.support.JmsUtils;
import org.workin.commons.util.AssertUtils;
import org.workin.jms.core.ProducerService;
import org.workin.jms.core.strategy.ProductionStrategy;
import org.workin.jms.support.ProducerServiceSupport;

/**
 * @description 生产者服务模板
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ProducerTemplate extends ProducerServiceSupport implements ProducerService {
	
	@Override
	public <T> void send(String name, T message) {
		ProductionStrategy ps = getStrategy(name);
		AssertUtils.assertNotNull(ps, "Can not found production strategy of [" + name + "].");
		
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			connection = createConnection();
			session = createSession(connection, ps, false);
			producer = createProducer(session, ps);
			producer.send(ps.getMessageConverter().toMessage(message, session));
			if (session.getTransacted() && isSessionLocallyTransacted(session, ps))
				JmsUtils.commitIfNecessary(session);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			JmsUtils.closeMessageProducer(producer);
			JmsUtils.closeSession(session);
			// 只关闭而不停止连接
			ConnectionFactoryUtils.releaseConnection(connection, getConnectionFactory(), false);
		}
	}
	

}
