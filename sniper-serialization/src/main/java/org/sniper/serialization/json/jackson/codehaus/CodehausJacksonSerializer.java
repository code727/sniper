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
 * Create Date : 2016-7-12
 */

package org.sniper.serialization.json.jackson.codehaus;


import java.lang.reflect.Array;
import java.util.Collection;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.serialization.SerializationException;
import org.sniper.serialization.json.AbstractJsonSerializer;

/**
 * CodehausJackson 序列器实现类
 * @author  Daniele
 * @version 1.0
 */
public class CodehausJacksonSerializer extends AbstractJsonSerializer {
	
	private ObjectMapper objectMapper;
	
	public CodehausJacksonSerializer() {
		this(null);
	}
	
	public CodehausJacksonSerializer(ObjectMapper objectMapper) {
		if (objectMapper != null )
			this.objectMapper = objectMapper;
		else 
			this.objectMapper = new ObjectMapper();
	}
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Override
	public void setDateFormat(String dateFormat) {
		super.setDateFormat(dateFormat);
		
		if (StringUtils.isNotBlank(dateFormat))
			this.objectMapper.setDateFormat(DateUtils.getDateFormat(dateFormat));
		else
			this.objectMapper.setDateFormat(null);
	}
	
	@Override
	public boolean canSerialize(Object obj) {
		Class<?> type = ClassUtils.getCurrentType(obj);
		return type != null && this.objectMapper.canSerialize(type);
	}

	@Override
	public boolean canDeserialize(Object obj) {
		Class<?> type = ClassUtils.getCurrentType(obj);
		if (type == null)
			return false;
		
		JavaType javaType = TypeFactory.defaultInstance().constructType(type);
		return this.objectMapper.canDeserialize(javaType);
	}

	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		try {
			return this.objectMapper.writeValueAsBytes(t);
		} catch (Exception e) {
			throw new SerializationException("Cannot serialize", e);
		}
	}
		
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T deserializeToType(String json, Class<T> type) throws Exception {
		return (T) objectMapper.readValue(json, safeDeserializeType(type));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T deserializeToArray(String json, Class<T> arrayType) throws Exception {
		Class<?> componentType = arrayType.getComponentType();
		T[] array = (T[]) Array.newInstance(componentType, 1);
		array[0] = (T) deserializeToType(json, componentType);
		return (T) array;
	}
	
	@Override
	protected <T> T deserializeToCollection(String json, Class<T> collectionType) throws Exception {
		/* 将JSON字符串先构建成数组形式的再进行反序列化 */
		String jsonArray = new StringBuilder("[").append(json).append("]").toString();
		return multipleDeserializeToCollection(jsonArray, collectionType);
	}
		
	@Override
	protected <T, E> T multipleDeserializeToElementTypeCollection(String jsonArray, Class<E> elementType) throws Exception {
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Collection.class, safeDeserializeType(elementType));
		return objectMapper.readValue(jsonArray, javaType);
	}
	
	@Override
	protected <T> T multipleDeserializeToArray(String jsonArray, Class<T> arrayType) throws Exception {
		JavaType javaType = objectMapper.getTypeFactory().constructArrayType(arrayType.getComponentType());
		return this.objectMapper.readValue(jsonArray, javaType);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T multipleDeserializeToCollection(String jsonArray, Class<T> collectionType) throws Exception {
		return (T) objectMapper.readValue(jsonArray, safeDeserializeType(collectionType));
	}

}
