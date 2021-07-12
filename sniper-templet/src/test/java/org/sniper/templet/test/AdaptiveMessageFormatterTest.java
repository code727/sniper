/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015年6月21日
 */

package org.sniper.templet.test;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.sniper.commons.util.MapUtils;
import org.sniper.templet.message.formatter.AdaptiveMessageFormatter;
import org.sniper.test.domain.User;
import org.sniper.test.junit.BaseTestCase;

/**
 * 自适应参数对象消息格式化单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class AdaptiveMessageFormatterTest extends BaseTestCase {
	
	private static final String PLACEHOLDER_MESSAGE = "Hello,my name is %s, %d years old this year";
	
	private static final String NUMBER_PLACEHOLDER_MESSAGE_1 = "Hello,my name is {0}, {1} years old this year";
	
	private static final String PROPERTY_PLACEHOLDER_MESSAGE_1 = "Hello,my name is {name}, {age} years old this year";
	
	private static final String NUMBER_PLACEHOLDER_MESSAGE_2 = "Hello,my name is #{0}#, #{1}# years old this year";
	
	private static final String PROPERTY_PLACEHOLDER_MESSAGE_2 = "Hello,my name is #{name}#, #{age}# years old this year";
	
	private final AdaptiveMessageFormatter formatter1 = new AdaptiveMessageFormatter();
	
	private final AdaptiveMessageFormatter formatter2 = new AdaptiveMessageFormatter();
	
	private final User benParameters = new User();
	
	private final Map<String, Object> mapParameters = MapUtils.newHashMap();
	
	private Object[] arrayParameters;
	
	@Before
	public void init() {
		this.formatter2.setPrefix("#{");
		this.formatter2.setSuffix("}#");
		
		arrayParameters = new Object[] {"array", 1};
		
		mapParameters.put("0", "map_0");
		mapParameters.put("1", 2);
		
		mapParameters.put("name", "map_1");
		mapParameters.put("age", 3);
		
		benParameters.setName("bean");
		benParameters.setAge(4);
	}
	
//	@Test
	public void test1() {
		String message = formatter1.format(PLACEHOLDER_MESSAGE, arrayParameters);
		assertTrue(!message.equals(PLACEHOLDER_MESSAGE));
		System.out.println(message);
		
		message = formatter1.format(NUMBER_PLACEHOLDER_MESSAGE_1, arrayParameters);
		assertTrue(!message.equals(NUMBER_PLACEHOLDER_MESSAGE_1));
		System.out.println(message);
		
		message = formatter1.format(NUMBER_PLACEHOLDER_MESSAGE_1, mapParameters);
		assertTrue(!message.equals(NUMBER_PLACEHOLDER_MESSAGE_1));
		System.out.println(message);
		
		message = formatter1.format(PROPERTY_PLACEHOLDER_MESSAGE_1, mapParameters);
		assertTrue(!message.equals(PROPERTY_PLACEHOLDER_MESSAGE_1));
		System.out.println(message);
		
		message = formatter1.format(PROPERTY_PLACEHOLDER_MESSAGE_1, benParameters);
		assertTrue(!message.equals(PROPERTY_PLACEHOLDER_MESSAGE_1));
		System.out.println(message);
	}
	
	@Test
	public void test2() {
		String message = formatter2.format(PLACEHOLDER_MESSAGE, arrayParameters);
		assertTrue(!message.equals(PLACEHOLDER_MESSAGE));
		System.out.println(message);
		
		/* 当消息为数字占位符形式且参数类型不为Map时，实际上使用的是JDK原生MessageFormat来格式化消息的， 
		 * 不支持设置自定义的占位符前后缀 ，因此此步结果只会替换掉属于默认占位符的值  */
		message = formatter2.format(NUMBER_PLACEHOLDER_MESSAGE_2, arrayParameters);
		assertTrue(!message.equals(NUMBER_PLACEHOLDER_MESSAGE_2));
		System.out.println(message);
		
		message = formatter2.format(NUMBER_PLACEHOLDER_MESSAGE_2, mapParameters);
		assertTrue(!message.equals(NUMBER_PLACEHOLDER_MESSAGE_2));
		System.out.println(message);
		
		message = formatter2.format(PROPERTY_PLACEHOLDER_MESSAGE_2, mapParameters);
		assertTrue(!message.equals(PROPERTY_PLACEHOLDER_MESSAGE_2));
		System.out.println(message);
		
		message = formatter2.format(PROPERTY_PLACEHOLDER_MESSAGE_2, benParameters);
		assertTrue(!message.equals(PROPERTY_PLACEHOLDER_MESSAGE_2));
		System.out.println(message);
	}
		
}
