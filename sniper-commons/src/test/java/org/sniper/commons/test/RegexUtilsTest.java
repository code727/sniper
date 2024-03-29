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
 * Create Date : 2016年7月26日
 */

package org.sniper.commons.test;

import org.junit.Test;
import org.sniper.commons.enums.logic.RegexEnum;
import org.sniper.commons.util.RegexUtils;
import org.sniper.test.junit.BaseTestCase;

/**
 * 正则表达式工具单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class RegexUtilsTest extends BaseTestCase {
	
	@Test
	public void testMatchesAppend() {
		
		String test = "<link rel='dns-prefetch' href='http://img1.cache.netease.com' />"
			 + "\n" + "<link rel='apple-touch-icon-precomposed' href='http://img1.cache.netease.com/www/logo/logo-ipad-icon.png' >";
		System.out.println("-----------------  原文    -----------------");
		System.out.println(test);
		System.out.println("-----------------  新文    -----------------");
		System.out.println(RegexUtils.matchesAppend(test, RegexEnum.URL.getExpression(), "<url>", "</url>"));
	}

}
