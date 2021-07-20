/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-7-8
 */

package org.sniper.commons.test;

import org.junit.Test;
import org.sniper.commons.util.NumberUtils;
import org.sniper.test.junit.BaseTestCase;

/**
 * 数字工具类单元测试
 * @author  Daniele
 * @version 1.0
 */
public class NumberUtilsTest extends BaseTestCase {
	
	private final long min = 1;
	
	private final long max = 300;
	
//	@Test
	public void testMaxLimit() {
		long result;
		
		result = NumberUtils.maxLimit(min, min);
		assertTrue(result == min);
		System.out.println(result);
		
		result = NumberUtils.maxLimit(min, max);
		assertTrue(result == min);
		System.out.println(result);
		
		result = NumberUtils.maxLimit(min + 1, min);
		assertTrue(result == min);
		System.out.println(result);
		
		result = NumberUtils.maxLimit(min - 1, min);
		assertTrue(result == min - 1);
		System.out.println(result);
		
		result = NumberUtils.maxLimit(max, min);
		assertTrue(result == min);
		System.out.println(result);
		
		result = NumberUtils.maxLimit(max + 1, max);
		assertTrue(result == max);
		System.out.println(result);
	}
	
//	@Test
	public void testMinLimit() {
		long result;
		
		result = NumberUtils.minLimit(max, max);
		assertTrue(result == max);
		System.out.println(result);
		
		result = NumberUtils.minLimit(min, max);
		assertTrue(result == max);
		System.out.println(result);
		
		result = NumberUtils.minLimit(max + 1, max);
		assertTrue(result == max + 1);
		System.out.println(result);
		
		result = NumberUtils.minLimit(max + 1, min);
		assertTrue(result == max + 1);
		System.out.println(result);
	}
	
	@Test
	public void testRangeLimit() {
		long result;
		
		result = NumberUtils.rangeLimit(max, max, max);
		assertTrue(result == max);
		System.out.println(result);
		
		result = NumberUtils.rangeLimit(max, max, min);
		assertTrue(result == max);
		System.out.println(result);
		
		result = NumberUtils.rangeLimit(max + 1, max, min);
		assertTrue(result == max);
		System.out.println(result);
		
		result = NumberUtils.rangeLimit(max + 1, max, min);
		assertTrue(result == max);
		System.out.println(result);
		
		result = NumberUtils.rangeLimit(min - 1, max, min);
		assertTrue(result == min);
		System.out.println(result);
		
		result = NumberUtils.rangeLimit(min - 1, min, max);
		assertTrue(result == min);
		System.out.println(result);
		
		result = NumberUtils.rangeLimit(min, max, max);
		assertTrue(result == max);
		System.out.println(result);
		
		result = NumberUtils.rangeLimit(max, min, min);
		assertTrue(result == min);
		System.out.println(result);
		
		result = NumberUtils.rangeLimit(min + 1, min, max);
		assertTrue(result == min + 1);
		System.out.println(result);
		
		result = NumberUtils.rangeLimit(min + 1, max, min);
		assertTrue(result == min + 1);
		System.out.println(result);
	}
	
}
