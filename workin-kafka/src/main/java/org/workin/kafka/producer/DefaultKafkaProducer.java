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
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.workin.kafka.support.MQFactory;
import org.workin.kafka.support.ProduceResult;
import org.workin.kafka.topic.TopicNode;

/**
 * Kafka生产者实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultKafkaProducer<K, V> extends KafkaProducerSupport<K, V>
		implements KafkaProducer<K, V> {
	
	@Override
	public <T> void sendDefault(V data, ListenableFutureCallback<T> callback) {
		sendDefaultToPartition(null, null, data, callback);
	}
	
	@Override
	public <T> void sendDefault(K key, V data, ListenableFutureCallback<T> callback) {
		sendDefaultToPartition(null, key, data, callback);
	}
	
	@Override
	public <T> void sendDefaultToPartition(Integer partition, V data, ListenableFutureCallback<T> callback) {
		sendDefaultToPartition(partition, null, data, callback);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> void sendDefaultToPartition(Integer partition, K key, V data, ListenableFutureCallback<T> callback) {
		ListenableFuture<SendResult<K, V>> future = sendDefaultToPartition(partition, key, data);
		if (callback != null)
			future.addCallback((ListenableFutureCallback<? super SendResult<K, V>>) callback);
	}
	
	@Override
	public ListenableFuture<SendResult<K, V>> sendDefault(V data) {
		return sendDefaultToPartition(null, null, data);
	}
	
	@Override
	public ListenableFuture<SendResult<K, V>> sendDefault(K key, V data) {
		return sendDefaultToPartition(null, key, data);
	}
	
	@Override
	public ListenableFuture<SendResult<K, V>> sendDefaultToPartition(Integer partition, V data) {
		 return sendDefaultToPartition(partition, null, data);
	}
	
	@Override
	public ListenableFuture<SendResult<K, V>> sendDefaultToPartition(Integer partition, K key, V data) {
		return partition != null ? getKafkaTemplate().sendDefault(partition, key,
				data) : getKafkaTemplate().sendDefault(key, data);
	}
	
	@Override
	public ProduceResult<K, V> sendDefaultAndWait(V data) throws Exception {
		return sendDefaultToPartitionAndWait(null, null, data);
	}
	
	@Override
	public ProduceResult<K, V> sendDefaultAndWait(K key, V data) throws Exception {
		return sendDefaultToPartitionAndWait(null, key, data);
	}
	
	@Override
	public ProduceResult<K, V> sendDefaultToPartitionAndWait(Integer partition, V data) throws Exception {
		return sendDefaultToPartitionAndWait(partition, null, data);
	}
	
	@Override
	public ProduceResult<K, V> sendDefaultToPartitionAndWait(Integer partition, K key, V data) throws Exception {
		ListenableFuture<SendResult<K, V>> future = sendDefaultToPartition(partition, key, data);
		return MQFactory.buildProduceResult(future.get());
	}
	
	@Override
	public ListenableFuture<SendResult<K, V>> send(String topicKey, V data) {
		return send(topicKey, null, data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListenableFuture<SendResult<K, V>> send(String topicKey, K key, V data) {
		TopicNode topicNode = getTopicNode(topicKey);
		Integer partition = getPartition(topicNode);
		String topicName = getTopicName(topicNode, topicKey);
		
		ListenableFuture<SendResult<K, V>> future = (partition != null ? 
				getKafkaTemplate().send(topicName, partition, key, data) : getKafkaTemplate().send(topicName, key, data));
		
		ListenableFutureCallback<?> callback = getFutureCallback(topicNode);
		if (callback != null)
			future.addCallback((ListenableFutureCallback<? super SendResult<K, V>>) callback);
		
		return future;
	}
	
	@Override
	public ProduceResult<K, V> sendAndWait(String topicKey, V data) throws Exception {
		return sendAndWait(topicKey, null, data);
	}

	@Override
	public ProduceResult<K, V> sendAndWait(String topicKey, K key, V data) throws Exception {
		ListenableFuture<SendResult<K, V>> future = send(topicKey, key, data);	
		return MQFactory.buildProduceResult(future.get());
	}

	@Override
	public List<PartitionInfo> partitionsFor() {
		return partitionsFor(null);
	}

	@Override
	public List<PartitionInfo> partitionsFor(String topicKey) {
		String topicName = getTopicName(getTopicNode(topicKey), topicKey);
		return getKafkaTemplate().partitionsFor(topicName);
	}

	@Override
	public void flush() {
		getKafkaTemplate().flush();
	}

}
