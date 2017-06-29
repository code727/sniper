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
 * Create Date : 2015-1-26
 */

package org.sniper.json.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.StringUtils;

/**
 * JSON工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JSONUtils {
	
	private static MorpherRegistry morpherRegistry;
	
	private static JsonConfig jsonConfig;
	
	private static final Object lock = new Object();
	
	static {
		buildDefaultMorpher();
		buildDefaultJsonConfig();
	}
	
	/**
	 * 构建全局默认的Morpher
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	private static void buildDefaultMorpher() {
		morpherRegistry = net.sf.json.util.JSONUtils.getMorpherRegistry();
		morpherRegistry.registerMorpher(new DateMorpher(new String[] {
				DateUtils.DEFAULT_DATETIME_FORMAT, DateUtils.DEFAULT_DATE_FORMAT
			}
		)); 
	}
	
	/**
	 * 构建全局默认的JsonConfig
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	private static void buildDefaultJsonConfig() {
		jsonConfig = new JsonConfig();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DEFAULT_DATETIME_FORMAT);
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor(){
			
			@Override
			public Object processObjectValue(String propertyName, Object date, JsonConfig cfg) {
				return simpleDateFormat.format(date);
			}

			@Override
			public Object processArrayValue(Object date, JsonConfig cfg) {
				return simpleDateFormat.format(date);
			}
		});
	}
	
	/**
	 * 注册全局的Morpher
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param morpher
	 */
	public static void registerMorpher(Morpher morpher) {
		synchronized (lock) {
			if (morpher != null)
				morpherRegistry.registerMorpher(morpher);
		}
	}
	
	/**
	 * 设置全局的JsonConfig
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonConfig
	 */
	public static void setJsonConfig(JsonConfig jsonConfig) {
		synchronized (lock) {
			JSONUtils.jsonConfig = jsonConfig;
		}
	}

	/**
	 * 将对象转换成JSON字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static <T> String toString(T obj) {
		AssertUtils.assertNotNull(obj, "Converted object can not be null.");
		
		return JSONObject.fromObject(obj, jsonConfig).toString();
	}
	
	/**
	 * 将泛型集合中若干个Bean对象转换成JSON字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param beanCollection
	 * @return
	 */
	public static <T> String toString(Collection<T> beanCollection) {
		AssertUtils.assertNotEmpty(beanCollection, "Converted bean collection can not be null or empty.");
		
		return JSONArray.fromObject(beanCollection, jsonConfig).toString();
	}
	
	/**
	 * 将泛型列表中若干个Bean对象转换成JSON字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param beanList
	 * @return
	 */
	public static <T> String toString(List<T> beanList) {
		AssertUtils.assertNotEmpty(beanList, "Converted bean list can not be null or empty.");
				
		return JSONArray.fromObject(beanList, jsonConfig).toString();
	}
	
	/**
	 * 将数组中的Bean对象转换成JSON字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param beanArray
	 * @return
	 */
	public static String toString(Object[] beanArray) {
		AssertUtils.assertNotEmpty(beanArray, "Converted bean array can not be null or empty.");
				
		return JSONArray.fromObject(beanArray, jsonConfig).toString();
	}
	
	/**
	 * 将JSON字符串转换成指定类型的集合
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param json
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> toCollection(String json, Class<T> beanClass) {
		AssertUtils.assertNotBlank(json, "Converted json string can not be null or empty.");
				
		JSONArray jsonArray = JSONArray.fromObject(json, jsonConfig);
		return JSONArray.toCollection(jsonArray, beanClass);
	}
	
	/**
	 * 将JSON字符串转换成指定类型的列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param json
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(String json, Class<T> beanClass) {
		AssertUtils.assertNotBlank(json, "Converted json string can not be null or empty.");
				
		JSONArray jsonArray = JSONArray.fromObject(json, jsonConfig);
		return (List<T>) JSONArray.toCollection(jsonArray, beanClass);
	}
	
	/**
	 * 将JSON字符串转换成指定类型的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param json
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String json, Class<T> beanClass) {
		AssertUtils.assertNotBlank(json, "Converted json string can not be null or empty.");
				
		json = json.trim();
		if (StringUtils.startsWith(json, "[") && StringUtils.endsWith(json, "]"))
			return toList(json, beanClass).get(0);
		
		return (T) JSONObject.toBean(JSONObject.fromObject(json, jsonConfig), beanClass);
	}

}
