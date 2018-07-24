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
 * Create Date : 2015-6-18
 */

package org.sniper.templet.message.resolver;

import java.util.Locale;

import org.sniper.templet.message.service.MessageSource;

/**
 * 模板消息解析器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TempletMessageResolver extends AbstractLocaleMessageResolver {
	
	public TempletMessageResolver() {
		super();
	}
		
	public TempletMessageResolver(MessageSource messageSource) {
		super(messageSource);
	}
	
	/**
	 * 重写父类方法，只从消息源中获取消息，未获取到时返回指定的默认消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param locale
	 * @param param
	 * @param defaultMessage
	 * @return
	 */
	@Override
	protected String getLocaleMessage(String key, Locale locale, Object param, String defaultMessage) {
		String message = getMessageSource().getMessageByKey(key, locale);
		return message != null ? getMessageFormatter().format(message, param) : defaultMessage;
	}

}
