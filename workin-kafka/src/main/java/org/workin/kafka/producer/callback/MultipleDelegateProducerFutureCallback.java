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
 * Create Date : 2017-3-14
 */

package org.workin.kafka.producer.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.workin.kafka.exception.ProducerException;
import org.workin.kafka.producer.DelegatePolicy;
import org.workin.kafka.producer.service.ProducerSevice;
import org.workin.kafka.producer.service.ProducerSeviceManager;
import org.workin.kafka.support.ProduceRecord;
import org.workin.kafka.support.ProduceResult;

/**
 * 多委派生产者回调实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MultipleDelegateProducerFutureCallback<K,V> extends DelegateProducerFutureCallback<K, V> {
	
	@Autowired(required = false)
	private ProducerSeviceManager producerSeviceManager;
	
	/** 根据topic名称找不到对应委派时的生产策略 */
	private String delegatePolicy = DelegatePolicy.USE_DEFAULT_WHEN_DELEGATE_NOTFOUND.name();
	
	public String getDelegatePolicy() {
		return delegatePolicy;
	}

	public void setDelegatePolicy(String delegatePolicy) {
		this.delegatePolicy = delegatePolicy;
	}
	
	@Override
	protected void afterSuccess(ProduceResult<K, V> produceResult) {
		ProducerSevice delegate = selectDelegate(produceResult);
		logForSuccess(delegate, produceResult);
		
		delegate.afterSuccess(produceResult);
	}
	
	@Override
	protected void afterFailure(ProduceRecord<K, V> produceRecord, Throwable ex) {
		ProducerSevice delegate = selectDelegate(produceRecord);
		logForFailure(delegate, produceRecord, ex);
		
		delegate.afterFailure(produceRecord, ex);
	}
	
	/**
	 * 根据生产记录选择对应的委派代表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicName
	 * @return
	 */
	protected ProducerSevice selectDelegate(ProduceRecord<K, V> produceRecord) {
		// 根据源Topic名称来找到对应的委派代表
		String topicName = (produceRecord != null? produceRecord.getSourceTopic().getName() : null);
		ProducerSevice producerSevice = (producerSeviceManager != null ? 
				producerSeviceManager.getProducerSevice(topicName) : null);
		
		if (producerSevice == null) {
			if (DelegatePolicy.USE_DEFAULT_WHEN_DELEGATE_NOTFOUND.name().equalsIgnoreCase(delegatePolicy))
				producerSevice = delegate;
			else
				throw new ProducerException("Can not found delegate producer sevice for topic [" + topicName + "]");
		}
		
		return producerSevice;
	}
		
}
