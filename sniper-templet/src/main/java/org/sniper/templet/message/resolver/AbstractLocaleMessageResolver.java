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
 * Create Date : 2016-7-31
 */

package org.sniper.templet.message.resolver;

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

/**
 * 本地化消息解析器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractLocaleMessageResolver extends
		AbstractMessageResolver implements LocaleMessageResolver {
	
	/** 消息源 */
	private MessageSource messageSource;
		
	/** 消息格式化处理器 */
	private MessageFormatter<Object> messageFormatter;
	
	protected AbstractLocaleMessageResolver() {
		this(null);
	}
	
	protected AbstractLocaleMessageResolver(MessageSource messageSource) {
		this.messageSource = (messageSource != null ? messageSource : new ResourceBundleMessageSource());
		this.messageFormatter = new JdkMessageFormatter();
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		AssertUtils.assertNotNull(messageSource, "Property 'messageSource' is required");
		this.messageSource = messageSource;
	}

	public void setMessageFormatter(MessageFormatter<Object> messageFormatter) {
		AssertUtils.assertNotNull(messageFormatter, "Property 'messageFormatter' is required");
		this.messageFormatter = messageFormatter;
	}

	public MessageFormatter<Object> getMessageFormatter() {
		return messageFormatter;
	}
	
	@Override
	public String getMessage(String key, Locale locale) {
		return getMessage(key, locale, key);
	}

	@Override
	public String getMessage(String key, Locale locale, String defaultMessage) {
		return getMessage(key, locale, null, defaultMessage);
	}

	@Override
	public String getMessage(String key, Locale locale, Object param) {
		return getMessage(key, locale, param, key);
	}

	@Override
	public String getMessage(String key, Locale locale, Object[] params) {
		return getMessage(key, locale, params, key);
	}
	
	@Override
	public String getMessage(String key, Object[] params, String defaultMessage) {
		return getMessage(key, null, params, defaultMessage);
	}
	
	@Override
	public String getMessage(String key, Object param, String defaultMessage) {
		return getMessage(key, null, param, defaultMessage);
	}
	
	@Override
	public String getMessage(String key, Locale locale, Object param, String defaultMessage) {
		return getLocaleMessage(key, locale, param, defaultMessage);
	}
	
	@Override
	public String getMessage(String key, Locale locale, Object[] params, String defaultMessage) {
		return getLocaleMessage(key, locale, params, defaultMessage);
	}
	
	/**
	 * 获取本地化消息:</P>
	 * 1.首先从自定义的消息源中获取消息，未获取到时进入第2步，否则进入第4步；</P>
	 * 2.再从与当前类所属包同名的配置文件中获取，未获取到时进入第3步，否则进入第4步；</P>
	 * 3.再从与当前类同名的配置文件中获取；</P>
	 * 4.统一判断消息是否为空，不为空时进行格式化，否则返回指定的默认值。
	 * @author Daniele 
	 * @param key
	 * @param locale
	 * @param param
	 * @param defaultMessage
	 * @return
	 */
	protected String getLocaleMessage(String key, Locale locale, Object param, String defaultMessage) {
		// 首先从自定义的消息源中获取消息
		String message = this.messageSource.getMessageByKey(key, locale);
		if (message == null) {
			// 如果返回的消息为空，说明没有获取到消息，则再从与当前类所属包同名的配置文件中获取
			message = getPackageMessage(key, locale);
			// 同理，如果返回的消息再次为空，则再从与当前类同名的配置文件中获取
			if (message == null) {
				message = getClassMessage(key, locale);
			}
		}
		
		// 统一判断消息是否为空，不为空时进行格式化，否则返回指定的默认值
		return message != null ? this.messageFormatter.format(message, param) : defaultMessage;
	}
	
	/**
	 * 从与当前类所属包同名的配置文件中获取
	 * @author Daniele 
	 * @param key
	 * @param locale
	 * @return
	 */
	private String getPackageMessage(String key, Locale locale) {
		try {
			ResourceBundle bundle = MessageUtils.getResourceBundle(ClassUtils.getPackageBaseName(this.getClass()), locale);
			return bundle.getString(StringUtils.safeString(key));
		} catch (MissingResourceException e) {
			return null;
		}
	}
	
	/**
	 * 从与当前类同名的配置文件中获取
	 * @author Daniele 
	 * @param key
	 * @param locale
	 * @return
	 */
	private String getClassMessage(String key, Locale locale) {
		try {
			ResourceBundle bundle = MessageUtils.getResourceBundle(ClassUtils.getClassBaseName(this.getClass()), locale);
			return bundle.getString(StringUtils.safeString(key));
		} catch (MissingResourceException e) {
			return null;
		}
	}

}
