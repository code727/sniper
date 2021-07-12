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

package org.sniper.kafka.producer.spring.callback;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.sniper.kafka.exception.ProducerException;
import org.sniper.kafka.producer.behavior.DefaultProducerBehavior;
import org.sniper.kafka.producer.behavior.ProducerBehavior;
import org.sniper.kafka.producer.service.ProducerService;
import org.sniper.kafka.support.MQFactory;
import org.sniper.kafka.support.ProduceRecord;
import org.sniper.kafka.support.ProduceResult;

/**
 * 生产者回调抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractProducerFutureCallback<K, V, T> implements ListenableFutureCallback<T>, ProducerService<K, V> {
	
	protected ProducerBehavior producerBehavior = new DefaultProducerBehavior(getClass());

	public ProducerBehavior getProducerBehavior() {
		return producerBehavior;
	}

	public void setProducerBehavior(ProducerBehavior producerBehavior) {
		this.producerBehavior = producerBehavior;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(T result) {
		if (producerBehavior.isInterestedInSuccess()) {
			if (result instanceof ProduceResult)
				afterSuccess((ProduceResult<K, V>) result);
			else
				afterSuccess(MQFactory.buildProduceResult((SendResult<K, V>) result));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onFailure(Throwable ex) {
		ProduceRecord<K, V> produceRecord = null;
		
		if (ex instanceof KafkaProducerException) {
			ProducerRecord<K, V> record = (ProducerRecord<K, V>) ((KafkaProducerException) ex).getProducerRecord();
			if (record != null)
				produceRecord = (ProduceRecord<K, V>) MQFactory.buildProduceRecord(record);
		}
	
		afterFailure(produceRecord, ex);
		if (producerBehavior.isThrowExceptionOnError())
			throw new ProducerException(ex);
	}
	
	@Override
	public void afterSuccess(ProduceResult<K, V> produceResult) {
		
	}
	
	@Override
	public void afterFailure(ProduceRecord<K, V> produceRecord, Throwable ex) {
		
	}

}
