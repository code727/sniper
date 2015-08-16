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

import java.util.Map;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.workin.jms.core.manager.ProductionStrategiesManager;
import org.workin.jms.core.strategy.ProductionStrategy;

/**
 * @description 生产者服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class ProducerServiceSupport extends JmsDestinationAccessor
		implements ProductionStrategiesManager {
		
	/** 生产策略映射集 */
	private Map<String, ProductionStrategy> strategies;
	
	@Override
	public void setStrategies(Map<String, ProductionStrategy> strategies) {
		this.strategies = strategies;
	}

	@Override
	public Map<String, ProductionStrategy> getStrategies() {
		return this.strategies;
	}

	@Override
	public ProductionStrategy getStrategy(String name) {
		return this.strategies.get(name);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.strategies == null)
			throw new IllegalAccessException("Property 'strategies' of producer service is required.");
	}
	
	@Override
	protected Session createSession(Connection connection, String strategyName) throws JMSException {
		return createSession(connection, strategyName, false);
	}
	
	@Override
	protected Session createSession(Connection connection, String strategyName, boolean startConnection) throws JMSException {
		return super.createSession(connection, getStrategy(strategyName), startConnection);
	}
		
	/**
	 * @description 根据会话和指定名称的生产策略创建生产者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName 
	 * @return
	 */
	protected MessageProducer createProducer(Session session, String strategyName) throws JMSException {
		return createProducer(session, getStrategy(strategyName));
	}
	
	/**
	 * @description 根据会话和生产策略创建生产者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @return
	 * @throws JMSException
	 */
	protected MessageProducer createProducer(Session session, ProductionStrategy strategy) throws JMSException {
		return createProducer(session, strategy, true);
	}
	
	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName
	 * @param autoAssign
	 * @return
	 * @throws JMSException
	 */
	protected MessageProducer createProducer(Session session, String strategyName, boolean autoAssign) throws JMSException {
		return createProducer(session, getStrategy(strategyName), autoAssign);
	}
	
	/**
	 * @description 根据会话和生产策略创建生产者，并指定是否为生产者自动分配生产策略
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param autoAssign
	 * @return
	 * @throws JMSException
	 */
	protected MessageProducer createProducer(Session session, ProductionStrategy strategy, boolean autoAssign) throws JMSException {
		Destination destination = strategy.getDestination();
		if (destination == null)
			throw new IllegalArgumentException("Property destination install is required of production strategy");
		
		MessageProducer producer = session.createProducer(destination);
		if (autoAssign)
			assign(producer, strategy);
		
		return producer;
	}
	
	/**
	 * @description 为生产者分配生产策略
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param producer
	 * @param strategy
	 * @throws JMSException
	 */
	private void assign(MessageProducer producer, ProductionStrategy strategy) throws JMSException {
		producer.setDeliveryMode(strategy.getDeliveryMode());
		producer.setPriority(strategy.getPriority());
		producer.setTimeToLive(strategy.getTimeToLive());
		producer.setDisableMessageID(!strategy.isMessageIdEnabled());
		producer.setDisableMessageTimestamp(!strategy.isMessageTimestampEnabled());
	}

}
