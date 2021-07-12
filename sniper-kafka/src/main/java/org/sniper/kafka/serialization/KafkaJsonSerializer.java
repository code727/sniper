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

import org.sniper.serialization.json.JsonSerializer;
import org.sniper.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;

/**
 * Kafka JSON序列化解析器
 * @author  Daniele
 * @version 1.0
 */
public class KafkaJsonSerializer<T> extends AbstractSerializer<T> {
	
	/** JSON序列化解析器 */
	private JsonSerializer jsonSerializer;
	
	public KafkaJsonSerializer() {
		this(null);
	}
	
	public KafkaJsonSerializer(JsonSerializer jsonSerializer) {
		if (jsonSerializer == null)
			this.jsonSerializer = new FasterxmlJacksonSerializer();
		else
			this.jsonSerializer = jsonSerializer;
	}

	@Override
	public byte[] serialize(String topic, T data) {
		return jsonSerializer.serialize(data);
	}

}
