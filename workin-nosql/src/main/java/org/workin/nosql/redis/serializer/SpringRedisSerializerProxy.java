/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-8-24
 */

package org.workin.nosql.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.workin.commons.util.AssertUtils;
import org.workin.serialization.SerializationException;
import org.workin.serialization.Serializer;

/**
 * Spring Redis序列化器代理
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class SpringRedisSerializerProxy implements Serializer {
	
	private RedisSerializer redisSerializer;
	
	public SpringRedisSerializerProxy(RedisSerializer redisSerializer) {
		AssertUtils.assertNotNull(redisSerializer, "Spring redis serializer must not be null");
		
		this.redisSerializer = redisSerializer;
	}
	
	public RedisSerializer getRedisSerializer() {
		return redisSerializer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		return redisSerializer.serialize(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes) throws SerializationException {
		return (T) redisSerializer.deserialize(bytes);
	}

}
