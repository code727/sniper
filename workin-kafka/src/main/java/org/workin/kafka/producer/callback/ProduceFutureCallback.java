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

package org.workin.kafka.producer.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.workin.commons.util.CodecUtils;
import org.workin.kafka.support.MQFactory;
import org.workin.kafka.support.ProduceResult;
import org.workin.serialization.Serializer;
import org.workin.serialization.json.codehaus.JacksonSerializer;

/**
 * 生产回调事件抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ProduceFutureCallback<K, V> extends ProduceFutureCallbackAdapter<K, V> {
	
	protected final transient Logger logger;
	
	private Serializer serializer = new JacksonSerializer();
	
	public ProduceFutureCallback() {
		logger = LoggerFactory.getLogger(getClass());
	}
	
	public Serializer getSerializer() {
		return serializer;
	}

	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}

	@Override
	public void onSuccess(SendResult<K, V> result) {
		ProduceResult<K, V> produceResult = MQFactory.buildProduceResult(result);
		logger.info("Producer success send message:"
				+ CodecUtils.bytesToString(serializer.serialize(produceResult)));
		afterSuccess(produceResult);
	}

	@Override
	public void onFailure(Throwable ex) {
		logger.error("Producer send message is failure");
		afterFailure(ex);
	}
	
	/**
	 * 生产者发送消息成功以后的业务处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param result
	 */
	protected void afterSuccess(ProduceResult<K, V> produceResult) {
		
	}
	
	/**
	 * 生产者发送消息失败以后的业务处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ex
	 */
	protected void afterFailure(Throwable ex) {
		
	}
	
}
