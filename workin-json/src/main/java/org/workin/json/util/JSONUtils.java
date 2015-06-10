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

package org.workin.json.util;

import java.util.Collection;
import java.util.List;

import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.DateUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description JSON工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JSONUtils {
	
	static {
		MorpherRegistry morpherRegistry = net.sf.json.util.JSONUtils.getMorpherRegistry();
		morpherRegistry.registerMorpher(new DateMorpher(new String[] {
				DateUtils.DEFAULT_DATETIME_FORMAT, DateUtils.DEFAULT_DATE_FORMAT
			}
		)); 
	}
	
	/**
	 * @description 将单个Bean对象转换成JSON字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 */
	public static <T> String toString(T bean) {
		AssertUtils.assertNotNull(bean, "Converted bean can not be null.");
		return JSONObject.fromObject(bean).toString();
	}
	
	/**
	 * @description 将泛型集合中若干个Bean对象转换成JSON字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param beanCollection
	 * @return
	 */
	public static <T> String toString(Collection<T> beanCollection) {
		AssertUtils.assertTrue(CollectionUtils.isNotEmpty(beanCollection),
				"Converted bean collection can not be null or empty.");
		return JSONArray.fromObject(beanCollection).toString();
	}
	
	/**
	 * @description 将泛型列表中若干个Bean对象转换成JSON字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param beanList
	 * @return
	 */
	public static <T> String toString(List<T> beanList) {
		AssertUtils.assertTrue(CollectionUtils.isNotEmpty(beanList),
				"Converted bean list can not be null or empty.");
		return JSONArray.fromObject(beanList).toString();
	}
	
	/**
	 * @description 将数组中的Bean对象转换成JSON字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param beanArray
	 * @return
	 */
	public static String toString(Object[] beanArray) {
		AssertUtils.assertTrue(ArrayUtils.isNotEmpty(beanArray),
				"Converted bean array can not be null or empty.");
		return JSONArray.fromObject(beanArray).toString();
	}
	
	/**
	 * @description 将JSON字符串转换成指定类型的集合
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param json
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> toCollection(String json, Class<T> beanClass) {
		AssertUtils.assertTrue(StringUtils.isNotBlank(json),
				"Converted json string can not be null or empty.");
		JSONArray jsonArray = JSONArray.fromObject(json);
		return JSONArray.toCollection(jsonArray, beanClass);
	}
	
	/**
	 * @description 将JSON字符串转换成指定类型的列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param json
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(String json, Class<T> beanClass) {
		AssertUtils.assertTrue(StringUtils.isNotBlank(json),
				"Converted json string can not be null or empty.");
		JSONArray jsonArray = JSONArray.fromObject(json);
		return (List<T>) JSONArray.toCollection(jsonArray, beanClass);
	}
	
	/**
	 * @description 将JSON字符串转换成指定类型的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param json
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String json, Class<T> beanClass) {
		AssertUtils.assertTrue(StringUtils.isNotBlank(json),
				"Converted json string can not be null or empty.");
		return (T) JSONObject.toBean(JSONObject.fromObject(json.replaceAll("\\[|\\]", "")), beanClass);
	}

}
