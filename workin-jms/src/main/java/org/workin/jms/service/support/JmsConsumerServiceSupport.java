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
 * Create Date : 2015-8-14
 */

package org.workin.jms.service.support;

import java.util.Map;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;
import org.workin.commons.util.MapUtils;
import org.workin.jms.strategy.DefaultJmsConsumeStrategy;
import org.workin.jms.strategy.JmsComsumeStrategiesManager;
import org.workin.jms.strategy.JmsConsumeStrategy;

/**
 * @description JMS消费者服务支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class JmsConsumerServiceSupport extends JmsServiceSupport implements JmsComsumeStrategiesManager {
	
	private Map<String, JmsConsumeStrategy> consumeStrategies;
	
	private JmsConsumeStrategy defaultConsumeStrategy;
	
	@Override
	public void setComsumeStrategies(Map<String, JmsConsumeStrategy> comsumeStrategies) {
		this.consumeStrategies = comsumeStrategies;
	}
	
	@Override
	public Map<String, JmsConsumeStrategy> getComsumeStrategies() {
		return this.consumeStrategies;
	}

	@Override
	public JmsConsumeStrategy getConsumeStrategy(String name) {
		JmsConsumeStrategy consumeStrategy = this.consumeStrategies.get(name);
		return consumeStrategy != null ? consumeStrategy : getDefaultConsumeStrategy();
	}

	@Override
	public JmsConsumeStrategy getDefaultConsumeStrategy() {
		return this.defaultConsumeStrategy;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (super.getJmsTemplate().getDefaultDestination() == null)
			throw new IllegalArgumentException("JmsTemplate property 'defaultDestination' is required.");
				
		this.defaultConsumeStrategy = createDefaultConsumeStrategy();
		
		if (this.consumeStrategies == null)
			this.consumeStrategies = MapUtils.newHashMap();
		
		// 将默认的消费策略加入到映射集中
		this.consumeStrategies.put(DEFAULT_PRODUCTION_STRATEGY_NAME, getDefaultConsumeStrategy());
	}

	/** 
	 * @description 创建默认的消费策略
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return 
	 */
	protected JmsConsumeStrategy createDefaultConsumeStrategy() {
		JmsConsumeStrategy consumeStrategy = new DefaultJmsConsumeStrategy();
		
		JmsTemplate jmsTemplate = super.getJmsTemplate();
		consumeStrategy.setDestination(jmsTemplate.getDefaultDestination());
		consumeStrategy.setReceiveTimeout(jmsTemplate.getReceiveTimeout());
		consumeStrategy.setSessionAcknowledgeMode(jmsTemplate.getSessionAcknowledgeMode());
		consumeStrategy.setSessionTransacted(jmsTemplate.isSessionTransacted());
		
		return consumeStrategy;
	}

	@Override
	protected Destination getDestination(String name) {
		JmsConsumeStrategy consumeStrategy = getConsumeStrategy(name);
		Destination destination = consumeStrategy.getDestination();
		return destination != null ? destination : getDefaultDestination();
	}

	@Override
	protected Destination getDefaultDestination() {
		return super.getJmsTemplate().getDefaultDestination();
	}

}
