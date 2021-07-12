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

package org.sniper.jms.core.strategy;

import javax.jms.MessageListener;

/**
 * JMS消费策略接口
 * @author  Daniele
 * @version 1.0
 */
public interface ConsumeStrategy extends SharedStrategy {
	
	/**
	 * 设置消息选择器字符串
	 * @author Daniele 
	 * @param messageSelector
	 */
	public void setMessageSelector(String messageSelector);
	
	/**
	 * 获取消息选择器字符串
	 * @author Daniele 
	 * @return
	 */
	public String getMessageSelector();
	
	/**
	 * 设置是否不接收来自同一个连接的消息
	 * @author Daniele 
	 * @param pubSubNoLocal
	 */
	public void setPubSubNoLocal(boolean pubSubNoLocal);

	/**
	 * 判断是否不接收来自同一个连接的消息
	 * @author Daniele 
	 * @return
	 */
	public boolean isPubSubNoLocal();
	
	/**
	 * 设置消息接收时的超时时间
	 * @author Daniele 
	 * @param receiveTimeout
	 */
	public void setReceiveTimeout(long receiveTimeout);

	/**
	 * 获取消息接收时的超时时间
	 * @author Daniele 
	 * @return
	 */
	public long getReceiveTimeout();
	
	/**
	 * 设置接收消息时所用的监听器
	 * @author Daniele 
	 * @param messageListener
	 */
	public void setMessageListener(MessageListener messageListener);
	
	/**
	 * 获取接收消息时所用的监听器
	 * @author Daniele 
	 * @return
	 */
	public MessageListener getMessageListener();
	
}
