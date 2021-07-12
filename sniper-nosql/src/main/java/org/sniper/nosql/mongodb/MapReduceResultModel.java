/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-9-16
 */

package org.sniper.nosql.mongodb;

/**
 * MongoDB MapReduce的结果集(results键)对象
 * @author  Daniele
 * @version 1.0
 */
public class MapReduceResultModel<K, V> {
	
	/** _id键值 */
	private K id;
	
	/** value键值 */
	private V value;

	public K getId() {
		return id;
	}

	public void setId(K id) {
		this.id = id;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
