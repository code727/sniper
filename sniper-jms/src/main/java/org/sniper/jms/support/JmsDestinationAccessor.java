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
 * Create Date : 2015-8-16
 */

package org.sniper.jms.support;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.sniper.commons.util.StringUtils;
import org.sniper.jms.core.strategy.SharedStrategy;

/**
 * JMS目的地访问器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class JmsDestinationAccessor extends JmsAccessor {
	
	/** 目的地解析器 */
	private DestinationResolver destinationResolver = new DynamicDestinationResolver();
	
	public void setDestinationResolver(DestinationResolver destinationResolver) {
		if (destinationResolver != null)
			this.destinationResolver = destinationResolver;
	}
	
	public DestinationResolver getDestinationResolver() {
		return this.destinationResolver;
	}
	
	/**
	 * 根据名称是否解析出一个发布/订阅域(topic)目的地对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 * @param destinationName
	 * @param pubSubDomain
	 * @return 
	 * @throws JMSException
	 */
	protected Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain) throws JMSException {
		return StringUtils.isNotEmpty(destinationName) ? 
				getDestinationResolver().resolveDestinationName(session, destinationName, pubSubDomain) : null;
	}
	
	/**
	 * 获取必要的目的地对象，否则抛出IllegalArgumentException
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param strategy
	 * @param destination
	 * @return
	 */
	protected Destination getRequiredDestination(SharedStrategy strategy, Destination destination) {
		if (destination == null)
			// 优先选择传入的目的地，否则从消费策略中获取
			destination = strategy.getDestination();
		
		if (destination == null)
			throw new IllegalArgumentException("Destination must not be null.");
		
		return destination;
	}

}
