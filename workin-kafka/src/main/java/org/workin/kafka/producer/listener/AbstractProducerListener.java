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

package org.workin.kafka.producer.listener;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListenerAdapter;
import org.workin.kafka.exception.ProducerException;
import org.workin.kafka.support.MQFactory;
import org.workin.kafka.support.ProduceRecord;
import org.workin.kafka.support.ProduceResult;
import org.workin.serialization.Serializer;
import org.workin.serialization.json.codehaus.JacksonSerializer;

/**
 * 生产者监听器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractProducerListener<K,V> extends ProducerListenerAdapter<K,V> {
	
	protected final Logger logger;
	
	/** logger序列化器 */
	protected Serializer loggerSerializer = new JacksonSerializer();
	
	/** 当监听到生产者异常后是否抛出这个异常 */
	private boolean throwExceptionOnError;
	
	public AbstractProducerListener() {
		this.logger = LoggerFactory.getLogger(getClass());
	}
	
	public Serializer getLoggerSerializer() {
		return loggerSerializer;
	}

	public void setLoggerSerializer(Serializer loggerSerializer) {
		this.loggerSerializer = loggerSerializer;
	}

	public boolean isThrowExceptionOnError() {
		return throwExceptionOnError;
	}

	public void setThrowExceptionOnError(boolean throwExceptionOnError) {
		this.throwExceptionOnError = throwExceptionOnError;
	}

	@Override
	public void onSuccess(String topic, Integer partition, K key, V value, RecordMetadata recordMetadata) {
		ProducerRecord<K, V> producerRecord = new ProducerRecord<K, V>(topic, partition, key, value);
		afterSuccess(MQFactory.buildProduceResult(producerRecord, recordMetadata));
	}
	
	@Override
	public void onError(String topic, Integer partition, K key, V value, Exception ex) {
		
		if (throwExceptionOnError)
			/* 当抛出异常后，可在生产者的send方法中catch到异常，
			 * 在一些事务性要求比较严格的应用场景下，catch这个异常很有必要，
			 * 同时也阻止了afterFailure方法的执行 */
			throw new ProducerException(ex);
		
		afterFailure(MQFactory.buildProduceRecord(topic, partition, key, value), ex);
	}
	
	/**
	 * 发送成功以后的监听处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param produceResult
	 */
	protected void afterSuccess(ProduceResult<K, V> produceResult) {
		
	}
	
	/**
	 * 发送错失败以后的监听处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param produceRecord
	 * @param ex
	 */
	protected void afterFailure(ProduceRecord<K, V> produceRecord, Exception ex) {
		
	}

}
