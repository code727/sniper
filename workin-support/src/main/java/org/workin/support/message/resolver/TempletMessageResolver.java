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

package org.workin.support.message.resolver;

import org.workin.support.message.MessageService;
import org.workin.support.message.formatter.AdaptiveMessageFormatter;
import org.workin.support.message.formatter.MessageFormatter;

/**
 * @description 模板消息解析器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TempletMessageResolver implements MessageResolver {
	
	/** 消息源服务 */
	private MessageService messageService;
		
	/** 消息格式化处理器 */
	private MessageFormatter<Object> messageFormatter = new AdaptiveMessageFormatter();
	
	public TempletMessageResolver(MessageService messageService) {
		if (messageService == null)
			throw new IllegalArgumentException("Parameter 'messageService' is required.");
		
		this.messageService = messageService;
	}
	
	public MessageService getMessageService() {
		return messageService;
	}
	
	public void setMessageFormatter(MessageFormatter<Object> messageFormatter) {
		if (messageFormatter != null)
			this.messageFormatter = messageFormatter;
	}

	public MessageFormatter<Object> getMessageFormatter() {
		return messageFormatter;
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
		String message = this.messageService.getMessageByKey(key);
		return message != null ? this.messageFormatter.format(message, params)  : defaultMessage;
	}

}
