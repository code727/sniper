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

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SettableListenableFuture;
import org.workin.commons.util.ReflectionUtils;
import org.workin.kafka.producer.KafkaProducerSupport;
import org.workin.kafka.support.MQFactory;
import org.workin.kafka.support.ProduceResult;
import org.workin.kafka.topic.TopicNode;

/**
 * Kafka生产者支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class SpringKafkaProducerSupport extends KafkaProducerSupport {
	
    private KafkaTemplate<?, ?> kafkaTemplate;
    
    /** 全局的生产者监听 */
    private volatile ProducerListener<?, ?> producerListener;
    
    /** 全局的生产者回调 */
    protected ListenableFutureCallback<?> producerCallback;
    
	public KafkaTemplate<?, ?> getKafkaTemplate() {
		return kafkaTemplate;
	}

	public void setKafkaTemplate(KafkaTemplate<?, ?> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public ListenableFutureCallback<?> getProducerCallback() {
		return producerCallback;
	}

	public void setProducerCallback(ListenableFutureCallback<?> producerCallback) {
		this.producerCallback = producerCallback;
	}

	@Override
	protected void checkProperties() {		
		if (kafkaTemplate == null)
			throw new IllegalArgumentException("Property 'kafkaTemplate' is required");
	}
	
	@Override
	protected void init() throws Exception {
		super.init();
		
		// kafkaTemplate中的默认topic名称强制与defaultTopic对象的名称保持一致
		kafkaTemplate.setDefaultTopic(defaultTopic.getName());
		
		producerListener = ReflectionUtils.getFieldValue(kafkaTemplate, "producerListener");
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
	 * 创建生产回调，此方法和KafkaTemplate doSend方法内部创建callback的逻辑基本一致
	 * 只是在生产成功时是将自定义的ProduceResult对象设置到了future中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param future
	 * @param producer
	 * @param producerRecord
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <K, V> Callback createProduceCallback(final SettableListenableFuture<ProduceResult<K, V>> future, 
			final Producer<K, V> producer, final ProducerRecord<K, V> producerRecord) {
			
		return new Callback() {
			
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				try {
					if (exception == null) {
						future.set(MQFactory.buildProduceResult(producerRecord, metadata));
						if (producerListener != null && producerListener.isInterestedInSuccess()) {
							((ProducerListener<K, V>) producerListener).onSuccess(producerRecord.topic(),
									producerRecord.partition(), producerRecord.key(), producerRecord.value(), metadata);
						}
					} else {
						future.setException(new KafkaProducerException(producerRecord, "Failed to send", exception));
						if (producerListener != null) {
							((ProducerListener<K, V>) producerListener).onError(producerRecord.topic(),
									producerRecord.partition(), producerRecord.key(), producerRecord.value(), exception);
						}
					}
				} finally {
					producer.close();
				}
			}
		};
	}

}
