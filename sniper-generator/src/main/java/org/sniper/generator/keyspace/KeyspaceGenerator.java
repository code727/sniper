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

import org.sniper.generator.Generator;
import org.sniper.support.Namespace;

/**
 * 基于键空间的生成器接口
 * @author  Daniele
 * @version 1.0
 */
public interface KeyspaceGenerator<K, V> extends Generator<V>, Namespace<K> {
	
	/**
	 * 根据指定的键生成结果
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public V generateByKey(K key);
	
	/**
	 * 根据指定的键批量生成结果
	 * @author Daniele 
	 * @param key
	 * @param count
	 * @return
	 */
	public List<V> batchGenerateByKey(K key, int count);

}
