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

package org.sniper.kafka.support;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.sniper.commons.util.AssertUtils;
import org.sniper.kafka.topic.ConsumeTopic;
import org.sniper.kafka.topic.Topic;

/**
 * 消息队列工厂
 * @author  Daniele
 * @version 1.0
 */
public class MQFactory {
	
	/**
	 * 根据topic名称、分区和消息的键值构建出Kafka生产记录
	 * @author Daniele 
	 * @param topic
	 * @param partition
	 * @param key
	 * @param value
	 * @return
	 */
	public static <K, V> ProducerRecord<K, V> buildProducerRecord(String topic, Integer partition, K key, V value) {
		return new ProducerRecord<K, V>(topic, partition, null, key, value);
	}
	
	/**
	 * 根据topic名称、分区和消息的键值构建出可序列化的生产记录
	 * @author Daniele 
	 * @param topic
	 * @param partition
	 * @param key
	 * @param value
	 * @return
	 */
	public static <K, V> ProduceRecord<K, V> buildProduceRecord(String topic, Integer partition, K key, V value) {
		AssertUtils.assertNotBlank(topic, "Producer topic name must not be null or blank.");
		
		Topic sourceTopic = new Topic(topic, partition, null);
		Message<K, V> message = new Message<K, V>(key, value);
		
		return new ProduceRecord<K, V>(sourceTopic, message);
	}
	
	/**
	 * 根据Kafka ProducerRecord对象构建出可序列化的生产记录
	 * @author Daniele 
	 * @param record
	 * @return
	 */
	public static <K, V> ProduceRecord<K, V> buildProduceRecord(ProducerRecord<K, V> record) {
		AssertUtils.assertNotNull(record, "Producer record must not be null.");
		
		Topic sourceTopic = new Topic(record.topic(), record.partition(), record.timestamp());
		Message<K, V> message = new Message<K, V>(record.key(), record.value());
		
		return new ProduceRecord<K, V>(sourceTopic, message);
	}
	
	/**
	 * 根据生产者发送结果构建出可序列化的生产结果
	 * @author Daniele 
	 * @param result
	 * @return
	 */
	public static <K, V> ProduceResult<K, V> buildProduceResult(SendResult<K, V> result) {
		AssertUtils.assertNotNull(result, "Producer send result must not be null.");
		return buildProduceResult(result.getProducerRecord(), result.getRecordMetadata());
	}
	
	/**
	 * 根据生产者记录和元数据构建出可序列化的生产结果
	 * @author Daniele 
	 * @param record
	 * @return
	 */
	public static <K, V> ProduceResult<K, V> buildProduceResult(ProducerRecord<K, V> record, RecordMetadata metadata) {
		AssertUtils.assertNotNull(record, "Producer record must not be null.");
		AssertUtils.assertNotNull(record, "Producer record must metadata not be null.");
		
		Topic source = new Topic(record.topic(), record.partition(), record.timestamp());
		Topic target = new Topic(metadata.topic(), metadata.partition(), metadata.timestamp());
		Message<K, V> message = new Message<K, V>(record.key(), record.value());
		
		return new ProduceResult<K, V>(source, target, message);
	}
	
	/**
	 * 根据消费者记录构建出可序列化的消费结果
	 * @author Daniele 
	 * @param record
	 * @return
	 */
	public static <K, V> ConsumeResult<K, V> buildConsumeResult(ConsumerRecord<K, V> record) {
		AssertUtils.assertNotNull(record, "Consumer record must not be null.");
		
		ConsumeTopic topic = new ConsumeTopic(record.topic(), record.partition(), record.timestamp(), record.offset(),
				record.timestampType(), record.serializedKeySize(), record.serializedValueSize());
		Message<K, V> message = new Message<K, V>(record.key(), record.value());
		
		return new ConsumeResult<K, V>(topic, message);
	}

}
