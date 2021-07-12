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

package org.sniper.jms.core.template;

import javax.jms.Destination;

import org.sniper.jms.core.JmsService;
import org.sniper.jms.support.JmsServiceSupport;

/**
 * JMS服务模板实现类
 * @author  Daniele
 * @version 1.0
 */
public class JmsTemplate extends JmsServiceSupport implements JmsService {

	@Override
	public <T> void send(String strategyName, T message) {
		getProducerTemplate().send(strategyName, message);
	}

	@Override
	public <T> void send(String strategyName, String destinationName, T message) {
		getProducerTemplate().send(strategyName, destinationName, message);
	}

	@Override
	public <T> void send(String strategyName, Destination destination, T message) {
		getProducerTemplate().send(strategyName, destination, message);
	}

	@Override
	public <T> T receive(String strategyName) {
		return getConsumerTemplate().receive(strategyName);
	}

	@Override
	public <T> T receive(String strategyName, String destinationName) {
		return getConsumerTemplate().receive(strategyName, destinationName);
	}

	@Override
	public <T> T receive(String strategyName, Destination destination) {
		return getConsumerTemplate().receive(strategyName, destination);
	}

	@Override
	public <T> T selectReceive(String strategyName, String messageSelector) {
		return getConsumerTemplate().selectReceive(strategyName, messageSelector);
	}

	@Override
	public <T> T selectReceive(String strategyName, String destinationName, String messageSelector) {
		return getConsumerTemplate().selectReceive(strategyName, destinationName, messageSelector);
	}

	@Override
	public <T> T selectReceive(String strategyName, Destination destination, String messageSelector) {
		return getConsumerTemplate().selectReceive(strategyName, destination, messageSelector);
	}
	
}
