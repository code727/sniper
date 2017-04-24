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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 映射集工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapUtils {
	
	/**
	 * 实例化一个HashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}
	
	/**
	 * 实例化一个具有指定容量的HashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <K, V> HashMap<K, V> newHashMap(int initialCapacity) {
		return new HashMap<K, V>(initialCapacity);
	}
		
	/**
	 * 根据指定的映射集实例化一个HashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
		return map != null ? new HashMap<K, V>(map) : new HashMap<K, V>();
	}
	
	/**
	 * 实例化一个LinkedHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
		return new LinkedHashMap<K, V>();
	}
	
	/**
	 * 实例化一个具有指定容量LinkedHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int initialCapacity) {
		return new LinkedHashMap<K, V>(initialCapacity);
	}
	
	/**
	 * 根据指定的映射集实例化一个LinkedHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
		return map != null ? new LinkedHashMap<K, V>(map) : new LinkedHashMap<K, V>();
	}
	
	/**
	 * 实例化一个Hashtable对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K, V> Hashtable<K, V> newHashtable() {
		return new Hashtable<K, V>();
	}
	
	/**
	 * 实例化一个具有指定容量Hashtable对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <K, V> Hashtable<K, V> newHashtable(int initialCapacity) {
		return new Hashtable<K, V>(initialCapacity);
	}
	
	/**
	 * 根据指定的映射集实例化一个Hashtable对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K, V> Hashtable<K, V> newHashtable(Map<? extends K, ? extends V> map) {
		return map != null ? new Hashtable<K, V>(map) : new Hashtable<K, V>();
	}
	
	/**
	 * 实例化一个TreeMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K, V> TreeMap<K, V> newTreeMap() {
		return new TreeMap<K, V>();
	}
	
	/**
	 * 实例化一个具有指定比较器的TreeMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param comparator
	 * @return
	 */
	public static <K, V> TreeMap<K, V> newTreeMap(Comparator<? super K> comparator) {
		return comparator != null ? new TreeMap<K, V>(comparator) : new TreeMap<K, V>();
	}
	
	/**
	 * 实例化一个具有指定排序器的TreeMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sortedMap
	 * @return
	 */
	public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> sortedMap) {
		return sortedMap != null ? new TreeMap<K, V>(sortedMap) : new TreeMap<K, V>();
	}
	
	/**
	 * 根据指定的映射集实例化一个TreeMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K, V> TreeMap<K, V> newTreeMap(Map<? extends K, ? extends V> map) {
		return map != null ? new TreeMap<K, V>(map) : new TreeMap<K, V>();
	}
	
	/**
	 * 实例化一个ConcurrentHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
		return new ConcurrentHashMap<K, V>();
	}
	
	/**
	 * 实例化一个具有指定容量的ConcurrentHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int initialCapacity) {
		return new ConcurrentHashMap<K, V>(initialCapacity);
	}
	
	/**
	 * 根据指定的映射集实例化一个ConcurrentHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> map) {
		return map != null ? new ConcurrentHashMap<K, V>(map) : new ConcurrentHashMap<K, V>();
	}
	
	/**
	 * 实例化一个IdentityHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
		return new IdentityHashMap<K, V>();
	}
	
	/**
	 * 实例化一个具有指定容量的IdentityHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(int initialCapacity) {
		return new IdentityHashMap<K, V>(initialCapacity);
	}
	
	/**
	 * 根据指定的映射集实例化一个IdentityHashMap对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(Map<? extends K, ? extends V> map) {
		return map != null ? new IdentityHashMap<K, V>(map) : new IdentityHashMap<K, V>();
	}
		
	/**
	 * 判断Map对象是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?,?> map) {
		return map == null || map.size() == 0;
	}
	
	/**
	 * 判断Map对象是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map<?,?> map) {
		return !isEmpty(map);
	}
	
	/**
	 * 将映射集里的所有键值对元素连接成URL查询字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @return
	 */
	public static <K extends CharSequence, V> String joinQueryString(Map<K, V> map) {
		return joinQueryString(map, null);
	}
	
	/**
	 * 将映射集里排除后剩余的键值对元素连接成URL查询字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @param excludeNames
	 * @return
	 */
	public static <K1 extends CharSequence, K2 extends CharSequence, V> String joinQueryString(Map<K1, V> map, K2[] excludeNames) {
		if (isEmpty(map))
			return StringUtils.EMPTY_STRING;
		
		StringBuilder builder = new StringBuilder();
		
		Set<Entry<K1, V>> entrySet = map.entrySet();
		if (ArrayUtils.isNotEmpty(excludeNames)) {
			Set<Entry<K1,V>> remainEntrySet = CollectionUtils.newLinkedHashSet();
			Iterator<Entry<K1, V>> iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Entry<K1, V> entry = iterator.next();
				if (!ArrayUtils.containsValue(excludeNames, entry.getKey()))
					remainEntrySet.add(entry);
			}
			entrySet = remainEntrySet;
		}
		for (Entry<K1,V> entry : entrySet) {
			if (builder.length() > 0)
				builder.append("&");
			builder.append(entry.getKey()).append("=").append(entry.getValue());
		}
		
		return builder.toString();
	}
	
	/**
	 * 将映射集里的所有键值对元素按各部分的连接符连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map 映射集
	 * @param kvSeperator 键值之间的连接符
	 * @param itemSeperator 键值对之间的连接符
	 * @return
	 */
	public static <K, V> String join(Map<K, V> map, Object kvSeperator, Object itemSeperator) {
		return join(map.entrySet(), kvSeperator, itemSeperator);
	}
	
	/**
	 * 将映射集里排除后剩余的键值对元素按各部分的连接符连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map
	 * @param kvSeperator
	 * @param itemSeperator
	 * @param excludeNames
	 * @return
	 */
	public static <K, V> String join(Map<K, V> map, Object kvSeperator, Object itemSeperator, K[] excludeNames) {
		return map != null ? join(map.entrySet(), kvSeperator, itemSeperator, excludeNames) : StringUtils.EMPTY_STRING;
	}
	
	/**
	 * 将集合里的所有Entry元素按各部分的连接符连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entrySet 映射集
	 * @param kvSeperator 键值之间的连接符
	 * @param itemSeperator 键值对之间的连接符
	 * @return
	 */
	public static <K,V> String join(Set<Entry<K,V>> entrySet, Object kvSeperator, Object itemSeperator) {
		return join(entrySet, kvSeperator, itemSeperator, null);
	}
	
	/**
	 * 将集合里排除后剩余的Entry元素按各部分的连接符连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entrySet
	 * @param kvSeperator
	 * @param itemSeperator
	 * @param excludeKeys
	 * @return
	 */
	public static <K, V> String join(Set<Entry<K,V>> entrySet, Object kvSeperator, Object itemSeperator, K[] excludeKeys) {
		if (CollectionUtils.isEmpty(entrySet))
			return StringUtils.EMPTY_STRING;
		
		if (ArrayUtils.isNotEmpty(excludeKeys)) {
			Iterator<Entry<K, V>> iterator = entrySet.iterator();
			Set<Entry<K,V>> remainEntrySet = CollectionUtils.newLinkedHashSet();
			while (iterator.hasNext()) {
				Entry<K, V> entry = iterator.next();
				if (!ArrayUtils.contains(excludeKeys, entry.getKey()))
					remainEntrySet.add(entry);
			}
			
			return join(remainEntrySet, kvSeperator, itemSeperator, null);
		} else {
			StringBuilder builder = new StringBuilder();
			for (Entry<K,V> entry : entrySet) {
				if (builder.length() > 0)
					builder.append(itemSeperator);
				builder.append(entry.getKey()).append(kvSeperator).append(entry.getValue());
			}
			
			return builder.toString();
		}
	}
	
	/**
	 * 获取两映射键的差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static Set<?> keySubtract(Map<?,?> map1, Map<?,?> map2) {
		if (map1 == null)
			return null;
		if (map2 == null)
			return map1.keySet();
		
		return CollectionUtils.subtract(map1.keySet(), map2.keySet());
	}
	
	/**
	 * 获取两映射键的补集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static Set<?> keyComplement(Map<?,?> map1, Map<?,?> map2) {
		if (map1 == null || map2 == map1)
			return null;
		
		return CollectionUtils.complement(map1.keySet(), map2.keySet());
	}

}
