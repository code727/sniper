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
 * Create Date : 2017-6-30
 */

package org.sniper.kafka.serialization;

import org.sniper.commons.util.ClassUtils;
import org.sniper.serialization.json.JsonSerializer;
import org.sniper.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;

/**
 * Kafka JSON反序列化解析器
 * @author  Daniele
 * @version 1.0
 */
public class KafkaJsonDeserializer<T> extends AbstractDeserializer<T> {
	
	/** JSON序列化解析器 */
	private final JsonSerializer jsonSerializer;
	
	/** 反序列化目标对象类型 */
	private final Class<T> targetType;
	
	public KafkaJsonDeserializer() {
		this(null, null);
	}
	
	public KafkaJsonDeserializer(Class<T> type) {
		this(null, type);
	}
	
	public KafkaJsonDeserializer(JsonSerializer jsonSerializer) {
		this(jsonSerializer, null);
	}
	
	@SuppressWarnings("unchecked")
	public KafkaJsonDeserializer(JsonSerializer jsonSerializer, Class<T> targetType) {
		if (jsonSerializer == null)
			this.jsonSerializer = new FasterxmlJacksonSerializer();
		else
			this.jsonSerializer = jsonSerializer;
		
		if (targetType != null)
			this.targetType = targetType;
		else
			this.targetType = (Class<T>) ClassUtils.getSuperclassGenricType(getClass());
	}
	
	public JsonSerializer getJsonSerializer() {
		return jsonSerializer;
	}

	public Class<T> getTargetType() {
		return targetType;
	}
	
	@Override
	public T deserialize(String topic, byte[] data) {
		return (T) this.jsonSerializer.deserialize(data, this.targetType);
	}
	
}
