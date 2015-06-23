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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.workin.commons.util.MessageUtils;
import org.workin.commons.util.StringUtils;
import org.workin.spring.beans.IntegerPropertyEditor;
import org.workin.support.message.resolver.MessageResolver;
import org.workin.support.model.MessageModel;
import org.workin.web.ServletAware;
import org.workin.web.WebAppContextMessageResolver;

/**
 * @description SpringMVC控制器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class ControllerSupport implements MessageResolver, ServletAware {
		
	@Autowired
	private WebAppContextMessageResolver messageResolver;
	
	public WebAppContextMessageResolver getMessageResolver() {
		return messageResolver;
	}

	public void setMessageResolver(WebAppContextMessageResolver messageResolver) {
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
			return message = messageResolver.getMessage(key, params, defaultMessage);
		} catch (NoSuchMessageException e) {
			// 获取不到时，从与当前包同名的配置文件中获取
			message = MessageUtils.getPackageMessage(this.getClass(), locale, key, params, null);
		}
		// 仍然获取不到时，最后从与当前类同名的配置文件中获取
		return message != null ? message : MessageUtils
				.getClassMessage(this.getClass(), locale, key, null, key);
	}
	
	/**
	 * @description 设置消息模型对象中的本地化消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param result
	 */
	protected <T> void setLocalMessage(MessageModel model) {
		String message = model.getMessage();
		if (StringUtils.isNotBlank(message)) 
			model.setMessage(getMessage(message));
	}
		
	/**
	 * @description 在绑定表单之前，先注册列表中的若干属性编辑器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder  
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		IntegerPropertyEditor integerPropertyEditor = new IntegerPropertyEditor();
		integerPropertyEditor.setAllowEmpty(false);
		integerPropertyEditor.setDefaultValue("1");
		binder.registerCustomEditor(Integer.class, new IntegerPropertyEditor());
//		if (MapUtils.isNotEmpty(this.propertyEdities)) {
//			Iterator<Entry<String, PropertyEditor>> pes = this.propertyEdities.entrySet().iterator();
//			while (pes.hasNext()) {
//				Entry<String, PropertyEditor> pe = pes.next();
//				String classStr = pe.getKey();
//				if (StringUtils.isNotBlank(classStr)) {
//					binder.registerCustomEditor(Class.forName(classStr.trim()), pe.getValue());
//				}
//			}
//		}
    } 
		
}
