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
 * Create Date : 2015-6-11
 */

package org.workin.web.springmvc;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.workin.commons.util.MessageUtils;
import org.workin.support.context.ApplicationContextHolder;
import org.workin.web.LocaleMessageResolver;
import org.workin.web.ServletAware;
import org.workin.web.springmvc.invocation.ServletSetterInvocation;

/**
 * @description SpringMVC控制器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ControllerSupport implements LocaleMessageResolver, ServletAware {
	
	private static Logger logger = LoggerFactory.getLogger(ControllerSupport.class);
	
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

	/**
	 * @description 获取HttpServletRequest对象。
	 * 				需要配置org.springframework.web.context.request.RequestContextListener过滤器或
	 * 				org.workin.web.springmvc.invocation.ServletSetterInvocation拦截器后才能获取到不为空的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	@Override
	public HttpServletRequest getHttpServletRequest() {
		ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
		HttpServletRequest request = null;
		if (requestAttributes == null) {
			request = (HttpServletRequest) ApplicationContextHolder
					.getAttribute(ServletSetterInvocation.HTTP_SERVLET_REQUEST_NAME);
			if (request == null) 
				logger.warn("HttpServletRequest is null,please configure ["
						+ RequestContextListener.class + "] in web.xml or configure ["
							+ ServletSetterInvocation.class + "] in SpringMVC");
		} else 
			request = requestAttributes.getRequest();
		return request;
	}

	/**
	 * @description 获取HttpServletResponse对象。
	 * 				需要配置org.workin.web.springmvc.invocation.ServletSetterInvocation拦截器后才能获取到不为空的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	@Override
	public HttpServletResponse getHttpServletResponse() {
		HttpServletResponse response = (HttpServletResponse) ApplicationContextHolder
				.getAttribute(ServletSetterInvocation.HTTP_SERVLET_RESPONSE_NAME);
		
		if (response == null)
			logger.warn("HttpServletResponse is null,please configure ["
					+ ServletSetterInvocation.class + "] in SpringMVC");
		return response;
	}

	@Override
	public HttpSession getHttpSession() {
		return this.getHttpSession(false);
	}
	
	@Override
	public HttpSession getHttpSession(boolean create) {
		HttpServletRequest request = this.getHttpServletRequest();
		return request != null ? request.getSession(create) : null;
	}

	@Override
	public Locale getLocale() {
		HttpServletRequest request = this.getHttpServletRequest();
		return request != null ? localeResolver.resolveLocale(request) : Locale.getDefault();
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
		Locale locale = this.getLocale();
		String message = key; 
		try {
			// 首先从SpringMVC全局配置文件中获取消息
			return message = messageSource.getMessage(key, params, locale);
		} catch (NoSuchMessageException e) {
			// 获取不到时，从与当前包同名的配置文件中获取
			message = MessageUtils.getPackageMessage(this.getClass(), locale, key, params, null);
		}
		// 仍然获取不到时，最后从与当前类同名的配置文件中获取
		return message != null ? message : MessageUtils
				.getClassMessage(this.getClass(), locale, key, null, key);
	}

}
