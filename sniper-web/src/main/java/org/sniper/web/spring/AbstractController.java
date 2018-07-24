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

package org.sniper.web.spring;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sniper.beans.propertyeditors.DatePropertyEditor;
import org.sniper.beans.propertyeditors.StringBufferPropertyEditor;
import org.sniper.beans.propertyeditors.StringBuilderPropertyEditor;
import org.sniper.commons.response.GenericResponse;
import org.sniper.commons.response.MessagingResponse;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.MessageUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.templet.message.resolver.MessageResolver;
import org.sniper.web.ServletAware;
import org.sniper.web.WebMessageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * SpringMVC控制器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractController implements MessageResolver, ServletAware {
		
	@Autowired
	private WebMessageResolver messageResolver;
			
	public WebMessageResolver getMessageResolver() {
		return messageResolver;
	}

	public void setMessageResolver(WebMessageResolver messageResolver) {
		this.messageResolver = messageResolver;
	}

	public Locale getLocale() {
		return this.messageResolver.getLocale();
	}
	
	@Override
	public HttpServletRequest getHttpServletRequest() {
		return this.messageResolver.getHttpServletRequest();
	}

	@Override
	public HttpServletResponse getHttpServletResponse() {
		return WebContextHelper.getHttpServletResponse();
	}

	@Override
	public HttpSession getHttpSession() {
		return this.getHttpSession(false);
	}
	
	@Override
	public HttpSession getHttpSession(boolean create) {
		return WebContextHelper.getHttpSession(create);
	}

	@Override
	public String getMessage(String key) {
		return getMessage(key, null, key);
	}
	
	@Override
	public String getMessage(String key, String defaultMessage) {
		return getMessage(key, null, defaultMessage);
	}
	
	@Override
	public String getMessage(String key, Object param) {
		return getMessage(key, param, key);
	}
	
	@Override
	public String getMessage(String key, Object[] params) {
		return getMessage(key, params, key);
	}

	@Override
	public String getMessage(String key, Object param, String defaultMessage) {
		return getMessage(key, new Object[] { param }, defaultMessage);
	}

	@Override
	public String getMessage(String key, Object[] params, String defaultMessage) {
		Locale locale = this.getLocale();
		// 首先从SpringMVC全局配置文件中获取消息
		String message = this.messageResolver.getMessage(key, params, null); 
		if (message == null) {
			// 如果返回的消息为空，说明没有获取到真正的消息，则再从与当前包同名的配置文件中获取
			message = MessageUtils.getPackageMessage(this.getClass(), locale, key, params, null);
		}
		
		// 同理，如果返回的消息再次为空，则再从与当前类同名的配置文件中获取。否则，直接返回最终结果
		return message != null ? message : MessageUtils.getClassMessage(
				this.getClass(), locale, key, params, defaultMessage);
	}
	
	/**
	 * 设置消息响应对象中的本地化消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param response
	 */
	protected void setLocaleMessage(MessagingResponse<?> response) {
		setLocaleMessage(response, null);
	}
	
	/**
	 * 设置消息响应对象中的本地参数化消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param response
	 * @param params
	 */
	protected void setLocaleMessage(MessagingResponse<?> response, Object[] params) {
		String message = response.getMessage();
		if (StringUtils.isNotBlank(message)) 
			response.setMessage(getMessage(message, params));
	}
	
	/**
	 * 设置响应对象中的本地消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param response
	 */
	protected void setLocaleMessage(GenericResponse<?> response) {
		setLocaleMessage(response, null);
	}
	
	/**
	 * 设置响应对象中的本地参数化消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param response
	 * @param params
	 */
	protected void setLocaleMessage(GenericResponse<?> response, Object[] params) {
		if (response instanceof MessagingResponse)
			setLocaleMessage((MessagingResponse<?>) response, params);
	}
	
	/**
	 * 在绑定表单之前，统一的进行初始化绑定操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder  
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor(DateUtils.DEFAULT_DATETIME_FORMAT));
		binder.registerCustomEditor(StringBuffer.class, new StringBufferPropertyEditor());
		binder.registerCustomEditor(StringBuilder.class, new StringBuilderPropertyEditor());
		overrideInitBinder(request, binder);
    } 
	
	/**
	 * 覆盖默认的初始化绑定操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param binder
	 */
	protected void overrideInitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {}
	
}
