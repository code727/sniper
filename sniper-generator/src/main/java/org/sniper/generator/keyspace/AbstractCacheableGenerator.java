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
 * Create Date : 2018-10-30
 */

package org.sniper.generator.keyspace;

import java.util.Map;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.lock.ParameterizeLock;
import org.sniper.lock.jdk.JdkParameterizeLock;

/**
 * 可缓存的生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractCacheableGenerator<K, E, V> extends AbstractKeyspaceGeneratorGenerator<K, V> {
	
	protected final ParameterizeLock<K> keyLock;
	
	/** 本地缓存 */
	protected final Map<K, E> cache;
	
	/** 本地缓存步长 */
	private int cacheStepSize;
	
	protected AbstractCacheableGenerator(K defaultKeyspace, ParameterizeLock<K> keyLock) {
		super(defaultKeyspace);
		this.cache = MapUtils.newConcurrentHashMap();
		this.keyLock = (keyLock != null ? keyLock : new JdkParameterizeLock<K>());
		this.cacheStepSize = 10000;
	}

	public int getCacheStepSize() {
		return cacheStepSize;
	}

	public void setCacheStepSize(int cacheStepSize) {
		AssertUtils.assertTrue(cacheStepSize > 0, String.format(
				"%s property 'cacheStepSize' must greater than 0", this.getClass().getName()));
		this.cacheStepSize = cacheStepSize;
	}
	
}
