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
 * Create Date : 2019-1-15
 */

package org.sniper.nosql.test;

import java.util.List;

import org.junit.Test;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.nosql.redis.enums.ListPosition;

/**
 * Redis列表命令单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisListCommandsTest extends AbstractRedisTest {
	
	@Test
	public void testllInsert() {
		// key不存在时返回0
		assertEquals(0L, redisCommands.lInsert(key, ListPosition.BEFORE, "b", "a"));
		
		redisCommands.lPush(key, "a");
		// key存在但pivot不存在时返回-1
		assertEquals(-1L, redisCommands.lInsert(key, ListPosition.BEFORE, "b", "a"));
		
		// a的前面插入b，成功后返回列表总长度
		assertEquals(redisCommands.lInsert(key, ListPosition.BEFORE, "a", "b"), redisCommands.lLen(key));
		System.out.println(redisCommands.lRangeAll(key));
		
		// a的后面插入c，成功后返回列表总长度
		assertEquals(redisCommands.lInsert(key, ListPosition.AFTER, "a", "c"), redisCommands.lLen(key));
		System.out.println(redisCommands.lRangeAll(key));
	}
	
//	@Test
//	public void testllPushAndlPop() {
//		assertTrue(redisCommands.lPush(key, values) == values.length);
//		assertTrue(redisCommands.lPush(key, "f") == values.length + 1);
//		
//		Object value = redisCommands.lPop(key);
//		assertNotNull(value);
//		System.out.println(value);
//	}
	
//	@Test
	public void testllPushXAndlPop() {
		assertTrue(redisCommands.lPushX(key, "a") == 0);
		
		redisCommands.lPush(key, "a");
		assertTrue(redisCommands.lPushX(key, "b") == 2);
		
		Object value = redisCommands.lPop(key);
		assertNotNull(value);
		System.out.println(value);
	}
	
//	@Test
	public void testllPushAndlRange() {
		redisCommands.lPush(key, values);
		
		long begin = 0;
		long end = values.length;
		
		List<String> list = redisCommands.lRange(key, begin, end);
		assertTrue(CollectionUtils.isNotEmpty(list));
		System.out.println(list);
		
		begin = 1;
		end = 2;
		list = redisCommands.lRange(key, begin, end);
		assertTrue(CollectionUtils.isNotEmpty(list));
		System.out.println(list);
		
		begin = 0;
		end = values.length + 1;
		list = redisCommands.lRange(key, begin, end);
		assertTrue(CollectionUtils.isNotEmpty(list));
		System.out.println(list);
		
		begin = -1;
		end = values.length;
		list = redisCommands.lRange(key, begin, end);
		assertTrue(CollectionUtils.isNotEmpty(list));
		System.out.println(list);
		
		begin = 0;
		end = -1;
		list = redisCommands.lRange(key, begin, end);
		assertTrue(CollectionUtils.isNotEmpty(list));
		System.out.println(list);
	}
	
//	@Test
	public void testllPushAndlRangeAll() {
		redisCommands.lPush(key, values);
		
		List<String> list = redisCommands.lRangeAll(key);
		assertEquals(values.length, list.size());
		System.out.println(list);
	}
	
//	@Test
	public void testllPushAndlRem() {
		redisCommands.lPush(key, new String[] { "a", "a", "a", "a", "a" });
		
		assertEquals(0L, redisCommands.lRem(key, 100, "b"));
		assertEquals(1L, redisCommands.lRem(key, 1, "a"));
		assertTrue(redisCommands.lRem(key, 100, "a") < 100);
	}
	
//	@Test
	public void testllPushAndlRemAll() {
		String[] values = new String[] { "a", "a", "a", "a", "a" };
		redisCommands.lPush(key, values);
		
		assertEquals(0L, redisCommands.lRemAll(key, "b"));
		assertEquals(values.length, redisCommands.lRemAll(key, "a").intValue());
	}
	
//	@Test
	public void testllPushAndlTrim() {
		redisCommands.lPush(key, values);
		
		int begin = 1;
		int end = values.length - 2;
		
		redisCommands.lTrim(key, begin, end);
		List<Object> list = redisCommands.lRangeAll(key);
		assertEquals((begin - 0) + (values.length - end), list.size());
		System.out.println(list);
	}
	
//	@Test
	public void testRPushAndRPop() {
		assertEquals(1, redisCommands.rPush(key, "a").intValue());
		Object value = redisCommands.rPop(key);
		assertNotNull(value);
		System.out.println(value);
		
		assertEquals(values.length, redisCommands.rPush(key, values).intValue());
		value = redisCommands.rPop(key);
		assertNotNull(value);
		System.out.println(value);
	}
	
//	@Test
	public void testRPushXAndRPop() {
		assertTrue(redisCommands.rPushX(key, "a") == 0);
		
		redisCommands.rPush(key, "a");
		assertTrue(redisCommands.rPushX(key, "b") == 2);
		
		Object value = redisCommands.rPop(key);
		assertNotNull(value);
		System.out.println(value);
	}
	
//	@Test
	public void testRPopLPush() {
		Object value = redisCommands.rPopLPush(key, key);
		assertNull(value);
		
		String destKey = key;
		value = redisCommands.rPopLPush(key, destKey);
		assertNull(value);
		
		redisCommands.lPush(key, values);
		System.out.println("Before RPopLPush:" + redisCommands.lRangeAll(key));
		
		value = redisCommands.rPopLPush(key, destKey);
		System.out.println(String.format("After RPopLPush src key '%s' list:%s", key, redisCommands.lRangeAll(key)));
		System.out.println(String.format("After RPopLPush dest key '%s' list:%s", destKey, redisCommands.lRangeAll(destKey)));
		
		destKey = "new_" + key;
		value = redisCommands.rPopLPush(key, destKey);
		assertNotNull(value);
		System.out.println(String.format("After RPopLPush src key '%s' list:%s", key, redisCommands.lRangeAll(key)));
		System.out.println(String.format("After RPopLPush dest key '%s' list:%s", destKey, redisCommands.lRangeAll(destKey)));
	}

}
