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
 * Create Date : 2017-12-29
 */

package org.sniper.nosql.test;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.sniper.commons.util.MapUtils;
import org.sniper.nosql.redis.model.xscan.MappedScanResult;
import org.sniper.nosql.redis.model.zset.ZSetTuple;
import org.sniper.nosql.redis.option.Limit;
import org.sniper.nosql.redis.option.ScanOption;
import org.sniper.nosql.redis.option.ZStoreOption;

/**
 * Redis有序集合单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisSortedSetCommandsTest extends AbstractRedisTest {
	
	protected final Map<Object, Double> scoreMembers = MapUtils.newLinkedHashMap();
	
	protected long begin = 0;
	protected long end = 9;
	
	protected double minScore = -0.9;
	protected double maxScore = 0.9;
	
	@Override
	public void init() {
		super.init();
		
		scoreMembers.put(values[0], 0.1);
		scoreMembers.put(values[1], 0.2);
		scoreMembers.put(values[2], 0.3);
		scoreMembers.put(values[3], 0.3);
	}
	
//	@Test
	public void testZAdd() {
		scoreMembers.put(null, 0.4);
		scoreMembers.put(values[4], null);
		
		// 2表示为在当前方法内临时添加的两个空键值
		assertEquals(scoreMembers.size() - 2, redisCommands.zAdd(key, scoreMembers).intValue());
	}
	
//	@Test
	public void testZCard() {
		assertEquals(0, redisCommands.zCard(key).intValue());
		
		Long count = redisCommands.zAdd(key, scoreMembers);
		assertEquals(count, redisCommands.zCard(key));
		
		redisCommands.del(key);
		scoreMembers.put(null, 0.4);
		scoreMembers.put(values[4], null);
		
		redisCommands.zAdd(key, scoreMembers);
		// 由于添加的两个空键值是无效的，因此实际的成员个数和上一次插入的个数是一样的
		assertEquals(count, redisCommands.zCard(key));
	}
	
//	@Test
	public void testZCount() {
		assertEquals(0, redisCommands.zCount(key, 0.1, 0.9).intValue());
		
		Long count = redisCommands.zAdd(key, scoreMembers);
		assertEquals(count, redisCommands.zCount(key, 0.1, 0.9));
	}
	
//	@Test
	public void testZRange() {
		assertNull(redisCommands.zRange(key, begin, end));
		
		scoreMembers.put("f", 0.0);
		Long count = redisCommands.zAdd(key, scoreMembers);
		
		Set<Object> set = redisCommands.zRange(key, begin, end);
		assertEquals(count.intValue(), set.size());
		System.out.println(set);
		
		set = redisCommands.zRange(key, 0, -1);
		assertEquals(count.intValue(), set.size());
		System.out.println(set);
	}
	
//	@Test
	public void testZRangeAll() {
		assertNull(redisCommands.zRangeAll(key));
		
		Long count = redisCommands.zAdd(key, scoreMembers);
		Set<Object> set = redisCommands.zRangeAll(key);
		assertEquals(count.intValue(), set.size());
		System.out.println(set);
	}
	
//	@Test
	public void testZRangeByScore() {
		assertNull(redisCommands.zRangeByScore(key, minScore, maxScore));
		
		Long count = redisCommands.zAdd(key, scoreMembers);
		Set<Object> set = redisCommands.zRangeByScore(key, minScore, maxScore);
		assertEquals(count.intValue(), set.size());
		System.out.println(set);
		
		set = redisCommands.zRangeByScore(key, 0.1, 0.3);
		assertEquals(count.intValue(), set.size());
		System.out.println(set);
		
		set = redisCommands.zRangeByScore(key, 0.1, 0.1);
		assertEquals(1, set.size());
		System.out.println(set);
		
		set = redisCommands.zRangeByScore(key, 0.3, 0.3);
		assertEquals(2, set.size());
		System.out.println(set);
		
		assertNull(redisCommands.zRangeByScore(key, -0.1, 0.0));
		assertNull(redisCommands.zRangeByScore(key, 0.4, 0.9));
		
		Limit limit = new Limit(-1, 0);
		
		set = redisCommands.zRangeByScore(key, minScore, maxScore, limit);
		assertNull(set);
		
		limit.setOffset(0);
		
		set = redisCommands.zRangeByScore(key, minScore, maxScore, limit);
		assertNull(set);
		
		limit.setOffset(1);
		
		set = redisCommands.zRangeByScore(key, minScore, maxScore, limit);
		assertNull(set);
		
		limit.setOffset(0);
		limit.setCount(1);
		
		set = redisCommands.zRangeByScore(key, minScore, maxScore, limit);
		assertEquals(1, set.size());
		
		limit.setOffset(1);
		
		set = redisCommands.zRangeByScore(key, minScore, maxScore, limit);
		assertEquals(1, set.size());
		
		limit.setOffset(0);
		limit.setCount(scoreMembers.size());
		
		set = redisCommands.zRangeByScore(key, minScore, maxScore, limit);
		assertEquals(scoreMembers.size(), set.size());
		
		limit.setOffset(1);
		limit.setCount(scoreMembers.size());
		
		set = redisCommands.zRangeByScore(key, minScore, maxScore, limit);
		assertEquals(scoreMembers.size() - 1, set.size());
		
		limit.setOffset(0);
		limit.setCount(scoreMembers.size() + 1);
		
		set = redisCommands.zRangeByScore(key, minScore, maxScore, limit);
		assertEquals(scoreMembers.size(), set.size());
	}
	
//	@Test
	public void testZRank() {
		assertNull(redisCommands.zRank(key, values[0]));
		
		scoreMembers.put("dubin", 0d);
		scoreMembers.put("daniele", 95d);
		redisCommands.zAdd(key, scoreMembers);
		
		Long index = redisCommands.zRank(key, values[0]);
		assertEquals(1L, index);
		
		index = redisCommands.zRank(key, "dubin");
		assertEquals(0L, index);
		
		index = redisCommands.zRank(key, "daniele");
		assertEquals((long) scoreMembers.size() - 1, index);
	}
	
//	@Test
	public void testZRem() {
		assertEquals(0L, redisCommands.zRem(key, values[0]));
		
		redisCommands.zAdd(key, scoreMembers);
		assertEquals(0L, redisCommands.zRem(key, "0"));
		assertEquals(1L, redisCommands.zRem(key, values[0]));
		assertEquals((long) scoreMembers.size() - 1, redisCommands.zRem(key, values));
		assertEquals(0L, redisCommands.zRem(key, values));
	}
	
//	@Test
	public void testZRemAll() {
		assertEquals(0L, redisCommands.zRemAll(key));
		
		redisCommands.zAdd(key, scoreMembers);
		assertEquals((long) scoreMembers.size(), redisCommands.zRemAll(key));
	}
	
//	@Test
	public void testZRemRangeByRank() {
		assertEquals(0L, redisCommands.zRemRangeByRank(key, -1, 0));
		assertEquals(0L, redisCommands.zRemRangeByRank(key, 0, scoreMembers.size()));
		
		redisCommands.zAdd(key, scoreMembers);
		assertEquals((long) scoreMembers.size(), redisCommands.zRemRangeByRank(key, 0, -1));
		
		redisCommands.zAdd(key, scoreMembers);
		
		long end = scoreMembers.size() - 2;
		assertEquals(end + 1, redisCommands.zRemRangeByRank(key, 0, end));
		
		redisCommands.zAdd(key, scoreMembers);
		end = scoreMembers.size();
		assertEquals(end, redisCommands.zRemRangeByRank(key, 0, end));
		assertEquals(0L, redisCommands.zRemRangeByRank(key, 0, end));
	}
	
//	@Test
	public void testZRemRangeByScore() {
		assertEquals(0L, redisCommands.zRemRangeByScore(key, minScore, maxScore));
		
		redisCommands.zAdd(key, scoreMembers);
		assertEquals((long) scoreMembers.size(), redisCommands.zRemRangeByScore(key, minScore, maxScore));
		
		redisCommands.zAdd(key, scoreMembers);
		assertEquals(0L, redisCommands.zRemRangeByScore(key, minScore, 0.0));
		assertEquals(0L, redisCommands.zRemRangeByScore(key, 0.1, 0.0));
		assertEquals(1L, redisCommands.zRemRangeByScore(key, 0.0, 0.1));
		assertEquals(1L, redisCommands.zRemRangeByScore(key, 0.2, 0.2));
		assertEquals(2L, redisCommands.zRemRangeByScore(key, 0.3, 0.3));
		
		redisCommands.zAdd(key, scoreMembers);
		assertEquals((long)scoreMembers.size(), redisCommands.zRemRangeByScore(key, 0.1, 0.3));
	}
	
//	@Test
	public void testZRevRange() {
		assertNull(redisCommands.zRevRange(key, begin, end));
		Long count = redisCommands.zAdd(key, scoreMembers);
		
		Set<Object> set = redisCommands.zRevRange(key, begin, end);
		assertEquals(count.intValue(), set.size());
		System.out.println(set);
		
		set = redisCommands.zRevRange(key, 0, -1);
		assertEquals(count.intValue(), set.size());
		System.out.println(set);
	}
	
//	@Test
	public void testZRevRangeAll() {
		assertNull(redisCommands.zRevRangeAll(key));
		
		Long count = redisCommands.zAdd(key, scoreMembers);
		Set<Object> set = redisCommands.zRevRangeAll(key);
		assertEquals(count.intValue(), set.size());
		System.out.println(set);
	}
	
//	@Test
	public void testZRevRangeByScore() {
		assertNull(redisCommands.zRevRangeByScore(key, minScore, maxScore));
		
		Long count = redisCommands.zAdd(key, scoreMembers);
		Set<Object> set = redisCommands.zRevRangeByScore(key, minScore, maxScore);
		assertEquals(count.intValue(), set.size());
		System.out.println(set);
		
		set = redisCommands.zRevRangeByScore(key, 0.2, 0.3);
		assertNotNull(set);
		System.out.println(set);
	}
	
//	@Test
	public void testZRevRank() {
		assertNull(redisCommands.zRevRank(key, values[0]));
		
		redisCommands.zAdd(key, scoreMembers);
		
		Long index = redisCommands.zRevRank(key, "a");
		assertEquals((long) scoreMembers.size() - 1, index);
		
		index = redisCommands.zRevRank(key, "d");
		assertEquals(0L, index);
	}
	
//	@Test
	public void testZScore() {
		assertNull(redisCommands.zScore(key, values[0]));
		
		redisCommands.zAdd(key, scoreMembers);
		
		Double score = redisCommands.zScore(key, "0");
		assertNull(score);
		
		score = redisCommands.zScore(key, values[0]);
		assertNotNull(score);
		System.out.println(score);
	}
	
//	@Test
	public void testZUnionStore() {
		assertEquals(0L, redisCommands.zUnionStore(keys[0], key));
		
		redisCommands.zAdd(key, scoreMembers);
		assertEquals(0L, redisCommands.zUnionStore(key, keys[0]));
		
		redisCommands.zAdd(key, scoreMembers);
		assertEquals((long) scoreMembers.size(), redisCommands.zUnionStore(key, key));
		assertEquals((long) scoreMembers.size(), redisCommands.zUnionStore(keys[0], key));
		
		redisCommands.zAdd(keys[0], 0.4, "f");
		// 元素f将被覆盖掉
		assertEquals((long) scoreMembers.size(), redisCommands.zUnionStore(keys[0], key));
		System.out.println(redisCommands.zRangeAll(keys[0]));
		
		redisCommands.flushAll();
		
		redisCommands.zAdd(keys[0], 0.1, "a");
		redisCommands.zAdd(keys[1], 0.2, "b");
		redisCommands.zAdd(key, 0.3, "c");
		
		String[] keys = new String[] {this.keys[0], this.keys[1]};
		// 元素c将被覆盖掉
		assertEquals((long) keys.length, redisCommands.zUnionStore(key, keys, 10));
		assertEquals((long) keys.length, redisCommands.zUnionStore(keys[0], keys));
		
		System.out.println(redisCommands.zRangeAll(key));
		System.out.println(redisCommands.zRangeAll(keys[0]));
		
		assertEquals((long) keys.length, redisCommands.zUnionStore(key, keys, ZStoreOption.build(keys)));
		System.out.println(redisCommands.zRangeAll(key));
	}
	
//	@Test
	public void testZInterStore() {
		assertEquals(0L, redisCommands.zInterStore(keys[0], key));
		
		redisCommands.zAdd(key, scoreMembers);
		assertEquals(0L, redisCommands.zInterStore(key, keys[0]));
		
		redisCommands.zAdd(key, scoreMembers);
		assertEquals((long) scoreMembers.size(), redisCommands.zInterStore(key, key));
		assertEquals((long) scoreMembers.size(), redisCommands.zInterStore(keys[0], key));
		
		redisCommands.zAdd(keys[0], 0.4, "f");
		// 元素f将被覆盖掉
		assertEquals((long) scoreMembers.size(), redisCommands.zInterStore(keys[0], key));
		System.out.println(redisCommands.zRangeAll(keys[0]));
		
		redisCommands.flushAll();
		
		Map<String, Double> scoreMembers0 = MapUtils.newHashMap();
		scoreMembers0.put("a", 0.1);
		scoreMembers0.put("b", 0.2);
		scoreMembers0.put("c", 0.3);
		
		Map<String, Double> scoreMembers1 = MapUtils.newHashMap();
		scoreMembers1.put("a", 0.4);
		scoreMembers1.put("b", 0.5);
		scoreMembers1.put("d", 0.6);
		
		redisCommands.zAdd(keys[0], scoreMembers0);
		redisCommands.zAdd(keys[1], scoreMembers1);
		redisCommands.zAdd(key, 0.3, "c");
		
		String[] keys = new String[] {this.keys[0], this.keys[1]};
		// 元素c将被覆盖掉
		assertEquals((long) keys.length, redisCommands.zInterStore(key, keys, 10));
		assertEquals((long) keys.length, redisCommands.zInterStore(keys[0], keys));
		
		System.out.println(redisCommands.zRangeAll(key));
		System.out.println(redisCommands.zRangeAll(keys[0]));
		
		assertEquals((long) keys.length, redisCommands.zInterStore(key, keys, ZStoreOption.build(keys)));
		System.out.println(redisCommands.zRangeAll(key));
	}
	
//	@Test
	public void testZIncrBy() {
		double increment = 1;
		int max = 10;
		for (int i = 0; i < max; i++) {
			assertEquals(increment * (i + 1), redisCommands.zIncrBy(key, increment, values[0]));
		}
		
		assertEquals(increment * max, redisCommands.zScore(key, values[0]));
	}
	
//	@Test
	public void testZRangeByScoreWithScores() {
		assertNull(redisCommands.zRangeByScoreWithScores(key, minScore, maxScore));
		
		redisCommands.zAdd(key, scoreMembers);
		
		Set<ZSetTuple<Object>> tuples = redisCommands.zRangeByScoreWithScores(key, minScore, maxScore);
		assertEquals(scoreMembers.size(), tuples.size());
		System.out.println(tuples);
		
		Limit limit = new Limit(1, scoreMembers.size());
		tuples = redisCommands.zRangeByScoreWithScores(key, minScore, maxScore, limit);
		assertEquals((int) (scoreMembers.size() - limit.getOffset()), tuples.size());
		System.out.println(tuples);
	}
	
//	@Test
	public void testZRevRangeByScoreWithScores() {
		assertNull(redisCommands.zRevRangeByScoreWithScores(key, minScore, maxScore));
		
		redisCommands.zAdd(key, scoreMembers);
		
		Set<ZSetTuple<Object>> tuples = redisCommands.zRevRangeByScoreWithScores(key, minScore, maxScore);
		assertEquals(scoreMembers.size(), tuples.size());
		System.out.println(tuples);
		
		Limit limit = new Limit(1, scoreMembers.size());
		tuples = redisCommands.zRevRangeByScoreWithScores(key, minScore, maxScore, limit);
		assertEquals((int) (scoreMembers.size() - limit.getOffset()), tuples.size());
		System.out.println(tuples);
	}
	
	@Test
	public void testZScan() {
		MappedScanResult<String, Double> scanResult = redisCommands.zScan(key);
		assertNotNull(scanResult);
		assertTrue(scanResult.isEmpty());
		
		redisCommands.zAdd(key, scoreMembers);
		
		scanResult = redisCommands.zScan(keys[0], scanResult.getCursorId());
		assertNotNull(scanResult);
		assertTrue(scanResult.isEmpty());
		
		System.out.println("Scanning......");
		scanResult = redisCommands.zScan(key);
		assertNotNull(scanResult);
		assertTrue(scanResult.isNotEmpty());
		
		System.out.println(scanResult);
		if (scanResult.completed())
			System.out.println("Scan completed!");
		
		ScanOption option = new ScanOption();
		option.setCount(1L);
		
		System.out.println("Scanning by option......");
		scanResult = redisCommands.zScan(key, scanResult.getCursorId(), option);
		assertNotNull(scanResult);
		assertTrue(scanResult.isNotEmpty());
		assertTrue(scanResult.size() >= (option.getCount() / 2));
		System.out.println(scanResult);
		
		while (!scanResult.completed()) {
			System.out.println("Scan again......");
			scanResult = redisCommands.zScan(key, scanResult.getCursorId(), option);
			System.out.println(scanResult);
		}
		System.out.println("Scan completed!");
		
		option.setPattern("*a*");
		
		System.out.println("Scanning by option......");
		scanResult = redisCommands.zScan(key, scanResult.getCursorId(), option);
		assertNotNull(scanResult);
		assertTrue(scanResult.size() >= (option.getCount() / 2));
		System.out.println(scanResult);
		
		while (!scanResult.completed()) {
			System.out.println("Scan again......");
			scanResult = redisCommands.zScan(key, scanResult.getCursorId(), option);
			System.out.println(scanResult);
		}
		System.out.println("Scan completed!");
	}
	
}