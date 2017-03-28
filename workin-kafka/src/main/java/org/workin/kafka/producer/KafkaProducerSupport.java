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

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.workin.commons.util.StringUtils;
import org.workin.kafka.topic.TopicManager;
import org.workin.kafka.topic.TopicNode;
import org.workin.spring.beans.CheckableInitializingBean;

/**
 * Kafka生产者支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class KafkaProducerSupport<K, V> extends CheckableInitializingBean {
	
    private KafkaTemplate<K, V> kafkaTemplate;
    
    private TopicManager topicManager;
    
    private static final String defaultTopicName = "default";
		
	public KafkaTemplate<K, V> getKafkaTemplate() {
		return kafkaTemplate;
	}

	public void setKafkaTemplate(KafkaTemplate<K, V> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public TopicManager getTopicManager() {
		return topicManager;
	}

	public void setTopicManager(TopicManager topicManager) {
		this.topicManager = topicManager;
	}
	
	@Override
	protected void checkProperties() {		
		if (kafkaTemplate == null)
			throw new IllegalArgumentException("Property 'kafkaTemplate' is required");
	}
	
	@Override
	protected void init() throws Exception {
		if (StringUtils.isBlank(kafkaTemplate.getDefaultTopic()))
			kafkaTemplate.setDefaultTopic(defaultTopicName);
	}
	
	/**
	 * 根据键名称获取Topic节点
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicKey
	 * @return
	 */
	protected TopicNode getTopicNode(String topicKey) {
		return topicManager != null ? topicManager.getTopicNode(topicKey) : null;
	}
	
	/**
	 * 获取Topic节点名称，为空时返回全局默认的名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicNode
	 * @return
	 */
	protected String getTopicName(TopicNode topicNode) {
		return getTopicName(topicNode, null);
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
			return StringUtils.isNotBlank(name) ? name : kafkaTemplate.getDefaultTopic();
		
		String topicName = topicNode.getName();
		return StringUtils.isNotBlank(topicName) ? topicName
				: (StringUtils.isNotBlank(name) ? name : kafkaTemplate.getDefaultTopic());
	}
	
	/**
	 * 获取Topic实例所关联的消息转换器，为空时返回全局默认的
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicNode
	 * @return
	 */
	protected MessageConverter getMessageConverter(TopicNode topicNode) {
		if (topicNode == null)
			return kafkaTemplate.getMessageConverter();
		
		MessageConverter converter = topicNode.getMessageConverter();
		return converter != null ? converter : kafkaTemplate.getMessageConverter();
	}
	
	/**
	 * 获取Topic实例分区索引
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicNode
	 * @return
	 */
	protected Integer getPartition(TopicNode topicNode) {
		return topicNode != null ? topicNode.getPartition() : null;
	}
	
	/**
	 * 获取Topic实例时间戳
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicNode
	 * @return
	 */
	public Long getTimestamp(TopicNode topicNode) {
		return topicNode != null ? topicNode.getTimestamp() : null;
	}

	/**
	 * 获取Topic实例上注册的生产回调事件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param topicNode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> ListenableFutureCallback<T> getFutureCallback(TopicNode topicNode) {
		return (ListenableFutureCallback<T>) (topicNode != null ? topicNode.getFutureCallback() : null);
	}

}
