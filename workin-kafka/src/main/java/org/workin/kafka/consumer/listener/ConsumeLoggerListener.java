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
 * Create Date : 2017-3-10
 */

package org.workin.kafka.consumer.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.workin.commons.util.CodecUtils;
import org.workin.kafka.support.ConsumeResult;
import org.workin.kafka.support.MQFactory;
import org.workin.serialization.Serializer;
import org.workin.serialization.json.codehaus.JacksonSerializer;

/**
 * 消费日志监听实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ConsumeLoggerListener<K, V> implements ConsumeListener<K, V> {
		
	protected final transient Logger logger = LoggerFactory.getLogger(ConsumeLoggerListener.class);
	
	private Serializer serializer = new JacksonSerializer();
	
	@Override
	public void onMessage(ConsumerRecord<K, V> record) {
		ConsumeResult<K, V> consumeResult = MQFactory.buildConsumeResult(record);
		logger.info("Consumer success receive message:"
				+ CodecUtils.bytesToString(serializer.serialize(consumeResult)));
		
		onMessage(record, null);
	}
	
	@Override
	public void onMessage(ConsumerRecord<K, V> record, Acknowledgment acknowledgment) {
		ConsumeResult<K, V> consumeResult = MQFactory.buildConsumeResult(record);
		logger.info("Consumer success receive message:"
				+ CodecUtils.bytesToString(serializer.serialize(consumeResult)));
	}

}
