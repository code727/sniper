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

package org.sniper.jms.core;

import javax.jms.Destination;

/**
 * JMS生产者服务接口
 * @author  Daniele
 * @version 1.0
 */
public interface ProducerService {
		
	/**
	 * 按指定名称的生产策略发送消息
	 * @author Daniele 
	 * @param strategyName 生产策略名称
	 * @param message 消息数据
	 */
	public <T> void send(String strategyName, T message);
	
	/**
	 * 按指定的生产策略将消息发送到目的地
	 * @author Daniele 
	 * @param strategyName
	 * @param destinationName
	 * @param message
	 */
	public <T> void send(String strategyName, String destinationName, T message);
	
	/**
	 * 按指定的生产策略将消息发送到目的地
	 * @author Daniele 
	 * @param strategyName
	 * @param destination
	 * @param message
	 */
	public <T> void send(String strategyName, Destination destination, T message);
	
		
}
