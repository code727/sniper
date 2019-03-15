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

import java.util.Set;

import org.junit.Test;
import org.sniper.nosql.redis.model.xscan.IndexedScanResult;
import org.sniper.nosql.redis.option.ScanOption;

/**
 * Redis集合单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisSetCommandsTest extends AbstractRedisTest {
	
//	@Test
	public void testSAdd() {
		assertEquals(1, redisCommands.sAdd(key, "0").intValue());
		assertEquals(0, redisCommands.sAdd(key, "0").intValue());
		
		assertEquals(values.length, redisCommands.sAdd(key, values).intValue());
		assertEquals(0, redisCommands.sAdd(key, values).intValue());
	}
	
//	@Test
	public void testSCard() {
		assertEquals(0, redisCommands.sCard(key).intValue());
		
		redisCommands.sAdd(key, values);
		assertEquals(values.length, redisCommands.sCard(key).intValue());
	}
	
//	@Test
	public void testSDiff() {
		// null - null = null
		Set<Object> diff = redisCommands.sDiff(keys);
		assertNull(diff);
		
		redisCommands.sAdd(keys[1], values);
		diff = redisCommands.sDiff(keys);
		// null - k1 = null
		assertNull(diff);
		
		redisCommands.del(keys[1]);
		redisCommands.sAdd(keys[0], values);
		// k0 - null = k0
		diff = redisCommands.sDiff(keys);
		assertNotNull(diff);
		System.out.println(diff);
		
		// k0 == k1, k0 - k1 = null;
		redisCommands.sAdd(keys[1], values);
		diff = redisCommands.sDiff(keys);
		assertNull(diff);
		
		redisCommands.del(keys);
		redisCommands.sAdd(keys[0], new String[] { "0", "1", "2" });
		redisCommands.sAdd(keys[1], new String[] { "0"});
		
		// k0 = {0,1,2}, k1 = {0}, k0 - k1 = {1,2};
		diff = redisCommands.sDiff(keys);
		assertNotNull(diff);
		System.out.println(diff);
		
		// k0 = {0,1,2}, k1 = {0,1,2,3}, k0 - k1 = null;
		redisCommands.sAdd(keys[1], new String[] { "1", "2", "3"});
		diff = redisCommands.sDiff(keys);
		assertNull(diff);
		
		redisCommands.del(keys);
		redisCommands.sAdd(keys[0], new String[] { "0", "1", "2" });
		redisCommands.sAdd(keys[1], new String[] { "0", "a", "b"});
		// k0 = {0,1,2}, k1 = {0,a,b}, k0 - k1 = {1,2};
		diff = redisCommands.sDiff(keys);
		assertNotNull(diff);
		System.out.println(diff);
	}
	
//	@Test
	public void testSDiffStore() {
		String destKey = key;
		
		assertEquals(0, redisCommands.sDiffStore(destKey, keys).intValue());
		
		redisCommands.sAdd(keys[1], values);
		// null - k1 = null -> dest = null
		assertEquals(0, redisCommands.sDiffStore(destKey, keys).intValue());
		redisCommands.del(keys[1]);
		
		redisCommands.sAdd(keys[0], values);
		// k0 - null = k0 -> dest(dest != k0)
		assertEquals(values.length, redisCommands.sDiffStore(destKey, keys).intValue());
		// k0 - null = k0 -> dest(dest == k0)
		assertEquals(values.length, redisCommands.sDiffStore(keys[0], keys).intValue());
		// k0 - null = k0 -> dest(dest == k1)
		assertEquals(values.length, redisCommands.sDiffStore(keys[1], keys).intValue());
	}
	
//	@Test
	public void testSInter() {
		// null ∩ null = null
		Set<Object> inter = redisCommands.sInter(keys);
		assertNull(inter);
		
		redisCommands.sAdd(keys[1], values);
		inter = redisCommands.sInter(keys);
		// null ∩ k1 = null
		assertNull(inter);
		
		redisCommands.del(keys[1]);
		redisCommands.sAdd(keys[0], values);
		// k0 ∩ null = null
		inter = redisCommands.sInter(keys);
		assertNull(inter);
		
		// k0 == k1, k0 ∩ k1 = k0 or k1;
		redisCommands.sAdd(keys[1], values);
		inter = redisCommands.sInter(keys);
		assertNotNull(inter);
		System.out.println(inter);
		
		redisCommands.del(keys);
		redisCommands.sAdd(keys[0], new String[] { "0", "1", "2" });
		redisCommands.sAdd(keys[1], new String[] { "0"});
		
		// k0 = {0,1,2}, k1 = {0}, k0 ∩ k1 = {0};
		inter = redisCommands.sInter(keys);
		assertNotNull(inter);
		System.out.println(inter);
	}
	
//	@Test
	public void testSInterStore() {
		String destKey = key;
		
		assertEquals(0, redisCommands.sInterStore(destKey, keys).intValue());
		
		redisCommands.sAdd(keys[1], values);
		// null ∩ k1 = null -> dest = null
		assertEquals(0, redisCommands.sInterStore(destKey, keys).intValue());
		redisCommands.del(keys);
		
		redisCommands.sAdd(keys[0], values);
		// k0 ∩ null = null -> dest(dest != k0)
		assertEquals(0, redisCommands.sInterStore(destKey, keys).intValue());
		// k0 ∩ null = null -> dest(dest == k0)
		assertEquals(0, redisCommands.sInterStore(keys[0], keys).intValue());
		// k0 ∩ null = null -> dest(dest == k1)
		assertEquals(0, redisCommands.sDiffStore(keys[1], keys).intValue());
		
		redisCommands.del(keys);
		redisCommands.sAdd(keys[0], new String[] { "0", "1", "2" });
		redisCommands.sAdd(keys[1], new String[] { "0" });
		
		// k0{0,1,2} ∩ k1{0} = 0 -> dest
		assertEquals(1, redisCommands.sInterStore(destKey, keys).intValue());
		assertEquals(1, redisCommands.sInterStore(keys[0], keys).intValue());
		assertEquals(1, redisCommands.sInterStore(keys[1], keys).intValue());
		
		redisCommands.del(keys);
		redisCommands.sAdd(keys[0], new String[] { "0", "1", "2" });
		redisCommands.sAdd(keys[1], new String[] { "a", "b", "c" });
		assertEquals(0, redisCommands.sInterStore(destKey, keys).intValue());
		assertEquals(0, redisCommands.sInterStore(keys[0], keys).intValue());
		assertEquals(0, redisCommands.sInterStore(keys[1], keys).intValue());
	}
	
//	@Test
	public void testSUnion() {
		// null ∪ null = null
		Set<Object> union = redisCommands.sUnion(keys);
		assertNull(union);

		redisCommands.sAdd(keys[1], values);
		union = redisCommands.sUnion(keys);
		// null ∪ k1 = k1
		assertNotNull(union);
		System.out.println(union);

		redisCommands.del(keys[1]);
		redisCommands.sAdd(keys[0], values);
		// k0 ∪ null = k0
		union = redisCommands.sUnion(keys);
		assertNotNull(union);
		System.out.println(union);

		// k0 == k1, k0 ∪ k1 = k0 or k1;
		redisCommands.sAdd(keys[1], values);
		union = redisCommands.sUnion(keys);
		assertNotNull(union);
		System.out.println(union);

		redisCommands.del(keys);
		redisCommands.sAdd(keys[0], new String[] { "0", "1", "2" });
		redisCommands.sAdd(keys[1], new String[] { "0", "a", "b", "c" });

		// k0 = {0,1,2}, k1 = {0,a,b,c}, k0 ∪ k1 = {0,1,2,a,b,c};
		union = redisCommands.sUnion(keys);
		assertNotNull(union);
		System.out.println(union);
	}
	
//	@Test
	public void testSUnionStore() {
		String destKey = key;
		
		assertEquals(0, redisCommands.sUnionStore(destKey, keys).intValue());
		
		redisCommands.sAdd(keys[1], values);
		// null ∪  k1 = k1 -> dest = k1
		assertEquals(values.length, redisCommands.sUnionStore(destKey, keys).intValue());
		redisCommands.del(keys);
		
		redisCommands.sAdd(keys[0], values);
		// k0 ∪  null = k0 -> dest(dest != k0)
		assertEquals(values.length, redisCommands.sUnionStore(destKey, keys).intValue());
		// k0 ∪  null = k0 -> dest(dest == k0)
		assertEquals(values.length, redisCommands.sUnionStore(keys[0], keys).intValue());
		// k0 ∪ null = k0 -> dest(dest == k1)
		assertEquals(values.length, redisCommands.sUnionStore(keys[1], keys).intValue());
		
		redisCommands.del(keys);
		redisCommands.sAdd(keys[0], new String[] { "0", "1", "2" });
		redisCommands.sAdd(keys[1], new String[] { "0" });
		
		// k0{0,1,2} ∪ k1{0} = {0,1,2} -> dest
		assertEquals(3, redisCommands.sUnionStore(destKey, keys).intValue());
		assertEquals(3, redisCommands.sUnionStore(keys[0], keys).intValue());
		assertEquals(3, redisCommands.sUnionStore(keys[1], keys).intValue());
		
		redisCommands.del(keys);
		redisCommands.sAdd(keys[0], new String[] { "0", "1", "2" });
		redisCommands.sAdd(keys[1], new String[] { "a", "b", "c" });
		assertEquals(6, redisCommands.sUnionStore(destKey, keys).intValue());
		assertEquals(6, redisCommands.sUnionStore(keys[0], keys).intValue());
		assertEquals(6, redisCommands.sUnionStore(keys[1], keys).intValue());
	}
	
//	@Test
	public void testSIsMemberAndSMembers() {
		assertFalse(redisCommands.sIsMember(key, "a"));
		assertNull(redisCommands.sMembers(key));
		
		redisCommands.sAdd(key, values);
		assertFalse(redisCommands.sIsMember(key, "0"));
		assertTrue(redisCommands.sIsMember(key, "a"));
		Set<Object> members = redisCommands.sMembers(key);
		assertNotNull(members);
		System.out.println(members);
	}
	
//	@Test
	public void testSMove() {
		assertFalse(redisCommands.sMove(keys[0], keys[1], "a"));
		redisCommands.sAdd(keys[0], values);
		
		assertFalse(redisCommands.sMove(keys[0], keys[0], "0"));
		assertFalse(redisCommands.sMove(keys[0], keys[1], "0"));
		
		assertTrue(redisCommands.sMove(keys[0], keys[0], "a"));
		assertTrue(redisCommands.sMove(keys[0], keys[1], "a"));
		assertEquals(values.length - 1, redisCommands.sMembers(keys[0]).size());
		assertEquals(1, redisCommands.sMembers(keys[1]).size());
		
		assertFalse(redisCommands.sMove(keys[0], keys[1], "a"));
	}
	
//	@Test
	public void testSPop() {
		assertNull(redisCommands.sPop(key));
		
		redisCommands.sAdd(key, values);
		for (int i = 0; i < values.length; i++) {
			Object member = redisCommands.sPop(key);
			assertNotNull(member);
			System.out.println(member);
		}
		
		assertNull(redisCommands.sPop(key));
	}
	
//	@Test
	public void testSRandMember() {
		assertNull(redisCommands.sRandMember(key));
		
		redisCommands.sAdd(key, values);
		Object member = redisCommands.sRandMember(key);
		assertNotNull(member);
		System.out.println(member);
		
		// 由于sRandMember命令不是随机出列操作，因此执行此命令后，集合的元素个数不会发生变化
		assertEquals(values.length, redisCommands.sMembers(key).size());
	}
	
//	@Test
	public void testSRem() {
		assertEquals(0, redisCommands.sRem(key, values).intValue());
		assertEquals(0, redisCommands.sRem(key, "a").intValue());
		
		redisCommands.sAdd(key, values);
		assertEquals(values.length, redisCommands.sRem(key, values).intValue());
		
		redisCommands.sAdd(key, values);
		assertEquals(1, redisCommands.sRem(key, values[0]).intValue());
		assertEquals(2, redisCommands.sRem(key, new String[]{values[1], values[2]}).intValue());
		assertEquals(2, redisCommands.sRem(key, new String[]{values[3], values[4], "0", "1"}).intValue());
	}
	
	@Test
	public void testSScan() {
		IndexedScanResult<String> scanResult = redisCommands.sscan(key);
		assertNotNull(scanResult);
		assertTrue(scanResult.isEmpty());
		
		redisCommands.sAdd(key, names.values());
		
		scanResult = redisCommands.sscan(keys[0], scanResult.getCursorId());
		assertNotNull(scanResult);
		assertTrue(scanResult.isEmpty());
		
		System.out.println("Scanning......");
		scanResult = redisCommands.sscan(key);
		assertNotNull(scanResult);
		assertTrue(scanResult.isNotEmpty());
		
		System.out.println(scanResult);
		if (scanResult.completed())
			System.out.println("Scan completed!");
		
		ScanOption option = new ScanOption();
		option.setCount(1L);
		
		System.out.println("Scanning by option......");
		scanResult = redisCommands.sscan(key, scanResult.getCursorId(), option);
		assertNotNull(scanResult);
		assertTrue(scanResult.isNotEmpty());
		// 如果没有使用 MATCH选项， 那么命令返回的元素数量通常和 COUNT选项指定的一样， 或者比COUNT选项指定的数量稍多一些
		assertTrue(scanResult.size() >= option.getCount());
		System.out.println(scanResult);
		
		while (!scanResult.completed()) {
			System.out.println("Scan again......");
			scanResult = redisCommands.sscan(key, scanResult.getCursorId(), option);
			System.out.println(scanResult);
		}
		System.out.println("Scan completed!");
		
		option.setPattern("*T*");
		
		System.out.println("Scanning by option......");
		scanResult = redisCommands.sscan(key, scanResult.getCursorId(), option);
		assertNotNull(scanResult);
		assertTrue(scanResult.size() <= option.getCount());
		System.out.println(scanResult);
		
		while (!scanResult.completed()) {
			System.out.println("Scan again......");
			scanResult = redisCommands.sscan(key, scanResult.getCursorId(), option);
			System.out.println(scanResult);
		}
		System.out.println("Scan completed!");
	}

}
