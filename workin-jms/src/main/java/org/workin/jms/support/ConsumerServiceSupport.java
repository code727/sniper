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
 * Create Date : 2015-8-17
 */

package org.workin.jms.support;

import java.util.Map;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;
import org.workin.jms.core.manager.ConsumeStrategiesManager;
import org.workin.jms.core.strategy.ConsumeStrategy;

/**
 * @description 消费者服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class ConsumerServiceSupport extends JmsDestinationAccessor implements ConsumeStrategiesManager{

	private Map<String, ConsumeStrategy> strategies;
	
	@Override
	public void setStrategies(Map<String, ConsumeStrategy> strategies) {
		this.strategies = strategies;
	}

	@Override
	public Map<String, ConsumeStrategy> getStrategies() {
		return this.strategies;
	}

	@Override
	public ConsumeStrategy getStrategy(String name) {
		return this.strategies.get(name);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (MapUtils.isEmpty(this.strategies))
			throw new IllegalAccessException("Consumer strategies must not be empty.");
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
	 * @description 根据会话和指定名称的消费策略创建消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName 
	 * @return
	 */
	protected MessageConsumer createConsumer(Session session, String strategyName) throws JMSException {
		return createConsumer(session, getStrategy(strategyName));
	}
	
	/**
	 * @description 根据会话和消费策略创建消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, ConsumeStrategy strategy) throws JMSException {
		return createConsumer(session, strategy, true);
	}
	
	/**
	 * @description 根据会话和指定名称的消费策略创建消费者，并指定是否为消费者自动分配消费策略
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName
	 * @param autoAssign
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, String strategyName, boolean autoAssign) throws JMSException {
		return createConsumer(session, getStrategy(strategyName), autoAssign);
	}
	
	/**
	 * @description 根据会话和消费策略创建消费者，并指定是否为消费者自动分配消费策略
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param autoAssign
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, ConsumeStrategy strategy, boolean autoAssign) throws JMSException {
		return createConsumer(session, strategy, (Destination) null, autoAssign);
	}
	
	/**
	 * @description 根据会话、消费策略和目的地名称创建消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param destinationName
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, ConsumeStrategy strategy, String destinationName) throws JMSException {
		return createConsumer(session, strategy, destinationName, true);
	}
	
	/**
	 * @description 根据会话、消费策略和目的地创建消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param destination
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, ConsumeStrategy strategy, Destination destination) throws JMSException {
		return createConsumer(session, strategy, destination, true);
	}
	
	/**
	 * @description 根据会话、消费策略和目的地名称创建消费者，并指定是否为消费者自动分配消费策略
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param destinationName
	 * @param autoAssign
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, ConsumeStrategy strategy, String destinationName, boolean autoAssign) throws JMSException {
		return createConsumer(session, strategy, StringUtils.isNotEmpty(destinationName) ? 
				resolveDestinationName(session, destinationName, strategy.isPubSubDomain()) : (Destination) null, autoAssign);
	}
	
	/**
	 * @description 根据会话、消费策略和目的地创建消费者，并指定是否为消费者自动分配消费策略
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param destination
	 * @param autoAssign
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, ConsumeStrategy strategy, Destination destination, boolean autoAssign) throws JMSException {
		if (destination == null)
			// 优先选择传入的目的地，否则从消费策略中获取
			destination = strategy.getDestination();
		
		if (destination == null)
			throw new IllegalArgumentException("Destination must not be null.");
		
		MessageConsumer consumer = session.createConsumer(destination);
		if (autoAssign)
			assign(consumer, strategy);
		
		return consumer;
	}

	/**
	 * @description 为消费者分配消费策略
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param producer
	 * @param strategy
	 * @throws JMSException
	 */
	private void assign(MessageConsumer consumer, ConsumeStrategy strategy) throws JMSException {
		consumer.setMessageListener(strategy.getMessageListener());
	}

}
