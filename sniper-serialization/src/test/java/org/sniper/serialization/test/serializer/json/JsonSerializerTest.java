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
 * Create Date : 2017年10月17日
 */

package org.sniper.serialization.test.serializer.json;

import org.junit.Before;
import org.junit.Test;
import org.sniper.commons.enums.ebusiness.O2OType;
import org.sniper.serialization.json.FastJsonSerializer;
import org.sniper.serialization.json.JsonLibSerializer;
import org.sniper.serialization.json.jackson.codehaus.CodehausJacksonSerializer;
import org.sniper.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;
import org.sniper.test.junit.BaseTestCase;

/**
 * JSON序列化器单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JsonSerializerTest extends BaseTestCase {
	
	private CodehausJacksonSerializer codehausJacksonSerializer = new CodehausJacksonSerializer();
	
	private FasterxmlJacksonSerializer fasterxmlJacksonSerializer = new FasterxmlJacksonSerializer();
	
	private FastJsonSerializer fastJsonSerializer = new FastJsonSerializer();
	
	private JsonLibSerializer jsonLibSerializer = new JsonLibSerializer();
	
	protected Class<?> type;
	protected Object value;
	
	@Before
	public void init() {
		type = O2OType[].class;
		value = O2OType.ONLINE;
	}
	
//	@Test
	public void testCanSerialize() {
		System.out.println("CodehausJacksonSerializer can serialize:" + codehausJacksonSerializer.canSerialize(type)); 
		System.out.println("FasterxmlJacksonSerializer can serialize:" + fasterxmlJacksonSerializer.canSerialize(type));
		System.out.println("FastJsonSerializer can serialize:" + fastJsonSerializer.canSerialize(type)); 
		System.out.println("JsonLibSerializer can serialize:" + jsonLibSerializer.canSerialize(type)); 
	}
	
//	@Test
	public void testCanDeserialize() {
		System.out.println("CodehausJacksonSerializer can deserialize:" + codehausJacksonSerializer.canDeserialize(type)); 
		System.out.println("FasterxmlJacksonSerializer can deserialize:" + fasterxmlJacksonSerializer.canDeserialize(type));
		System.out.println("FastJsonSerializer can deserialize:" + fastJsonSerializer.canDeserialize(type)); 
		System.out.println("JsonLibSerializer can deserialize:" + jsonLibSerializer.canDeserialize(type)); 
	}
	
	@Test
	public void test() {
		String codehausJacksonResult = codehausJacksonSerializer.serializeToString(value);
		String fasterxmlJacksonResult = fasterxmlJacksonSerializer.serializeToString(value);
		String fastJsonResult = fastJsonSerializer.serializeToString(value);
		String jsonLibResult = jsonLibSerializer.serializeToString(value);
		
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
