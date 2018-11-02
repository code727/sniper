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

package org.sniper.generator.keyspace;

import java.util.List;

import org.sniper.commons.util.AssertUtils;
import org.sniper.generator.AbstractGenerator;

/**
 * 基于键空间的生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractKeyspaceGeneratorGenerator<K, V> extends AbstractGenerator<V>
		implements KeyspaceGenerator<K, V> {
		
	/** 全局默认的键空间 */
	protected final K defaultKeyspace;
	
	protected AbstractKeyspaceGeneratorGenerator(K defaultKeyspace) {
		checkKeyspace(defaultKeyspace);
		this.defaultKeyspace = defaultKeyspace;
	}

	@Override
	public K getDefaultKeyspace() {
		return defaultKeyspace;
	}

	@Override
	public V generate() {
		return generateByKey(this.defaultKeyspace);
	}
	
	@Override
	public V generateByKey(K key) {
		checkKeyspace(key);
		return doGenerateByKey(key);
	}
	
	@Override
	public List<V> batchGenerate(int count) {
		return batchGenerateByKey(this.defaultKeyspace, count);
	}
	
	@Override
	public List<V> batchGenerateByKey(K key, int count) {
		checkKeyspace(key);
		checkBatchCount(count);
		return doBatchGenerateByKey(key, count);
	}
	
	/**
	 * 检查键空间的合法性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keyspace
	 */
	protected void checkKeyspace(Object keyspace) {
		AssertUtils.assertNotNull(keyspace, "Keyspace must not be null");
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
