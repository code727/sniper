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
 * Create Date : 2017-3-13
 */

package org.sniper.kafka.consumer.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.sniper.kafka.consumer.ConsumerDelegatePolicy;
import org.sniper.kafka.consumer.service.ConsumerService;
import org.sniper.kafka.consumer.service.ConsumerServiceManager;
import org.sniper.kafka.exception.ConsumerException;
import org.sniper.kafka.support.ConsumeResult;

/**
 * 多委派消费者监听实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MultipleDelegateComsumerListener<K, V> extends DelegateComsumerListener<K, V> {
	
	@Autowired(required = false)
	private ConsumerServiceManager consumerServiceManager;
	
	/** 根据topic名称找不到对应委派时的消费策略 */
	private String delegatePolicy = ConsumerDelegatePolicy.USE_DEFAULT_WHEN_DELEGATE_NOTFOUND.name();
	
	public String getDelegatePolicy() {
		return delegatePolicy;
	}

	public void setDelegatePolicy(String delegatePolicy) {
		this.delegatePolicy = delegatePolicy;
	}

	public ConsumerServiceManager getConsumerSeviceManager() {
		return consumerServiceManager;
	}

	public void setConsumerSeviceManager(ConsumerServiceManager consumerServiceManager) {
		this.consumerServiceManager = consumerServiceManager;
	}

	@Override
	protected void receive(ConsumeResult<K, V> consumeResult) {
		ConsumerService<K, V> delegate = selectDelegate(consumeResult);
		log(delegate, consumeResult);
		
		delegate.receive(consumeResult);
	}
	
	/**
	 * 根据实际消费结果选择对应的委派代表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param consumeResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected ConsumerService<K, V> selectDelegate(ConsumeResult<K, V> consumeResult) {
		// 根据产生实际消费的Topic名称来找到对应的委派代表
		String topicName = consumeResult.getTarget().getName();
		ConsumerService<K, V> consumerSevice = (ConsumerService<K, V>) (consumerServiceManager != null ? 
				consumerServiceManager.getService(topicName) : null);
				
		if (consumerSevice == null) {
			if (ConsumerDelegatePolicy.USE_DEFAULT_WHEN_DELEGATE_NOTFOUND.name().equalsIgnoreCase(delegatePolicy))
				consumerSevice = delegate;
			else
				throw new ConsumerException("Can not found delegate " + "consumer sevice for topic [" + topicName + "]");
		}
		
		return consumerSevice;
	}
	
}


