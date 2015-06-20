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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.workin.support.message.formatter.AdaptiveMessageFormatter;
import org.workin.support.message.formatter.MessageFormatter;
import org.workin.support.model.impl.ResultModel;
import org.workin.test.junit.BaseTestCase;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AdaptiveMessageFormatterTest extends BaseTestCase {
	
	private MessageFormatter<Object> formatter = new AdaptiveMessageFormatter();
	
	@Test
	public void testFormat() throws NoSuchMethodException {
		String message1 = "This result code is {code}, data is {data}";
		ResultModel<String> model = new ResultModel<String>();
		model.setCode("0001");
		model.setDate("workin test!");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "0001");
		map.put("data", "workin test!");
		
		String result = formatter.format(message1, model);
		assertEquals(result, formatter.format(message1, map));
		System.out.println(result);
		
		String message2 = "This result code is {0}, data is {1}";
		map.put("0", "0001");
		map.put("1", "workin test!");
		
		result = formatter.format(message2, map);
		assertEquals(result, formatter.format(message2, new String[] { "0001", "workin test!" }));
		System.out.println(result);
		System.out.println(formatter.format(message2, "0001"));
		
		result = formatter.format(message2, model);
		assertEquals(message2, result);
		System.out.println("Can not format when message contains number placeholder and parameter is a java bean.");
	}

}
