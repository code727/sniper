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

package org.sniper.jms.core.strategy;

import javax.jms.Destination;

import org.springframework.jms.support.converter.MessageConverter;

/**
 * JMS会话访问策略接口
 * @author  Daniele
 * @version 1.0
 */
public interface SharedStrategy {
	
	/**
	 * 设置是否采用发布/订阅方式发送或接收消息
	 * @author Daniele 
	 * @param pubSubDomain
	 */
	public void setPubSubDomain(boolean pubSubDomain);
	
	/**
	 * 判断是否采用发布/订阅方式发送或接收消息
	 * @author Daniele 
	 * @return
	 */
	public boolean isPubSubDomain();
	
	/**
	 * 设置是否使用事务
	 * @author Daniele 
	 * @param sessionTransacted
	 */
	public void setSessionTransacted(boolean sessionTransacted);
	
	/**
	 * 判断是否使用了事务
	 * @author Daniele 
	 * @return
	 */
	public boolean isSessionTransacted();
	
	/**
	 * 设置会话确认模式
	 * @author Daniele 
	 * @param sessionAcknowledgeMode
	 */
	public void setSessionAcknowledgeMode(int sessionAcknowledgeMode);
	
	/**
	 * 获取会话确认模式
	 * @author Daniele 
	 * @return
	 */
	public int getSessionAcknowledgeMode();
	
	 /**
     * 设置消息的目的地
     * @author Daniele 
     * @param destination
     */
    public void setDestination(Destination destination);

    /**
     * 获取消息的目的地
     * @author Daniele 
     * @return
     */
    public Destination getDestination();
    
    /**
     * 设置消息转换器
     * @author Daniele 
     * @param messageConverter
     */
    public void setMessageConverter(MessageConverter messageConverter);

    /**
     * 获取消息转换器
     * @author Daniele 
     * @return
     */
	public MessageConverter getMessageConverter();

	/**
     * 设置消息的转发模式
     * @author Daniele 
     * @param deliveryMode
     */
    public void setDeliveryMode(int deliveryMode);

    /**
     * 获取消息的转发模式
     * @author Daniele 
     * @return
     */
    public int getDeliveryMode();

	/** 
	 * 设置当抛出异常时，是否自动回滚事务
	 * @author Daniele 
	 * @param autoRollback 
	 */
    public void setAutoRollback(boolean autoRollback);

	/** 
	 * 判断当抛出异常时，是否自动回滚事务
	 * @author Daniele 
	 * @return 
	 */
    public boolean isAutoRollback();

}
