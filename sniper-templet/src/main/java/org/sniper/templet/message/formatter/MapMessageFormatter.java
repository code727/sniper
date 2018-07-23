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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;

/**
 * Map参数消息格式化处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapMessageFormatter<V> extends PlaceholderMessageFormatter<Map<String, V>> {
	
	@Override
	public String format(String message, Map<String, V> param) {
		if (MapUtils.isNotEmpty(param)) {
			Iterator<Entry<String, V>> iterator = param.entrySet().iterator();
			StringBuilder mark = new StringBuilder();
			while (iterator.hasNext()) {
				Entry<String, V> nameValuePair = iterator.next();
				mark.setLength(0);
				mark.append(this.getPrefix()).append(nameValuePair.getKey()).append(this.getSuffix());
				message = StringUtils.replaceAll(message, mark.toString(), StringUtils.toString(nameValuePair.getValue())); 
			}
		}
		
		return message;
	}
	
}
