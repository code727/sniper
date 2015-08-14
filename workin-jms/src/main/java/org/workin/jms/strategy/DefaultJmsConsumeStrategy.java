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

import javax.jms.MessageListener;

/**
 * @description 默认的JMS消费策略实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultJmsConsumeStrategy extends DefaultJmsSessionAccessStrategy
		implements JmsConsumeStrategy {
	
	private long receiveTimeout;
	
	private MessageListener messageListener;

	@Override
	public void setReceiveTimeout(long receiveTimeout) {
		if (receiveTimeout > 0)
			this.receiveTimeout = receiveTimeout;
	}

	@Override
	public long getReceiveTimeout() {
		return this.receiveTimeout;
	}

	@Override
	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	@Override
	public MessageListener getMessageListener() {
		return this.messageListener;
	}

}
