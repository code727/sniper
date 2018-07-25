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
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.MessageUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.templet.message.formatter.JdkMessageFormatter;
import org.sniper.templet.message.formatter.MessageFormatter;
import org.sniper.templet.message.source.MessageSource;
import org.sniper.templet.message.source.ResourceBundleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义的Spring Web应用消息解析器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CustomizeSpringWebMessageReslover extends AbstractSpringWebMessageReslover {
	
	/** 消息源 */
	@Autowired(required = false)
	private MessageSource messageSource;
		
	/** 消息格式化处理器 */
	@Autowired(required = false)
	private MessageFormatter<Object> messageFormatter;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.messageSource == null) {
			this.messageSource = new ResourceBundleMessageSource();
		}
		
		if (this.messageFormatter == null) {
			this.messageFormatter = new JdkMessageFormatter();
		}
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		AssertUtils.assertNotNull(messageSource, "Property 'messageSource' is required");
		this.messageSource = messageSource;
	}

	public MessageFormatter<Object> getMessageFormatter() {
		return messageFormatter;
	}

	public void setMessageFormatter(MessageFormatter<Object> messageFormatter) {
		AssertUtils.assertNotNull(messageFormatter, "Property 'messageFormatter' is required");
		this.messageFormatter = messageFormatter;
	}

	@Override
	public String getMessage(String key, Object param, String defaultMessage) {
		String message = this.messageSource.getMessageByKey(key, getLocale());
		return message != null ? this.messageFormatter.format(message, param) : defaultMessage;
	}

	@Override
	public String getMessage(String key, Object[] params, String defaultMessage) {
		String message = this.messageSource.getMessageByKey(key, getLocale());
		return message != null ? this.messageFormatter.format(message, params) : defaultMessage;
	}

	@Override
	public String getMessage(Class<?> clazz, String key, Object param, String defaultMessage) {
		Locale locale = getLocale();
		String message = getPackageMessage(clazz, key, locale);
		if (message == null) {
			message = getClassMessage(clazz, key, locale);
		}
		return message != null ? this.messageFormatter.format(message, param) : defaultMessage;
	}

	@Override
	public String getMessage(Class<?> clazz, String key, Object[] params, String defaultMessage) {
		Locale locale = getLocale();
		String message = getPackageMessage(clazz, key, locale);
		if (message == null) {
			message = getClassMessage(clazz, key, locale);
		}
		return message != null ? this.messageFormatter.format(message, params) : defaultMessage;
	}
	
	/**
	 * 从与指定类所属包同名的配置文件中获取
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param locale
	 * @return
	 */
	private String getPackageMessage(Class<?> clazz, String key, Locale locale) {
		try {
			ResourceBundle bundle = MessageUtils.getResourceBundle(ClassUtils.getPackageBaseName(this.getClass()), locale);
			return bundle.getString(StringUtils.safeString(key));
		} catch (MissingResourceException e) {
			return null;
		}
	}
	
	/**
	 * 从与指定类同名的配置文件中获取
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param locale
	 * @return
	 */
	private String getClassMessage(Class<?> clazz, String key, Locale locale) {
		try {
			ResourceBundle bundle = MessageUtils.getResourceBundle(ClassUtils.getClassBaseName(this.getClass()), locale);
			return bundle.getString(StringUtils.safeString(key));
		} catch (MissingResourceException e) {
			return null;
		}
	}

}
