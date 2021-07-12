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

package org.sniper.context;

import java.util.Map;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 基于Map类型的线程局部变量上下文对象
 * @author  Daniele
 * @version 1.0
 */
public class MapThreadLocalContext implements Context {
	
	private final ThreadLocal<Object> context = new ThreadLocal<Object>();
	
	/** 全局默认的属性名称 */
	private Object attributeName;
	
	public MapThreadLocalContext() {
		this(StringUtils.EMPTY);
	}
	
	public MapThreadLocalContext(Object attributeName) {
		setAttributeName(attributeName);
	}
	
	@Override
	public void setAttributeName(Object attributeName) {
		AssertUtils.assertNotNull(attributeName, "Global default context attribute name must not be null");
		this.attributeName = attributeName;
	}

	@Override
	public Object getAttributeName() {
		return attributeName;
	}

	@Override
	public <V> V getAttribute() {
		return getAttribute(attributeName);
	}

	/**
	 * 根据名称获取线程局部变量的属性值
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V getAttribute(K name) {
		if (context.get() == null)
			return null;
		
		Map<K, V> map = (Map<K, V>) context.get();
		 if (MapUtils.isNotEmpty(map))
			 return map.get(name);
		 
		 return null;
	}
	
	@Override
	public <V> void setAttribute(V value) {
		setAttribute(attributeName, value);
	}

	/**
	 * 设置线程局部变量的属性值
	 * @author Daniele 
	 * @param name
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> void setAttribute(K name, V value) {
		Map<K, V> map = (Map<K, V>) context.get();
		if (map == null)
			map = MapUtils.newConcurrentHashMap();
		
		map.put(name, value);
		context.set(map);
	}
	
	@Override
	public <V> V removeAttribute() {
		return removeAttribute(attributeName);
	}

	/**
	 * 根据名称删除对应的局部变量属性
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V removeAttribute(K name) {
		Map<K, V> map = (Map<K, V>) context.get();
		if (MapUtils.isNotEmpty(map))
			return map.remove(name);
		
		return null;
	}

	@Override
	public void clear() {
		context.remove();
	}
		
}
