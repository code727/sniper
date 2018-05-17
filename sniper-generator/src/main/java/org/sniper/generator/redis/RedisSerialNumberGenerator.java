/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-11-14
 */

package org.sniper.generator.redis;

import org.sniper.commons.util.AssertUtils;
import org.sniper.generator.AbstractParameterizeNumberGenerator;

/**
 * Redis流水号生成器实现类，生成结果满足几点要求：</P>
 * 1.在指定的参数化维度区间内流水号是唯一的；</P>
 * 2.在指定的参数化维度区间内流水号的连续性和递增性取决于内部代理的Redis生成器实现类；</P>
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisSerialNumberGenerator<P> extends AbstractParameterizeNumberGenerator<P, Long> {
	
	private AbstractRedisParameterizeGenerator<P> redisParameterizeGenerator;
	
	public RedisSerialNumberGenerator(AbstractRedisParameterizeGenerator<P> redisParameterizeGenerator) {
		AssertUtils.assertNotNull(redisParameterizeGenerator, "Redis parameterize generator must not be null");
		this.redisParameterizeGenerator = redisParameterizeGenerator;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Long generateByKey(String key) {
		return this.redisParameterizeGenerator.generate((P) key);
	}
			
}
