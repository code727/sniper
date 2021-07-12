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
 * Create Date : 2018年11月2日
 */

package org.sniper.generator.sequence;

import org.sniper.commons.util.AssertUtils;

/**
 * 键空间趋势序列抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractKeyspaceTrendSequence<K, V> extends AbstractTrendSequence<V>
		implements KeyspaceTrendSequence<K, V> {
	
	protected final K defaultKeyspace;
	
	protected AbstractKeyspaceTrendSequence(K defaultKeyspace) {
		checkKeyspace(defaultKeyspace);
		this.defaultKeyspace = defaultKeyspace;
	}
	
	@Override
	public K getDefaultSpaceId() {
		return defaultKeyspace;
	}
	
	@Override
	public V update(int stepSize) {
		return updateByKey(this.defaultKeyspace, stepSize);
	}

	@Override
	public V updateByKey(K key) {
		return updateByKey(key, getStepSize());
	}

	@Override
	public V updateByKey(K key, int stepSize) {
		checkKeyspace(key);
		checkStepSize(stepSize);
		return doUpdateByKey(key, stepSize);
	}
	
	/**
	 * 检查键空间的合法性
	 * @author Daniele 
	 * @param keyspace
	 */
	protected void checkKeyspace(Object keyspace) {
		AssertUtils.assertNotNull(keyspace, "Keyspace must not be null");
	}
	
	/** 
	 * 根据指定的键和步长更新序列
	 * @author Daniele 
	 * @param key
	 * @param stepSize
	 * @return 
	 */
	protected abstract V doUpdateByKey(K key, int stepSize);

}
