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
 * Create Date : 2016-8-1
 */

package org.workin.nosql.redis.dao.jedis;

import org.workin.nosql.redis.dao.RedisDaoSupport;

import redis.clients.jedis.JedisPool;

/**
 * @description JedisDao支持类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class JedisDaoSupport extends RedisDaoSupport {
	
	 private JedisPool jedisPool;
	 
	 public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.jedisPool == null)
			throw new IllegalArgumentException(
					"JedisPool object can not be null, please inject to spring container.");
	}

}
