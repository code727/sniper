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

package org.workin.web.spring;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.workin.commons.model.CodeModel;
import org.workin.commons.model.MessageModel;
import org.workin.commons.util.DateUtils;
import org.workin.commons.util.MessageUtils;
import org.workin.commons.util.ObjectUtils;
import org.workin.commons.util.StringUtils;
import org.workin.spring.beans.propertyeditors.DatePropertyEditor;
import org.workin.spring.beans.propertyeditors.StringBufferPropertyEditor;
import org.workin.spring.beans.propertyeditors.StringBuilderPropertyEditor;
import org.workin.templet.message.resolver.MessageResolver;
import org.workin.web.ServletAware;
import org.workin.web.WebApplicationContextMessageResolver;

/**
 * SpringMVC控制器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public abstract class ControllerSupport implements MessageResolver, ServletAware {
		
	@Autowired
	private WebApplicationContextMessageResolver messageResolver;
			
	public WebApplicationContextMessageResolver getMessageResolver() {
		return messageResolver;
	}

	public void setMessageResolver(WebApplicationContextMessageResolver messageResolver) {
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
		String message = messageResolver.getMessage(key, params, defaultMessage); 
		if (ObjectUtils.equals(message, defaultMessage)) {
			// 如果返回的消息与指定的默认值一致，说明没有到真正的消息，则再从与当前包同名的配置文件中获取
			message = MessageUtils.getPackageMessage(this.getClass(), locale, key, params, defaultMessage);
		}
		
		// 同理，如果返回的消息与指定的默认值再次一致，则再从与当前包同名的配置文件中获取
		return !ObjectUtils.equals(message, defaultMessage) ? message : MessageUtils
				.getClassMessage(this.getClass(), locale, key, params, defaultMessage);
	}
	
	/**
	 * 设置消息模型对象中的本地化消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param result
	 */
	protected void setLocaleMessage(MessageModel model) {
		setLocaleMessage(model, null);
	}
	
	/**
	 * 设置消息模型对象中的本地参数化消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param model
	 * @param params
	 */
	protected void setLocaleMessage(MessageModel model, Object[] params) {
		String message = model.getMessage();
		if (StringUtils.isNotBlank(message)) 
			model.setMessage(getMessage(message, params));
	}
	
	/**
	 * 设置可编码的对象模型中的本地消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param model
	 */
	protected void setLocaleMessage(CodeModel model) {
		setLocaleMessage(model, null);
	}
	
	/**
	 * 设置可编码的对象模型中的本地参数化消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param model
	 * @param params
	 */
	protected void setLocaleMessage(CodeModel model, Object[] params) {
		if (model instanceof MessageModel)
			setLocaleMessage((MessageModel) model, params);
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
