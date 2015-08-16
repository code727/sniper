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

package org.workin.jms.core.strategy.impl;

import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.Topic;

import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.workin.jms.core.strategy.SharedStrategy;

/**
 * @description 默认JMS共享策略实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultSharedStrategy implements SharedStrategy {
	
	/** 生产/消息目的地 */
	private Destination destination;
	
	/** 是否采用发布/订阅机制 */
	private boolean pubSubDomain;
	
	/** 消息确认接收方式 */
	private int sessionAcknowledgeMode = Session.AUTO_ACKNOWLEDGE;
	
	/** 是否启用事务 */
	private boolean sessionTransacted = false;
	
	/** 消息转换器 */
	private MessageConverter messageConverter = new SimpleMessageConverter();
	
	@Override
	public void setDestination(Destination destination) {
		if (isPubSubDomain() && !(destination instanceof Topic))
			// 当采用发布/订阅机制来生产或消费消息时，目的地必须是一个javax.jms.Topic实例
			throw new IllegalArgumentException(
					"Destination must be instance of javax.jms.Topic when property 'pubSubDomain' is true");
			
		this.destination = destination;
	}

	@Override
	public Destination getDestination() {
		return this.destination;
	}
	
	@Override
	public void setPubSubDomain(boolean pubSubDomain) {
		this.pubSubDomain = pubSubDomain;
	}

	@Override
	public boolean isPubSubDomain() {
		return this.pubSubDomain;
	}

	@Override
	public void setSessionTransacted(boolean sessionTransacted) {
		this.sessionTransacted = sessionTransacted;
	}

	@Override
	public boolean isSessionTransacted() {
		return this.sessionTransacted;
	}

	@Override
	public void setSessionAcknowledgeMode(int sessionAcknowledgeMode) {
		this.sessionAcknowledgeMode = sessionAcknowledgeMode;
	}

	@Override
	public int getSessionAcknowledgeMode() {
		return this.sessionAcknowledgeMode;
	}
	
	@Override
	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

	@Override
	public MessageConverter getMessageConverter() {
		return this.messageConverter;
	}

}
