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

package org.sniper.serialization.json;

import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.CodecUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.serialization.AbstractTypedSerializer;
import org.sniper.serialization.SerializationException;

/**
 * JSON序列器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractJsonSerializer extends AbstractTypedSerializer implements JsonSerializer {
	
	/** 序列化日期时指定的格式 */
	private String dateFormat;
	
	@Override
	public String getDateFormat() {
		return dateFormat;
	}
	
	@Override
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	/**
	 * 重写父类方法，将对象序列化成二进制数组后再进行最基本的字符串转换即可得到JSON字符串
	 * @author Daniele 
	 * @param t
	 * @return
	 * @throws SerializationException
	 */
	@Override
	public <T> String serializeToString(T t) throws SerializationException {
		return CodecUtils.bytesToString(serialize(t), getEncoding());
	}
	
	@Override
	public <T> T deserialize(String text, Class<T> type) throws SerializationException {
		if (StringUtils.isBlank(text))
			return null;
		
		try {
			if (isJsonArray(text)) {
				if (ClassUtils.isCollection(type))
					// 指定的类型为Collection、List、Set或其它集合类型时，则统一返回Collection<LinkedHashMap>
					return multipleDeserializeToCollection(text, type);
				
				if (ClassUtils.isArray(type))
					return multipleDeserializeToArray(text, type);
				
				return multipleDeserializeToElementTypeCollection(text, type);
			} else {
				if (ClassUtils.isCollection(type))
					// 指定的类型为Collection、List或其它集合类型时，则统一返回Collection<LinkedHashMap>
					return deserializeToCollection(text, type);
				
				if (ClassUtils.isArray(type))
					return deserializeToArray(text, type);
				
				return deserializeToType(text, type);
			}
			
		} catch (Exception e) {
			throw new SerializationException("Cannot deserialize", e);
		}
	}
		
	/**
	 * 判断字符串是否为一个JSON数组
	 * @author Daniele 
	 * @param jsonString
	 * @return
	 */
	private boolean isJsonArray(String jsonString) {
		return jsonString.startsWith(StringUtils.LEFT_BRACKET) && jsonString.endsWith(StringUtils.RIGHT_BRACKET);
	}
		
	/**
	 * 将JSON字符串反序列化为指定类型的对象
	 * @author Daniele 
	 * @param json JSON字符串
	 * @param type 目标对象类型
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T deserializeToType(String json, Class<T> type) throws Exception;
	
	/**
	 * 将JSON字符串反序列化到指定类型的数组中
	 * @author Daniele 
	 * @param json JSON字符串
	 * @param arrayType 数组类型
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T deserializeToArray(String json, Class<T> arrayType) throws Exception;
	
	/**
	 * 将JSON字符串反序列化到集合中
	 * @author Daniele 
	 * @param json
	 * @param collectionType
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T deserializeToCollection(String json, Class<T> collectionType) throws Exception;
	
	/**
	 * 将JSON数组字符串反序列化到指定元素类型的集合中
	 * @author Daniele 
	 * @param jsonArray JSON数组字符串
	 * @param elementType 集合元素的类型
	 * @return
	 * @throws Exception
	 */
	protected abstract <T, E> T multipleDeserializeToElementTypeCollection(String jsonArray, Class<E> elementType) throws Exception;
	
	/**
	 * 将JSON数组字符串反序列化到指定类型的数组中
	 * @author Daniele 
	 * @param jsonArray JSON数组字符串
	 * @param arrayType 数组类型
	 * @return 
	 * @throws Exception
	 */
	protected abstract <T> T multipleDeserializeToArray(String jsonArray, Class<T> arrayType) throws Exception;
	
	/**
	 * 将JSON数组字符串反序列化到指定类型的集合中
	 * @author Daniele 
	 * @param jsonArray JSON数组字符串
	 * @param collectionType 集合类型
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T multipleDeserializeToCollection(String jsonArray, Class<T> collectionType) throws Exception;
	
}
