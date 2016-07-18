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

package org.workin.serialization.json.sf;

import java.util.Date;

import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import org.workin.commons.util.CodecUtils;
import org.workin.commons.util.DateUtils;
import org.workin.serialization.SerializationException;
import org.workin.serialization.json.AbstractJsonSerializer;

/**
 * @description JsonLib序列器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JsonLibSerializer extends AbstractJsonSerializer {
	
	private MorpherRegistry morpherRegistry;
	
	private JsonConfig jsonConfig;
		
	public JsonLibSerializer() {
		buildDefaultMorpher();
		buildDefaultJsonConfig();
	}
	
	public MorpherRegistry getMorpherRegistry() {
		return morpherRegistry;
	}

	public void setMorpherRegistry(MorpherRegistry morpherRegistry) {
		if (morpherRegistry != null)
			this.morpherRegistry = morpherRegistry;
	}

	public JsonConfig getJsonConfig() {
		return jsonConfig;
	}

	public void setJsonConfig(JsonConfig jsonConfig) {
		this.jsonConfig = jsonConfig;
	}
	
	/**
	 * @description 构建全局默认的Morpher
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	private void buildDefaultMorpher() {
		this.morpherRegistry = JSONUtils.getMorpherRegistry();
		this.morpherRegistry.registerMorpher(new DateMorpher(new String[] {
				DateUtils.DEFAULT_DATETIME_FORMAT, DateUtils.DEFAULT_DATE_FORMAT
			}
		)); 
	}
	
	/**
	 * @description 构建全局默认的JsonConfig
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	private void buildDefaultJsonConfig() {
		this.jsonConfig = new JsonConfig();
		this.jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor(){
			
			@Override
			public Object processObjectValue(String propertyName, Object date, JsonConfig cfg) {
				return DateUtils.objectToString(date, getDateFormat());
			}

			@Override
			public Object processArrayValue(Object date, JsonConfig cfg) {
				return DateUtils.objectToString(date, getDateFormat());
			}
		});
	}
	
	/**
	 * @description 注册全局的Morpher
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param morpher
	 */
	public void registerMorpher(Morpher morpher) {
		if (morpher != null)
			morpherRegistry.registerMorpher(morpher);
	}
		
	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		return CodecUtils.getBytes(JSONSerializer.toJSON(t, getJsonConfig()).toString(), getEncoding());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> type) throws SerializationException {
		String jsonString = CodecUtils.bytesToString(bytes, getEncoding());
		if (isJsonArray(jsonString)) {
			JSONArray jsonArray = JSONArray.fromObject(jsonString, getJsonConfig());
			// 统一的转换为Collection对象
			return (T) JSONArray.toCollection(jsonArray, type != null ? type : getType());
//			return (T) JSONArray.toArray(jsonArray, getType());
		} else {
			JSONObject jsonObject = JSONObject.fromObject(jsonString, getJsonConfig());
			// 转换为单个JavaBean或一个Map对象
			return (T) JSONObject.toBean(jsonObject, type != null ? type : getType());
		}
	}
	
}
