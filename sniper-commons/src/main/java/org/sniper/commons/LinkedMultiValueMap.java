/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-8-9
 */

package org.sniper.commons;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;

/**
 * 链表结构的多值Map实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class LinkedMultiValueMap<K, V> implements MultiValueMap<K, V> {

	private static final long serialVersionUID = -2513021116442444423L;
	
	private final Map<K, List<V>> targetMap;
	
	public LinkedMultiValueMap() {
		this(null);
	}
	
	public LinkedMultiValueMap(Map<K, List<V>> map) {
		this.targetMap = MapUtils.newLinkedHashMap(map);
	}
	
	public LinkedMultiValueMap(int initialCapacity) {
		this.targetMap = MapUtils.newLinkedHashMap(initialCapacity);
	}
	
	@Override
	public int size(K key) {
		return CollectionUtils.size(this.targetMap.get(key));
	}

	@Override
	public boolean isEmpty(K key) {
		return CollectionUtils.isEmpty(this.targetMap.get(key));
	}

	@Override
	public boolean isNotEmpty(K key) {
		return !isEmpty(key);
	}

	@Override
	public V getFirst(K key) {
		return CollectionUtils.getFirst(this.targetMap.get(key));
	}

	@Override
	public V getLast(K key) {
		return CollectionUtils.getLast(this.targetMap.get(key));
	}

	@Override
	public void add(K key, V value) {
		List<V> values = this.targetMap.get(key);
		if (values == null) {
			values = CollectionUtils.newLinkedList(); 
			this.targetMap.put(key, values);
		}
		
		values.add(value);
	}
	
	@Override
	public void add(Map<K, V> values) {
		if (MapUtils.isNotEmpty(values)) {
			for (Entry<K, V> entry : values.entrySet()) {
				add(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public void set(K key, V value) {
		List<V> values = new LinkedList<V>();
		values.add(value);
		this.targetMap.put(key, values);
	}

	@Override
	public void set(Map<K, V> values) {
		if (MapUtils.isNotEmpty(values)) {
			for (Entry<K, V> entry : values.entrySet()) {
				set(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public Map<K, V> toSingleValueMap() {
		Map<K, V> singleValueMap = MapUtils.newLinkedHashMap(this.targetMap.size());
		for (Entry<K, List<V>> entry : this.targetMap.entrySet()) {
			singleValueMap.put(entry.getKey(), entry.getValue().get(0));
		}
		return singleValueMap;
	}
	
	@Override
	public int size() {
		return this.targetMap.size();
	}

	@Override
	public boolean isEmpty() {
		return this.targetMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.targetMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.targetMap.containsValue(value);
	}

	@Override
	public List<V> get(Object key) {
		return this.targetMap.get(key);
	}

	@Override
	public List<V> put(K key, List<V> value) {
		return this.targetMap.put(key, value);
	}

	@Override
	public List<V> remove(Object key) {
		return this.targetMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends List<V>> map) {
		this.targetMap.putAll(map);
	}

	@Override
	public void clear() {
		this.targetMap.clear();
	}

	@Override
	public Set<K> keySet() {
		return this.targetMap.keySet();
	}

	@Override
	public Collection<List<V>> values() {
		return this.targetMap.values();
	}

	@Override
	public Set<Entry<K, List<V>>> entrySet() {
		return this.targetMap.entrySet();
	}
	
	@Override
	public LinkedMultiValueMap<K, V> clone() {
		return new LinkedMultiValueMap<K, V>(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.targetMap.equals(obj);
	}

	@Override
	public int hashCode() {
		return this.targetMap.hashCode();
	}

	@Override
	public String toString() {
		return this.targetMap.toString();
	}

}
