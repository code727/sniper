/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-12-17
 */

package org.workin.nosql.redis.serializer;

import java.nio.charset.Charset;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.workin.commons.util.ArrayUtils;

/**
 * JOSN格式的redis序列化器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 * @see org.workin.serialization.json.JacksonSerializer
 */
@Deprecated
public class JacksonJsonRedisSerializer<T> implements RedisSerializer<T> {
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private final JavaType javaType;

	@SuppressWarnings("unchecked")
	public JacksonJsonRedisSerializer(String typeClass) throws ClassNotFoundException {
		this((Class<T>) Class.forName(typeClass));
	}
	
	public JacksonJsonRedisSerializer(Class<T> type) {
		this.javaType = getJavaType(type);
	}

	/**
	 * 将指定的对象序列化成字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param t
	 * @return
	 * @throws SerializationException
	 */
	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) 
			return new byte[0];
		
		try {
			return this.objectMapper.writeValueAsBytes(t);
		} catch (Exception ex) {
			throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}

	/**
	 * 将指定的字节数组反序列化成对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bytes
	 * @return
	 * @throws SerializationException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (ArrayUtils.isEmpty(bytes)) 
			return null;
		
		try {
			return (T) this.objectMapper.readValue(bytes, 0, bytes.length, javaType);
		} catch (Exception ex) {
			throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
		}
	}
	
	protected JavaType getJavaType(Class<?> clazz) {
		return TypeFactory.defaultInstance().constructType(clazz);
	}

}
