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
import org.workin.jms.strategy.DefaultJmsProductionStrategy;
import org.workin.jms.strategy.JmsProductionStrategiesManager;
import org.workin.jms.strategy.JmsProductionStrategy;

/**
 * @description JMS生产者服务支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class JmsProducerServiceSupport extends JmsServiceSupport
		implements JmsProductionStrategiesManager {
		
	private Map<String, JmsProductionStrategy> productionStrategies;
	
	private JmsProductionStrategy defaultProductionStrategy;

	@Override
	public void setProductionStrategies(Map<String, JmsProductionStrategy> productionStrategies) {
		this.productionStrategies = productionStrategies;
	}

	@Override
	public Map<String, JmsProductionStrategy> getProductionStrategies() {
		return this.productionStrategies;
	}

	@Override
	public JmsProductionStrategy getProductionStrategy(String name) {
		JmsProductionStrategy productionStrategy = this.productionStrategies.get(name);
		return productionStrategy != null ? productionStrategy : getDefaultProductionStrategy();
	}
	
	@Override
	public JmsProductionStrategy getDefaultProductionStrategy() {
		return this.defaultProductionStrategy;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (super.getJmsTemplate().getDefaultDestination() == null)
			throw new IllegalArgumentException("JmsTemplate property 'defaultDestination' is required.");
				
		this.defaultProductionStrategy = createDefaultProductionStrategy();
		
		if (this.productionStrategies == null)
			this.productionStrategies = MapUtils.newHashMap();
		
		// 将默认的生产策略加入到映射集中
		this.productionStrategies.put(DEFAULT_PRODUCTION_STRATEGY_NAME, getDefaultProductionStrategy());
	}
	
	/**
	 * @description 创建默认的生产策略
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected JmsProductionStrategy createDefaultProductionStrategy() {
		JmsProductionStrategy productionStrategy = new DefaultJmsProductionStrategy();
		
		JmsTemplate jmsTemplate = super.getJmsTemplate();
		productionStrategy.setDeliveryMode(jmsTemplate.getDeliveryMode());
		productionStrategy.setDestination(jmsTemplate.getDefaultDestination());
		productionStrategy.setExplicitQosEnabled(jmsTemplate.isExplicitQosEnabled());
		
		productionStrategy.setMessageConverter(jmsTemplate.getMessageConverter());
		productionStrategy.setMessageIDEnabled(jmsTemplate.isMessageIdEnabled());
		productionStrategy.setMessageTimestampEnabled(jmsTemplate.isMessageTimestampEnabled());
		productionStrategy.setPriority(jmsTemplate.getPriority());
		productionStrategy.setPubSubNoLocal(jmsTemplate.isPubSubNoLocal());
		productionStrategy.setTimeToLive(jmsTemplate.getTimeToLive());
		
		productionStrategy.setSessionAcknowledgeMode(jmsTemplate.getSessionAcknowledgeMode());
		productionStrategy.setSessionTransacted(jmsTemplate.isSessionTransacted());
			
		return productionStrategy;
	}

	
	@Override
	protected Destination getDestination(String name) {
		 JmsProductionStrategy productionStrategy = getProductionStrategy(name);
		 Destination destination = productionStrategy.getDestination();
		 return destination != null ? destination : getDefaultDestination();
	}

	@Override
	protected Destination getDefaultDestination() {
		return super.getJmsTemplate().getDefaultDestination();
	}
	
}
