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
 * Create Date : 2017-10-17
 */

package org.sniper.serialization.test.json;

import org.junit.Test;
import org.sniper.serialization.json.FastJsonSerializer;
import org.sniper.serialization.json.JsonLibSerializer;
import org.sniper.serialization.json.jackson.codehaus.CodehausJacksonSerializer;
import org.sniper.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;
import org.sniper.serialization.test.AbstractSerializerTest;
import org.sniper.test.domain.User;

/**
 * JSON序列化器单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JsonSerializerTest extends AbstractSerializerTest {
	
	private CodehausJacksonSerializer codehausJacksonSerializer = new CodehausJacksonSerializer();
	
	private FasterxmlJacksonSerializer fasterxmlJacksonSerializer = new FasterxmlJacksonSerializer();
	
	private FastJsonSerializer fastJsonSerializer = new FastJsonSerializer();
	
	private JsonLibSerializer jsonLibSerializer = new JsonLibSerializer();
	
	@Test
	public void testCanSerialize() {
		System.out.println("CodehausJacksonSerializer can serialize:" + codehausJacksonSerializer.canSerialize(User.class)); 
		System.out.println("FasterxmlJacksonSerializer can serialize:" + fasterxmlJacksonSerializer.canSerialize(User.class));
		System.out.println("FastJsonSerializer can serialize:" + fastJsonSerializer.canSerialize(User.class)); 
		System.out.println("JsonLibSerializer can serialize:" + jsonLibSerializer.canSerialize(User.class)); 
	}
	
	@Test
	public void testCanDeserialize() {
		System.out.println("CodehausJacksonSerializer can deserialize:" + codehausJacksonSerializer.canDeserialize(User.class)); 
		System.out.println("FasterxmlJacksonSerializer can deserialize:" + fasterxmlJacksonSerializer.canDeserialize(User.class));
		System.out.println("FastJsonSerializer can deserialize:" + fastJsonSerializer.canDeserialize(User.class)); 
		System.out.println("JsonLibSerializer can deserialize:" + jsonLibSerializer.canDeserialize(User.class)); 
	}
	
	@Test
	public void test() {
		String codehausJacksonResult = codehausJacksonSerializer.serializeToString(user);
		String fasterxmlJacksonResult = fasterxmlJacksonSerializer.serializeToString(user);
		String fastJsonResult = fastJsonSerializer.serializeToString(user);
		String jsonLibResult = jsonLibSerializer.serializeToString(user);
		
		System.out.println("CodehausJacksonSerializer serialize result:" + codehausJacksonResult); 
		System.out.println("FasterxmlJacksonSerializer serialize result:" + fasterxmlJacksonResult);
		System.out.println("FastJsonSerializer serialize result:" + fastJsonResult); 
		System.out.println("JsonLibSerializer serialize result:" + jsonLibResult); 
		System.out.println("-------------------------------------------------------"); 
		
		System.out.println("CodehausJacksonSerializer deserialize result:" + codehausJacksonSerializer.deserialize(codehausJacksonResult)); 
		System.out.println("FasterxmlJacksonSerializer deserialize result:" + fasterxmlJacksonSerializer.deserialize(fasterxmlJacksonResult));
		System.out.println("FastJsonSerializer deserialize result:" + fastJsonSerializer.deserialize(fastJsonResult)); 
		System.out.println("JsonLibSerializer deserialize result:" + jsonLibSerializer.deserialize(jsonLibResult)); 
	}

}
