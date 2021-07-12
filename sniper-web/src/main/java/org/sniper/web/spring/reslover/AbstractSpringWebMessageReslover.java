/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-7-25
 */

package org.sniper.web.spring.reslover;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.sniper.commons.util.AssertUtils;
import org.sniper.web.AbstractWebMessageResolver;
import org.sniper.web.WebMessageResolver;
import org.sniper.web.spring.WebContextHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Spring Web应用消息解析器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractSpringWebMessageReslover extends AbstractWebMessageResolver
		implements WebMessageResolver, InitializingBean {
	
	@Autowired(required = false)
	private LocaleResolver localeResolver;
	
	public LocaleResolver getLocaleResolver() {
		return localeResolver;
	}

	public void setLocaleResolver(LocaleResolver localeResolver) {
		AssertUtils.assertNotNull(this.localeResolver, "Property 'localeResolver' is required");
		this.localeResolver = localeResolver;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.localeResolver == null) {
			localeResolver = new SessionLocaleResolver();
		}
	}
	
	@Override
	public HttpServletRequest getHttpServletRequest() {
		return WebContextHelper.getHttpServletRequest();
	}

	@Override
	public Locale getLocale() {
		HttpServletRequest request = getHttpServletRequest();
		return request != null ? localeResolver.resolveLocale(request) : Locale.getDefault();
	}

}
