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

package org.worin.support.test;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.workin.commons.model.impl.ResultModel;
import org.workin.support.message.formatter.AdaptiveMessageFormatter;
import org.workin.test.junit.BaseTestCase;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AdaptiveMessageFormatterTest extends BaseTestCase {
	
	private AdaptiveMessageFormatter formatter = new AdaptiveMessageFormatter();
	
	private ResultModel<String> model = new ResultModel<String>();
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	@Before
	public void initDate() {
		model.setCode("0001");
		model.setData("workin test_001");
		
		map.put("code", "0001");
		map.put("data", "workin test_001");
		map.put("0", "0001");
		map.put("1", "workin test_001");
	}
	
//	@Test
	public void testFormat1() {
		String message1 = "This result code is {code}, data is {data}";
		String result = formatter.format(message1, model);
		
		assertEquals(result, formatter.format(message1, map));
		System.out.println(result);
	}
	
//	@Test
	public void testFormat2() {
		String message2 = "This result code is {0}, data is {1}";
		String result = formatter.format(message2, map);
		
		assertEquals(result, formatter.format(message2, new String[]{"0001", "workin test_001"}));
		System.out.println(result);
	}
	
//	@Test
	public void testFormat3() {
		formatter.setPrefix("#{");
		formatter.setSuffix("}#");
		String message3 = "This result code is #{code}#, data is #{data}#";
		String result = formatter.format(message3, map);
		
		assertEquals(result, formatter.format(message3, model));
		System.out.println(result);
	}
	
	@Test
	public void testFormat4() {
		String message = "This result code is {0}, data is {1}";
		String result = formatter.format(message, model);
		
		assertTrue(!message.equals(result));
		assertTrue(MessageFormat.format(message, model).equals(result));
		System.out.println(result);
	}
	
}
