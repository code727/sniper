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

package org.workin.kafka.producer.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.workin.kafka.support.MQFactory;
import org.workin.kafka.support.ProduceRecord;
import org.workin.kafka.support.ProduceResult;
import org.workin.serialization.Serializer;
import org.workin.serialization.json.JacksonSerializer;

/**
 * 生产者回调抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractProducerFutureCallback<K, V> implements
		ListenableFutureCallback<SendResult<K, V>> {
	
	protected final Logger logger;
	
	/** logger序列化器 */
	protected Serializer loggerSerializer = new JacksonSerializer();
	
	/** 是否关注Success事件的发生 */
	private boolean interestedInSuccess = true;
	
	public AbstractProducerFutureCallback() {
		logger = LoggerFactory.getLogger(getClass());
	}
	
	public Serializer getLoggerSerializer() {
		return loggerSerializer;
	}

	public void setLoggerSerializer(Serializer loggerSerializer) {
		this.loggerSerializer = loggerSerializer;
	}
	
	public boolean isInterestedInSuccess() {
		return interestedInSuccess;
	}

	public void setInterestedInSuccess(boolean interestedInSuccess) {
		this.interestedInSuccess = interestedInSuccess;
	}

	@Override
	public void onSuccess(SendResult<K, V> result) {
		if (interestedInSuccess)
			afterSuccess(MQFactory.buildProduceResult(result));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onFailure(Throwable ex) {
		ProduceRecord<K, V> produceRecord = null;
		
		if (ex instanceof KafkaProducerException) 
			produceRecord = (ProduceRecord<K, V>) MQFactory.buildProduceRecord(
					((KafkaProducerException) ex).getProducerRecord());
	
		afterFailure(produceRecord, ex);
	}
	
	/**
	 * 生产者发送消息成功以后的业务处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param produceResult
	 */
	protected void afterSuccess(ProduceResult<K, V> produceResult) {
		
	}
	
	/**
	 * 生产者发送消息失败以后的业务处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param produceRecord
	 * @param ex
	 */
	protected void afterFailure(ProduceRecord<K, V> produceRecord, Throwable ex) {
		
	}

}
