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

package org.workin.kafka.producer.spring.callback;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.commons.util.CodecUtils;
import org.workin.kafka.producer.service.ProducerSevice;
import org.workin.kafka.support.ProduceRecord;
import org.workin.kafka.support.ProduceResult;

/**
 * 委派生产者回调实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DelegateProducerFutureCallback<K, V> extends
		AbstractProducerFutureCallback<K, V> implements InitializingBean {
	
	@Autowired
	protected ProducerSevice delegate;
	
	public ProducerSevice getDelegate() {
		return delegate;
	}

	public void setDelegate(ProducerSevice delegate) {
		this.delegate = delegate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (delegate == null)
			throw new IllegalArgumentException("Default delegate producer sevice is required");
	}
	
	@Override
	protected void afterSuccess(ProduceResult<K, V> produceResult) {
		logForSuccess(delegate, produceResult);
		delegate.afterSuccess(produceResult);
	}
	
	@Override
	protected void afterFailure(ProduceRecord<K, V> produceRecord, Throwable ex) {
		logForFailure(delegate, produceRecord, ex);
		delegate.afterFailure(produceRecord, ex);
	}
	
	/**
	 * 打印生产者发送成功后的日志
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param delegate
	 * @param produceResult
	 */
	protected void logForSuccess(ProducerSevice delegate, ProduceResult<K, V> produceResult) {
		if (logger.isDebugEnabled())
			logger.debug("Producer success send message:{},will be delegate [{}] execute follow-up task.",
					CodecUtils.bytesToString(loggerSerializer.serialize(produceResult)), delegate.getClass());
	}
	
	/**
	 * 打印生产者发送失败后的日志
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param delegate
	 * @param produceRecord
	 * @param ex
	 */
	protected void logForFailure(ProducerSevice delegate, ProduceRecord<K, V> produceRecord, Throwable ex) {
		if (produceRecord != null)
			logger.error("Producer send message is failure:{},error cause:{},will be delegate [{}] execute follow-up task.",
					CodecUtils.bytesToString(loggerSerializer.serialize(produceRecord)), ex, delegate.getClass());
		else
			logger.error("Producer send message is failure,error cause:{},will be delegate [{}] execute follow-up task.",
					ex, delegate.getClass());
	}

}
