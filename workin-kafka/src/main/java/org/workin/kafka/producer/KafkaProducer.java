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

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.workin.kafka.support.ProduceResult;

/**
 * Kafka生产者接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface KafkaProducer<K, V> extends KafkaProducerOperations {
	
	/**
	 * 将消息数据发送到默认Topic中，发送结束后回调指定的事件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public <T> void sendDefault(V data, ListenableFutureCallback<T> callback);
	
	/**
	 * 将键值对数据发送到默认Topic，发送结束后回调指定的事件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param data
	 * @param callback
	 */
	public <T> void sendDefault(K key, V data, ListenableFutureCallback<T> callback);
	
	/**
	 * 将消息数据发送到默认Topic的指定分区中，发送结束后回调指定的事件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param partition
	 * @param data
	 * @param callback
	 */
	public <T> void sendDefaultToPartition(Integer partition, V data, ListenableFutureCallback<T> callback);
	
	/** 
	 * 将键值对数据发送到默认Topic的指定分区中，发送结束后回调指定的事件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param partition
	 * @param key
	 * @param data
	 * @param callback 
	 */
	public <T> void sendDefaultToPartition(Integer partition, K key, V data, ListenableFutureCallback<T> callback);
	
	/**
	 * 将消息数据发送到默认Topic中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public ListenableFuture<SendResult<K, V>> sendDefault(V data);
	
	/**
	 * 将键值对消息数据发送到默认Topic中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param data
	 * @return
	 */
	public ListenableFuture<SendResult<K, V>> sendDefault(K key, V data);
	
	/**
	 * 将消息数据发送到默认Topic的指定分区中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param partition
	 * @param data
	 * @return
	 */
	public ListenableFuture<SendResult<K, V>> sendDefaultToPartition(Integer partition, V data);
	
	/**
	 * 将键值对消息数据发送到默认Topic的指定分区中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param partition
	 * @param key
	 * @param data
	 * @return
	 */
	public ListenableFuture<SendResult<K, V>> sendDefaultToPartition(Integer partition, K key, V data);
	
	/**
	 * 将消息数据发送到默认Topic中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public ProduceResult<K, V> sendDefaultAndWait(V data) throws Exception;
	
	/**
	 * 将键值对消息数据发送到默认Topic中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param data
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public ProduceResult<K, V> sendDefaultAndWait(K key, V data) throws Exception;
	
	/**
	 * 将消息数据发送到默认Topic的指定分区中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param partition
	 * @param data
	 * @returns 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public ProduceResult<K, V> sendDefaultToPartitionAndWait(Integer partition, V data) throws Exception;
	
	/**
	 *  将键值对消息数据发送到默认Topic的指定中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param partition
	 * @param key
	 * @param data
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public ProduceResult<K, V> sendDefaultToPartitionAndWait(Integer partition, K key, V data) throws Exception;
		
	/**
	 * 将消息数据发送到指定的Topic实例中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicKey Topic实例的键
	 * @param data
	 * @return
	 */
	public ListenableFuture<SendResult<K, V>> send(String topicKey, V data);
	
	/**
	 * 将键值对消息数据发送到指定的Topic实例中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicKey Topic实例的键
	 * @param key
	 * @param data
	 * @return
	 */
	public ListenableFuture<SendResult<K, V>> send(String topicKey, K key, V data);
	
	/**
	 * 将消息数据发送到指定的Topic实例中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicKey Topic实例的键
	 * @param data
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public ProduceResult<K, V> sendAndWait(String topicKey, V data) throws Exception;
	
	/**
	 * 将键值对消息数据发送到指定的Topic实例中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicKey Topic实例的键
	 * @param key
	 * @param data
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public ProduceResult<K, V> sendAndWait(String topicKey, K key, V data) throws Exception;

}
