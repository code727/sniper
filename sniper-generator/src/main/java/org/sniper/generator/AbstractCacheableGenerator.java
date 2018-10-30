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
 * Create Date : 2018-10-26
 */

package org.sniper.generator;

import java.util.List;

import org.sniper.commons.util.AssertUtils;

/**
 * 可缓存的生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractCacheableGenerator<K, V> extends AbstractGenerator<V>
		implements CacheableGenerator<K, V> {
		
	private static final String CACHE_KEY_ERROR_MESSAGE = "%s cache key must not be null";
	
	/** 默认的全局键 */
	protected K globalKey;

	public K getGlobalKey() {
		return globalKey;
	}

	public void setGlobalKey(K globalKey) {
		this.globalKey = globalKey;
	}
	
	@Override
	public V generate() {
		return generateByKey(this.globalKey);
	}
	
	@Override
	public V generateByKey(K key) {
		checkCacheKey(key);
		return doGenerateByKey(key);
	}
	
	@Override
	public List<V> batchGenerate(int count) {
		return batchGenerateByKey(this.globalKey, count);
	}
	
	@Override
	public List<V> batchGenerateByKey(K key, int count) {
		checkCacheKey(key);
		checkBatchCount(count);
		return doBatchGenerateByKey(key, count);
	}
	
	protected void checkCacheKey(Object cacheKey) {
		AssertUtils.assertNotNull(cacheKey, String.format(CACHE_KEY_ERROR_MESSAGE, this.getClass().getName()));
	}
	
	/** 
	 * 根据指定的键执行生成结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return 
	 */
	protected abstract V doGenerateByKey(K key);
	
	/**
	 * 根据指定的键执行批量生成结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param count
	 * @return
	 */
	protected abstract List<V> doBatchGenerateByKey(K key, int count);

}
