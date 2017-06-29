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

package org.sniper.kafka.producer;

import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.sniper.commons.KayValuePair;
import org.sniper.kafka.support.ProduceResult;

/**
 * 生产者接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface KafkaProducer extends KafkaProducerOperations {
	
	/**
	 * 将消息值发送到默认topic
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value	
	 * @return
	 */
	public <K, V> Future<ProduceResult<K, V>> sendDefault(V value);
	
	/**
	 * 将消息键值发送到默认topic
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Future<ProduceResult<K, V>> sendDefault(K key, V value);
	
	/**
	 * 将键值对消息发送到默认Topic
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pair
	 * @return
	 */
	public <K, V> Future<ProduceResult<K, V>> sendDefault(KayValuePair<K, V> pair);
	
	/**
	 * 将数据包发送到默认Topic
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param packet
	 * @return
	 */
	public <K, V> Future<ProduceResult<K, V>> sendDefault(MessagePacket<K, V> packet);
	
	/**
	 * 将消息值发送到默认topic后等待返回生产结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public <K, V> ProduceResult<K, V> sendDefaultAndWait(V value) throws Exception;
	
	/**
	 * 将消息键值发送到默认Topic后等待返回生产结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 
	 * @param value
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public <K, V> ProduceResult<K, V> sendDefaultAndWait(K key, V value) throws Exception;
	
	/**
	 * 将键值对消息发送到默认Topic后等待返回生产结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pair
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public <K, V> ProduceResult<K, V> sendDefaultAndWait(KayValuePair<K, V> pair) throws Exception;
	
	/**
	 * 将数据包发送到默认Topic后等待返回生产结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param packet
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public <K, V> ProduceResult<K, V> sendDefaultAndWait(MessagePacket<K, V> packet) throws Exception;
	
	/**
	 * 将消息值发送到指定的Topic
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name Topic注册名称
	 * @param value
	 * @return
	 */
	public <K, V> Future<ProduceResult<K, V>> send(String name, V value);
	
	/**
	 * 将消息键值发送到指定的Topic
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name Topic注册名称
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Future<ProduceResult<K, V>> send(String name, K key, V value);
	
	/**
	 * 将键值对消息发送到指定的Topic
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param pair
	 * @return
	 */
	public <K, V> Future<ProduceResult<K, V>> send(String name, KayValuePair<K, V> pair);
	
	/**
	 * 将数据包发送到指定的Topic
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param packet
	 * @return
	 */
	public <K, V> Future<ProduceResult<K, V>> send(String name, MessagePacket<K, V> packet);
	
	/**
	 * 将消息值发送到指定的Topic实例后等待返回生产结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name Topic注册名称
	 * @param value
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public <K, V> ProduceResult<K, V> sendAndWait(String name, V value) throws Exception;
	
	/**
	 * 将消息键值发送到指定的Topic实例后等待返回生产结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name Topic注册名称
	 * @param key
	 * @param value
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public <K, V> ProduceResult<K, V> sendAndWait(String name, K key, V value) throws Exception;
	
	/**
	 * 将键值对消息发送到指定的Topic实例后等待返回生产结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param pair
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public <K, V> ProduceResult<K, V> sendAndWait(String name, KayValuePair<K, V> pair) throws Exception;
	
	/**
	 * 将数据包发送到指定的Topic实例后等待返回生产结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param packet
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public <K, V> ProduceResult<K, V> sendAndWait(String name, MessagePacket<K, V> packet) throws Exception;
	
	/**
	 * 发送生产记录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param producerRecord
	 * @return
	 */
	public <K, V> Future<ProduceResult<K, V>> send(ProducerRecord<K, V> producerRecord);
		
	/**
	 * 发送生产记录后等待返回生产结果    并指定在生产过程中的回调行为
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param producerRecord
	 * @return 超时时间(metadata.fetch.timeout.ms)内等待返回生产结果实例
	 * @throws Exception 当获取生产结果出现超时或内部异常而导致中断时，将统一抛出此异常
	 */
	public <K, V> ProduceResult<K, V> sendAndWait(ProducerRecord<K, V> producerRecord) throws Exception;
		
}