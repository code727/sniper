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
 * Create Date : 2015-6-20
 */

package org.sniper.templet.message.formatter;

import java.text.MessageFormat;
import java.util.Map;

import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.RegexUtils;

/**
 * 自适应参数对象消息格式化实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AdaptiveMessageFormatter extends
		PlaceholderMessageFormatter<Object> implements MessageFormatter<Object> {
	
	private MessageFormatter<Object> jdkMessageFormatter = new JdkMessageFormatter();
	
	private MessageFormatter<Map<String, Object>> mapMessageFormatter = new MapMessageFormatter<Object>();
	
	private MessageFormatter<Object> beanMessageFormatter = new BeanMessageFormatter();
		
	public MessageFormatter<Object> getJdkMessageFormatter() {
		return this.jdkMessageFormatter;
	}

	public MessageFormatter<Map<String, Object>> getMapMessageFormatter() {
		return this.mapMessageFormatter;
	}

	public void setMapMessageFormatter(MessageFormatter<Map<String, Object>> mapMessageFormatter) {
		if (this.mapMessageFormatter != null)
			this.mapMessageFormatter = mapMessageFormatter;
	}

	public MessageFormatter<Object> getBeanMessageFormatter() {
		return this.beanMessageFormatter;
	}

	public void setBeanMessageFormatter(MessageFormatter<Object> beanMessageFormatter) {
		if (this.beanMessageFormatter != null)
			this.beanMessageFormatter = beanMessageFormatter;
	}
	
	@Override
	public void setPrefix(String prefix) {
		
		if (this.mapMessageFormatter instanceof PlaceholderMessageFormatter)
			((PlaceholderMessageFormatter<Map<String, Object>>) this.mapMessageFormatter).setPrefix(prefix);
		
		if (this.beanMessageFormatter instanceof PlaceholderMessageFormatter)
			((PlaceholderMessageFormatter<Object>) this.beanMessageFormatter).setPrefix(prefix);
		
		super.setPrefix(prefix);
	}
	
	@Override
	public void setSuffix(String suffix) {
		
		if (this.mapMessageFormatter instanceof PlaceholderMessageFormatter)
			((PlaceholderMessageFormatter<Map<String, Object>>) this.mapMessageFormatter).setSuffix(suffix);
		
		if (this.beanMessageFormatter instanceof PlaceholderMessageFormatter)
			((PlaceholderMessageFormatter<Object>) this.beanMessageFormatter).setSuffix(suffix);
		
		super.setSuffix(suffix);
	}


	@SuppressWarnings("unchecked")
	@Override
	public String format(String message, Object param) {
		if (ClassUtils.isJavaType(param)) {
			if (param instanceof Map)
				// 参数为java.util.Map对象时，则用Map参数消息格式化处理器处理
				return this.mapMessageFormatter.format(message, (Map<String, Object>) param);
			
			if (RegexUtils.has(message, RegexUtils.REGEX.get(MessageFormat.class.getName())))
				/* 参数为其余JAVA类型对象，并且消息中包含java.text.MessageFormat能处理的{0}...{9}占位符时，
				 * 则用JDK原生态消息格式化处理器处理 */
				return this.jdkMessageFormatter.format(message, param);
		} else {
			/* 参数为自定义类型对象，并且消息中包含java.text.MessageFormat能处理的{0}...{9}占位符时，
			 * 则同样用JDK原生态消息格式化处理器处理 */
			if (RegexUtils.has(message, RegexUtils.REGEX.get(MessageFormat.class.getName())))
				return this.jdkMessageFormatter.format(message, param);
		}
		
		// 剩余情况则一律用Bean对象消息格式化处理器处理
		return this.beanMessageFormatter.format(message, param);
	}
	
}
