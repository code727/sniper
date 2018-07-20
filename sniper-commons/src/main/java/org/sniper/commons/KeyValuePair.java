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
 * Create Date : 2017-3-31
 */

package org.sniper.commons;

import java.io.Serializable;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 键值对
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class KeyValuePair<K, V> implements Serializable {
	
	private static final long serialVersionUID = -6146608197344697759L;

	/** 键 */
	private K key;
	
	/** 值 */
	private V value;
			
	public KeyValuePair(K key) {
		this(key, null);
	}
	
	public KeyValuePair(KeyValuePair<K, V> keyValuePair) {
		this(keyValuePair != null ? keyValuePair.getKey() : null, keyValuePair.getValue());
	}
	
	public KeyValuePair(K key, V value) {
		checkKey(key);
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		checkKey(key);
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	/**
	 * 检查键的合法性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 */
	protected void checkKey(K key) {
		AssertUtils.assertTrue(ObjectUtils.isNotBlank(key), "Key must not be null or blank");
	}
	
	@Override
	public String toString() {
		return key + StringUtils.ASSIGNMENT + value;
	}
		
}
