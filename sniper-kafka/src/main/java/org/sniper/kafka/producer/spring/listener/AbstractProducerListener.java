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

package org.sniper.kafka.producer.spring.listener;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.sniper.kafka.exception.ProducerException;
import org.sniper.kafka.producer.behavior.DefaultProducerBehavior;
import org.sniper.kafka.producer.behavior.ProducerBehavior;
import org.sniper.kafka.producer.service.ProducerService;
import org.sniper.kafka.support.MQFactory;
import org.sniper.kafka.support.ProduceRecord;
import org.sniper.kafka.support.ProduceResult;
import org.springframework.kafka.support.ProducerListener;

/**
 * 生产者监听器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractProducerListener<K,V> implements ProducerListener<K, V>, ProducerService<K, V> {
	
	protected ProducerBehavior producerBehavior = new DefaultProducerBehavior(getClass());
	
	public ProducerBehavior getProducerBehavior() {
		return producerBehavior;
	}

	public void setProducerBehavior(ProducerBehavior producerBehavior) {
		this.producerBehavior = producerBehavior;
	}

	/**
	 * 重写父类方法，修改为根据配置的属性来判断
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	@Override
	public boolean isInterestedInSuccess() {
		return producerBehavior.isInterestedInSuccess();
	}
	
	@Override
	public void onSuccess(String topic, Integer partition, K key, V value, RecordMetadata recordMetadata) {
		if (isInterestedInSuccess()) {
			ProducerRecord<K, V> producerRecord = new ProducerRecord<K, V>(topic, partition, key, value);
			afterSuccess(MQFactory.buildProduceResult(producerRecord, recordMetadata));
		}
	}
	
	@Override
	public void onError(String topic, Integer partition, K key, V value, Exception ex) {
		afterFailure(MQFactory.buildProduceRecord(topic, partition, key, value), ex);
		if (producerBehavior.isThrowExceptionOnError())
			/* 当抛出异常后，可在生产者的send方法中catch到异常，
			 * 在一些事务性要求比较严格的应用场景下，catch这个异常很有必要*/
			throw new ProducerException(ex);
	}
	
	/**
	 * 发送成功以后的监听处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param produceResult
	 */
	@Override
	public void afterSuccess(ProduceResult<K, V> produceResult) {
		
	}
	
	/**
	 * 发送错失败以后的监听处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param produceRecord
	 * @param ex
	 */
	@Override
	public void afterFailure(ProduceRecord<K, V> produceRecord, Throwable ex) {
		
	}

}
