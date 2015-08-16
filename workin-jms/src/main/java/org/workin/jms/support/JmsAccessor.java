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

package org.workin.jms.support;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.workin.jms.core.strategy.SharedStrategy;

/**
 * @description JMS访问器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class JmsAccessor implements InitializingBean {
	
	/** 连接工厂 */
	private ConnectionFactory connectionFactory;
	
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public ConnectionFactory getConnectionFactory() {
		return this.connectionFactory;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (getConnectionFactory() == null) 
			throw new IllegalArgumentException("Property 'connectionFactory' is required");
	}
	
	/**
	 * @description 创建连接
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 * @throws JMSException
	 */
	protected Connection createConnection() throws JMSException {
		return getConnectionFactory().createConnection();
	}
	
	/**
	 * @description 根据连接和策略创建会话对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param strategy
	 * @return
	 * @throws JMSException
	 */
	protected Session createSession(Connection connection, SharedStrategy strategy) throws JMSException {
		return createSession(connection, strategy, false);
	}
	
	/**
	 * @description 根据连接和策略创建会话对象，并选择是否启动连接
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param strategy
	 * @param startConnection
	 * @return
	 * @throws JMSException
	 */
	protected Session createSession(Connection connection, SharedStrategy strategy, boolean startConnection) throws JMSException {
		Session session = connection.createSession(strategy.isSessionTransacted(), strategy.getSessionAcknowledgeMode());
		if (startConnection)
			connection.start();
		
		return session;
	}
	
	/**
	 * @description 根据连接和指定名称对应的策略创建会话对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param strategyName
	 * @return
	 * @throws JMSException
	 */
	protected abstract Session createSession(Connection connection, String strategyName) throws JMSException;
	
	/**
	 * @description 根据连接和指定名称对应的策略创建会话对象，并选择是否启动连接
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param strategyName
	 * @param startConnection
	 * @return
	 * @throws JMSException
	 */
	protected abstract Session createSession(Connection connection, String strategyName, boolean startConnection) throws JMSException;
	
	/**
	 * @description 判断是否为客户端回复会话模式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @return
	 * @throws JMSException
	 */
	protected boolean isClientAcknowledge(Session session) throws JMSException {
		return (session.getAcknowledgeMode() == Session.CLIENT_ACKNOWLEDGE);
	}
	
	protected boolean isSessionLocallyTransacted(Session session, SharedStrategy strategy) {
		return strategy.isSessionTransacted()
				&& !ConnectionFactoryUtils.isSessionTransactional(session,getConnectionFactory());
	}

}
