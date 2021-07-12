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
import java.util.Map;

import org.junit.Test;
import org.sniper.commons.util.MapUtils;
import org.sniper.nosql.redis.model.xscan.MappedScanResult;
import org.sniper.nosql.redis.option.ScanOption;

/**
 * Redis哈希命令单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class RedisHashCommandsTest extends AbstractRedisTest {
	
	protected final Map<String, Object> hashKeyValues = MapUtils.newHashMap();
	
	@Override
	public void init() {
		super.init();
		
		hashKeyValues.put("id", 1L);
		hashKeyValues.put("name", "dubin");
		hashKeyValues.put("age", 35);
	}
	
//	@Test
	public void testHSetAndHGet() {
		String hashKey = "name";
		String value = "dubin";
		
		assertTrue(redisCommands.hSet(key, hashKey, value));
		assertFalse(redisCommands.hSet(key, hashKey, value));
		
		assertEquals(value, redisCommands.hGet(key, hashKey));
	}
	
//	@Test
	public void testHMSetAndHGetAll() {
		redisCommands.hMSet(key, hashKeyValues);
		
		Map<String, Object> result = redisCommands.hGetAll(key);
		assertEquals(hashKeyValues.size(), result.size());
		System.out.println(result);
	}
	
//	@Test
	public void testHMSetAndHMGet() {
		redisCommands.hMSet(key, hashKeyValues);
		
		List<Object> values = redisCommands.hMGet(key, hashKeyValues.keySet());
		assertEquals(hashKeyValues.size(), values.size());
		System.out.println(values);
	}
	
//	@Test
	public void testHMSetAndHVals() {
		redisCommands.hMSet(key, hashKeyValues);
		
		List<Object> values = redisCommands.hVals(key);
		assertEquals(hashKeyValues.size(), values.size());
		System.out.println(values);
	}
	
//	@Test
	public void testHIncr() {
		int max = 10;
		for (int i = 0; i < max; i++) {
			assertEquals((long) (i + 1), redisCommands.hIncr(key, keys[1]));
		}
		
		Object hGet = redisCommands.hGet(key, keys[1]);
		System.out.println(hGet);
		
		long increment = 5L;
		for (int i = 0; i < max; i++) {
			assertEquals(increment * (i + 1), redisCommands.hIncrBy(key, keys[0], increment));
		}
		
		hGet = redisCommands.hGet(key, keys[0]);
		System.out.println(hGet);
	}
	
//	@Test
	public void testHDecr() {
		int max = 10;
		for (int i = 0; i < max; i++) {
			assertEquals((long)-(i + 1), redisCommands.hDecr(key, keys[1]));
		}
		
		Object hGet = redisCommands.hGet(key, keys[1]);
		System.out.println(hGet);
		
		long decrement = 5L;
		for (int i = 0; i < max; i++) {
			assertEquals(-(decrement * (i + 1)), redisCommands.hDecrBy(key, keys[0], decrement));
		}
		
		hGet = redisCommands.hGet(key, keys[0]);
		System.out.println(hGet);
	}
	
	@Test
	public void testHScan() {
		MappedScanResult<String, Object> scanResult = redisCommands.hScan(key);
		assertNotNull(scanResult);
		assertTrue(scanResult.isEmpty());
		
		redisCommands.hMSet(key, hashKeyValues);
		
		scanResult = redisCommands.hScan(keys[0], scanResult.getCursorId());
		assertNotNull(scanResult);
		assertTrue(scanResult.isEmpty());
		
		System.out.println("Scanning......");
		scanResult = redisCommands.hScan(key);
		assertNotNull(scanResult);
		assertTrue(scanResult.isNotEmpty());
		
		System.out.println(scanResult);
		if (scanResult.completed())
			System.out.println("Scan completed!");
		
		ScanOption option = new ScanOption();
		option.setCount(1L);
		
		System.out.println("Scanning by option......");
		scanResult = redisCommands.hScan(key, scanResult.getCursorId(), option);
		assertNotNull(scanResult);
		assertTrue(scanResult.isNotEmpty());
		assertTrue(scanResult.size() >= (option.getCount() / 2));
		System.out.println(scanResult);
		
		while (!scanResult.completed()) {
			System.out.println("Scan again......");
			scanResult = redisCommands.hScan(key, scanResult.getCursorId(), option);
			System.out.println(scanResult);
		}
		System.out.println("Scan completed!");
		
		option.setPattern("*a*");
		
		System.out.println("Scanning by option......");
		scanResult = redisCommands.hScan(key, scanResult.getCursorId(), option);
		assertNotNull(scanResult);
		assertTrue(scanResult.size() >= (option.getCount() / 2));
		System.out.println(scanResult);
		
		while (!scanResult.completed()) {
			System.out.println("Scan again......");
			scanResult = redisCommands.hScan(key, scanResult.getCursorId(), option);
			System.out.println(scanResult);
		}
		System.out.println("Scan completed!");
		
	}
	
}
