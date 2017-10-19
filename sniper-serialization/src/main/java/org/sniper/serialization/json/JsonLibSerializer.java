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
import java.util.Map;

import org.sniper.beans.propertyeditors.DatePropertyEditor;
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
	public boolean canSerialize(Object obj) {
		Class<?> type = ObjectUtils.getClass(obj);
		if (type == null)
			return false;
		
		return morpherRegistry.getMorpherFor(type) != null;
	}

	@Override
	public boolean canDeserialize(Object obj) {
		Class<?> type = ObjectUtils.getClass(obj);
		if (type == null)
			return false;
		
		return morpherRegistry.getMorpherFor(type) != null;
	}
			
	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		return CodecUtils.getBytes(JSONSerializer.toJSON(t, jsonConfig).toString(), getEncoding());
	}
	
	/**
	 * 重写父类方法，由于Json在做反序列化操作时:<P>
	 * 1)如果调用方传入的目标对象类型为null或Object，则默认反序列化结果的类型为net.sf.json.JSONObject<P>
	 * 2)如果调用方传入的目标对象类型为java.util.Map接口类型，则默认反序列化结果的类型为java.util.HashMap<P>
	 * 因此，为达到保持与CodehausJacksonSerializer和FasterxmlJacksonSerializer实现类在反序列化默认行为上的一致性，以及向调用方屏蔽掉第三方专用API的目的，
	 * 针对于上述两种情况，可以在反序列化执行之前，调用此方法将目标对象类型统一返回为java.util.LinkedHashMap
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
		JSONObject jsonObject = JSONObject.fromObject(json, jsonConfig);
		return (T) JSONObject.toBean(jsonObject, safeDeserializeType(type));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T deserializeToArray(String json, Class<T> arrayType) throws Exception {
		Class<?> componentType = arrayType.getComponentType();
		T[] array = (T[]) Array.newInstance(componentType, 1);
		array[0] = (T) deserializeToType(json, componentType);
		return (T) array;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T deserializeToCollection(String json) throws Exception {
		List<Object> list = CollectionUtils.newArrayList();
		list.add(deserializeToType(json, null));
		return (T) list;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T multipleDeserializeToElementTypeCollection(String jsonArray, Class<?> elementType) throws Exception {
		JSONArray array = JSONArray.fromObject(jsonArray, jsonConfig);
		return (T) JSONArray.toCollection(array, elementType);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T multipleDeserializeToArray(String jsonArray, Class<T> arrayType) throws Exception {
		JSONArray array = JSONArray.fromObject(jsonArray, jsonConfig);
		return (T) JSONArray.toArray(array, arrayType.getComponentType());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T multipleDeserializeToCollection(String jsonArray, Class<?> collectionType) throws Exception {
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
