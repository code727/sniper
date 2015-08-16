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

package org.workin.jms.core.strategy;

import javax.jms.MessageListener;

/**
 * @description JMS消费策略接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ConsumeStrategy extends SharedStrategy {
	
	/**
	 * @description 设置是否不接收来自同一个连接的消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pubSubNoLocal
	 */
	public void setPubSubNoLocal(boolean pubSubNoLocal);

	/**
	 * @description 判断是否不接收来自同一个连接的消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isPubSubNoLocal();
	
	/**
	 * @description 设置消息接收时的超时时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param receiveTimeout
	 */
	public void setReceiveTimeout(long receiveTimeout);

	/**
	 * @description 获取消息接收时的超时时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public long getReceiveTimeout();
	
	/**
	 * @description 设置接收消息时所用的监听器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param messageListener
	 */
	public void setMessageListener(MessageListener messageListener);
	
	/**
	 * @description 获取接收消息时所用的监听器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public MessageListener getMessageListener();

}
