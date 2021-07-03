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
 * Create Date : 2015-1-19
 */

package org.sniper.commons.constant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 常量抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractConstant<K, V> implements Constant<K, V> {
	
	private static final long serialVersionUID = -3130508094194649273L;

	/** 键 */
	protected final K key;
	
	/** 值 */
	protected final V value;
	
	protected AbstractConstant(K key, V value) {
		AssertUtils.assertNotNull(key, "Constant key must not be null");
		AssertUtils.assertNotNull(value, "Constant value must not be null");
		
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}
	
	/**
	 * 判断指定的键是否与当前常量对象匹配
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public boolean matches(K key) {
		return this.key.equals(key);
	}
	
	@Override
	public final boolean equals(Object obj) {
		return this == obj;
	}
	
	@Override
	public final int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public String toString() {
		return String.format("{\"key\":%s,\"value\":%s}",
				key instanceof CharSequence ? StringUtils.appendDoubleQuotes(key.toString()) : key,
				value instanceof CharSequence ? StringUtils.appendDoubleQuotes(value.toString()) : value);
	}
		
	/**
	 * 创建常量映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param constantType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static <K, V, C extends Constant<K, V>> Map<K, C> createMapping(Class<C> constantType) {
		List<Field> fields = ReflectionUtils.getDeclaredFields(constantType);
		int fieldSize = CollectionUtils.size(fields);
		Map<K, C> mappings = MapUtils.newHashMap(fieldSize);
		
		if (fieldSize > 0) {
			try {
				for (Field field : fields) {
					int modifiers = field.getModifiers();
					/* 将同时具备public static final三个修饰符的成员对象加入到常量映射集 */
					if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
						Constant<K, V> constant = (Constant<K, V>) field.get(constantType);
						mappings.put(constant.getKey(), (C) constant);
					}
				}
			} catch (Exception e) { e.printStackTrace();}
		}
		
		return mappings;
	}
			
}
