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

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 基于Spring Web应用的消息解析器实现类
 * @author  Daniele
 * @version 1.0
 */
@Configuration
public class SpringWebMessageReslover extends AbstractSpringWebMessageReslover {
		
	@Autowired(required = false)
	private MessageSource messageSource;
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		AssertUtils.assertNotNull(messageSource, "Property 'messageSource' is required");
		this.messageSource = messageSource;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if(this.messageSource == null) {
			ResourceBundleMessageSource source = new ResourceBundleMessageSource();
			source.setBasename(MessageUtils.DEFAULT_I18N_BASENAME);
			this.messageSource = source;
		}
	}
		
	@Override
	public String getMessage(String key, Object param, String defaultMessage) {
		return getMessage(key, new Object[] { param }, defaultMessage);
	}

	@Override
	public String getMessage(String key, Object[] params, String defaultMessage) {
		Locale locale = this.getLocale();
		try {
			return messageSource.getMessage(key, params, locale);
		} catch (NoSuchMessageException e) {
			// 未获取到消息时，将默认值作为消息返回
			return defaultMessage;
		}
	}

	@Override
	public String getMessage(Class<?> clazz, String key, Object param, String defaultMessage) {
		return getMessage(clazz, key, new Object[] { param }, defaultMessage);
	}

	@Override
	public String getMessage(Class<?> clazz, String key, Object[] params, String defaultMessage) {
		Locale locale = this.getLocale();
		String message = MessageUtils.getPackageMessage(clazz, locale , key, params, null);
		return message != null ? message : MessageUtils.getClassMessage(clazz, locale, key, params, defaultMessage);
	}
	
}
