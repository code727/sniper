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

package org.sniper.jms.core.strategy.impl;

import javax.jms.Message;

import org.sniper.jms.core.strategy.ProductionStrategy;

/**
 * 默认的生产策略实现类
 * @author  Daniele
 * @version 1.0
 */
public class DefaultProductionStrategy extends
		DefaultSharedStrategy implements ProductionStrategy {
	
	/** 是否启用唯一标识来区分提供商发送的每个消息的功能 */
	private boolean messageIDEnabled = true;
	
	private boolean messageTimestampEnabled = true;
		
	private boolean explicitQosEnabled = false;
	
	/** 消息优先级 */
	private int priority = Message.DEFAULT_PRIORITY;

	/** 消息生存时间 */
	private long timeToLive = Message.DEFAULT_TIME_TO_LIVE;
	
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
	public void setExplicitQosEnabled(boolean explicitQosEnabled) {
		this.explicitQosEnabled = explicitQosEnabled;
	}

	@Override
	public boolean isExplicitQosEnabled() {
		return this.explicitQosEnabled;
	}

}
