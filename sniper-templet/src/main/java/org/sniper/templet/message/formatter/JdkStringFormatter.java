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
 * Create Date : 2018-12-12
 */

package org.sniper.templet.message.formatter;

import java.util.Collection;

import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.RegexUtils;

/**
 * JDK原生字符串格式化处理器实现类
 * @author  Daniele
 * @version 1.0
 */
public class JdkStringFormatter implements MessageFormatter<Object> {

	@Override
	public boolean support(String message, Object param) {
		return RegexUtils.hasStringFormatPlaceholder(message);
	}

	@Override
	public String format(String message, Object param) {
		if (ClassUtils.isArray(param)) 
			return String.format(message, (Object[]) param);
		
		if (CollectionUtils.isCollection(param)) 
			return String.format(message, CollectionUtils.toObjectArray((Collection<?>) param));
		
		return param != null ? String.format(message, param) : message;
	}

}
