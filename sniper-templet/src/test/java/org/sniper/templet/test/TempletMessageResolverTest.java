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
 * Create Date : 2016年8月1日
 */

package org.sniper.templet.test;

import java.util.Map;

import org.junit.Test;
import org.sniper.commons.util.MapUtils;
import org.sniper.templet.message.formatter.AdaptiveMessageFormatter;
import org.sniper.templet.message.resolver.TempletMessageResolver;
import org.sniper.templet.message.source.ResourceBundleMessageSource;
import org.sniper.test.junit.BaseTestCase;

/**
 * 模板消息解析器单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class TempletMessageResolverTest extends BaseTestCase {
	
	@Test
	public void test() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//		messageSource.setBaseName("i18n/test");
		
		TempletMessageResolver templetMessageResolver = new TempletMessageResolver(messageSource);
		templetMessageResolver.setMessageFormatter(new AdaptiveMessageFormatter());
		
		System.out.println(templetMessageResolver.getMessage("test.message1"));
		System.out.println(templetMessageResolver.getMessage("test.message2", 2));
		
		Map<String, Object> parameters = MapUtils.newHashMap();
		parameters.put("name", "daniele");
		System.out.println(templetMessageResolver.getMessage("test.message3", parameters));
	}

}
