/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-3-26
 */

package org.sniper.nosql.redis;

import org.sniper.serialization.Serializer;
import org.sniper.support.timer.ExpirableBean;

/**
 * Redis库配置对象
 * @author  Daniele
 * @version 1.0
 */
public class RedisRepository extends ExpirableBean {
		
	/** 当前库索引 */
	private int dbIndex;
		
	/** 键序列化器 */
	private Serializer keySerializer;
	
	/** 值序列化器 */
	private Serializer valueSerializer;
	
	/** 哈希键序列化器 */
	private Serializer hashKeySerializer;
	
	/** 哈希值序列化器 */
	private Serializer hashValueSerializer; 

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}
	
	public Serializer getKeySerializer() {
		return keySerializer;
	}

	public void setKeySerializer(Serializer keySerializer) {
		this.keySerializer = keySerializer;
	}

	public Serializer getValueSerializer() {
		return valueSerializer;
	}

	public void setValueSerializer(Serializer valueSerializer) {
		this.valueSerializer = valueSerializer;
	}

	public Serializer getHashKeySerializer() {
		return hashKeySerializer;
	}

	public void setHashKeySerializer(Serializer hashKeySerializer) {
		this.hashKeySerializer = hashKeySerializer;
	}

	public Serializer getHashValueSerializer() {
		return hashValueSerializer;
	}

	public void setHashValueSerializer(Serializer hashValueSerializer) {
		this.hashValueSerializer = hashValueSerializer;
	}

}
