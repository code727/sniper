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

package org.sniper.jms.support;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.sniper.jms.core.strategy.SharedStrategy;
import org.sniper.jms.util.JmsUtils;

/**
 * JMS访问器抽象类
 * @author  Daniele
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
	 * 创建连接
	 * @author Daniele 
	 * @return
	 * @throws JMSException
	 */
	protected Connection createConnection() throws JMSException {
		return getConnectionFactory().createConnection();
	}
	
	/**
	 * 根据连接和策略创建会话对象
	 * @author Daniele 
	 * @param connection
	 * @param strategy
	 * @return
	 * @throws JMSException
	 */
	protected Session createSession(Connection connection, SharedStrategy strategy) throws JMSException {
		return createSession(connection, strategy, false);
	}
	
	/**
	 * 根据连接和策略创建会话对象，并选择是否启动连接
	 * @author Daniele 
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
	 * 根据连接和指定名称对应的策略创建会话对象
	 * @author Daniele 
	 * @param connection
	 * @param strategyName
	 * @return
	 * @throws JMSException
	 */
	protected abstract Session createSession(Connection connection, String strategyName) throws JMSException;
	
	/**
	 * 根据连接和指定名称对应的策略创建会话对象，并选择是否启动连接
	 * @author Daniele 
	 * @param connection
	 * @param strategyName
	 * @param startConnection
	 * @return
	 * @throws JMSException
	 */
	protected abstract Session createSession(Connection connection, String strategyName, boolean startConnection) throws JMSException;
	
	/**
	 * 判断当前会话是否具备本地事务特性
	 * @author Daniele 
	 * @param session
	 * @param strategy
	 * @return
	 */
	protected boolean isSessionLocallyTransacted(Session session, SharedStrategy strategy) {
		return strategy.isSessionTransacted()
				&& !ConnectionFactoryUtils.isSessionTransactional(session,getConnectionFactory());
	}

	/**
	 * 判断会话是否具有事务性
	 * @author Daniele 
	 * @param session
	 * @return
	 */
	protected boolean hasTransactional(Session session, SharedStrategy strategy) {
		try {
			return session.getTransacted() && isSessionLocallyTransacted(session, strategy);
		} catch (JMSException e) {
			return false;
		}
	}
	
	/**
	 * 自动回滚事务
	 * @author Daniele 
	 * @param session
	 * @param strategy
	 * @throws JMSException
	 */
	protected void autoRollback(Session session, SharedStrategy strategy) throws JMSException {
		if (strategy.isAutoRollback() && hasTransactional(session, strategy))
			JmsUtils.rollback(session);
	}

}
