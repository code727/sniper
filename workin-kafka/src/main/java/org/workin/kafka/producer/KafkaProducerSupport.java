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

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.workin.commons.util.StringUtils;
import org.workin.kafka.topic.TopicManager;
import org.workin.kafka.topic.TopicNode;
import org.workin.spring.beans.CheckableInitializingBean;

/**
 * 生产者支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class KafkaProducerSupport extends CheckableInitializingBean {
	
	protected static final String DEFAULT_TOPICNAME = "default";
	
	protected TopicNode defaultTopic;
	
    private TopicManager topicManager;
    
	public TopicNode getDefaultTopic() {
		return defaultTopic;
	}

	public void setDefaultTopic(TopicNode defaultTopic) {
		this.defaultTopic = defaultTopic;
	}

	public TopicManager getTopicManager() {
		return topicManager;
	}

	public void setTopicManager(TopicManager topicManager) {
		this.topicManager = topicManager;
	}
		
	@Override
	protected void init() throws Exception {
		if (defaultTopic == null) {
			defaultTopic = new TopicNode(DEFAULT_TOPICNAME);
		} else if (StringUtils.isBlank(defaultTopic.getName()))
			defaultTopic.setName(DEFAULT_TOPICNAME);
	}
	
	/**
	 * 根据注册名称获取Topic节点
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	protected TopicNode getTopicNode(String name) {
		return topicManager != null ? topicManager.getTopicNode(name) : null;
	}
	
	/**
	 * 获取Topic节点名称，节点为空时返回指定的名称，若指定的名称也为空，返回全局默认的名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicNode
	 * @param name
	 * @return
	 */
	protected String getTopicName(TopicNode topicNode, String name) {
		if (topicNode == null)
			return StringUtils.isNotBlank(name) ? name : defaultTopic.getName();
		
		String topicName = topicNode.getName();
		return StringUtils.isNotBlank(topicName) ? topicName
				: (StringUtils.isNotBlank(name) ? name : defaultTopic.getName());
	}
		
	/**
	 * 根据名称获取Topic节点上注册的生产回调事件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	protected Callback getCallback(String name) {
		String topicName = getTopicName(getTopicNode(name), name);
		TopicNode topicNode = getTopicNode(topicName);
		
		return (topicNode != null ? topicNode.getCallback() : null);
	}
	
	/**
	 * 根据注册名称获取Topic节点上注册的各项生产数据指标后创建出原生Kafka生产记录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name Topic注册名
	 * @param key
	 * @param value
	 * @return
	 */
	protected <K, V> ProducerRecord<K, V> createProducerRecord(String name, K key, V value) {
		return createProducerRecord(name, key, value, null);
	}
	
	/**
	 * 根据注册名称获取Topic节点上注册的各项生产数据指标后创建出原生Kafka生产记录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name Topic注册名
	 * @param key
	 * @param value
	 * @param partition 未找到节点时传入的分区索引
	 * @return
	 */
	protected <K, V> ProducerRecord<K, V> createProducerRecord(String name, K key, V value, Integer partition) {
		return createProducerRecord(name, key, value, partition, null);
	}
	
	/**
	 * 根据注册名称获取Topic节点上注册的各项生产数据指标后创建出原生Kafka生产记录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param packet
	 * @return
	 */
	protected <K, V> ProducerRecord<K, V> createProducerRecord(String name, MessagePacket<K, V> packet) {
		return createProducerRecord(name, packet.getKey(), packet.getValue(), packet.getPartition(), packet.getTimestamp());
	}
	
	/**
	 * 根据注册名称获取Topic节点上注册的各项生产数据指标后创建出原生Kafka生产记录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name Topic注册名
	 * @param key
	 * @param value
	 * @param partition 未找到节点或节点注册的分区索引为空时传入的自定义分区索引
	 * @param timestamp 未找到节点或节点注册的时间戳为空时传入的自定义时间戳
	 * @return
	 */
	protected <K, V> ProducerRecord<K, V> createProducerRecord(String name, K key, V value, Integer partition, Long timestamp) {
		// 先根据注册名称找到节点真实的名称，否则就按注册名称作为默认名称
		String topicName = getTopicName(getTopicNode(name), name);
		// topicName有可能返回的是全局默认名称，因此这里再找一次节点
		TopicNode topicNode = getTopicNode(topicName);
		
		if (topicNode != null) {
			Integer recordPartition = (topicNode.getPartition() != null ? topicNode.getPartition() : partition);
			Long recordTimestamp = (topicNode.getTimestamp() != null ? topicNode.getTimestamp() : timestamp);
			return new ProducerRecord<K, V>(topicNode.getName(), recordPartition, recordTimestamp, key, value);
		} else {
			return new ProducerRecord<K, V>(topicName, partition, timestamp, key, value);
		}
	}
	
}
