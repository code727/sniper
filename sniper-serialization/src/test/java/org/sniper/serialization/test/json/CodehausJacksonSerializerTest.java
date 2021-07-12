/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-7-12
 */

package org.sniper.serialization.test.json;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.StdDateFormat;
import org.junit.Test;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.serialization.json.jackson.codehaus.CodehausJacksonSerializer;
import org.sniper.serialization.test.AbstractSerializerTest;
import org.sniper.test.domain.User;

/**
 * Jackson序列化器单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class CodehausJacksonSerializerTest extends AbstractSerializerTest {
	
	private CodehausJacksonSerializer codehausJacksonSerializer;
	
	public CodehausJacksonSerializerTest() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new StdDateFormat());
		this.codehausJacksonSerializer = new CodehausJacksonSerializer(mapper);
		
	}
	
	/**
	 * 单值测试
	 * @author Daniele 
	 * @throws Exception
	 */
//	@Test
	public void singleValueTest() throws Exception {
		String json = codehausJacksonSerializer.serializeToString(user);
		System.out.println("----------------------------JSON------------------------");
		System.out.println(json);
		
		Object deserializeResult = null;
		
		System.out.println("--------------------------Deserialize--------------------");
		// LinkedHashMap
		deserializeResult = codehausJacksonSerializer.deserialize(json);
		System.out.println("Default type -> " + deserializeResult.getClass());
		
		// LinkedHashMap
		deserializeResult = codehausJacksonSerializer.deserialize(json, null);
		System.out.println("Null type -> " + deserializeResult.getClass());
		
		// LinkedHashMap
		deserializeResult = codehausJacksonSerializer.deserialize(json, Object.class);
		System.out.println("Object type -> " + deserializeResult.getClass());
		
		// LinkedHashMap
		deserializeResult = codehausJacksonSerializer.deserialize(json, Map.class);
		System.out.println("Map type -> " + deserializeResult.getClass());
		
		// User
		deserializeResult = codehausJacksonSerializer.deserialize(json, User.class);
		System.out.println("Java bean type - > " + deserializeResult.getClass());
		
		// Object[LinkedHashMap]
		deserializeResult = codehausJacksonSerializer.deserialize(json, Object[].class);
		System.out.println("Object array type -> " + deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("Object array element type -> " + ((Object[])deserializeResult)[0].getClass());
		
		// Map[LinkedHashMap]
		deserializeResult = codehausJacksonSerializer.deserialize(json, Map[].class);
		System.out.println("Map array type -> " + deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("Map array element type -> " + ((Object[])deserializeResult)[0].getClass());
		
		// User[]
		deserializeResult = codehausJacksonSerializer.deserialize(json, User[].class);
		System.out.println("Java bean array type - > " + deserializeResult.getClass());
		
		// List<LinkedHashMap>
		deserializeResult = codehausJacksonSerializer.deserialize(json, List.class);
		System.out.println("List type -> " +  deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("List element type -> " + ((List<?>)deserializeResult).get(0).getClass());
//
		// Set<LinkedHashMap>
		deserializeResult = codehausJacksonSerializer.deserialize(json, Set.class);
		System.out.println("Set type -> " +  deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("Set element type -> " + ((Set<?>)deserializeResult).iterator().next().getClass());
	}
	
	@Test
	public void multiValueTest() throws Exception {
		String jsonArray = codehausJacksonSerializer.serializeToString(list);
		System.out.println("----------------------------JSON------------------------");
		System.out.println(jsonArray);
		
		Object deserializeResult = null;
		System.out.println("--------------------------Deserialize--------------------");
		
		// List<LinkedHashMap>
		deserializeResult = codehausJacksonSerializer.deserialize(jsonArray);
		System.out.println("Default type -> " + deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("List element type -> " + ((List<?>)deserializeResult).get(0).getClass());
		
		// List<LinkedHashMap>
		deserializeResult = codehausJacksonSerializer.deserialize(jsonArray, null);
		System.out.println("Null type -> " + deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("List element type -> " + ((List<?>)deserializeResult).get(0).getClass());
		
		// List<LinkedHashMap>
		deserializeResult = codehausJacksonSerializer.deserialize(jsonArray, Object.class);
		System.out.println("Object type -> " + deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("List element type -> " + ((List<?>)deserializeResult).get(0).getClass());
		
		// List<LinkedHashMap>
		deserializeResult = codehausJacksonSerializer.deserialize(jsonArray, Map.class);
		System.out.println("Map type -> " + deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("Map element type -> " + ((List<?>)deserializeResult).get(0).getClass());
		
		// List<User>
		deserializeResult = codehausJacksonSerializer.deserialize(jsonArray, User.class);
		System.out.println("Java bean type - > " + deserializeResult.getClass());
		
		// Object[LinkedHashMap]
		deserializeResult = codehausJacksonSerializer.deserialize(jsonArray, Object[].class);
		System.out.println("Object array type -> " + deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("Object array element type -> " + ((Object[])deserializeResult)[0].getClass());
		
		// Map[LinkedHashMap]
		deserializeResult = codehausJacksonSerializer.deserialize(jsonArray, Map[].class);
		System.out.println("Map array type -> " + deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("Map array element type -> " + ((Map[])deserializeResult)[0].getClass());
		
		// User[]
		deserializeResult = codehausJacksonSerializer.deserialize(jsonArray, User[].class);
		System.out.println("Java bean array type - > " + deserializeResult.getClass());
		
		// List<LinkedHashMap>
		deserializeResult = codehausJacksonSerializer.deserialize(jsonArray, List.class);
		System.out.println("List type -> " +  deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("List element type -> " + ((List<?>)deserializeResult).get(0).getClass());
		
		// Set<LinkedHashMap>
		deserializeResult = codehausJacksonSerializer.deserialize(jsonArray, Set.class);
		System.out.println("Set type -> " +  deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("Set element type -> " + ((Set<?>)deserializeResult).iterator().next().getClass());
	}
		
}
