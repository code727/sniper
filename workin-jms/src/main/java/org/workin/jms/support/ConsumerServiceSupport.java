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
 * 消费者服务抽象类
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
	 * 根据会话和指定名称的消费策略创建消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName 
	 * @return
	 */
	protected MessageConsumer createConsumer(Session session, String strategyName) throws JMSException {
		return createConsumer(session, strategies.get(strategyName));
	}
	
	/**
	 * 根据会话和指定的消费策略创建消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, ConsumeStrategy strategy) throws JMSException {
		return createConsumer(session, strategy, (Destination) null);
	}
	
	/**
	 * 根据会话、策略名称和目的地名称创建消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName
	 * @param destinationName
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, String strategyName, String destinationName) throws JMSException {
		return createSelectableConsumer(session, strategyName, destinationName, null);
	}	
	
	/**
	 * 根据会话、策略名称和目的地创建消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName
	 * @param destination
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, String strategyName, Destination destination) throws JMSException {
		return createSelectableConsumer(session, strategyName, destination, null);
	}
	
	/**
	 * 根据会话、策略和目的地名称创建消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param destinationName
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, ConsumeStrategy strategy, String destinationName) throws JMSException {
		return createSelectableConsumer(session, strategy, destinationName, null);
	}	
	
	/**
	 * 根据会话、策略和目的地创建消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param destination
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createConsumer(Session session, ConsumeStrategy strategy, Destination destination) throws JMSException {
		return createSelectableConsumer(session, strategy, destination, null);
	}
	
	/**
	 * 根据会话和消费策略名称创建具备选择功能的消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName
	 * @param messageSelector
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createSelectableConsumer(Session session, String strategyName, String messageSelector) throws JMSException {
		return createSelectableConsumer(session, strategyName, (Destination) null, messageSelector);
	}
	
	/**
	 * 根据会话和消费策略创建具备选择能力的消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param messageSelector
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createSelectableConsumer(Session session, ConsumeStrategy strategy, String messageSelector) throws JMSException {
		return createSelectableConsumer(session, strategy, (Destination) null, messageSelector);
	}
	
	/**
	 * 根据会话、策略名称和目的地创建具备选择能力的消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName
	 * @param destination
	 * @param messageSelector
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createSelectableConsumer(Session session, String strategyName, Destination destination, String messageSelector) throws JMSException {
		return createSelectableConsumer(session, strategies.get(strategyName),
				destination, messageSelector);
	}
	
	/**
	 * 根据会话、策略名称和目的地名称创建具备选择能力的消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategyName
	 * @param destinationName
	 * @param messageSelector
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createSelectableConsumer(Session session, String strategyName, String destinationName, String messageSelector) throws JMSException {
		return createSelectableConsumer(session, strategies.get(strategyName),
				destinationName, messageSelector);
	}
	
	/**
	 * 根据会话、策略和目的地名称创建具备选择能力的消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param destinationName
	 * @param messageSelector
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createSelectableConsumer(Session session, ConsumeStrategy strategy, String destinationName, String messageSelector) throws JMSException {
		return createSelectableConsumer(session, strategy, resolveDestinationName(
				session, destinationName, strategy.isPubSubDomain()), messageSelector);
	}
			
	/**
	 * 根据会话、消费策略和目的地创建具备选择能力的消费者
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param strategy
	 * @param destination
	 * @param messageSelector
	 * @param autoBind
	 * @return
	 * @throws JMSException
	 */
	protected MessageConsumer createSelectableConsumer(Session session, ConsumeStrategy strategy, Destination destination, String messageSelector) throws JMSException {
		destination = getRequiredDestination(strategy, destination);
		
		if (StringUtils.isBlank(messageSelector))
			// 优先选择传入的消息选择器，否则从消费策略中获取
			messageSelector = strategy.getMessageSelector();
		
		MessageConsumer consumer;
//		if (StringUtils.isNotBlank(messageSelector)) {
		if (strategy.isPubSubDomain())
			consumer = session.createConsumer(destination, messageSelector, strategy.isPubSubNoLocal());
		else
			consumer = session.createConsumer(destination, messageSelector);
//		} else
//			consumer = session.createConsumer(destination);
				
		return consumer;
	}
				
	/**
	 * 判断当前消费者是否采用异步消费方式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param consumer
	 * @return
	 */
	protected boolean isAsynConsume(MessageConsumer consumer) {
		try {
			return consumer != null && consumer.getMessageListener() != null;
		} catch (JMSException e) {
			return false;
		}
	}

}
