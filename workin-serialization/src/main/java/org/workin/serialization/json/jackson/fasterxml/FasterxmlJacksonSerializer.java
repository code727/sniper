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
 * Create Date : 2017-4-1
 */

package org.workin.serialization.json.jackson.fasterxml;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

import org.workin.commons.util.ClassUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.DateUtils;
import org.workin.commons.util.StringUtils;
import org.workin.serialization.SerializationException;
import org.workin.serialization.json.AbstractJsonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;

/**
 * FasterxmlJackson 序列器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FasterxmlJacksonSerializer extends AbstractJsonSerializer {
	
	private ObjectMapper objectMapper;
	
	public FasterxmlJacksonSerializer() {
		this(null);
	}
	
	public FasterxmlJacksonSerializer(ObjectMapper objectMapper) {
		if (objectMapper != null )
			this.objectMapper = objectMapper;
		else {
			this.objectMapper = new ObjectMapper();
			this.objectMapper.setDateFormat(DateUtils.getDateFormat(dateFormat));
		}
	}
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	
	public void setObjectMapper(ObjectMapper objectMapper) {
		if (objectMapper != null)
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
	public <T> byte[] serialize(T t) throws SerializationException {
		try {
			return this.objectMapper.writeValueAsBytes(t);
		} catch (Exception e) {
			throw new SerializationException("Cannot serialize", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(String text, Class<T> type) throws SerializationException {
		
		Class<?> clazz = (type != null ? type : getType());
		
		try {
			if (!isJsonArray(text)) {
				if (clazz != null && !ClassUtils.isCollection(clazz)) {
					return (T) (!ClassUtils.isArray(clazz) ? 
							beanDeserialize(text, clazz) : beanDeserializeToArray(text, clazz));
				} else 
					// 指定的类型为null、Collection、List或其它集合类型时，则统一返回Collection<LinkedHashMap>
					return beanDeserializeToCollection(text);
			} else {
				if (clazz != null && !ClassUtils.isCollection(clazz)) {
					return (T) (!ClassUtils.isArray(clazz) ? 
							multipleBeanDeserializeToElementTypeCollection(text, clazz) : multipleBeanDeserializeToArray(text, clazz));
				} else
					// 指定的类型为null、Collection、List或其它集合类型时，则统一返回Collection<LinkedHashMap>
					return multipleBeanDeserializeToCollection(text, clazz);
			}
		} catch (Exception e) {
			throw new SerializationException("Cannot deserialize", e);
		}
	}
	
	/**
	 * 将JsonBean字符串反序列化为指定类型的bean对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonBean
	 * @param beanClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T beanDeserialize(String jsonBean, Class<?> beanClazz) throws Exception {
		return (T) objectMapper.readValue(jsonBean, beanClazz);
	}
	
	/**
	 * 将JsonBean字符串反序列化到指定类型的数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonBean
	 * @param arrayClazz
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private <T> T beanDeserializeToArray(String jsonBean, Class<?> arrayClazz) throws Exception {
		Class<?> componentType = arrayClazz.getComponentType();
		T[] array = (T[]) Array.newInstance(componentType, 1);
		array[0] = (T) this.objectMapper.readValue(jsonBean, componentType);
		return (T) array;
	}
	
	/**
	 * 将JsonBean字符串反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonBean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T beanDeserializeToCollection(String jsonBean) throws Exception {
		List<Object> list = CollectionUtils.newArrayList();
		list.add(objectMapper.readValue(jsonBean, Object.class));
		return (T) list;
	}
	
	/**
	 * 将代表多个bean的JSON字符串反序列化到指定元素类型的集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonArray
	 * @param beanClazz
	 * @return
	 * @throws Exception
	 */
	private <T> T multipleBeanDeserializeToElementTypeCollection(String jsonArray, Class<?> beanClazz) throws Exception {
		CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(Collection.class, beanClazz);
		return objectMapper.readValue(jsonArray, collectionType);
	}
	
	/**
	 * 将代表多个bean的JSON字符串反序列化到指定类型的数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonArray
	 * @param arrayClazz
	 * @return 
	 * @throws Exception
	 */
	private <T> T multipleBeanDeserializeToArray(String jsonArray, Class<?> arrayClazz) throws Exception {
		ArrayType arrayType = objectMapper.getTypeFactory().constructArrayType(arrayClazz.getComponentType());
		return this.objectMapper.readValue(jsonArray, arrayType);
	}
	
	/**
	 * 将代表多个bean的JSON字符串反序列化到指定类型的集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonArray
	 * @param collectionClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T multipleBeanDeserializeToCollection(String jsonArray, Class<?> collectionClazz) throws Exception {
		return (T) objectMapper.readValue(jsonArray, collectionClazz != null ? collectionClazz : Object.class);
	}

}
