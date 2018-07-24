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
 * Create Date : 2015-6-19
 */

package org.sniper.templet.message.formatter;

import java.text.MessageFormat;
import java.util.Collection;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.CollectionUtils;

/**
 * JDK原生消息格式化处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JdkMessageFormatter implements MessageFormatter<Object> {
	
	@Override
	public String format(String message, Object param) {
		if (ArrayUtils.isArray(param)) {
			return MessageFormat.format(message, (Object[]) param);
		}
		
		if (CollectionUtils.isCollection(param)) {
			return MessageFormat.format(message, CollectionUtils.toObjectArray((Collection<?>) param));
		}
		
		return param != null ? MessageFormat.format(message, param) : message;
	}
		
}
