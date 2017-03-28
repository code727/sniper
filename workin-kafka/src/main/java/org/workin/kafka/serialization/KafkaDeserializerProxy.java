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
 * Create Date : 2017-3-28
 */

package org.workin.kafka.serialization;

import java.util.Arrays;

import org.workin.commons.util.AssertUtils;
import org.workin.serialization.SerializationException;
import org.workin.serialization.Serializer;

/**
 * Kafka反序列化器代理实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class KafkaDeserializerProxy<T> extends AbstractDeserializer<T> {
	
	private Serializer serializer;
	
	public KafkaDeserializerProxy(Serializer serializer) {
		AssertUtils.assertNotNull(serializer, "Workin serializer must not be null");
		
		this.serializer = serializer;
	}
	
	@Override
	public T deserialize(String topic, byte[] data) {
		try {
			return serializer.deserialize(data);
		} catch (SerializationException e) {
			throw new SerializationException(
					"Can't deserialize data [" + Arrays.toString(data) + "] from topic [" + topic + "]", e);
		}
	}

}
