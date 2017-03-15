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

package org.workin.kafka.consumer.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.workin.kafka.consumer.DelegatePolicy;
import org.workin.kafka.consumer.service.ConsumerSevice;
import org.workin.kafka.consumer.service.ConsumerSeviceManager;
import org.workin.kafka.exception.ConsumerException;
import org.workin.kafka.support.ConsumeResult;

/**
 * 多委派消费者监听实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MultipleDelegateComsumerListener<K, V> extends DelegateComsumerListener<K, V> {
	
	@Autowired(required = false)
	private ConsumerSeviceManager consumerSeviceManager;
	
	/** 根据topic名称找不到对应委派时的消费策略 */
	private String delegatePolicy = DelegatePolicy.USE_DEFAULT_WHEN_DELEGATE_NOTFOUND.name();
	
	public String getDelegatePolicy() {
		return delegatePolicy;
	}

	public void setDelegatePolicy(String delegatePolicy) {
		this.delegatePolicy = delegatePolicy;
	}

	public ConsumerSeviceManager getConsumerSeviceManager() {
		return consumerSeviceManager;
	}

	public void setConsumerSeviceManager(ConsumerSeviceManager consumerSeviceManager) {
		this.consumerSeviceManager = consumerSeviceManager;
	}

	@Override
	protected void receive(ConsumeResult<K, V> consumeResult) {
		ConsumerSevice delegate = selectDelegate(consumeResult);
		log(delegate, consumeResult);
		
		delegate.receive(consumeResult);
	}
	
	/**
	 * 根据实际消费结果选择对应的委派代表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param consumeResult
	 * @return
	 */
	protected ConsumerSevice selectDelegate(ConsumeResult<K, V> consumeResult) {
		// 根据产生实际消费的Topic名称来找到对应的委派代表
		String topicName = consumeResult.getConsumeTopic().getName();
		ConsumerSevice consumerSevice = (consumerSeviceManager != null ? 
				consumerSeviceManager.getConsumerSevice(topicName) : null);
				
		if (consumerSevice == null) {
			if (DelegatePolicy.USE_DEFAULT_WHEN_DELEGATE_NOTFOUND.name().equalsIgnoreCase(delegatePolicy))
				consumerSevice = delegate;
			else
				throw new ConsumerException("Can not found delegate " + "consumer sevice for topic [" + topicName + "]");
		}
		
		return consumerSevice;
	}
	
}


