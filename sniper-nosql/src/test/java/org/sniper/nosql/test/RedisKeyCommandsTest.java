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
 * Create Date : 2019-1-16
 */

package org.sniper.nosql.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.sniper.commons.util.MapUtils;
import org.sniper.nosql.redis.enums.DataType;
import org.sniper.nosql.redis.enums.Order;
import org.sniper.nosql.redis.model.xscan.IndexedScanResult;
import org.sniper.nosql.redis.option.Limit;
import org.sniper.nosql.redis.option.ScanOption;
import org.sniper.nosql.redis.option.SortOption;
import org.sniper.nosql.redis.option.SortOptional;

/**
 * Redis键命令单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class RedisKeyCommandsTest extends AbstractRedisTest {
	
//	@Test
	public void testType() {
		assertEquals(DataType.NONE, redisCommands.type(key));
		
		redisCommands.set(key, "a");
		assertEquals(DataType.STRING, redisCommands.type(key));
		
		redisCommands.lPush(keys[0], values);
		assertEquals(DataType.LIST, redisCommands.type(keys[0]));
		
		redisCommands.sAdd(keys[1], values);
		assertEquals(DataType.SET, redisCommands.type(keys[1]));
		
		redisCommands.zAdd(keys[2], 1.0, "a");
		assertEquals(DataType.ZSET, redisCommands.type(keys[2]));
		
		redisCommands.hSet(keys[3], keys[3], "a");
		assertEquals(DataType.HASH, redisCommands.type(keys[3]));
	}
	
//	@Test
	public void testKeys() {
		assertNull(redisCommands.keys());
		
		redisCommands.set(key, "a");
		assertNotNull(redisCommands.keys());
	}
	
//	@Test
	public void testRandomKey() {
		assertNull(redisCommands.randomKey());
		
		String key0 = "0";
		String key1 = "1";
		Map<String, Object> kValues = MapUtils.newHashMap();
		kValues.put(key0, "a");
		kValues.put(key1, "b");
		redisCommands.mSet(kValues);
		
		Object key = redisCommands.randomKey();
		assertTrue(kValues.containsKey(key));
		System.out.println(String.format("The kes is:%s,type is:%s", key, key.getClass()));
		
		key = redisCommands.randomKey(int.class);
		System.out.println(String.format("The kes is:%s,type is:%s", key, key.getClass()));
	}
	
//	@Test
	public void testDel() {
		Map<Integer, Object> keyValues = MapUtils.newHashMap();
		keyValues.put(0, "0");
		keyValues.put(1, "1");
		
		redisCommands.mSet(keyValues);
		assertEquals((long)keyValues.size(), redisCommands.del(keyValues.keySet()));
		
		redisCommands.mSet(keyValues);
		int[] keys = new int[] { 0, 1 };
		assertEquals((long)keyValues.size(), redisCommands.del(keys));
	}
	
//	@Test
	public void testSort() {
		assertNull(redisCommands.sort(key));
				
		Long count = redisCommands.lPush(key, numberValues);
		List<Object> list = redisCommands.sort(key);
		assertEquals(count.intValue(), list.size());
		System.out.println(list);
	}
	
//	@Test
	public void testSortByOptional1() {
		SortOptional optional = null;
		assertNull(redisCommands.sortByOptional(key, optional));
		
		Long count = redisCommands.lPush(key, numberValues);
		Limit limit = new Limit(0, count);
		optional = new SortOption(limit, Order.DESC);
		
		List<Object> list = redisCommands.sortByOptional(key, optional);
		assertEquals((int)(limit.getCount() - limit.getOffset()), list.size());
		System.out.println(list);
		
		optional.setLimit(new Limit(0, count + 1));
		
		list = redisCommands.sortByOptional(key, optional);
		assertEquals(count.intValue(), list.size());
		System.out.println(list);
		
		count = redisCommands.lPush(keys[0], values);
		
		list = redisCommands.sortByOptional(keys[0], optional);
		assertEquals(count.intValue(), list.size());
		System.out.println(list);
		
		optional.setOrder(Order.ASC);
		
		list = redisCommands.sortByOptional(keys[0], optional);
		assertEquals(count.intValue(), list.size());
		System.out.println(list);
	}
	
//	@Test
	public void testSortByOptional2() {		
		int[] ids = new int[names.size()];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = i + 1;
		}
		
		String key = "id";
		redisCommands.lPush(key, ids);
		redisCommands.mSet(names);
		redisCommands.mSet(ages);
		
		SortOptional optional = new SortOption();
		
		/* 按年龄大小升序排列后返回ID值列表 */
		optional.setBy("age_*");
		List<Object> list = redisCommands.sortByOptional(key, optional);
		assertEquals(names.size(), list.size());
		System.out.println(list);
		
		/* 按年龄大小降序排列后返回ID值列表 */
		optional.setOrder(Order.DESC);
		list = redisCommands.sortByOptional(key, optional);
		assertEquals(names.size(), list.size());
		System.out.println(list);
		
		/* 按年龄大小降序排列后返回ID值对应的姓名和年龄 */
		optional.setGets("name_*", "age_*");
		list = redisCommands.sortByOptional(key, optional);
		assertEquals(names.size() * optional.getGets().length, list.size());
		System.out.println(list);
		
		/* 按年龄大小升序排列后返回ID值对应的姓名和年龄 */
		optional.setOrder(Order.ASC);
		list = redisCommands.sortByOptional(key, optional);
		assertEquals(names.size() * optional.getGets().length, list.size());
		System.out.println(list);
		
		optional.setLimit(new Limit(1, names.size()));
		list = redisCommands.sortByOptional(key, optional);
		assertEquals((names.size() - 1) * optional.getGets().length, list.size());
		System.out.println(list);
	}
	
//	@Test
	public void testSortStore() {
		Long count = redisCommands.sortStore(key, keys[0]);
		assertEquals(0L, count);
		
		redisCommands.lPush(key, numberValues);
		count = redisCommands.sortStore(key, keys[0], 10);
		assertEquals(numberValues.length, count.intValue());
		System.out.println(redisCommands.lRangeAll(keys[0]));
		
		redisCommands.set(keys[1], "0");
		// 变更前数据类型
		DataType beforeDataType = redisCommands.type(keys[1]);
		
		// 覆盖keys[1]的原有值
		count = redisCommands.sortStore(key, keys[1], 10);
		assertEquals(numberValues.length, count.intValue());
		
		DataType afterDataType = redisCommands.type(keys[1]);
		System.out.println(String.format("Key '%s' change before data type is '%s',change after data type is '%s'",
				keys[1], beforeDataType.code(), afterDataType.code()));
		System.out.println(redisCommands.lRangeAll(keys[1]));
	}
	
//	@Test
	public void testSortStoreByOptional() {
		
		SortOptional optional = null;
		Long count = redisCommands.sortStoreByOptional(key, optional, keys[0]);
		assertEquals(0L, count);
		
		Long pushCount = redisCommands.lPush(key, numberValues);
		Limit limit = new Limit(0, pushCount);
		optional = new SortOption(limit, Order.DESC);
		
		count = redisCommands.sortStoreByOptional(key, optional, keys[0]);
		assertEquals(pushCount, count);
		System.out.println(redisCommands.lRangeAll(keys[0]));
		
		optional.setLimit(new Limit(0, pushCount + 1));
		
		count = redisCommands.sortStoreByOptional(key, optional, keys[0]);
		assertEquals(pushCount, count);
		System.out.println(redisCommands.lRangeAll(keys[0]));
		
		pushCount = redisCommands.lPush(key, values);
		optional.setLimit(new Limit(0, pushCount));
				
		count = redisCommands.sortStoreByOptional(key, optional, keys[0]);
		assertEquals(pushCount, count);
		System.out.println(redisCommands.lRangeAll(keys[0]));
	
		optional.setOrder(Order.ASC);
		optional.setLimit(new Limit(2, pushCount));
	
		count = redisCommands.sortStoreByOptional(key, optional, keys[0]);
		assertEquals(pushCount - 2, count);
		System.out.println(redisCommands.lRangeAll(keys[0]));
		
		count = redisCommands.sortStoreByOptional(key, optional, key, 10);
		assertEquals(pushCount - 2, count);
		System.out.println(redisCommands.lRangeAll(key));
	}
	
	@Test
	public void testScan() {
		redisCommands.mSet(names);
		redisCommands.mSet(ages);
		
		System.out.println("Scanning......");
		IndexedScanResult<Object> scanResult = redisCommands.scan();
		assertNotNull(scanResult);
		System.out.println(scanResult);
		if (scanResult.completed())
			System.out.println("Scan completed!");
		
		ScanOption option = new ScanOption();
		option.setCount(1L);
		
		System.out.println("Scanning by option......");
		scanResult = redisCommands.scan(scanResult.getCursorId(), option);
		assertNotNull(scanResult);
		// 如果没有使用 MATCH选项， 那么命令返回的元素数量通常和 COUNT选项指定的一样， 或者比COUNT选项指定的数量稍多一些
		assertTrue(scanResult.size() >= option.getCount());
		System.out.println(scanResult);
		
		while (!scanResult.completed()) {
			System.out.println("Scan again......");
			scanResult = redisCommands.scan(scanResult.getCursorId(), option);
			System.out.println(scanResult);
		}
		System.out.println("Scan completed!");
		
		option.setPattern("name_*");
		
		System.out.println("Scanning by option......");
		scanResult = redisCommands.scan(scanResult.getCursorId(), option);
		assertNotNull(scanResult);
		assertTrue(scanResult.size() <= option.getCount());
		System.out.println(scanResult);
		
		while (!scanResult.completed()) {
			System.out.println("Scan again......");
			scanResult = redisCommands.scan(scanResult.getCursorId(), option);
			System.out.println(scanResult);
		}
		System.out.println("Scan completed!");
	}
	
}
