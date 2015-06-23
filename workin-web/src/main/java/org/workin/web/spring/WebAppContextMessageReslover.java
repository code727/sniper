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

package org.workin.web.spring;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.workin.web.WebAppContextMessageResolver;

/**
 * @description 基于Spring Web应用上下文环境的消息解析器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class WebAppContextMessageReslover implements WebAppContextMessageResolver, InitializingBean {
	
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
		if (this.localeResolver == null)
			throw new IllegalArgumentException("Property 'localeResolver' is required");
		
		if (this.messageSource == null)
			throw new IllegalArgumentException("Property 'messageSource' is required");
	}

	@Override
	public String getMessage(String key) {
		return this.getMessage(key, null, key);
	}

	@Override
	public String getMessage(String key, String defaultMessage) {
		return this.getMessage(key, null, defaultMessage);
	}

	@Override
	public String getMessage(String key, Object param, String defaultMessage) {
		return this.getMessage(key, new Object[] { param }, defaultMessage);
	}

	@Override
	public String getMessage(String key, Object param) {
		return this.getMessage(key, new Object[] { param }, key);
	}

	@Override
	public String getMessage(String key, Object[] params) {
		return this.getMessage(key, params, key);
	}

	@Override
	public String getMessage(String key, Object[] params, String defaultMessage) {
		try {
			return messageSource.getMessage(key, params, this.getLocale());
		} catch (NoSuchMessageException e) {
			throw new NoSuchMessageException(key);
		}
	}
	
	public HttpServletRequest getHttpServletRequest() {
		return WebContextHelper.getHttpServletRequest();
	}

	@Override
	public Locale getLocale() {
		HttpServletRequest request = this.getHttpServletRequest();
		return request != null ? localeResolver.resolveLocale(request) : Locale.getDefault();
	}

}
