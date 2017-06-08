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
 * Create Date : 2016-7-13
 */

package org.workin.serialization.json;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.workin.commons.util.ClassUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.StringUtils;
import org.workin.serialization.SerializationException;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 阿里FastJson序列化器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FastJsonSerializer extends AbstractJsonSerializer {
	
	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		 SerializeWriter out = null;
		 try {
			 out = new SerializeWriter();
			 JSONSerializer serializer = new JSONSerializer(out);
			 
			 if (StringUtils.isNotBlank(dateFormat)) {
				 serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
				 serializer.setDateFormat(dateFormat);
			 }
			 
			 serializer.write(t);
			 
			 byte[] bytes = out.toBytes(getEncoding());
			 return bytes;
		 } catch (Exception e) {
			 throw new SerializationException("Cannot serialize", e);
		 } finally {
			 if (out != null)
				 out.close();
		 }
	}

	@SuppressWarnings({ "unchecked", "resource" })
	@Override
	public <T> T deserialize(String text, Class<T> type) throws SerializationException {
		
		DefaultJSONParser jsonParser = new DefaultJSONParser(text);
		if (StringUtils.isNotBlank(dateFormat)) 
			jsonParser.setDateFormat(dateFormat);
		
		try {
			if (!isJsonArray(text)) {
				if (type != null) {
					if ( !ClassUtils.isCollection(type)) {
						return (T) (!ClassUtils.isArray(type) ? 
								beanDeserialize(jsonParser, type) : beanDeserializeToArray(jsonParser, type));
					} else
						// 指定的类型为Collection、List或其它集合类型时，则统一返回Collection<LinkedHashMap>
						return beanDeserializeToCollection(jsonParser);
				} else
					// 指定的类型为null时，则返回LinkedHashMap
					return beanDeserializeToMap(jsonParser);
			} else {
				if (type != null && !ClassUtils.isCollection(type)) {
					return (T) (!ClassUtils.isArray(type) ? 
							multipleBeanDeserializeToElementTypeCollection(jsonParser, type) : multipleBeanDeserializeToArray(jsonParser, type));
				} else
					// 指定的类型为null、Collection、List或其它集合类型时，则统一返回Collection<LinkedHashMap>
					return multipleBeanDeserializeToCollection(jsonParser, type);
			}
		} catch (Exception e) {
			throw new SerializationException("Cannot deserialize", e);
		}
	}
	
	/**
	 * 将JsonBean字符串反序列化为指定类型的bean对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonParser
	 * @param beanClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T beanDeserialize(DefaultJSONParser jsonParser, Class<?> beanClazz) throws Exception {
		return (T) jsonParser.parseObject(beanClazz);
	}
	
	/**
	 * 将JsonBean字符串反序列化到指定类型的数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonParser
	 * @param arrayClazz
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private <T> T beanDeserializeToArray(DefaultJSONParser jsonParser, Class<?> arrayClazz) throws Exception {
		Class<?> componentType = arrayClazz.getComponentType();
		T[] array = (T[]) Array.newInstance(componentType, 1);
		array[0] = (T) jsonParser.parseObject(componentType);
		return (T) array;
	}
	
	/**
	 * 将JsonBean字符串反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonParser
	 * @param collectionClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T beanDeserializeToCollection(DefaultJSONParser jsonParser) throws Exception {
		List<Object> list = CollectionUtils.newArrayList();
		list.add(beanDeserializeToMap(jsonParser));
		return (T) list;
	}
	
	/**
	 * 将JsonBean字符串反序列化为Map
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonParser
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T beanDeserializeToMap(DefaultJSONParser jsonParser) throws Exception {
		return (T) jsonParser.parseObject(LinkedHashMap.class);
	}
	
	/**
	 * 将代表多个bean的JSON字符串反序列化到指定元素类型的集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonParser
	 * @param beanClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T multipleBeanDeserializeToElementTypeCollection(DefaultJSONParser jsonParser, Class<?> beanClazz) throws Exception {
		return (T) jsonParser.parseArray(beanClazz);
	}
	
	/**
	 * 将代表多个bean的JSON字符串反序列化到指定类型的数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonParser
	 * @param arrayClazz
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T multipleBeanDeserializeToArray(DefaultJSONParser jsonParser, Class<?> arrayClazz) throws Exception {
		Collection<T> collection = multipleBeanDeserializeToElementTypeCollection(jsonParser, arrayClazz.getComponentType());
		return (T) CollectionUtils.toArray(collection);
	}
	
	/**
	 * 将代表多个bean的JSON字符串反序列化到指定类型的集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonParser
	 * @param collectionClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T multipleBeanDeserializeToCollection(DefaultJSONParser jsonParser, Class<?> collectionClazz) throws Exception {
		return (T) jsonParser.parseArray(LinkedHashMap.class);
	}

}
