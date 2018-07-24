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

import org.sniper.commons.util.AssertUtils;
import org.sniper.templet.message.formatter.AdaptiveMessageFormatter;
import org.sniper.templet.message.formatter.MessageFormatter;
import org.sniper.templet.message.service.MessageService;

/**
 * 模板消息解析器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TempletMessageResolver extends AbstractLocaleMessageResolver {
	
	/** 消息源服务 */
	private MessageService messageService;
		
	/** 消息格式化处理器 */
	private MessageFormatter<Object> messageFormatter;
	
	public TempletMessageResolver() {
		this(null);
	}
	
	public TempletMessageResolver(MessageService messageService) {
		AssertUtils.assertNotNull(messageService, "Parameter 'messageService' is required");
		this.messageService = messageService;
		this.messageFormatter = new AdaptiveMessageFormatter();
	}
	
	public void setMessageService(MessageService messageService) {
		AssertUtils.assertNotNull(messageService, "Parameter 'messageService' is required");
		this.messageService = messageService;
	}

	public MessageService getMessageService() {
		return messageService;
	}
	
	public void setMessageFormatter(MessageFormatter<Object> messageFormatter) {
		AssertUtils.assertNotNull(messageFormatter, "Parameter 'messageFormatter' is required");
		this.messageFormatter = messageFormatter;
	}

	public MessageFormatter<Object> getMessageFormatter() {
		return messageFormatter;
	}
	
	@Override
	public String getMessage(String key, Locale locale, Object[] params, String defaultMessage) {
		String message = this.messageService.getMessageByKey(key, locale);
		return message != null ? this.messageFormatter.format(message, params) : defaultMessage;
	}
	
}
