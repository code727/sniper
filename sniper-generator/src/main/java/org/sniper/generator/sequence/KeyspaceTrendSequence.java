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

package org.sniper.generator.sequence;

import org.sniper.generator.keyspace.Keyspace;

/**
 * 键空间趋势序列接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface KeyspaceTrendSequence<K, V> extends TrendSequence<V>, Keyspace<K> {
	
	/**
	 * 根据指定的键更新序列
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public V updateByKey(K key);
	
	/**
	 * 根据指定的键和趋势步长更新序列
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param stepSize
	 * @return
	 */
	public V updateByKey(K key, int stepSize);

}
