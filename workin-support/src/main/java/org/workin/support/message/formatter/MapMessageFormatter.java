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

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;
import org.workin.support.encoder.StringEncoder;

/**
 * @description Map参数消息格式化处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapMessageFormatter extends PlaceholderMessageFormatter<Map<String, Object>> {
	
	@Override
	public String format(String message, Map<String, Object> param, String encoding) throws UnsupportedEncodingException {
		StringEncoder encoder = super.getEncoder();
		if (MapUtils.isNotEmpty(param)) {
			Iterator<Entry<String, Object>> iterator = param.entrySet().iterator();
			StringBuffer mark = new StringBuffer();
			if (encoder != null && StringUtils.isNotBlank(encoding)) {
				while (iterator.hasNext()) {
					Entry<String, Object> nameValuePair = iterator.next();
					mark.setLength(0);
					mark.append(this.getPrefix()).append(nameValuePair.getKey()).append(this.getSuffix());
					message = StringUtils.replace(message, mark.toString(), encoder.encode(StringUtils.toString(nameValuePair.getValue()), encoding)); 
				}
			} else {
				while (iterator.hasNext()) {
					Entry<String, Object> nameValuePair = iterator.next();
					mark.setLength(0);
					mark.append(this.getPrefix()).append(nameValuePair.getKey()).append(this.getSuffix());
					message = StringUtils.replace(message, mark.toString(), StringUtils.toString(nameValuePair.getValue())); 
				}
			}
		}
		return message;
	}

}
