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

package org.sniper.commons.util;

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
import java.util.Set;
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

import org.sniper.commons.exception.ListIndexOutOfBoundsException;

/**
 * 集合工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0.0
 */
public class CollectionUtils {
	
	private CollectionUtils() {}
	
	/**
	 * 判断集合是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param collection
	 * @return 
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}
	
	/**
	 * 判断集合是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param collection
	 * @return 
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}
	
	/**
	 * 实例化一个ArrayList对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> ArrayList<T> newArrayList() {
		return newArrayList(null);
	}
		
	/**
	 * 根据指定的集合实例化一个ArrayList对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> ArrayList<T> newArrayList(Collection<? extends T> collection) {
		return collection != null ? new ArrayList<T>(collection) : new ArrayList<T>();
	}
	
	/**
	 * 实例化一个具备指定初始容量的ArrayList对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <T> ArrayList<T> newArrayList(int initialCapacity) {
		return new ArrayList<T>(NumberUtils.minLimit(initialCapacity, 0));
	}
		
	/**
	 * 实例化一个LinkedList对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> LinkedList<T> newLinkedList() {
		return newLinkedList(null);
	}
		
	/**
	 * 根据指定的集合实例化一个LinkedList对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> LinkedList<T> newLinkedList(Collection<? extends T> collection) {
		return collection != null ? new LinkedList<T>(collection) : new LinkedList<T>();
	}
	
	/**
	 * 实例化一个HashSet对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> HashSet<T> newHashSet() {
		return newHashSet(null);
	}
	
	/**
	 * 根据指定的集合实例化一个HashSet对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> HashSet<T> newHashSet(Collection<? extends T> collection) {
		return collection != null ? new HashSet<T>(collection) : new HashSet<T>();
	}
	
	/**
	 * 实例化一个具备指定初始容量的HashSet对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <T> HashSet<T> newHashSet(int initialCapacity) {
		return new HashSet<T>(NumberUtils.minLimit(initialCapacity, 0));
	}
	
	/**
	 * 实例化一个LinkedHashSet对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> LinkedHashSet<T> newLinkedHashSet() {
		return newLinkedHashSet(null);
	}
	
	/**
	 * 根据指定的集合实例化一个LinkedHashSet对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> LinkedHashSet<T> newLinkedHashSet(Collection<? extends T> collection) {
		return collection != null ? new LinkedHashSet<T>(collection) : new LinkedHashSet<T>();
	}
	
	/**
	 * 实例化一个具备指定初始容量的LinkedHashSet对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <T> LinkedHashSet<T> newLinkedHashSet(int initialCapacity) {
		return new LinkedHashSet<T>(NumberUtils.minLimit(initialCapacity, 0));
	}
	
	/**
	 * 实例化一个Vector对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> Vector<T> newVector() {
		return newVector(null);
	}
	
	/**
	 * 根据指定的集合实例化一个Vector对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> Vector<T> newVector(Collection<? extends T> collection) {
		return collection != null ? new Vector<T>(collection) : new Vector<T>();
	}
	
	/**
	 * 实例化一个具备指定初始容量的Vector对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <T> Vector<T> newVector(int initialCapacity) {
		return newVector(initialCapacity, 0);
	}
	
	/**
	 * 实例化一个具备指定初始容量和增长系数的Vector对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @param capacityIncrement
	 * @return
	 */
	public static <T> Vector<T> newVector(int initialCapacity, int capacityIncrement) {
		return new Vector<T>(NumberUtils.minLimit(initialCapacity, 0), NumberUtils.minLimit(capacityIncrement, 0));
	}
	
	/**
	 * 实例化一个ArrayDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> ArrayDeque<T> newArrayDeque() {
		return newArrayDeque(null);
	}
	
	/**
	 * 根据指定的集合实例化一个ArrayDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> ArrayDeque<T> newArrayDeque(Collection<? extends T> collection) {
		return collection != null ? new ArrayDeque<T>(collection) : new ArrayDeque<T>();
	}
	
	/**
	 * 实例化一个具备指定初始容量的ArrayDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <T> ArrayDeque<T> newArrayDeque(int initialCapacity) {
		return new ArrayDeque<T>(NumberUtils.minLimit(initialCapacity, 0));
	}
	
	/**
	 * 实例化一个PriorityQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> PriorityQueue<T> newPriorityQueue() {
		return newPriorityQueue(null);
	}
	
	/**
	 * 根据指定的集合实例化一个PriorityQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> PriorityQueue<T> newPriorityQueue(Collection<? extends T> collection) {
		return collection != null ? new PriorityQueue<T>(collection) : new PriorityQueue<T>();
	}
	
	/**
	 * 实例化一个具备指定初始容量的PriorityQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <T> PriorityQueue<T> newPriorityQueue(int initialCapacity) {
		return new PriorityQueue<T>(NumberUtils.minLimit(initialCapacity, 0));
	}
	
	/**
	 * 实例化一个DelayQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T extends Delayed> DelayQueue<T> newDelayQueue() {
		return newDelayQueue(null);
	}
	
	/**
	 * 根据指定的集合实例化一个DelayQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T extends Delayed> DelayQueue<T> newDelayQueue(Collection<? extends T> collection) {
		return collection != null ? new DelayQueue<T>(collection) : new DelayQueue<T>();
	}
	
	/**
	 * 实例化一个ArrayBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> ArrayBlockingQueue<T> newArrayBlockingQueue() {
		return newArrayBlockingQueue(null);
	}
	
	/**
	 * 根据指定的集合实例化一个ArrayBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 加入阻塞队列的集合
	 * @return
	 */
	public static <T> ArrayBlockingQueue<T> newArrayBlockingQueue(Collection<? extends T> collection) {
		return newArrayBlockingQueue(false, collection);
	}
	
	/**
	 * 实例化一个具备指定初始容量的ArrayBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <T> ArrayBlockingQueue<T> newArrayBlockingQueue(int initialCapacity) {
		return newArrayBlockingQueue(initialCapacity, false);
	}
	
	/**
	 * 实例化一个ArrayBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fair 如果为true则阻塞线程的队列访问，插入或删除，按FIFO顺序处理。否则访问顺序不固定
	 * @return
	 */
	public static <T> ArrayBlockingQueue<T> newArrayBlockingQueue(boolean fair) {
		return newArrayBlockingQueue(0, fair);
	}
	
	/**
	 * 实例化一个ArrayBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fair 如果为true则阻塞线程的队列访问，插入或删除，按FIFO顺序处理。否则访问顺序不固定
	 * @param collection 加入阻塞队列的集合
	 * @return
	 */
	public static <T> ArrayBlockingQueue<T> newArrayBlockingQueue(boolean fair, Collection<? extends T> collection) {
		return newArrayBlockingQueue(0, fair, collection);
	}
	
	/**
	 * 实例化一个具备指定初始容量的ArrayBlockingQueue对象，但实际容量不会小于指定集合的容量
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @param collection 加入阻塞队列的集合
	 * @return
	 */
	public static <T> ArrayBlockingQueue<T> newArrayBlockingQueue(int initialCapacity, Collection<? extends T> collection) {
		return newArrayBlockingQueue(initialCapacity, false, collection);
	}
	
	/**
	 * 实例化一个具备指定初始容量的ArrayBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @param fair 如果为true则阻塞线程的队列访问，插入或删除，按FIFO顺序处理。否则访问顺序不固定
	 * @return
	 */
	public static <T> ArrayBlockingQueue<T> newArrayBlockingQueue(int initialCapacity, boolean fair) {
		return newArrayBlockingQueue(initialCapacity, fair, null);
	}
	
	/**
	 * 实例化一个具备指定初始容量的ArrayBlockingQueue对象，但实际容量不会小于指定集合的容量
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @param fair 如果为true则阻塞线程的队列访问，插入或删除，按FIFO顺序处理。否则访问顺序不固定
	 * @param collection 加入阻塞队列的集合
	 * @return
	 */
	public static <T> ArrayBlockingQueue<T> newArrayBlockingQueue(int initialCapacity, boolean fair, Collection<? extends T> collection) {
		return collection != null ? new ArrayBlockingQueue<T>(NumberUtils.minLimit(initialCapacity, NumberUtils.minLimit(collection.size(), 1)), fair, collection)
				: new ArrayBlockingQueue<T>(NumberUtils.minLimit(initialCapacity, 1), fair);
	}
		
	/**
	 * 实例化一个LinkedBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> LinkedBlockingQueue<T> newLinkedBlockingQueue() {
		return newLinkedBlockingQueue(null);
	}
	
	/**
	 * 根据指定的集合实例化一个LinkedBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> LinkedBlockingQueue<T> newLinkedBlockingQueue(Collection<? extends T> collection) {
		return collection != null ? new LinkedBlockingQueue<T>(collection) : new LinkedBlockingQueue<T>();
	}
	
	/**
	 * 实例化一个LinkedBlockingDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> LinkedBlockingDeque<T> newLinkedBlockingDeque() {
		return newLinkedBlockingDeque(null);
	}
	
	/**
	 * 根据指定的集合实例化一个LinkedBlockingDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> LinkedBlockingDeque<T> newLinkedBlockingDeque(Collection<? extends T> collection) {
		return collection != null ? new LinkedBlockingDeque<T>(collection) : new LinkedBlockingDeque<T>();
	}
	
	/**
	 * 实例化一个具备指定初始容量的LinkedBlockingDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <T> LinkedBlockingDeque<T> newLinkedBlockingDeque(int initialCapacity) {
		return newLinkedBlockingDeque(NumberUtils.minLimit(initialCapacity, 1));
	}
	
	/**
	 * 实例化一个PriorityBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> PriorityBlockingQueue<T> newPriorityBlockingQueue() {
		return newPriorityBlockingQueue(null);
	}
	
	/**
	 * 根据指定的集合实例化一个PriorityBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> PriorityBlockingQueue<T> newPriorityBlockingQueue(Collection<? extends T> collection) {
		return collection != null ? new PriorityBlockingQueue<T>(collection) : new PriorityBlockingQueue<T>();
	}
	
	/**
	 * 实例化一个具备指定初始容量的PriorityBlockingQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param initialCapacity
	 * @return
	 */
	public static <T> PriorityBlockingQueue<T> newPriorityBlockingQueue(int initialCapacity) {
		return new PriorityBlockingQueue<T>(NumberUtils.minLimit(initialCapacity, 1));
	}
	
	/**
	 * 实例化一个SynchronousQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> SynchronousQueue<T> newSynchronousQueue() {
		return newSynchronousQueue(false);
	}
	
	/**
	 * 实例化一个SynchronousQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fair
	 * @return
	 */
	public static <T> SynchronousQueue<T> newSynchronousQueue(boolean fair) {
		return new SynchronousQueue<T>(fair);
	}
	
	/**
	 * 实例化一个ConcurrentLinkedQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> ConcurrentLinkedQueue<T> newConcurrentLinkedQueue() {
		return newConcurrentLinkedQueue(null);
	}
	
	/**
	 * 根据指定的集合实例化一个ConcurrentLinkedQueue对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> ConcurrentLinkedQueue<T> newConcurrentLinkedQueue(Collection<? extends T> collection) {
		return collection != null ? new ConcurrentLinkedQueue<T>(collection) : new ConcurrentLinkedQueue<T>();
	}
	
	/**
	 * 实例化一个ConcurrentLinkedDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> ConcurrentLinkedDeque<T> newConcurrentLinkedDeque() {
		return newConcurrentLinkedDeque(null);
	}
	
	/**
	 * 根据指定的集合实例化一个ConcurrentLinkedDeque对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> ConcurrentLinkedDeque<T> newConcurrentLinkedDeque(Collection<? extends T> collection) {
		return collection != null ? new ConcurrentLinkedDeque<T>(collection) : new ConcurrentLinkedDeque<T>(collection);
	}
	
	/**
	 * 清空集合中所有的空元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 */
	public static void trimNull(Collection<?> collection) {
		removeAll(collection, null);
	}
		
	/**
	 * 删除集合中所有指定的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param element
	 */
	public static void removeAll(Collection<?> collection, Object element) {
		if (isNotEmpty(collection)) {
			if (element instanceof Collection)
				collection.removeAll((Collection<?>) element);
			else {
				Set<Object> set = newHashSet();
				set.add(element);
				collection.removeAll(set);
			}
		}
	}
	
	/**
	 * 删除集合中所有重复的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 */
	public static void removeDuplication(Collection<?> collection) {
		if (isNotEmpty(collection) && !(collection instanceof Set)) {
			
			// 记录已被迭代过的集合元素
			LinkedHashSet<Object> iterated = newLinkedHashSet();
			Object element;
			Iterator<?> iterator = collection.iterator();
			while (iterator.hasNext()) {
				element = iterator.next();
				if (iterated.contains(element))
					// 如果副本中已存在当前元素，说明以前已迭代处理过相同的元素，因此需删除这个重复的	
					iterator.remove();
				else 
					// 添加到已迭代过的记录集合中
					iterated.add(element);
			}
		}
	}
	
	/**
	 * 求两集合的并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s1
	 * @param s2
	 * @return s1∪s2
	 */
	public static Set<?> union(Set<?> s1, Set<?> s2) {
		// "空∪s2"为s2
		if (isEmpty(s1))
			return newLinkedHashSet(s2);
		
		// "s1∪空"为s1
		if (isEmpty(s2))
			return newLinkedHashSet(s1);
		
		HashSet<Object> set = newLinkedHashSet(s1);
		set.addAll(s2);
		return set;
	}
	
	/**
	 * 求两集合的并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c1
	 * @param c2
	 * @return c1∪c2
	 */
	public static List<?> union(Collection<?> c1, Collection<?> c2) {
		// "空∪c2"为c2
		if (isEmpty(c1))
			return newArrayList(c2);
		
		// "c1∪空"为c1
		if (isEmpty(c2))
			return newArrayList(c1);
		
		List<Object> list = newArrayList(c1);
		list.addAll(c2);
		
		return list;
	}
	
	/**
	 * 求两集合的唯一元素并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c1
	 * @param c2
	 * @return c1∪c2
	 */
	public static List<?> unionUnique(Collection<?> c1, Collection<?> c2) {
		// "空∪c2"为c2
		if (isEmpty(c1))
			return newArrayList(newLinkedHashSet(c2));
		
		// "c1∪空"为c1
		if (isEmpty(c2))
			return newArrayList(newLinkedHashSet(c1));
		
		Set<Object> set = newLinkedHashSet(c1);
		set.addAll(c2);
				
		return newArrayList(set);
	}
	
	/**
	 * 求两集合的交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s1
	 * @param s2
	 * @return s1∩s2
	 */
	public static Set<?> intersection(Set<?> s1, Set<?> s2) {
		// "s1∩空"或"空∩s2"都为空
		if (isEmpty(s1) || isEmpty(s2))
			return newLinkedHashSet();
		
		LinkedHashSet<Object> set = newLinkedHashSet(s1);
		set.retainAll(s2);
		
		return set;
	}
	
	/**
	 * 求两集合的交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c1
	 * @param c2
	 * @return c1∩c2
	 */
	public static List<?> intersection(Collection<?> c1, Collection<?> c2) {
		// "c1∩空"或"空∩c2"都为空
		if (isEmpty(c1) || isEmpty(c2))
			return newArrayList();
		
		List<Object> list = newArrayList(c1);
		list.retainAll(c2);
		
		return list;
	}
	
	/**
	 * 求两集合唯一元素的交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c1
	 * @param c2
	 * @return c1∩c2
	 */
	public static List<?> intersectionUnique(Collection<?> c1, Collection<?> c2) {
		List<?> list = intersection(c1, c2);
		removeDuplication(list);
		return list;
	}
	
	/**
	 * 求两集合的差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static Set<?> subtract(Set<?> s1, Set<?> s2) {
		// "s1-空"或"空-s2"都为s1
		if (isEmpty(s1) || isEmpty(s2))
			return newLinkedHashSet(s1); 
		
		Set<?> set = newLinkedHashSet(s1);
		set.removeAll(s2);
		return set;
	}
		
	/**
	 * 求两集合的差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c1
	 * @param c2
	 * @return c1 - c2
	 */
	public static List<?> subtract(Collection<?> c1, Collection<?> c2) {
		// "c1-null"或"null-c2"都为c1
		if (isEmpty(c1) || isEmpty(c2))
			return newArrayList(c1);
		
		List<?> list = newArrayList(c1);
		list.removeAll(c2);
		return list;
	}
	
	/**
	 * 求两集合唯一元素的差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static List<?> subtractUnique(Collection<?> c1, Collection<?> c2) {
		List<?> list = subtract(c1, c2);
		removeDuplication(list);
		return list;
	}
	
	/**
	 * 求两集合的补集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s1
	 * @param s2
	 * @return s1相对于s2的补集，等价于s2与s1的差集(s2-s1)
	 */
	public static Set<?> complement(Set<?> s1, Set<?> s2) {
		return subtract(s2, s1);
	}
	
	/**
	 * 求两集合的补集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c1
	 * @param c2
	 * @return c1相对于c2的补集，等价于c2与c1的差集(c2-c1)
	 */
	public static Collection<?> complement(Collection<?> c1, Collection<?> c2) {
		return subtract(c2, c1);
	}
	
	/**
	 * 求两集合唯一元素的补集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c1
	 * @param c2
	 * @return c1相对于c2的补集，等价于c2与c1的差集(c2-c1)
	 */
	public static Collection<?> complementUnique(Collection<?> c1, Collection<?> c2) {
		return subtractUnique(c2, c1);
	}
		
	/**
	 * 获取集合元素的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static int size(Collection<?> collection) {
		return collection != null ? collection.size() : 0;
	}
	
	/**
	 * 将集合转换成Object类型的数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> Object[] toObjectArray(Collection<T> collection) {
		return isNotEmpty(collection) ? collection.toArray() : new Object[] {};
	}
	
	/**
	 * 将集合转换成具有共同元素类型的数组。
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
	 * 将集合转换成指定类型的数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param componentType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<T> collection, Class<T> componentType) {
		AssertUtils.assertNotNull(componentType, "Component type can not be null");
		if (isEmpty(collection))
			return (T[]) Array.newInstance(componentType, 0);
		
		return collection.toArray((T[]) Array.newInstance(componentType, collection.size()));
	}
	
	/**
	 * 判断执行对象是否为Collection接口实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static boolean isCollection(Object obj) {
		return obj instanceof Collection;
	}
	
	/**
	 * 获取索引位对应的元素值，未获取到时默认返回null
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @param index
	 * @return
	 */
	public static <T> T get(List<T> list, int index) {
		return get(list, index, null);
	}
	
	/**
	 * 获取索引位对应的元素值，未获取到时返回指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public static <T> T get(List<T> list, int index, T defaultValue) {
		int max = size(list) - 1;
		if (index < 0 || index > max)
			return defaultValue;
		
		return list.get(index);
	}
		
	/**
	 * 获取列表中的第一个元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @return
	 */
	public static <T> T getFirst(List<T> list) {
		return getFirst(list, null);
	}
	
	/**
	 * 获取列表中的第一个元素，未获取到时返回指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @param defaultValue
	 * @return
	 */
	public static <T> T getFirst(List<T> list, T defaultValue) {
		if (list instanceof LinkedList)
			return list.size() > 0 ? ((LinkedList<T>) list).getFirst() : defaultValue;
		
		return get(list, 0, null);
	}
	
	/**
	 * 获取列表中的最后一个元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @return
	 */
	public static <T> T getLast(List<T> list) {
		return getLast(list, null);
	}
	
	/**
	 * 获取列表中的最后一个元素，未获取到时返回指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @param defaultValue
	 * @return
	 */
	public static <T> T getLast(List<T> list, T defaultValue) {
		if (list instanceof LinkedList)
			return list.size() > 0 ? ((LinkedList<T>) list).getLast() : defaultValue;
		
		return get(list, size(list) - 1, defaultValue);
	}
	
	/**
	 * 将指定索引位的值合并到列表中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 * @param index
	 * @param value
	 */
	public static <T> void merge(List<T> list, int index, T value) {
		AssertUtils.assertNotNull(list, "Merged list must not be null");
		if (index < 0) throw new ListIndexOutOfBoundsException(index);
			
		if (index < list.size())
			list.set(index, value);
		else {
			int fillNullSize = index - list.size();
			while (fillNullSize > 0) {
				list.add(null);
				fillNullSize--;
			}
				
			list.add(value);
		}
	}
		
	/**
	 * 按默认的逗号分隔符将集合中各元素连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static <T> String join(Collection<T> collection) {
		return join(collection, ",");
	}
	
	/**
	 * 按指定的分隔符将集合中各元素连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param separator
	 * @return
	 */
	public static <T> String join(Collection<T> collection, String separator) {
		if (isEmpty(collection))
			return null;
		
		Iterator<T> iterator = collection.iterator();
		StringBuilder builder = new StringBuilder();
		
		if (StringUtils.isNotEmpty(separator)) {
			while (iterator.hasNext()) {
				builder.append(iterator.next());
				
				if (iterator.hasNext())
					builder.append(separator);
			}
		} else {
			while (iterator.hasNext()) {
				builder.append(iterator.next());
			}
		}
		
		return builder.toString();
	}
	
}

