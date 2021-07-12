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
 * JMS消费者服务接口
 * @author  Daniele
 * @version 1.0
 */
public interface ConsumerService {
	
	/**
	 * 按指定名称的消费策略接收消息
	 * @author Daniele 
	 * @param strategyName 消费策略名称
	 * @return
	 */
	public <T> T receive(String strategyName);
	
	/**
	 * 按指定名称的消费策略接收来自目的地的消息
	 * @author Daniele 
	 * @param strategyName
	 * @param destinationName
	 * @return
	 */
	public <T> T receive(String strategyName, String destinationName);
	
	/**
	 * 按指定名称的消费策略接收来自目的地的消息
	 * @author Daniele 
	 * @param strategyName
	 * @param destination
	 * @return
	 */
	public <T> T receive(String strategyName, Destination destination);
	
	/**
	 * 按指定名称的消费策略选择接收消息
	 * @author Daniele 
	 * @param strategyName
	 * @param messageSelector
	 * @return
	 */
	public <T> T selectReceive(String strategyName, String messageSelector);
	
	/**
	 * 按指定名称的消费策略选择接收来自目的地的消息
	 * @author Daniele 
	 * @param strategyName
	 * @param destinationName
	 * @param messageSelector
	 * @return
	 */
	public <T> T selectReceive(String strategyName, String destinationName, String messageSelector);
	
	/**
	 * 按指定名称的消费策略选择接收来自目的地的消息
	 * @author Daniele 
	 * @param strategyName
	 * @param destination
	 * @param messageSelector
	 * @return
	 */
	public <T> T selectReceive(String strategyName, Destination destination, String messageSelector);
	
}
