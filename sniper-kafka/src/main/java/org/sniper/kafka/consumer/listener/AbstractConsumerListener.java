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

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.sniper.kafka.support.ConsumeResult;
import org.sniper.kafka.support.MQFactory;
import org.sniper.serialization.Serializer;
import org.sniper.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;

/**
 * 消费者监听抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractConsumerListener<K, V> implements ConsumerListener<K, V> {
	
	protected final Logger logger;
	
	protected Serializer loggerSerializer = new FasterxmlJacksonSerializer();
		
	public AbstractConsumerListener() {
		logger = LoggerFactory.getLogger(getClass()); 
	}
	
	public Serializer getLoggerSerializer() {
		return loggerSerializer;
	}

	public void setLoggerSerializer(Serializer loggerSerializer) {
		this.loggerSerializer = loggerSerializer;
	}

	@Override
	public void onMessage(ConsumerRecord<K, V> record) {
		onMessage(record, (Acknowledgment) null);
	}
	
	@Override
	public void onMessage(ConsumerRecord<K, V> record, Acknowledgment acknowledgment) {
		if (acknowledgment != null)
			// ACK回复
			acknowledgment.acknowledge();
		
		receive(MQFactory.buildConsumeResult(record));
	}
	
	/**
	 * 处理接收到的消费结果
	 * @author Daniele 
	 * @param consumeResult
	 */
	protected abstract void receive(ConsumeResult<K, V> consumeResult);
	
}
