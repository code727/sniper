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

import java.util.concurrent.TimeUnit;

import org.sniper.serialization.Serializer;

/**
 * Redis库配置对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisRepository {
	
	/** 库的命名空间 */
	private String namespace;
	
	/** 当前库索引 */
	private int dbIndex;
	
	/** 针对于当前库所有数据的过期时间 */
	private long expireTime;
	
	/** 过期时间单位，默认为秒 */
	private TimeUnit timeUnit = TimeUnit.SECONDS;
	
	/** 键序列化器 */
	private Serializer keySerializer;
	
	/** 值序列化器 */
	private Serializer valueSerializer;
	
	/** 哈希键序列化器 */
	private Serializer hashKeySerializer;
	
	/** 哈希值序列化器 */
	private Serializer hashValueSerializer; 

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
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
