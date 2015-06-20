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

package org.workin.support.message.formatter;

import java.text.MessageFormat;
import java.util.Map;

import org.workin.commons.util.ClassUtils;
import org.workin.commons.util.RegexUtils;

/**
 * @description 自适应参数对象消息格式化实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AdaptiveMessageFormatter implements MessageFormatter<Object> {
	
	private MessageFormatter<Object> jdkMessageFormatter = new JdkMessageFormatter();
	
	private MessageFormatter<Map<String, Object>> mapMessageFormatter = new MapMessageFormatter();
	
	private MessageFormatter<Object> beanMessageFormatter = new BeanMessageFormatter();
	
	public MessageFormatter<Object> getJdkMessageFormatter() {
		return this.jdkMessageFormatter;
	}

	public MessageFormatter<Map<String, Object>> getMapMessageFormatter() {
		return this.mapMessageFormatter;
	}

	public void setMapMessageFormatter(
			MessageFormatter<Map<String, Object>> mapMessageFormatter) {
		this.mapMessageFormatter = mapMessageFormatter;
	}

	public MessageFormatter<Object> getBeanMessageFormatter() {
		return this.beanMessageFormatter;
	}

	public void setBeanMessageFormatter(
			MessageFormatter<Object> beanMessageFormatter) {
		this.beanMessageFormatter = beanMessageFormatter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String format(String message, Object param) {
		if (ClassUtils.isJavaTypeObject(param)) {
			if (param instanceof Map)
				// 参数为java.util.Map对象时，则用Map参数消息格式化处理器处理
				return this.mapMessageFormatter.format(message, (Map<String, Object>) param);
			else if (RegexUtils.has(message, RegexUtils.regex.get(MessageFormat.class.getName())))
				/* 参数为其余JAVA类型对象，并且消息中包含java.text.MessageFormat能处理的{0}...{9}占位符时，
				 * 则用JDK原生态消息格式化处理器处理 */
				return this.jdkMessageFormatter.format(message, param);
		} 
		
		// 参数为非JAVA类型的参数对象时，则一律用Bean对象消息格式化处理器处理
		return this.beanMessageFormatter.format(message, param);
	}
	
	public static void main(String[] args) {
		
		
	}

}
