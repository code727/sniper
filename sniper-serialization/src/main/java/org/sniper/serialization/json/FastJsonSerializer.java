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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.serialization.SerializationException;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 阿里FastJson序列化器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FastJsonSerializer extends AbstractJsonSerializer {
	
	@Override
	public boolean canSerialize(Object obj) {
		Class<?> type = ObjectUtils.getClass(obj);
		if (type == null)
			return false;
		
		SerializeConfig instance = SerializeConfig.getGlobalInstance();
		return instance.getObjectWriter(type) != null;
	}

	@Override
	public boolean canDeserialize(Object obj) {
		Class<?> type = ObjectUtils.getClass(obj);
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
		 SerializeWriter out = new SerializeWriter();
		 try {
			 JSONSerializer serializer = new JSONSerializer(out);
			 
			 String dateFormat = getDateFormat();
			 if (StringUtils.isNotBlank(dateFormat)) {
				 serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
				 serializer.setDateFormat(dateFormat);
			 }
			 
			 serializer.write(t);
			 return out.toBytes(getEncoding());
		 } catch (Exception e) {
			 throw new SerializationException("Cannot serialize", e);
		 } finally {
			 out.close();
		 }
	}
	
	/**
	 * 创建一个JSON解析器对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
		return (T) newJSONParser(jsonArray).parseArray(elementType);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T multipleDeserializeToArray(String jsonArray, Class<T> arrayType) throws Exception {
		Collection<T> collection = multipleDeserializeToElementTypeCollection(jsonArray, arrayType.getComponentType());
		return (T) CollectionUtils.toArray(collection);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T multipleDeserializeToCollection(String jsonArray, Class<?> collectionType) throws Exception {
		return (T) newJSONParser(jsonArray).parseArray(LinkedHashMap.class);
	}

}
