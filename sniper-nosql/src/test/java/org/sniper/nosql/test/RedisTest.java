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
 * Create Date : 2017年12月29日
 */

package org.sniper.nosql.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.sniper.nosql.redis.dao.RedisCommandsDao;
import org.sniper.serialization.json.JsonSerializer;
import org.sniper.serialization.json.jackson.codehaus.CodehausJacksonSerializer;
import org.sniper.test.domain.User;
import org.sniper.test.spring.JUnit4SpringTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext-redis.xml" })
public class RedisTest extends JUnit4SpringTestCase {
	
	protected JsonSerializer jsonSerializer = new CodehausJacksonSerializer();
	
	@Autowired
	private RedisCommandsDao redisCommandsDao;
	
	private User user = new User();
	
	@Before
	public void init() {
		user.setId(9527L);
		user.setName("杜斌");
		user.setAge(35);
		user.setMarried(true);
		user.setCreateTime(new Date());
	}
	
//	@Test
	public void testStringOperatin() {
		String key = "name";
		String name = "dubin";
		
		redisCommandsDao.set(key, name);
		name = redisCommandsDao.get(key);
		System.out.println(name);
		
		key = "age";
		int age = 36;
		redisCommandsDao.set2("string", key, age);
		age = redisCommandsDao.get2("string", key, int.class);
		System.out.println(age);
	}
	
//	@Test
	public void testKeys() {
		System.out.println(redisCommandsDao.keys());
		System.out.println(redisCommandsDao.valuesByPattern("age", int.class));
	}
	
//	@Test
	public void testJsonOperatin() {
		String key = "user";
		User value = user;
		
		redisCommandsDao.set2("json", key, value);
		value = redisCommandsDao.get2("json", key, User.class);
		System.out.println(jsonSerializer.serializeToString(value));
	}
	
	@Test
	public void testHessianOperatin() {
		String key = "user_hessian";
		User value = user;
		
//		redisCommandsDao.set2("hessian", key, value);
		value = redisCommandsDao.get2("hessian", key);
		System.out.println(jsonSerializer.serializeToString(value));
		
//		key = "name_hessian";
//		String name = "dubin";
//		redisCommandsDao.set2("hessian", key, name);
//		name = redisCommandsDao.get2("hessian",key);
//		System.out.println(name);
//		
//		key = "age_hessian";
//		int age = 36;
//		redisCommandsDao.set2("hessian", key, age);
//		age = redisCommandsDao.get2("hessian", key, Integer.class);
//		System.out.println(age);
	}
	
}
