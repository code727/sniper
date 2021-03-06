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
import org.sniper.concurrent.locks.KeyspaceLock;
import org.sniper.concurrent.locks.jdk.DefaultKeyspaceLock;

/**
 * 可缓存的生成器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractCacheableGenerator<K, E, V> extends AbstractKeyspaceGenerator<K, V> {
	
	protected final KeyspaceLock<K> keyLock;
	
	/** 本地缓存 */
	protected final Map<K, E> cache;
	
	/** 本地缓存步长 */
	private int cacheStepSize;
	
	protected AbstractCacheableGenerator(KeyspaceLock<K> keyLock, K defaultKeyspace) {
		super(defaultKeyspace);
		this.cache = MapUtils.newConcurrentHashMap();
		this.keyLock = (keyLock != null ? keyLock : new DefaultKeyspaceLock<K>());
		this.cacheStepSize = 10000;
	}

	public int getCacheStepSize() {
		return cacheStepSize;
	}

	public void setCacheStepSize(int cacheStepSize) {
		AssertUtils.assertTrue(cacheStepSize > 0, String.format(
				"Property 'cacheStepSize' value '%d' must greater than 0", cacheStepSize));
		this.cacheStepSize = cacheStepSize;
	}
	
}
