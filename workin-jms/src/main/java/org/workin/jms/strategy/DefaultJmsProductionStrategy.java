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
 * Create Date : 2015-8-13
 */

package org.workin.jms.strategy;

import javax.jms.Message;

import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

/**
 * @description 默认的生产策略实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultJmsProductionStrategy extends
		DefaultJmsSessionAccessStrategy implements JmsProductionStrategy {
	
	private boolean messageIDEnabled = true;
	
	private boolean messageTimestampEnabled = true;
	
	private boolean pubSubNoLocal = false;
	
	private boolean explicitQosEnabled = false;
	
	private int deliveryMode = Message.DEFAULT_DELIVERY_MODE;

	private int priority = Message.DEFAULT_PRIORITY;

	private long timeToLive = Message.DEFAULT_TIME_TO_LIVE;
	
	private MessageConverter messageConverter = new SimpleMessageConverter();
	
	@Override
	public void setMessageIDEnabled(boolean messageIDEnabled) {
		this.messageIDEnabled = messageIDEnabled;
	}

	@Override
	public boolean isMessageIdEnabled() {
		return this.messageIDEnabled;
	}

	@Override
	public void setMessageTimestampEnabled(boolean messageTimestampEnabled) {
		this.messageTimestampEnabled = messageTimestampEnabled;
	}

	@Override
	public boolean isMessageTimestampEnabled() {
		return this.messageTimestampEnabled;
	}

	@Override
	public void setDeliveryMode(int deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@Override
	public int getDeliveryMode() {
		return this.deliveryMode;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int getPriority() {
		return this.priority;
	}

	@Override
	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive; 
	}

	@Override
	public long getTimeToLive() {
		return this.timeToLive; 
	}

	@Override
	public void setPubSubNoLocal(boolean pubSubNoLocal) {
		this.pubSubNoLocal = pubSubNoLocal;
	}

	@Override
	public boolean isPubSubNoLocal() {
		return this.pubSubNoLocal;
	}

	@Override
	public void setExplicitQosEnabled(boolean explicitQosEnabled) {
		this.explicitQosEnabled = explicitQosEnabled;
	}

	@Override
	public boolean isExplicitQosEnabled() {
		return this.explicitQosEnabled;
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
