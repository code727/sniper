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

import org.workin.commons.util.MapUtils;
import org.workin.jms.core.manager.ProductionStrategiesManager;
import org.workin.jms.core.strategy.ProductionStrategy;

/**
 * 生产者服务抽象类
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
		
		if (MapUtils.isEmpty(getStrategies()))
			throw new IllegalAccessException("Producer strategies must not be empty.");
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
	 * 根据会话和指定名称的生产策略创建生产者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName 
	 * @return
	 */
	protected MessageProducer createProducer(Session session, String strategyName) throws JMSException {
		return createProducer(session, getStrategy(strategyName));
	}
	
	/**
	 * 根据会话和生产策略创建生产者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @return
	 * @throws JMSException
	 */
	protected MessageProducer createProducer(Session session, ProductionStrategy strategy) throws JMSException {
		return createProducer(session, strategy, (Destination) null);
	}
	
	/**
	 * 根据会话、策略名称和目的地名称创建生产者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName
	 * @param destinationName
	 * @return
	 * @throws JMSException
	 */
	protected MessageProducer createProducer(Session session, String strategyName, String destinationName) throws JMSException {
		return createProducer(session, strategies.get(strategyName), destinationName);
	}
	
	/**
	 * 根据会话、策略名称和目的地创建生产者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName
	 * @param destination
	 * @return
	 * @throws JMSException
	 */
	protected MessageProducer createProducer(Session session, String strategyName, Destination destination) throws JMSException {
		return createProducer(session, strategies.get(strategyName), destination);
	}
	
	/**
	 * 根据会话、生产策略和目的地名称创建生产者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param destinationName
	 * @return
	 * @throws JMSException
	 */
	protected MessageProducer createProducer(Session session, ProductionStrategy strategy, String destinationName) throws JMSException {
		return createProducer(session, strategy, resolveDestinationName(session, destinationName, strategy.isPubSubDomain()));
	}
	
	/**
	 * 根据会话、生产策略和目的地创建生产者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param destination
	 * @return
	 * @throws JMSException
	 */
	protected MessageProducer createProducer(Session session, ProductionStrategy strategy, Destination destination) throws JMSException {
		return session.createProducer(getRequiredDestination(strategy, destination));
	}
	
	/**
	 * 准备生产
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param producer
	 * @param strategy
	 * @throws JMSException
	 */
	protected void prepare(MessageProducer producer, ProductionStrategy strategy) throws JMSException {
		producer.setDisableMessageID(!strategy.isMessageIdEnabled());
		producer.setDisableMessageTimestamp(!strategy.isMessageTimestampEnabled());
	}
	
	/**
	 * 执行发送操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param producer
	 * @param message
	 * @param strategy
	 * @throws JMSException
	 */
	protected void doSend(Session session, MessageProducer producer, Object message, ProductionStrategy strategy) throws JMSException {
		if (strategy.isExplicitQosEnabled())
			producer.send(strategy.getMessageConverter().toMessage(message, session), 
					strategy.getDeliveryMode(), strategy.getPriority(), strategy.getTimeToLive());
		else
			producer.send(strategy.getMessageConverter().toMessage(message, session));
	}

}
