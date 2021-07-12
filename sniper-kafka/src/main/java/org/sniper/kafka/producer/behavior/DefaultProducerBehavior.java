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
 * Create Date : 2017年3月30日
 */

package org.sniper.kafka.producer.behavior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.CodecUtils;
import org.sniper.kafka.support.ProduceRecord;
import org.sniper.kafka.support.ProduceResult;
import org.sniper.serialization.Serializer;
import org.sniper.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;

/**
 * 生产者默认行为实现类
 * @author  Daniele
 * @version 1.0
 */
public class DefaultProducerBehavior implements ProducerBehavior {
	
	private final Logger logger;
	
	/** logger序列化器 */
	protected Serializer loggerSerializer = new FasterxmlJacksonSerializer();
	
	/** 是否关注成功事件 */
	private boolean interestedInSuccess = true;
	
	/** 出现错误时是否抛出异常 */
	private boolean throwExceptionOnError;
	
	public DefaultProducerBehavior() {
		this(DefaultProducerBehavior.class);
	}
	
	public DefaultProducerBehavior(Class<?> clazz) {
		this(clazz.getName());
	}
	
	public DefaultProducerBehavior(String name) {
		logger = LoggerFactory.getLogger(name);
	}
	
	public Serializer getLoggerSerializer() {
		return loggerSerializer;
	}

	public void setLoggerSerializer(Serializer loggerSerializer) {
		this.loggerSerializer = loggerSerializer;
	}

	@Override
	public boolean isInterestedInSuccess() {
		return interestedInSuccess;
	}

	@Override
	public void setInterestedInSuccess(boolean interestedInSuccess) {
		this.interestedInSuccess = interestedInSuccess;
	}

	@Override
	public boolean isThrowExceptionOnError() {
		return throwExceptionOnError;
	}

	@Override
	public void setThrowExceptionOnError(boolean throwExceptionOnError) {
		this.throwExceptionOnError = throwExceptionOnError;
	}
	
	public Logger getLogger() {
		return logger;
	}

	@Override
	public <K, V> void successLog(ProduceResult<K, V> produceResult) {
		logger.info("Producer success send message:{}",
				CodecUtils.bytesToString(loggerSerializer.serialize(produceResult)));
	}

	@Override
	public <K, V> void errorLog(ProduceRecord<K, V> produceRecord, Throwable ex) {
		if (produceRecord != null)
			logger.error("Producer send message is failure:{},error cause: {}",
					CodecUtils.bytesToString(loggerSerializer.serialize(produceRecord)), ex);
		else
			logger.error("Producer send message is failure,error cause: {}", ex);
	}
	
}
