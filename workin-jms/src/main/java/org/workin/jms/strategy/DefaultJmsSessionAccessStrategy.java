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

package org.workin.jms.strategy;

import javax.jms.Destination;
import javax.jms.Session;

/**
 * @description 默认JMS会话访问策略实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultJmsSessionAccessStrategy implements JmsSessionAccessStrategy {
	
	private Destination destination;
	
	private int sessionAcknowledgeMode = Session.AUTO_ACKNOWLEDGE;
	
	private boolean sessionTransacted = false;
	
	@Override
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	@Override
	public Destination getDestination() {
		return this.destination;
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

}
