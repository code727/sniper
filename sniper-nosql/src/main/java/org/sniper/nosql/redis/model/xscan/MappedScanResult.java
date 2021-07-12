/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-3-13
 */

package org.sniper.nosql.redis.model.xscan;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 已映射的扫描结果
 * @author  Daniele
 * @version 1.0
 */
public interface MappedScanResult<T, V> extends ScannableResult<Entry<T, V>> {
	
	/**
	 * 获取指定键对应的值
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public V get(Object key);
	
	/**
	 * 获取已映射的键集合
	 * @author Daniele 
	 * @return
	 */
	public Set<T> keySet();
	
	/**
	 * 获取已映射的值集合
	 * @author Daniele 
	 * @return
	 */
	public Collection<V> values();
	
	/**
	 * 判断指定的键是否存在于结果中
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public boolean containsKey(Object key);
	
	/**
	 * 判断指定的值是否存在于结果中
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public boolean containsValue(Object value);

}
