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
 * Create Date : 2019-2-25
 */

package org.sniper.nosql.test;

import java.util.List;

import org.junit.Test;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.nosql.redis.enums.GeoDistanceUnit;
import org.sniper.nosql.redis.model.geo.DefaultGeoLocations;
import org.sniper.nosql.redis.model.geo.GeoCircle;
import org.sniper.nosql.redis.model.geo.GeoDistance;
import org.sniper.nosql.redis.model.geo.GeoLocations;
import org.sniper.nosql.redis.model.geo.GeoPoint;
import org.sniper.nosql.redis.model.geo.GeoRadiusResult;
import org.sniper.nosql.redis.option.GeoRadiusOption;

/**
 * Redis地理信息命令行单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class RedisGeoCommandsTest extends AbstractRedisTest {
	
	private final String[] members = new String[] { "cd", "dj", "ls"};
	
	private final String otherMember = "bj";
	
	private final GeoPoint chengDu = new GeoPoint(104.06, 30.67);
	
	private final GeoPoint duJiangYan = new GeoPoint(103.62, 31.00);
	
	private final GeoPoint leShan = new GeoPoint(103.77, 29.57);
	
//	private final GeoPoint beiJing = new GeoPoint(116.30, 39.90);
	
//	@Test
	public void testGeoAdd() {
		Long count = redisCommands.geoAdd(key, members[0], chengDu);
		assertEquals(1L, count);
		
		count = redisCommands.geoAdd(key, members[0], chengDu);
		assertEquals(0L, count);
		
		DefaultGeoLocations<String> locations = new DefaultGeoLocations<String>(2);
		locations.add(members[1], duJiangYan);
		locations.add(members[2], leShan);
		
		count = redisCommands.geoAdd(key, locations);
		assertEquals(2L, count);
		
		count = redisCommands.geoAdd(key, locations);
		assertEquals(0L, count);
	}
	
//	@Test
	public void testGeoPos() {
		GeoPoint point = redisCommands.geoPos(key, members[0]);
		assertNull(point);
		
		GeoLocations<String> locations = redisCommands.geoPos(key, members);
		assertEquals(members.length, locations.size());
		System.out.println(locations);
		
		addGeoMembers();
		
		locations = redisCommands.geoPos(key, members);
		assertEquals(members.length, locations.size());
		System.out.println(locations);
		
		locations = redisCommands.geoPos(key, new String[] { otherMember });
		assertEquals(1, locations.size());
		assertNull(locations.get(otherMember));
		System.out.println(locations);
	}
	
//	@Test
	public void testGeoDist() {
		GeoDistance distance = redisCommands.geoDist(key, members[0], members[1]);
		assertNull(distance);
		
		addGeoMembers();
		
		// cd -> dj
		distance = redisCommands.geoDist(key, members[0], members[1]);
		assertNotNull(distance);
		System.out.println(distance);
		
		// cd -> ls
		distance = redisCommands.geoDist(key, members[1], members[2], GeoDistanceUnit.KILOMETERS);
		assertNotNull(distance);
		System.out.println(distance);
		
		// cd -> bj
		distance = redisCommands.geoDist(key, members[1], otherMember, GeoDistanceUnit.KILOMETERS);
		assertNull(distance);
		
		// bj -> cd
		distance = redisCommands.geoDist(key, otherMember, members[1]);
		assertNull(distance);
	}
	
//	@Test
	public void testGeoRadius() {
		addGeoMembers();
		
		GeoDistance radius = new GeoDistance(10, GeoDistanceUnit.KILOMETERS);
		GeoCircle circle = new GeoCircle(chengDu, radius);
		
		GeoRadiusResult<String> radiusResult = redisCommands.geoRadius(key, circle);
		assertNotNull(radiusResult);
		System.out.println(radiusResult);
		
		radius = new GeoDistance(100, GeoDistanceUnit.KILOMETERS);
		circle = new GeoCircle(chengDu, radius);
		 
		radiusResult = redisCommands.geoRadius(key, circle);
		assertNotNull(radiusResult);
		System.out.println(radiusResult);
		
		radius = new GeoDistance(200, GeoDistanceUnit.KILOMETERS);
		circle = new GeoCircle(chengDu, radius);
		GeoRadiusOption option = new GeoRadiusOption();
		// 返回各成员离中心点的距离
		option.withDist();
		
		radiusResult = redisCommands.geoRadius(key, circle, option);
		assertNotNull(radiusResult);
		System.out.println(radiusResult);
		
		// 返回各成员的地理位置坐标点
		option.withCoord();
		radiusResult = redisCommands.geoRadius(key, circle, option);
		assertNotNull(radiusResult);
		System.out.println(radiusResult);
	}
	
//	@Test
	public void testGeoRadiusByMember() {
		GeoRadiusResult<String> radiusResult = redisCommands.geoRadiusByMember(key, otherMember, 100);
		assertNull(radiusResult);
		
		addGeoMembers();
		
		radiusResult = redisCommands.geoRadiusByMember(key, otherMember, new GeoDistance(100));
		assertNull(radiusResult);
		
		radiusResult = redisCommands.geoRadiusByMember(key, members[0], new GeoDistance(100));
		assertNotNull(radiusResult);
		System.out.println(radiusResult);
		
		radiusResult = redisCommands.geoRadiusByMember(key, members[0], new GeoDistance(100, GeoDistanceUnit.KILOMETERS));
		assertNotNull(radiusResult);
		System.out.println(radiusResult);
		
		GeoRadiusOption option = new GeoRadiusOption();
		// 返回各成员离中心点的距离
		option.withDist();
		
		radiusResult = redisCommands.geoRadiusByMember(key, members[0], new GeoDistance(100, GeoDistanceUnit.KILOMETERS), option);
		assertNotNull(radiusResult);
		System.out.println(radiusResult);
		
		// 返回各成员的地理位置坐标点
		option.withCoord();
		
		radiusResult = redisCommands.geoRadiusByMember(key, members[0], new GeoDistance(100, GeoDistanceUnit.KILOMETERS), option);
		assertNotNull(radiusResult);
		System.out.println(radiusResult);
	}
	
//	@Test
	public void testGeoHash() {
		String geoHash = redisCommands.geoHash(key, members[0]);
		assertNull(geoHash);
		
		List<String> geoHashs = redisCommands.geoHash(key, members);
		assertTrue(CollectionUtils.isEmpty(geoHashs));
		
		addGeoMembers();
		
		geoHash = redisCommands.geoHash(key, members[0]);
		assertNotNull(geoHash);
		System.out.println(geoHash);
		
		geoHashs = redisCommands.geoHash(key, members);
		assertTrue(CollectionUtils.isNotEmpty(geoHashs));
		System.out.println(geoHashs);
		
		geoHash = redisCommands.geoHash(keys[0], members[0]);
		assertNull(geoHash);
		
		geoHashs = redisCommands.geoHash(keys[0], members);
		assertTrue(CollectionUtils.isEmpty(geoHashs));
	}
	
	@Test
	public void testGeoRemove() {
		Long count = redisCommands.geoRemove(key, members[0]);
		assertEquals(0L, count);
		
		count = redisCommands.geoRemove(key, members);
		assertEquals(0L, count);
		
		addGeoMembers();
		
		count = redisCommands.geoRemove(key, members[0]);
		assertEquals(1L, count);
		
		count = redisCommands.geoRemove(key, members);
		assertEquals(members.length - 1, count.intValue());
	}
	
	protected void addGeoMembers() {
		GeoLocations<String> locations = new DefaultGeoLocations<String>(3);
		locations.add(members[0], chengDu);
		locations.add(members[1], duJiangYan);
		locations.add(members[2], leShan);
		redisCommands.geoAdd(key, locations);
	}
	
}
