/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-3-12
 */

package org.sniper.kafka.consumer.listener;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.sniper.commons.util.CodecUtils;
import org.sniper.kafka.consumer.service.ConsumerService;
import org.sniper.kafka.support.ConsumeResult;

/**
 * 委派消费者监听实现类
 * @author  Daniele
 * @version 1.0
 */
public class DelegateComsumerListener<K, V> extends
		AbstractConsumerListener<K, V> implements InitializingBean {
	
	/** 被委派用于消费的服务 */
	@Autowired
	protected ConsumerService<K, V> delegate;
		
	public ConsumerService<K, V> getDelegate() {
		return delegate;
	}

	public void setDelegate(ConsumerService<K, V> delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (delegate == null)
			throw new IllegalArgumentException("Default delegate consumer sevice is required");
	}
	
	@Override
	protected void receive(ConsumeResult<K, V> consumeResult) {	
		log(delegate, consumeResult);
		delegate.receive(consumeResult);
	}
	
	/**
	 * 日志记录
	 * @author Daniele 
	 * @param delegate
	 * @param consumeResult
	 */
	protected void log(ConsumerService<K, V> delegate, ConsumeResult<K, V> consumeResult) {
		if (logger.isDebugEnabled())
			logger.debug("Consumer success receive message:{},will be delegate {} execute receive task.",
					CodecUtils.bytesToString(loggerSerializer.serialize(consumeResult)), delegate.getClass());
	}

}
