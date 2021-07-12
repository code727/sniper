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

package org.sniper.serialization.json;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.serialization.SerializationException;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 阿里FastJson序列化器实现类
 * @author  Daniele
 * @version 1.0
 */
public class FastJsonSerializer extends AbstractJsonSerializer {
	
	@Override
	public boolean canSerialize(Object obj) {
		Class<?> type = ClassUtils.getCurrentType(obj);
		if (type == null)
			return false;
		
		SerializeConfig instance = SerializeConfig.getGlobalInstance();
		return instance.getObjectWriter(type) != null;
	}

	@Override
	public boolean canDeserialize(Object obj) {
		Class<?> type = ClassUtils.getCurrentType(obj);
		if (type == null)
			return false;
		
		ParserConfig instance = ParserConfig.getGlobalInstance();
		try {
			return instance.getDeserializer(type) != null;
		} catch (JSONException e) {
			return false;
		}
	}
	
	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		JSONSerializer serializer = null;
		try {
			serializer = new JSONSerializer(new SerializeWriter());
			
			String dateFormat = getDateFormat();
			if (StringUtils.isNotBlank(dateFormat)) {
				serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
				serializer.setDateFormat(dateFormat);
			}
			serializer.write(t);
			return serializer.getWriter().toBytes(getEncoding());
		} catch (Exception e) {
			throw new SerializationException("Cannot serialize", e);
		} finally {
			if (serializer != null)
				serializer.close();
		}
	}
	
	/**
	 * 创建一个JSON解析器对象
	 * @author Daniele 
	 * @param text
	 * @return
	 */
	protected DefaultJSONParser newJSONParser(String text) {
		DefaultJSONParser jsonParser = new DefaultJSONParser(text);
		String dateFormat = getDateFormat();
		if (StringUtils.isNotBlank(dateFormat)) 
			jsonParser.setDateFormat(dateFormat);
		
		return jsonParser;
	}
	
	/**
	 * 重写父类方法，由于FastJson在做反序列化操作时:<P>
	 * 1)如果调用方传入的目标对象类型为null或Object，则默认反序列化结果的类型为com.alibaba.fastjson.JSONObject<P>
	 * 2)如果调用方传入的目标对象类型为java.util.Map接口类型，则默认反序列化结果的类型为java.util.HashMap<P>
	 * 因此，为达到保持与CodehausJacksonSerializer和FasterxmlJacksonSerializer实现类在反序列化默认行为上的一致性，以及向调用方屏蔽掉第三方专用API的目的，
	 * 针对于上述两种情况，可以在反序列化执行之前，调用此方法将目标对象类型统一返回为java.util.LinkedHashMap
	 * @author Daniele 
	 * @param type
	 * @return
	 */
	@Override
	protected Class<?> safeDeserializeType(Class<?> type) {
		Class<?> deserializeType = super.safeDeserializeType(type);
		if (deserializeType == Object.class || deserializeType == Map.class)
			return LinkedHashMap.class;
		
		return deserializeType;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T deserializeToType(String json, Class<T> type) throws Exception {
		return (T) newJSONParser(json).parseObject(safeDeserializeType(type));
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
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T, E> T multipleDeserializeToElementTypeCollection(String jsonArray, Class<E> elementType) throws Exception {
		return (T) newJSONParser(jsonArray).parseArray(safeDeserializeType(elementType));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T multipleDeserializeToArray(String jsonArray, Class<T> arrayType) throws Exception {
		Class<T> componentType = (Class<T>) arrayType.getComponentType();
		Collection<T> collection = multipleDeserializeToElementTypeCollection(jsonArray, componentType);
		return (T) CollectionUtils.toArray(collection, componentType);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T multipleDeserializeToCollection(String jsonArray, Class<T> collectionType) throws Exception {
		Collection<JSONObject> collection = (Collection<JSONObject>) newJSONParser(jsonArray)
				.parseObject(safeDeserializeType(collectionType));
		return (T) toOriginalMapElementCollection(collection);
	}
	
	/**
	 * 将com.alibaba.fastjson.JSONObject元素类型的集合转换为原生Map元素类型的集合，
	 * 目的与safeDeserializeType方法的一致
	 * @author Daniele 
	 * @param collection
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Collection<Map<String, Object>> toOriginalMapElementCollection(Collection<JSONObject> collection) throws Exception {
		// 要求集合的实际类型必须有默认构造函数
		Collection<Map<String, Object>> result = ReflectionUtils.newInstance(collection.getClass());
		Iterator<JSONObject> iterator = collection.iterator();
		
		while (iterator.hasNext()) {
			Map<String, Object> map = MapUtils.newLinkedHashMap();
			for (Entry<String, Object> entry : ((JSONObject) iterator.next()).entrySet()) {
				map.put(entry.getKey(), entry.getValue());
			}
			result.add(map);
		}
		return result;
	}

}
