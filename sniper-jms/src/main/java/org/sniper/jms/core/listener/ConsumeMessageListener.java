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
 * Create Date : 2015-8-18
 */

package org.sniper.jms.core.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;

import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.jms.support.JmsUtils;
import org.sniper.jms.core.strategy.ConsumeStrategy;

/**
 * 消费消息监听器
 * @author  Daniele
 * @version 1.0
 */
public abstract class ConsumeMessageListener extends ContextMessageListener {
	
	/** 消费对象 */
	private MessageConsumer consumer;
	
	/** 消费策略*/
	private ConsumeStrategy strategy;
	
	public MessageConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(MessageConsumer consumer) {
		this.consumer = consumer;
	}

	public ConsumeStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(ConsumeStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public void onMessage(Message message) {
		try {
			Object result = strategy.getMessageConverter().fromMessage(message);
			doReceiveHandle(result);
			
			if (org.sniper.jms.util.JmsUtils.hasTransactional(getSession(), strategy, getConnectionFactory()))
				JmsUtils.commitIfNecessary(getSession());
			/* 
			 * 如果使用客户端自行通知，则需要在MessageListener里
			 * 显式调用message.acknowledge()来通知服务器，服务器接收到通知后采取相应的操作。
			 */
			if (org.sniper.jms.util.JmsUtils.isClientAcknowledge(getSession()))
				message.acknowledge();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				org.sniper.jms.util.JmsUtils.autoRollback(getSession(), strategy, getConnectionFactory());
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
		} finally {
			JmsUtils.closeMessageConsumer(consumer);
			JmsUtils.closeSession(getSession());
			ConnectionFactoryUtils.releaseConnection(getConnection(), null, false);
		}
	}
	
	protected abstract <T> void doReceiveHandle(T message) throws Exception;
	
}