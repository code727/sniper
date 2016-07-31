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
 * Create Date : 2016年7月27日
 */

package org.worin.support.test;

import java.util.HashMap;

import org.junit.Test;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.test.junit.BaseTestCase;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HashMapTest extends BaseTestCase {

	@Test
	public void testHashMap() {
		// Hash计算因子
		HashMap<String, Object> map = MapUtils.newHashMap();
		map.put(null, null);
		
		int h = 0;
		String k = "b";
		
		/* hash(key) 实现部分 */
		h ^= k.hashCode();
		h ^= (h >>> 20) ^ (h >>> 12);
		h = h ^ (h >>> 7) ^ (h >>> 4);
		
		// indexFor(hash, table.length)实现部分
		int i = h & (16 - 1);
		System.out.println(h);
		System.out.println(i);
		
		int length = 16;
		for (i = 0; i < 1000000; i++) {
			System.out.println(NumberUtils.randomIn((Integer.MAX_VALUE) & (length - 1)) < (length - 1));
		}
		
	}

}
