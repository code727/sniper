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
 * Create Date : 2018-11-1
 */

package org.sniper.generator.keyspace;

import java.util.List;

import org.sniper.lock.ParameterizeLock;
import org.sniper.support.counter.AtomicLongIntervalCounter;
import org.sniper.support.counter.IntervalCounter;

/**
 * 基于计数器的缓存序列生成器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CounterCacheSequenceGenerator extends AbstractCacheableGenerator<Object, IntervalCounter<Long>, Long> {
	
	/** 代理的键空间生成器接口 */
	protected final KeyspaceGenerator<Object, Long> keyspaceGenerator;
	
	public CounterCacheSequenceGenerator(KeyspaceGenerator<Object, Long> keyspaceGenerator) {
		this(null, keyspaceGenerator);
	}

	public CounterCacheSequenceGenerator(ParameterizeLock<Object> keyLock, KeyspaceGenerator<Object, Long> keyspaceGenerator) {
		super(keyLock, keyspaceGenerator.getDefaultKeyspace());
		this.keyspaceGenerator = keyspaceGenerator;
	}

	@Override
	protected Long doGenerateByKey(Object key) {
		IntervalCounter<Long> counter = cache.get(key);
		if (counter == null) {
			keyLock.lock(key);
			try {
				// 双重检查，防止多线程环境针对同一参数同时创建多个队列
				if ((counter = cache.get(key)) == null) {
					long start = keyspaceGenerator.generateByKey(key);
					counter = new AtomicLongIntervalCounter(start, getCacheStepSize());
					cache.put(key, counter);
					return start;
				}
			} finally {
				keyLock.unlock(key);
			}
		}
		
		keyLock.lock(key);
		try {
			Long value = counter.increment();
			if (value == null) {
				counter.setStart(counter.getMaximum() + 1);
				value = counter.increment();
			}
			
			return value;
		} finally {
			keyLock.unlock(key);
		}
	}

	@Override
	protected List<Long> doBatchGenerateByKey(Object key, int count) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
