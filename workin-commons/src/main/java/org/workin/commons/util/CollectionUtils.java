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
 * Create Date : 2014-9-29
 */

package org.workin.commons.util;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @description 集合工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0.0
 */
public class CollectionUtils {
	
	/**
	 * @description 判断集合是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param collection
	 * @return 
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}
	
	/**
	 * @description 判断集合是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param collection
	 * @return 
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}
	
	/**
	 * @description 实例化一个ArrayList对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> ArrayList<T> newArrayList() {
		return newArrayList(null);
	}
		
	/**
	 * @description 根据指定的集合实例化一个ArrayList对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> ArrayList<T> newArrayList(Collection<? extends T> collection) {
		return collection != null ? new ArrayList<T>(collection)
				: new ArrayList<T>();
	}
		
	/**
	 * @description 实例化一个LinkedList对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> LinkedList<T> newLinkedList() {
		return newLinkedList(null);
	}
		
	/**
	 * @description 根据指定的集合实例化一个LinkedList对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> LinkedList<T> newLinkedList(Collection<? extends T> collection) {
		return collection != null ? new LinkedList<T>(collection) : new LinkedList<T>();
	}
	
	/**
	 * @description 实例化一个HashSet对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> HashSet<T> newHashSet() {
		return newHashSet(null);
	}
	
	/**
	 * @description 根据指定的集合实例化一个HashSet对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> HashSet<T> newHashSet(Collection<? extends T> collection) {
		return collection != null ? new HashSet<T>(collection) : new HashSet<T>();
	}
	
	/**
	 * @description 实例化一个LinkedHashSet对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> LinkedHashSet<T> newLinkedHashSet() {
		return newLinkedHashSet(null);
	}
	
	/**
	 * @description 根据指定的集合实例化一个LinkedHashSet对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> LinkedHashSet<T> newLinkedHashSet(Collection<? extends T> collection) {
		return collection != null ? new LinkedHashSet<T>(collection) : new LinkedHashSet<T>();
	}
	
	/**
	 * @description 实例化一个Vector对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> Vector<T> newVector() {
		return newVector(null);
	}
	
	/**
	 * @description 根据指定的集合实例化一个Vector对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> Vector<T> newVector(Collection<? extends T> collection) {
		return collection != null ? new Vector<T>(collection) : new Vector<T>();
	}
	
	/**
	 * @description 实例化一个ArrayDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> ArrayDeque<T> newArrayDeque() {
		return newArrayDeque(null);
	}
	
	/**
	 * @description 根据指定的集合实例化一个ArrayDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> ArrayDeque<T> newArrayDeque(Collection<? extends T> collection) {
		return collection != null ? new ArrayDeque<T>(collection) : new ArrayDeque<T>();
	}
	
	/**
	 * @description 实例化一个PriorityQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> PriorityQueue<T> newPriorityQueue() {
		return newPriorityQueue(null);
	}
	
	/**
	 * @description 根据指定的集合实例化一个PriorityQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> PriorityQueue<T> newPriorityQueue(Collection<? extends T> collection) {
		return collection != null ? new PriorityQueue<T>(collection) : new PriorityQueue<T>();
	}
	
	/**
	 * @description 实例化一个DelayQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T extends Delayed> DelayQueue<T> newDelayQueue() {
		return newDelayQueue(null);
	}
	
	/**
	 * @description 根据指定的集合实例化一个DelayQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T extends Delayed> DelayQueue<T> newDelayQueue(Collection<? extends T> collection) {
		return collection != null ? new DelayQueue<T>(collection) : new DelayQueue<T>();
	}
	
	/**
	 * @description 实例化一个ArrayBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> ArrayBlockingQueue<T> newArrayBlockingQueue() {
		return newArrayBlockingQueue(null);
	}
		
	/**
	 * @description 根据指定的集合实例化一个ArrayBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> ArrayBlockingQueue<T> newArrayBlockingQueue(Collection<? extends T> collection) {
		return isNotEmpty(collection) ? new ArrayBlockingQueue<T>(collection.size(), false, collection)
				: new ArrayBlockingQueue<T>(16);
	}
	
	/**
	 * @description 实例化一个LinkedBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> LinkedBlockingQueue<T> newLinkedBlockingQueue() {
		return newLinkedBlockingQueue(null);
	}
	
	/**
	 * @description 根据指定的集合实例化一个LinkedBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> LinkedBlockingQueue<T> newLinkedBlockingQueue(Collection<? extends T> collection) {
		return collection != null ? new LinkedBlockingQueue<T>(collection) : new LinkedBlockingQueue<T>();
	}
	
	/**
	 * @description 实例化一个LinkedBlockingDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> LinkedBlockingDeque<T> newLinkedBlockingDeque() {
		return newLinkedBlockingDeque(null);
	}
	
	/**
	 * @description 根据指定的集合实例化一个LinkedBlockingDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> LinkedBlockingDeque<T> newLinkedBlockingDeque(Collection<? extends T> collection) {
		return collection != null ? new LinkedBlockingDeque<T>(collection) : new LinkedBlockingDeque<T>();
	}
	
	/**
	 * @description 实例化一个PriorityBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> PriorityBlockingQueue<T> newPriorityBlockingQueue() {
		return newPriorityBlockingQueue(null);
	}
	
	/**
	 * @description 根据指定的集合实例化一个PriorityBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> PriorityBlockingQueue<T> newPriorityBlockingQueue(Collection<? extends T> collection) {
		return collection != null ? new PriorityBlockingQueue<T>(collection) : new PriorityBlockingQueue<T>();
	}
	
	/**
	 * @description 实例化一个SynchronousQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> SynchronousQueue<T> newSynchronousQueue() {
		return new SynchronousQueue<T>();
	}
	
	/**
	 * @description 实例化一个ConcurrentLinkedQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> ConcurrentLinkedQueue<T> newConcurrentLinkedQueue() {
		return newConcurrentLinkedQueue(null);
	}
	
	/**
	 * @description 根据指定的集合实例化一个ConcurrentLinkedQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> ConcurrentLinkedQueue<T> newConcurrentLinkedQueue(Collection<? extends T> collection) {
		return collection != null ? new ConcurrentLinkedQueue<T>(collection) : new ConcurrentLinkedQueue<T>();
	}
	
	/**
	 * @description 实例化一个ConcurrentLinkedDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> ConcurrentLinkedDeque<T> newConcurrentLinkedDeque() {
		return new ConcurrentLinkedDeque<T>();
	}
	
	/**
	 * @description 根据指定的集合实例化一个ConcurrentLinkedDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> ConcurrentLinkedDeque<T> newConcurrentLinkedDeque(Collection<? extends T> collection) {
		return new ConcurrentLinkedDeque<T>(collection);
	}
		
	/**
	 * @description 删除集合中所有指定的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param element
	 */
	public static void removeAll(Collection<?> collection, Object element) {
		if (isNotEmpty(collection)) {
			Iterator<?> iterator = collection.iterator();
			while (iterator.hasNext()) {
				if (ObjectUtils.equals(iterator.next(), element))
					iterator.remove();
			}
		}
	}
	
	/**
	 * @description 清空集合中所有的空元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 */
	public static void trimNull(Collection<?> collection) {
		removeAll(collection, null);
	}
	
	/**
	 * @description 获取两集合的差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c1
	 * @param c2
	 * @return c1与 c2的差集(c1 - c2)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection subtract(Collection c1, Collection c2) {
		if (isEmpty(c1) || isEmpty(c2))
			return c1;
		
		List list = newArrayList(c1);
		Iterator iterator = c2.iterator();
		Object element;
		while (iterator.hasNext()) {
			element = iterator.next();
			if (c2.contains(element))
				list.remove(element);
		}
		return list;
	}
	
	/**
	 * @description 获取两集合的补集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c1
	 * @param c2
	 * @return c1相对于c2的补集
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection complement(Collection c1, Collection c2) {
		if (c1 == null || c2 == null)
			return null;
		
		if (c1.size() == 0  && c1.size() == c2.size())
			return c1;
		
		List list = newArrayList();
		Iterator iterator = c2.iterator();
		Object element;
		while (iterator.hasNext()) {
			element = iterator.next();
			if (!c1.contains(element))
				list.add(element);
		}
		return list;
	}
	
	/**
	 * @description 获取集合元素的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static int size(Collection<?> collection) {
		return collection != null ? collection.size() : 0;
	}
	
	/**
	 * @description 将集合转换成Object类型的数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> Object[] toObjectArray(Collection<T> collection) {
		if (isEmpty(collection))
			return new Object[] {};
		
		return collection.toArray();
	}
	
	/**
	 * @description 将集合转换成具有共同元素类型的数组。
	 * 				此方法要求集合中至少有一个不为null的元素，否则方法将抛出NullPointerException。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<T> collection) {
		Class<?> elementType = ClassUtils.getCommonType(collection);
		if (elementType == null)
			throw new NullPointerException("Collection element type can not be null," +
					"requires at least one non-null element in the collection.");
		
		return collection.toArray((T[]) Array.newInstance(elementType, collection.size()));
	}
	
	/**
	 * @description 将集合转换成指定类型的数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param componentType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<T> collection, Class<T> componentType) {
		AssertUtils.assertNotNull(componentType, "Component type can not be null.");
		if (isEmpty(collection))
			return (T[]) Array.newInstance(componentType, 0);
		
		return collection.toArray((T[]) Array.newInstance(componentType, collection.size()));
	}
	
	/**
	 * @description 判断执行对象是否为Collection接口实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static boolean isCollection(Object obj) {
		return obj instanceof Collection;
	}
	
	/**
	 * @description 获取索引位对应的元素值，未获取到时默认返回null
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @param index
	 * @return
	 */
	public static <T> T get(List<T> list, int index) {
		return get(list, index, null);
	}
	
	/**
	 * @description 获取索引位对应的元素值，未获取到时返回指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public static <T> T get(List<T> list, int index, T defaultValue) {
		int max = size(list) - 1;
		if (max == -1 || index < 0 || index > max)
			return defaultValue;
		
		return list.get(index);
	}
	
	/**
	 * @description 获取列表中的第一个元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @return
	 */
	public static <T> T first(List<T> list) {
		return first(list, null);
	}
	
	/**
	 * @description 获取列表中的第一个元素，未获取到时返回指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @param defaultValue
	 * @return
	 */
	public static <T> T first(List<T> list, T defaultValue) {
		return get(list, 0, null);
	}
	
	/**
	 * @description 获取列表中的最后一个元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @return
	 */
	public static <T> T last(List<T> list) {
		return last(list, null);
	}
	
	/**
	 * @description 获取列表中的最后一个元素，未获取到时返回指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @param defaultValue
	 * @return
	 */
	public static <T> T last(List<T> list, T defaultValue) {
		return get(list, size(list) - 1, null);
	}
	
	
	/**
	 * @description 按指定的分隔符将列表中各元素连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @param separator
	 * @return
	 */
	public static <T> String join(List<T> list, String separator) {
		if (isEmpty(list))
			return StringUtils.EMPTY_STRING;
		
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isNotEmpty(separator)) {
			
			int max = list.size() - 1;
			for (int i = 0; i < max; i++)
				builder.append(list.get(i)).append(separator);
			
			builder.append(list.get(max));
		} else {
			for (T e : list) 
				builder.append(e);
		}
		return builder.toString();
	}
	
}

