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
 * Create Date : 2018-11-14
 */

package org.sniper.generator.keyspace;

import java.util.List;

/**
 * 基于键空间的参数化生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractKeyspaceParameterizeGenerator<K, P, V> extends AbstractKeyspaceGenerator<K, V>
		implements KeyspaceParameterizeGenerator<K, P, V> {

	protected AbstractKeyspaceParameterizeGenerator(K defaultKeyspace) {
		super(defaultKeyspace);
	}
		
	@Override
	public V generateByKey(K key) {
		return generate(key, null);
	}
	
	@Override
	public List<V> batchGenerateByKey(K key, int count) {
		return batchGenerate(key, null, count);
	}
	
	@Override
	public V generateByParameter(P parameter) {
		return generate(defaultKeyspace, parameter);
	}

	@Override
	public List<V> batchGenerateByParameter(P parameter, int count) {
		return batchGenerate(defaultKeyspace, parameter, count);
	}
	
	@Override
	public V generate(K key, P parameter) {
		checkKeyspace(key);
		return doGenerate(key, parameter);
	}

	@Override
	public List<V> batchGenerate(K key, P parameter, int count) {
		checkKeyspace(key);
		checkBatchCount(count);
		return doBatchGenerate(key, parameter, count);
	}

	@Override
	protected V doGenerateByKey(K key) {
		throw new NoSuchMethodError("No such method 'doGenerateByKey'");
	}

	@Override
	protected List<V> doBatchGenerateByKey(K key, int count) {
		throw new NoSuchMethodError("No such method 'doBatchGenerateByKey'");
	}
	
	/** 
	 * 根据键和参数执行生成操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param parameter
	 * @return 
	 */
	protected abstract V doGenerate(K key, P parameter);

	/** 
	 * 根据键和参数执行批量生成操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param parameter
	 * @param count
	 * @return 
	 */
	protected abstract List<V> doBatchGenerate(K key, P parameter, int count);

}
