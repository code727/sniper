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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.serialization.json.JsonSerializer;
import org.sniper.serialization.json.jackson.codehaus.CodehausJacksonSerializer;
import org.sniper.test.domain.User;

/**
 * Redis字符串命令单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisStringCommandsTest extends AbstractRedisTest {
	
	protected final JsonSerializer jsonSerializer = new CodehausJacksonSerializer();
	
	protected final User user = new User();
	
	@Override
	protected void before() {
		user.setId(9527L);
		user.setName("杜斌");
		user.setAge(35);
		user.setMarried(true);
		user.setCreateTime(new Date());
	}
	
	@Test
	public void testStringOperatin() {
		Object result;
		String name = "dubin";
		
		redisCommands.set(key, name);
		result = redisCommands.get(key);
		assertEquals(name, result);
		System.out.println(result);
		
		int age = 36;
		
		redisCommands.setIn("string", key, age);
		result = redisCommands.get("string", key, int.class);
		assertEquals(age, result);
		System.out.println(result);
	}
	
//	@Test
	public void testJsonOperatin() {
		User value = user;
		
		redisCommands.setIn("json", key, value);
		value = redisCommands.get("json", key, User.class);
		assertNotNull(value);
		System.out.println(jsonSerializer.serializeToString(value));
	}
	
//	@Test
	public void testHessianOperatin() {
		User value = user;
		
		redisCommands.setIn("hessian", key, value);
		value = redisCommands.getIn("hessian", key);
		assertNotNull(value);
		System.out.println(jsonSerializer.serializeToString(value));
		
		Object result;
		String name = "dubin";
		
		redisCommands.setIn("hessian", key, name);
		result = redisCommands.getIn("hessian",key);
		assertEquals(name, result);
		System.out.println(result);
		
		int age = 36;
		
		redisCommands.setIn("hessian", key, age);
		result = redisCommands.get("hessian", key, Integer.class);
		assertEquals(age, result);
		System.out.println(result);
	}
	
	@Test
	public void testSetNX() {
		redisCommands.setNX("lock_1", "lock", expireTime);
		redisCommands.setNX("lock_2", "lock", expireTime, TimeUnit.MILLISECONDS);
		
		long expireSeconds = redisCommands.ttl("lock_1");
		long expireMillis = redisCommands.pTtl("lock_1");
		
		assertTrue(expireSeconds <= expireTime);
		assertTrue(expireMillis <= TimeUnit.SECONDS.toMillis(expireTime));
		System.out.println(expireSeconds);
		System.out.println(expireMillis);
		
		expireSeconds = redisCommands.ttl("lock_2");
		expireMillis = redisCommands.pTtl("lock_2");
		
		assertTrue(expireSeconds <= expireTime);
		assertTrue(expireMillis <= TimeUnit.SECONDS.toMillis(expireTime));
		System.out.println(expireSeconds);
		System.out.println(expireMillis);
	}
			
//	@Test
	public void testEx() {
		redisCommands.setEx("test_setEx", expireTime, "ex");
		redisCommands.pSetEx("test_pSetEx", expireTime, "pex");
		
		long expireSeconds = redisCommands.ttl("test_setEx");
		long expireMillis = redisCommands.pTtl("test_setEx");
		
		assertTrue(expireSeconds <= expireTime);
		assertTrue(expireMillis <= TimeUnit.SECONDS.toMillis(expireTime));
		System.out.println(expireSeconds);
		System.out.println(expireMillis);
		
		expireSeconds = redisCommands.ttl("test_pSetEx");
		expireMillis = redisCommands.pTtl("test_pSetEx");
		
		assertTrue(expireSeconds <= expireTime);
		assertTrue(expireMillis <= TimeUnit.SECONDS.toMillis(expireTime));
		System.out.println(expireSeconds);
		System.out.println(expireMillis);
	}
	
//	@Test
	public void testAppend() {
		String value = "test";
		
		for (int i = 0; i < 2; i++) {
			long length = redisCommands.append(key, value, expireTime);
			assertTrue(length == (value.length() * (i + 1)));
			System.out.println(redisCommands.get(key));
		}
	}
	
//	@Test
	public void testSetAndGetRange() {
		String value = "test";
		
		redisCommands.setRange(key, 0, value, expireTime);
		assertEquals(value, redisCommands.getRange(key, 0, -1));
		
		redisCommands.setRange(key, 2, "ster", expireTime);
		assertEquals("tester", redisCommands.getRange(key, 0, -1));
		
		assertEquals(value, redisCommands.getRange(key, 0, 3));
	}
	
//	@Test
	public void testGetSet() {
		int value = 0;
		
		Integer oldValue = redisCommands.getSet(key, value, expireTime, Integer.class);
		assertNull(oldValue);
		
		oldValue = redisCommands.getSet(key, value + 1, expireTime, Integer.class);
		assertEquals(value, oldValue);
	}
	
//	@Test
	public void testMSetAndMGet() throws InterruptedException {
		Map<Object, Object> kValues = MapUtils.newHashMap();
		kValues.put("test_0", 0);
		kValues.put("test_1", 1);
		kValues.put("test_2", 2);
		
		redisCommands.mSet(kValues, expireTime);
		List<Object> values = redisCommands.mGet(kValues.keySet());
		assertTrue(values.size() == kValues.size());
		System.out.println(values);
		
		assertTrue(CollectionUtils.isNotEmpty(redisCommands.keysByPattern("test_*")));
		Thread.sleep(TimeUnit.SECONDS.toMillis(expireTime));
		assertTrue(CollectionUtils.isEmpty(redisCommands.keysByPattern("test_*")));
	}
	
//	@Test
	public void testMSetNX() {
		Map<Object, Object> kValues = MapUtils.newHashMap();
		kValues.put("a", 0);
		kValues.put("b", 1);
		
		redisCommands.mSetNX(kValues, expireTime);
		
		long expireSeconds = redisCommands.ttl("a");
		long expireMillis = redisCommands.pTtl("a");
		
		assertTrue(expireSeconds <= expireTime);
		assertTrue(expireMillis <= TimeUnit.SECONDS.toMillis(expireTime));
		System.out.println(expireSeconds);
		System.out.println(expireMillis);
		
		expireSeconds = redisCommands.ttl("b");
		expireMillis = redisCommands.pTtl("b");
		
		assertTrue(expireSeconds <= expireTime);
		assertTrue(expireMillis <= TimeUnit.SECONDS.toMillis(expireTime));
		System.out.println(expireSeconds);
		System.out.println(expireMillis);
	}
	
//	@Test
	public void testStrLen() {
		String value = StringUtils.unsignedUUID();
		redisCommands.set(key, value);
		assertTrue(value.length() == redisCommands.strLen(key));
	}
	
//	@Test
	public void testIncr() {
		Long value = redisCommands.incr(key);
		assertEquals(1L, value);
		
		value = redisCommands.incrBy(key, 5);
		assertEquals(6L, value);
	}
	
//	@Test
	public void testDecr() {
		Long value = redisCommands.decr(key);
		assertEquals(-1L, value);
		
		value = redisCommands.decrBy(key, 5);
		assertEquals(-6L, value);
	}

}
