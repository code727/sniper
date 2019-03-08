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
 * Create Date : 2019-2-15
 */

package org.sniper.nosql.test;

import java.util.Properties;

import org.junit.Test;
import org.sniper.commons.util.BooleanUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.nosql.redis.enums.Section;

/**
 * Redis服务终端命令单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisServerCommandsTest extends AbstractRedisTest {
	
//	@Test
	public void testInfo1() {
		Properties defalutProperties = redisCommands.info();
		Properties allProperties = redisCommands.info(Section.ALL);
		assertTrue(defalutProperties.size() != allProperties.size());
		System.out.println("defalutSize:" + defalutProperties.size());
		System.out.println("allSize:" + allProperties.size());
		
		int size = 0;
		for (Section section : Section.values()) {
			if (!(section.equals(Section.ALL) || section.equals(Section.DEFAULT))) {
				Properties properties = redisCommands.info(section);
				System.out.println(section.name() + ":");
				System.out.println(properties);
				size += properties.size();
			}
		}
		assertEquals(allProperties.size(), size);
	}
	
//	@Test
	public void testInfo2() {
		String message = redisCommands.info("test");
		assertNull(message);
		
		message = redisCommands.info("redis_version");
		assertNotNull(message);
		System.out.println(message);
		
		boolean aofEnabled = redisCommands.info("aof_enabled", boolean.class);
		System.out.println(aofEnabled);
	}
	
	@Test
	public void testConfigGet() {
		Properties configs = redisCommands.configGet("test");
		assertNull(configs);
		
		configs = redisCommands.configGet();
		assertNotNull(configs);
		System.out.println(configs);
		
		configs = redisCommands.configGet("s*");
		assertNotNull(configs);
		System.out.println(configs);
		
		String loglevel = redisCommands.config("loglevel");
		assertNotNull(loglevel);
		System.out.println("loglevel=" + loglevel);
		
		int databases = redisCommands.config("databases", int.class);
		assertNotNull(databases);
		System.out.println("databases=" + databases);
		
		boolean clusterEnabled = BooleanUtils.isTrue(redisCommands.config("cluster-enabled", boolean.class));
		System.out.println("clusterEnabled=" + clusterEnabled);
	}
	
//	@Test
	public void testConfigSet() {
		int maxLlen = 256;
		redisCommands.configSet("slowlog-max-len", maxLlen);
		int slowlogMaxLen = redisCommands.config("slowlog-max-len", int.class);
		assertEquals(maxLlen, slowlogMaxLen);
		System.out.println(slowlogMaxLen);
	}
	
//	@Test
	public void testXSave() {
		redisCommands.save();
		
		Long lastTime = redisCommands.lastSave();
		assertNotNull(lastTime);
		System.out.println(DateUtils.unixTimestampToString(lastTime));
		
		redisCommands.bgSave();
		lastTime = redisCommands.lastSave();
		assertNotNull(lastTime);
		System.out.println(DateUtils.unixTimestampToString(lastTime));
	}
	
//	@Test
	public void time() {
		Long time = redisCommands.time();
		assertNotNull(time);
		System.out.println(DateUtils.timeToString(time));
	}
	
//	@Test
	public void testEcho() {
		String message = "test";
		String echo = redisCommands.echo(message);
		assertEquals(message, echo);
		System.out.println(echo);
	}
	
//	@Test
	public void testPing() {
		String echo = redisCommands.ping();
		assertEquals("PONG", echo);
		System.out.println(echo);
		
		String message = "test";
		echo = redisCommands.ping(message);
		assertEquals(message, echo);
		System.out.println(echo);
	}
	
}
