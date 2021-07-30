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
 * Create Date : 2021-7-29
 */

package org.sniper.templet.test;

import java.util.Locale;

import org.junit.Test;
import org.sniper.commons.base.Responsible;
import org.sniper.commons.enums.http.HttpStatusEnum;
import org.sniper.commons.enums.status.ResponseStatusEnum;
import org.sniper.templet.message.formatter.AdaptiveMessageFormatter;
import org.sniper.templet.message.formatter.MessageFormatter;
import org.sniper.templet.message.source.ResourceBundleMessageSource;
import org.sniper.test.junit.BaseTestCase;

/**
 * ResourceBundleMessageSource单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class ResourceBundleMessageSourceTest extends BaseTestCase {
	
	private final ResourceBundleMessageSource messageSource;
	
	private final MessageFormatter<Object> messageFormatter;
	
	public ResourceBundleMessageSourceTest() {
		this.messageSource = new ResourceBundleMessageSource();
		this.messageSource.setBaseName("i18n/http/HttpStatusEnum,i18n/status/ResponseStatusEnum");
		this.messageFormatter = new AdaptiveMessageFormatter();
	}
	
	public String getLocaleMessage(Responsible<?> responsive) {
		return getLocaleMessage(responsive, (Locale) null);
	}
	
	public String getLocaleMessage(Responsible<?> responsive, Object... params) {
		return getLocaleMessage(responsive, null, params);
	}
	
	public String getLocaleMessage(Responsible<?> responsive, Locale locale) {
		return getLocaleMessage(responsive, locale, (Object[]) null);
	}
	
	public String getLocaleMessage(Responsible<?> responsive, Locale locale, Object... params) {
		return messageFormatter.format(messageSource.getMessageByKey(responsive.getMessage(), locale), params);
	}
	
	@Test
	public void test() {
		testResponseStatusEnum();
//		testHttpStatusEnum();
		
//		testResponseStatusEnum(ResponseStatusEnum.PARAM_NOT_EMPTY, "用户名");
//		testResponseStatusEnum(ResponseStatusEnum.PARAM_MUST_LESS_THAN_EQUALS_EXPECTED, "开始时间", "结束时间");
	}
	
	protected void testResponseStatusEnum(Responsible<?> responsive, Object... params) {
		ResourceBundleMessageSourceTest test = new ResourceBundleMessageSourceTest();
		System.out.println(test.getLocaleMessage(responsive, params));
	}
		
	protected void testResponseStatusEnum() {
		logger.info("Start test {} ...", ResponseStatusEnum.class.getName());
		ResourceBundleMessageSourceTest test = new ResourceBundleMessageSourceTest();
		
		for (ResponseStatusEnum status : ResponseStatusEnum.values()) {
			System.out.println(String.format("Resource - [%d]:%s", status.getCode(), test.getLocaleMessage(status)));
		}
		logger.info("End test {}", ResponseStatusEnum.class.getName());
	}
	
	protected void testHttpStatusEnum() {
		logger.info("Start test {} ...", HttpStatusEnum.class.getName());
		ResourceBundleMessageSourceTest test = new ResourceBundleMessageSourceTest();
		
		for (HttpStatusEnum status : HttpStatusEnum.values()) {
			System.out.println(String.format("HTTPResource - [%d]:%s", status.getCode(), test.getLocaleMessage(status)));
		}
		
		logger.info("End test {}", HttpStatusEnum.class.getName());
	}
	

}
