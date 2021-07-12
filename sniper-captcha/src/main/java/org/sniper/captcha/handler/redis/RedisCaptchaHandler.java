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
 * Create Date : 2016-3-1
 */

package org.sniper.captcha.handler.redis;

import java.io.Serializable;

import org.sniper.captcha.handler.AbstractCaptchaHandler;
import org.sniper.nosql.redis.command.RedisCommands;

/**
 * Redis库验证码处理器实现类
 * @author  Daniele
 * @version 1.0
 */
public class RedisCaptchaHandler extends AbstractCaptchaHandler {
	
	/** ID前缀标识 */
	private String prefix;
	
	/** 存储验证码数据的库索引 */
	private String dbName;
		
	private RedisCommands redisCommands;
	
	public RedisCaptchaHandler() {
		setPrefix("captcha_");
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public RedisCommands getRedisCommands() {
		return redisCommands;
	}

	public void setRedisCommands(RedisCommands redisCommands) {
		this.redisCommands = redisCommands;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.redisCommands == null)
			throw new IllegalArgumentException("Property 'redisCommands' is required");
	}
	
	@Override
	protected void doCreate(Serializable id, String text) {
		this.redisCommands.setIn(dbName, getPrefix() + id, text);
	}

	@Override
	protected void doUpdate(Serializable id, String text) {
		this.doCreate(id, text);
	}

	@Override
	public String get(Serializable id) {
		return this.redisCommands.getIn(dbName, getPrefix() + id);
	}

	@Override
	public String detele(Serializable id) {
		String text = this.get(id);
		this.redisCommands.del(dbName, getPrefix() + id);
		return text;
	}

}
