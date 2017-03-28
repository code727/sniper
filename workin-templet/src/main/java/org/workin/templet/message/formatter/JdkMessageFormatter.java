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

package org.workin.templet.message.formatter;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Collection;

import org.workin.codec.encoder.StringEncoder;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.StringUtils;

/**
 * JDK原生态消息格式化处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JdkMessageFormatter extends EncodeableMessageFormatter<Object> {
	
	@Override
	public String format(String message, Object param, String encoding) throws UnsupportedEncodingException {
		StringEncoder enc = this.getEncoder();
		if (enc != null && StringUtils.isNotBlank(encoding)) {
			if (ArrayUtils.isArray(param)) {
				Object[] params = (Object[]) param;
				this.encode(params, encoding);
				return MessageFormat.format(message, params);
			} else if (CollectionUtils.isCollection(param)) {
				return format(message, CollectionUtils.toObjectArray((Collection<?>) param), encoding);
			} else
				return param != null ? MessageFormat.format(message, 
						enc.encode(param.toString(), encoding)) : message;
		} else {
			if (ArrayUtils.isArray(param)) {
				return MessageFormat.format(message, (Object[]) param);
			} else if (CollectionUtils.isCollection(param)) {
				return format(message, CollectionUtils.toObjectArray((Collection<?>) param));
			} else
				return param != null ? MessageFormat.format(message, param) : message;
		}
	}
	
	/**
	 * 将数组中的元素重新编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected void encode(Object[] array, String encoding) throws UnsupportedEncodingException {
		StringEncoder enc = this.getEncoder();
		for (int i = 0; i < array.length; i++)
			array[i] = enc.encode(StringUtils.toString(array[i]), encoding);
	}
	
}
