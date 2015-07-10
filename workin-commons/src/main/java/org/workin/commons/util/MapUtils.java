/*
 * Copyright 2014 the original author or authors.
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
 * Create Date : 2014-11-3
 */

package org.workin.commons.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 映射集工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapUtils {
	
	/**
	 * @description 实例化一个HashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}
	
	/**
	 * @description 根据指定的映射集实例化一个HashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
		return map != null ? new HashMap<K, V>(map) : new HashMap<K, V>();
	}
	
	/**
	 * @description 实例化一个LinkedHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
		return new LinkedHashMap<K, V>();
	}
	
	/**
	 * @description 根据指定的映射集实例化一个LinkedHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
		return map != null ? new LinkedHashMap<K, V>(map) : new LinkedHashMap<K, V>();
	}
	
	/**
	 * @description 实例化一个TreeMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K, V> TreeMap<K, V> newTreeMap() {
		return new TreeMap<K, V>();
	}
	
	/**
	 * @description 根据指定的映射集实例化一个TreeMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K, V> TreeMap<K, V> newTreeMap(Map<? extends K, ? extends V> map) {
		return map != null ? new TreeMap<K, V>(map) : new TreeMap<K, V>();
	}
	
	/**
	 * @description 实例化一个Hashtable对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K, V> Hashtable<K, V> newHashtable() {
		return new Hashtable<K, V>();
	}
	
	/**
	 * @description 根据指定的映射集实例化一个Hashtable对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K, V> Hashtable<K, V> newHashtable(Map<? extends K, ? extends V> map) {
		return map != null ? new Hashtable<K, V>(map) : new Hashtable<K, V>();
	}
	
	/**
	 * @description 实例化一个ConcurrentHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
		return new ConcurrentHashMap<K, V>();
	}
	
	/**
	 * @description 根据指定的映射集实例化一个ConcurrentHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> map) {
		return map != null ? new ConcurrentHashMap<K, V>(map) : new ConcurrentHashMap<K, V>();
	}
		
	/**
	 * @description 判断Map对象是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?,?> map) {
		return map == null || map.size() == 0;
	}
	
	/**
	 * @description 判断Map对象是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map<?,?> map) {
		return !isEmpty(map);
	}
	
	/**
	 * @description 将映射集里的所有键值对元素连接成URL参数字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <V> String joinURLParameters(Map<String, V> map) {
		return map != null ? join(map.entrySet(), "=", "&") : StringUtils.EMPTY_STRING;
	}
	
	/**
	 * @description 将映射集里的所有键值对元素按各部分的连接符连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map 映射集
	 * @param kvSeperator 键值之间的连接符
	 * @param itemSeperator 键值对之间的连接符
	 * @return
	 */
	public static <K,V> String join(Map<K,V> map, String kvSeperator, String itemSeperator) {
		return join(map.entrySet(), kvSeperator, itemSeperator);
	}
	
	/**
	 * @description 将映射集里的所有元素按各部分的连接符连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entrySet 映射集
	 * @param kvSeperator 键值之间的连接符
	 * @param itemSeperator 键值对之间的连接符
	 * @return
	 */
	public static <K,V> String join(Set<Entry<K,V>> entrySet, String kvSeperator, String itemSeperator) {
		StringBuilder builder = new StringBuilder();
		for (Entry<K,V> entry : entrySet) {
			if (builder.length() > 0)
				builder.append(itemSeperator);
			builder.append(entry.getKey()).append(kvSeperator).append(entry.getValue());
		}
		return builder.toString();
	}
	
	/**
	 * @description 获取两映射键的差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map1
	 * @param map2
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Collection keySubtract(Map map1, Map map2) {
		if (map1 == null)
			return null;
		if (map2 == null)
			return map1.keySet();
		
		return CollectionUtils.subtract(map1.keySet(), map2.keySet());
	}
	
	/**
	 * @description 获取两映射键的补集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map1
	 * @param map2
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Collection keyComplement(Map map1, Map map2) {
		if (map1 == null || map2 == map1)
			return null;
		
		return CollectionUtils.complement(map1.keySet(), map2.keySet());
	}

}
