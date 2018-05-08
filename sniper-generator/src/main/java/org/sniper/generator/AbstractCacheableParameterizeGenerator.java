/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-5-8
 */

package org.sniper.generator;

import java.util.Map;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;

/**
 * 可缓存的参数化生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 * @param <T>
 */
public abstract class AbstractCacheableParameterizeGenerator<K, V, T> extends AbstractParameterizeGenerator<T>
		implements CacheableParameterizeGenerator<K, V, Object, String> {
	
	/** 缓存对象 */
	protected final Map<K, V> cache;
	
	/** 缓存大小 */
	private int cacheSize = 10000;
	
	protected AbstractCacheableParameterizeGenerator() {
		this.cache = MapUtils.newConcurrentHashMap();
	}
		
	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		AssertUtils.assertTrue(cacheSize > 0, "Generator cache size must greater than 0");
		this.cacheSize = cacheSize;
	}
	
}
