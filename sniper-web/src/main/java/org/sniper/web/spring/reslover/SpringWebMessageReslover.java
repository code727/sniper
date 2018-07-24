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
 * Create Date : 2015-6-23
 */

package org.sniper.web.spring.reslover;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.sniper.commons.util.AssertUtils;
import org.sniper.templet.message.resolver.AbstractMessageResolver;
import org.sniper.web.WebMessageResolver;
import org.sniper.web.spring.WebContextHelper;

/**
 * 基于Spring Web应用的消息解析器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SpringWebMessageReslover extends AbstractMessageResolver
		implements WebMessageResolver, InitializingBean {
	
	@Autowired
	private SessionLocaleResolver localeResolver;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	public SessionLocaleResolver getLocaleResolver() {
		return localeResolver;
	}

	public void setLocaleResolver(SessionLocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		AssertUtils.assertNotNull(this.localeResolver, "Property 'localeResolver' is required");
		AssertUtils.assertNotNull(this.messageSource, "Property 'messageSource' is required");
	}
	
	public HttpServletRequest getHttpServletRequest() {
		return WebContextHelper.getHttpServletRequest();
	}

	@Override
	public Locale getLocale() {
		HttpServletRequest request = getHttpServletRequest();
		return request != null ? localeResolver.resolveLocale(request) : Locale.getDefault();
	}

	@Override
	public String getMessage(String key, Object[] params, String defaultMessage) {
		try {
			return messageSource.getMessage(key, params, getLocale());
		} catch (NoSuchMessageException e) {
			// 未获取到消息时，将默认值作为消息返回
			return defaultMessage;
		}
	}
	
}
