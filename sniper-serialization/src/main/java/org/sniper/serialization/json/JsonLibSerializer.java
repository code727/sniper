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

import java.lang.reflect.Array;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.sniper.beans.propertyeditors.DatePropertyEditor;
import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.CodecUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.serialization.SerializationException;

import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.ObjectMorpher;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

/**
 * JsonLib序列器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JsonLibSerializer extends AbstractJsonSerializer {
	
	protected final MorpherRegistry morpherRegistry;
	
	protected final JsonConfig jsonConfig;
	
	public JsonLibSerializer() {
		this(null, null);
	}
	
	public JsonLibSerializer(MorpherRegistry morpherRegistry) {
		this(morpherRegistry, null);
	}
	
	public JsonLibSerializer(JsonConfig jsonConfig) {
		this(null, jsonConfig);
	}
	
	public JsonLibSerializer(MorpherRegistry morpherRegistry, JsonConfig jsonConfig) {
		if (morpherRegistry == null) 
			this.morpherRegistry = buildDefaultMorpherRegistry();
		else
			this.morpherRegistry = morpherRegistry;
		
		if (jsonConfig == null) 
			this.jsonConfig = buildDefaultJsonConfig();
		else
			this.jsonConfig = jsonConfig;
	}
	
	/**
	 * 构建默认的MorpherRegistry对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private MorpherRegistry buildDefaultMorpherRegistry() {
		MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
		
		/* 覆盖掉net.sf.ezmorph.bean.BeanMorpher对java.util.Date对象的处理
		 * 改由org.sniper.serialization.json.DateBeanMorpher处理 */
		morpherRegistry.registerMorpher(new DateBeanMorpher());
		
		return morpherRegistry;
	}
	
	/**
	 * 构建默认的JsonConfig对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private JsonConfig buildDefaultJsonConfig() {
		JsonConfig jsonConfig = new JsonConfig();
		
		/* 将Date类型的值处理为毫秒数，与其他的JSON序列化工具(CodehausJackson等)的默认行为保持一致 */
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor(){
			
			@Override
			public Object processObjectValue(String propertyName, Object date, JsonConfig cfg) {
				return date != null ? ((Date) date).getTime() : date;
			}

			@Override
			public Object processArrayValue(Object date, JsonConfig cfg) {
				return date != null ? ((Date) date).getTime() : date;
			}
		});
		
		return jsonConfig;
	}
		
	/**
	 * 注册指定类型的DateMorpher
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dateFormat
	 */
	private void registerDateMorpher(String dateFormat) {
		morpherRegistry.registerMorpher(new DateMorpher(new String[] { dateFormat }));
	}
	
	/**
	 * 注册指定类型的JSON Date值的处理过程
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dateFormat
	 */
	private void registerJsonDateProcessor(final String dateFormat) {
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor(){
			
			@Override
			public Object processObjectValue(String propertyName, Object date, JsonConfig cfg) {
				return date != null ? DateUtils.objectToString(date, dateFormat) : date;
			}

			@Override
			public Object processArrayValue(Object date, JsonConfig cfg) {
				return date != null ? DateUtils.objectToString(date, dateFormat) : date;
			}
		});
	}
	
	@Override
	public void setDateFormat(String dateFormat) {
		super.setDateFormat(dateFormat);
		
		if (StringUtils.isNotBlank(dateFormat)) {
			registerDateMorpher(dateFormat);
			registerJsonDateProcessor(dateFormat);
		}
	}
			
	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		return CodecUtils.getBytes(JSONSerializer.toJSON(t, jsonConfig).toString(), getEncoding());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(String jsonString, Class<T> type) throws SerializationException {
		try {
			if (!isJsonArray(jsonString)) {
				if (type != null) {
					if (!ClassUtils.isCollection(type)) {
						return (T) (!ClassUtils.isArray(type) ? 
								beanDeserialize(jsonString, type) : beanDeserializeToArray(jsonString, type));
					} else 
						// 指定的类型为Collection、List或其它集合类型时，则统一返回Collection<LinkedHashMap>
						return beanDeserializeToCollection(jsonString);
				} else 
					// 指定的类型为null时，则返回LinkedHashMap
					return beanDeserializeToMap(jsonString);
			} else {
				if (type != null && !ClassUtils.isCollection(type)) {
					return (T) (!ClassUtils.isArray(type) ? 
							multipleBeanDeserializeToElementTypeCollection(jsonString, type) : multipleBeanDeserializeToArray(jsonString, type));
				} else 
					// 指定的类型为null、Collection、List或其它集合类型时，则统一返回Collection<LinkedHashMap>
					return multipleBeanDeserializeToCollection(jsonString, type);
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
		JSONObject jsonObject = JSONObject.fromObject(jsonBean, jsonConfig);
		return (T) JSONObject.toBean(jsonObject, beanClazz);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T beanDeserializeToArray(String jsonBean, Class<?> arrayClazz) throws Exception {
		Class<?> componentType = arrayClazz.getComponentType();
		T[] array = (T[]) Array.newInstance(componentType, 1);
		array[0] = (T) beanDeserialize(jsonBean, componentType);
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
		list.add(beanDeserializeToMap(jsonBean));
		return (T) list;
	}
	
	/**
	 * 将JsonBean字符串反序列化为Object
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonBean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T beanDeserializeToMap(String jsonBean) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(jsonBean, jsonConfig);
		return (T) JSONObject.toBean(jsonObject, LinkedHashMap.class);
	}
	
	/**
	 * 将代表多个bean的JSON字符串反序列化到指定元素类型的集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonArray
	 * @param beanClazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T multipleBeanDeserializeToElementTypeCollection(String jsonArray, Class<?> beanClazz) throws Exception {
		JSONArray array = JSONArray.fromObject(jsonArray, jsonConfig);
		return (T) JSONArray.toCollection(array, beanClazz);
	}
	
	/**
	 * 将代表多个bean的JSON字符串反序列化到指定类型的数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonArray
	 * @param arrayClazz
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T multipleBeanDeserializeToArray(String jsonArray, Class<?> arrayClazz) throws Exception {
		JSONArray array = JSONArray.fromObject(jsonArray, jsonConfig);
		return (T) JSONArray.toArray(array, arrayClazz.getComponentType());
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
		JSONArray array = JSONArray.fromObject(jsonArray, jsonConfig);
		return (T) JSONArray.toCollection(array, LinkedHashMap.class);
	}
	
	/**
	 * DateBeanMorpher实现类
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	class DateBeanMorpher implements ObjectMorpher {

		private final Class<?> beanClass;
		
		private DatePropertyEditor datePropertyEditor;

		public DateBeanMorpher() {
			this.beanClass = Date.class;
			this.datePropertyEditor = new DatePropertyEditor(getDateFormat());
		}

		@Override
		public Object morph(Object sourceBean) {
			datePropertyEditor.setAsText(ObjectUtils.toString(sourceBean));
			return datePropertyEditor.getValue();
		}

		@Override
		public Class<?> morphsTo() {
			return beanClass;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean supports(Class clazz) {
			return !clazz.isArray();
		}
	}
	
}
