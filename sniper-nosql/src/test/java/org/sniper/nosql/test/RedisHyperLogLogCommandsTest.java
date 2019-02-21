/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-2-21
 */

package org.sniper.nosql.test;

import org.junit.Test;

/**
 * Redis HyperLogLog命令行单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisHyperLogLogCommandsTest extends AbstractRedisTest {
	
	private String[] databases = new String[] {"Redis", "MongoDB", "MySQL"};
	
	private String[] nosqls = new String[] {"Redis","MongoDB","HBase"};
	
	private String[] rdbms = new String[] {"MySQL", "MSSQL", "PostgreSQL"};
	
//	@Test
	public void testPFAdd() {
		Long result = redisCommands.pfAdd(key, databases);
		assertEquals(1L, result);
		
		result = redisCommands.pfAdd(key, databases[0]);
		assertEquals(0L, result);
		
		databases = new String[] { databases[0], "HBase", "Oracle" };
		result = redisCommands.pfAdd(key, databases[0]);
		assertEquals(0L, result);
	}
	
//	@Test
	public void testPFCount() {
		Long count = redisCommands.pfCount(key);
		assertEquals(0L, count);
		
		for (int i = 0; i < 2; i++) {
			redisCommands.pfAdd(key, databases);
		}
		
		count = redisCommands.pfCount(key);
		assertEquals(databases.length, count.intValue());
		
		for (int i = 0; i < keys.length; i++) {
			redisCommands.pfAdd(keys[i], databases);
		}
		
		count = redisCommands.pfCount(keys);
		assertEquals(databases.length, count.intValue());
	}
	
	@Test
	public void testPFMerge() {
		redisCommands.pfAdd(keys[0], nosqls);
		redisCommands.pfAdd(keys[1], rdbms);
		
		redisCommands.pfMerge(key, new String[]{keys[0], keys[1]});
		Long count = redisCommands.pfCount(key);
		assertEquals(nosqls.length + rdbms.length, count.intValue());
	}

}
