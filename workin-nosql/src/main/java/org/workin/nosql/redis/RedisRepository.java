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

package org.workin.nosql.redis;

import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @description Redis库配置对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisRepository {
	
	/** 库名称 */
	private String name;
	
	/** 针对于当前库所有数据的过期时间 */
	private long expireTime;
	
	/** 过期时间单位 */
	private String timeUnit;
	
	/** 键序列化器 */
	private RedisSerializer<?> keySerializer;
	
	/** 值序列化器 */
	private RedisSerializer<?> valueSerializer;
	
	/** 哈希键序列化器 */
	private RedisSerializer<?> hashKeySerializer;
	
	/** 哈希值序列化器 */
	private RedisSerializer<?> hashValueSerializer; 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public RedisSerializer<?> getKeySerializer() {
		return keySerializer;
	}

	public void setKeySerializer(RedisSerializer<?> keySerializer) {
		this.keySerializer = keySerializer;
	}

	public RedisSerializer<?> getValueSerializer() {
		return valueSerializer;
	}

	public void setValueSerializer(RedisSerializer<?> valueSerializer) {
		this.valueSerializer = valueSerializer;
	}

	public RedisSerializer<?> getHashKeySerializer() {
		return hashKeySerializer;
	}

	public void setHashKeySerializer(RedisSerializer<?> hashKeySerializer) {
		this.hashKeySerializer = hashKeySerializer;
	}

	public RedisSerializer<?> getHashValueSerializer() {
		return hashValueSerializer;
	}

	public void setHashValueSerializer(RedisSerializer<?> hashValueSerializer) {
		this.hashValueSerializer = hashValueSerializer;
	}

}
