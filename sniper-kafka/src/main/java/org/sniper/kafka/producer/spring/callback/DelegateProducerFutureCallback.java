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

package org.sniper.kafka.producer.spring.callback;

import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.sniper.kafka.producer.service.ProducerService;
import org.sniper.kafka.support.ProduceRecord;
import org.sniper.kafka.support.ProduceResult;

/**
 * 委派生产者回调实现类
 * @author  Daniele
 * @version 1.0
 */
public class DelegateProducerFutureCallback<K, V> extends
		AbstractProducerFutureCallback<K, V, ProduceResult<K, V>> implements InitializingBean {
	
	@Autowired
	protected ProducerService<K, V> delegate;
	
	public ProducerService<K, V> getDelegate() {
		return delegate;
	}

	public void setDelegate(ProducerService<K, V> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (delegate == null)
			throw new IllegalArgumentException("Default delegate producer sevice is required");
	}
	
	@Override
	public void afterSuccess(ProduceResult<K, V> produceResult) {
		logForSuccess(delegate, produceResult);
		delegate.afterSuccess(produceResult);
	}
	
	@Override
	public void afterFailure(ProduceRecord<K, V> produceRecord, Throwable ex) {
		logForFailure(delegate, produceRecord, ex);
		delegate.afterFailure(produceRecord, ex);
	}
	
	/**
	 * 打印生产者发送成功后的日志
	 * @author Daniele 
	 * @param delegate
	 * @param produceResult
	 */
	protected void logForSuccess(ProducerService<K, V> delegate, ProduceResult<K, V> produceResult) {
//		producerBehavior.successLog(produceResult);
		Logger logger = producerBehavior.getLogger();
		if (logger.isDebugEnabled())
			logger.debug("Delegate {} execute 'afterSuccess' service", delegate.getClass());
	}
	
	/**
	 * 打印生产者发送失败后的日志
	 * @author Daniele 
	 * @param delegate
	 * @param produceRecord
	 * @param ex
	 */
	protected void logForFailure(ProducerService<K, V> delegate, ProduceRecord<K, V> produceRecord, Throwable ex) {
//		producerBehavior.errorLog(produceRecord, ex);
		producerBehavior.getLogger().error("Delegate {} execute 'afterFailure' service", delegate.getClass());
	}

}
