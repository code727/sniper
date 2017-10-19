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
 * Create Date : 2016-7-13
 */

package org.sniper.serialization.test.serializer.json;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.serialization.json.JsonLibSerializer;
import org.sniper.serialization.test.domain.User;
import org.sniper.serialization.test.serializer.AbstractSerializerTest;

/**
 * JsonLib序列化器单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JsonLibSerializerTest extends AbstractSerializerTest {
	
	private JsonLibSerializer jsonLibSerializer;
	
	public JsonLibSerializerTest() {
		this.jsonLibSerializer = new JsonLibSerializer();
	}
	
	/**
	 * 单值测试
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @throws Exception
	 */
	@Test
	public void singleValueTest() throws Exception {
		String json = jsonLibSerializer.serializeToString(user);
		System.out.println("----------------------------JSON------------------------");
		System.out.println(json);
		
		Object deserializeResult = null;
		
		System.out.println("--------------------------Deserialize--------------------");
		deserializeResult = jsonLibSerializer.deserialize(json);
		System.out.println("Default type -> " + deserializeResult.getClass());
		
		deserializeResult = jsonLibSerializer.deserialize(json, null);
		System.out.println("Null type -> " + deserializeResult.getClass());
		
		deserializeResult = jsonLibSerializer.deserialize(json, Object.class);
		System.out.println("Object type -> " + deserializeResult.getClass());
		
		deserializeResult = jsonLibSerializer.deserialize(json, Map.class);
		System.out.println("Map type -> " + deserializeResult.getClass());
		
		deserializeResult = jsonLibSerializer.deserialize(json, User.class);
		System.out.println("Java bean type - > " + deserializeResult.getClass());
		
		deserializeResult = jsonLibSerializer.deserialize(json, Object[].class);
		System.out.println("Object array type -> " + deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("Object array element type -> " + ((Object[])deserializeResult)[0].getClass());
		
		deserializeResult = jsonLibSerializer.deserialize(json, User[].class);
		System.out.println("Java bean array type - > " + deserializeResult.getClass());
		
		deserializeResult = jsonLibSerializer.deserialize(json, List.class);
		System.out.println("List type -> " +  deserializeResult.getClass() + "(size=" + ObjectUtils.count(deserializeResult) + ")");
		System.out.println("List element type -> " + ((List<?>)deserializeResult).get(0).getClass());
	}
	
}
