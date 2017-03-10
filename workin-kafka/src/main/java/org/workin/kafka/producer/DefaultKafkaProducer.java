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

package org.workin.kafka.producer;

import java.util.List;

import org.apache.kafka.common.PartitionInfo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.workin.kafka.TopicNode;

/**
 * Kafka生产者实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultKafkaProducer<K, V> extends KafkaProducerSupport<K, V>
		implements KafkaProducer<K, V> {

	@Override
	public ListenableFuture<SendResult<K, V>> send(V data) {
		ListenableFuture<SendResult<K, V>> future = getKafkaTemplate().sendDefault(data);
		return future;
	}

	@Override
	public ListenableFuture<SendResult<K, V>> send(K key, V data) {
		ListenableFuture<SendResult<K, V>> future = getKafkaTemplate().sendDefault(key, data);
		return future;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> void send(V data, ListenableFutureCallback<T> callback) {
		ListenableFuture<SendResult<K, V>> future = getKafkaTemplate().sendDefault(data);
		if (callback != null)
			future.addCallback((ListenableFutureCallback<? super SendResult<K, V>>) callback);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void send(K key, V data, ListenableFutureCallback<T> callback) {
		ListenableFuture<SendResult<K, V>> future = getKafkaTemplate().sendDefault(key, data);
		if (callback != null)
			future.addCallback((ListenableFutureCallback<? super SendResult<K, V>>) callback);
	}

	@Override
	public ListenableFuture<SendResult<K, V>> send(String topicKey, V data) {
		return send(topicKey, null, data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListenableFuture<SendResult<K, V>> send(String topicKey, K key, V data) {
		KafkaTemplate<K, V> kafkaTemplate = getKafkaTemplate();
		
		TopicNode topicNode = getTopicNode(topicKey);
		Integer partition = getPartition(topicNode);
		
		ListenableFuture<SendResult<K, V>> future;
		if (partition != null)
			future = kafkaTemplate.send(getTopicName(topicNode), partition, key, data);
		else
			future = kafkaTemplate.send(getTopicName(topicNode), key, data);
		
		ListenableFutureCallback<?> callback = getFutureCallback(topicNode);
		if (callback != null)
			future.addCallback((ListenableFutureCallback<? super SendResult<K, V>>) callback);
		
		return future;
	}

	@Override
	public List<PartitionInfo> partitionsFor() {
		return partitionsFor(null);
	}

	@Override
	public List<PartitionInfo> partitionsFor(String topicKey) {
		return getKafkaTemplate().partitionsFor(getTopicName(getTopicNode(topicKey)));
	}

	@Override
	public void flush() {
		getKafkaTemplate().flush();
	}

}
