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

import org.sniper.beans.DefaultTypedBean;
import org.sniper.beans.Typed;
import org.sniper.commons.util.ClassUtils;
import org.sniper.serialization.json.JsonSerializer;
import org.sniper.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;

/**
 * Kafka JSON反序列化解析器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class KafkaJsonDeserializer<T> extends AbstractDeserializer<T> {
	
	/** JSON序列化解析器 */
	private JsonSerializer jsonSerializer;
	
	/** 类型化对象 */
	private Typed typed;
	
	public KafkaJsonDeserializer() {
		this(null);
	}
	
	public KafkaJsonDeserializer(JsonSerializer jsonSerializer) {
		if (jsonSerializer == null)
			this.jsonSerializer = new FasterxmlJacksonSerializer();
		else
			this.jsonSerializer = jsonSerializer;
		
		/* 类型化对象初始化 */
		this.typed = new DefaultTypedBean();
		this.typed.setType(ClassUtils.getSuperClassGenricType(getClass()));
	}
	
	public void setTypeClass(String typeClass) throws Exception {
		this.typed.setTypeClass(typeClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(String topic, byte[] data) {
		return (T) jsonSerializer.deserialize(data, typed.getType());
	}

}
