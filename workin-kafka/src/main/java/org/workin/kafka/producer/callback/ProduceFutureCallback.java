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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.workin.commons.util.CodecUtils;
import org.workin.kafka.producer.service.ProducerSevice;
import org.workin.kafka.support.MQFactory;
import org.workin.kafka.support.ProduceResult;
import org.workin.serialization.Serializer;
import org.workin.serialization.json.codehaus.JacksonSerializer;

/**
 * 生产回调事件实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ProduceFutureCallback<K, V> implements ListenableFutureCallback<SendResult<K, V>> {
	
	protected final transient Logger logger;
	
	@Autowired(required = false)
	private ProducerSevice producerSevice;
	
	private Serializer serializer = new JacksonSerializer();
	
	public ProduceFutureCallback() {
		logger = LoggerFactory.getLogger(getClass());
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
	private void afterSuccess(ProduceResult<K, V> produceResult) {
		/* 不强制要求在回调事件处理类中注册一个生产者服务，
		 * 当onSuccess事件触发后，消息已经在此方法被调用前成功发送到了队列，
		 * 因此，在某些事务性要求不高的应用场景（如日志收集）下，
		 * 只需简单的将生产出的消息记录到日志中即可，而无需再做后续处理
		 */
		if (producerSevice != null)
			producerSevice.afterSuccess(produceResult);
	}
	
	/**
	 * 生产者发送消息失败以后的业务处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ex
	 */
	private void afterFailure(Throwable ex) {
		/* 同理，这里也不强制要求注册一个生产者服务，
		 * 当onFailure事件触发后，消息已经在此方法被调用前发送失败了，
		 * 因此，在某些事务性要求不高的应用场景（如日志收集）下，
		 * 只要不是大规模出现发送失败的情况，通过无需针对异常再做后续处理
		 */
		if (producerSevice != null)
			producerSevice.afterFailure(ex);
	}
	
}
