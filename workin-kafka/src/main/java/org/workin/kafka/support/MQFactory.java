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
 * Create Date : 2017-3-9
 */

package org.workin.kafka.support;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.workin.commons.util.AssertUtils;
import org.workin.kafka.Topic;

/**
 * 消息队列工厂
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MQFactory {
	
	/**
	 * 从生产者发送结果中构建出可序列化的生产结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param result
	 * @return
	 */
	public static <K, V> ProduceResult<K, V> buildProduceResult(SendResult<K, V> result) {
		AssertUtils.assertNotNull(result, "Producer send result must not be null.");
		return buildProduceResult(result.getProducerRecord(), result.getRecordMetadata());
	}
	
	/**
	 * 从生产者记录中构建出可序列化的生产结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param record
	 * @return
	 */
	public static <K, V> ProduceResult<K, V> buildProduceResult(ProducerRecord<K, V> record, RecordMetadata metadata) {
		AssertUtils.assertNotNull(record, "Producer record must not be null.");
		
		Topic sourceTopic = new Topic(record.topic(), record.partition(), record.timestamp());
		Topic targetTopic = new Topic(metadata.topic(), metadata.partition(), metadata.timestamp());
		Message<K, V> message = new Message<K, V>(record.key(), record.value());
		
		return new ProduceResult<K, V>(sourceTopic, targetTopic, message);
	}

}
