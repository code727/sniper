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
 * Create Date : 2017-3-7
 */

package org.workin.kafka.producer.spring;

import java.util.List;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.kafka.core.KafkaOperations.ProducerCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SettableListenableFuture;
import org.workin.commons.KayValuePair;
import org.workin.kafka.producer.KafkaProducer;
import org.workin.kafka.producer.MessagePacket;
import org.workin.kafka.support.ProduceResult;

/**
 * SpringKafka生产者实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SpringKafkaProducer extends SpringKafkaProducerSupport implements KafkaProducer {

	@Override
	public <K, V> Future<ProduceResult<K, V>> sendDefault(V value) {
		return sendDefault(null, value);
	}

	@Override
	public <K, V> Future<ProduceResult<K, V>> sendDefault(K key, V value) {
		return sendDefault(new MessagePacket<K, V>(key, value));
	}
	
	@Override
	public <K, V> Future<ProduceResult<K, V>> sendDefault(KayValuePair<K, V> pair) {
		return sendDefault(new MessagePacket<K, V>(pair));
	}
	
	@Override
	public <K, V> Future<ProduceResult<K, V>> sendDefault(MessagePacket<K, V> packet) {
		return send(getKafkaTemplate().getDefaultTopic(), packet);
	}

	@Override
	public <K, V> ProduceResult<K, V> sendDefaultAndWait(V value) throws Exception {
		return sendDefaultAndWait(null, value);
	}

	@Override
	public <K, V> ProduceResult<K, V> sendDefaultAndWait(K key, V value) throws Exception {
		return sendDefaultAndWait(new MessagePacket<K, V>(key, value));
	}
	
	@Override
	public <K, V> ProduceResult<K, V> sendDefaultAndWait(KayValuePair<K, V> pair) throws Exception {
		return sendDefaultAndWait(pair.getKey(), pair.getValue());
	}
	
	@Override
	public <K, V> ProduceResult<K, V> sendDefaultAndWait(MessagePacket<K, V> packet) throws Exception {
		Future<ProduceResult<K, V>> future = sendDefault(packet);
		return future.get();
	}
	
	@Override
	public <K, V> Future<ProduceResult<K, V>> send(String name, V value) {
		return send(name, null, value);
	}

	@Override
	public <K, V> Future<ProduceResult<K, V>> send(String name, K key, V value) {
		return send(name, new MessagePacket<K, V>(key, value));
	}
	
	@Override
	public <K, V> Future<ProduceResult<K, V>> send(String name, KayValuePair<K, V> pair) {
		return send(name, new MessagePacket<K, V>(pair));
	}

	@Override
	public <K, V> Future<ProduceResult<K, V>> send(String name, MessagePacket<K, V> packet) {
		return send(createProducerRecord(name, packet));
	}

	@Override
	public <K, V> ProduceResult<K, V> sendAndWait(String name, V value) throws Exception {
		return sendAndWait(name, null, value);
	}

	@Override
	public <K, V> ProduceResult<K, V> sendAndWait(String name, K key, V value) throws Exception {
		return sendAndWait(name, new MessagePacket<K, V>(key, value));
	}
	
	@Override
	public <K, V> ProduceResult<K, V> sendAndWait(String name, KayValuePair<K, V> pair) throws Exception {
		return sendAndWait(name, new MessagePacket<K, V>(pair));
	}

	@Override
	public <K, V> ProduceResult<K, V> sendAndWait(String name, MessagePacket<K, V> packet) throws Exception {
		Future<ProduceResult<K, V>> future = send(name, packet);
		return future.get();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Future<ProduceResult<K, V>> send(final ProducerRecord<K, V> producerRecord) {
		KafkaTemplate<K, V> kafkaTemplate = (KafkaTemplate<K, V>) getKafkaTemplate();
		Future<ProduceResult<K, V>> result = kafkaTemplate.execute(new ProducerCallback<K, V, Future<ProduceResult<K, V>>>() {

			@Override
			public Future<ProduceResult<K, V>> doInKafka(Producer<K, V> producer) {
				SettableListenableFuture<ProduceResult<K, V>> future = new SettableListenableFuture<ProduceResult<K, V>>();
				producer.send(producerRecord, createProduceCallback(future, producer, producerRecord));
				
				if (producerCallback != null)
					future.addCallback((ListenableFutureCallback<? super ProduceResult<K, V>>) producerCallback);
				
				return future;
			}
		});
		
		if (isAutoFlush())
			flush();
		
		return result;
	}

	@Override
	public <K, V> ProduceResult<K, V> sendAndWait(ProducerRecord<K, V> producerRecord) throws Exception {
		Future<ProduceResult<K, V>> future = send(producerRecord);
		return future.get();
	}

	@Override
	public List<PartitionInfo> partitionsFor() {
		return partitionsFor(getKafkaTemplate().getDefaultTopic());
	}

	@Override
	public List<PartitionInfo> partitionsFor(String name) {
		return getKafkaTemplate().partitionsFor(name);
	}

	@Override
	public void flush() {
		getKafkaTemplate().flush();
	}

}
