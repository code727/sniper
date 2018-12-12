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

import java.util.Set;

import org.sniper.beans.BeanReflector;
import org.sniper.beans.DefaultBeanReflector;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;

/**
 * Java Bean对象消息格式化处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BeanMessageFormatter extends PlaceholderMessageFormatter<Object> {
	
	private final BeanReflector beanReflector = new DefaultBeanReflector();
		
	@Override
	public boolean support(String message, Object param) {
		return true;
	}
			
	@Override
	public String format(String message, Object bean) {
		if (bean != null) {
			// 获取需要被替换的标记子串
			Set<String> markSet = CollectionUtils.newHashSet(StringUtils.leftSubstringAll(message, this.getPrefix(), this.getSuffix()));
			if (CollectionUtils.isNotEmpty(markSet)) {
				StringBuilder expression = new StringBuilder();
				for (String mark : markSet) {
					expression.setLength(0);
					expression.append(this.getPrefix()).append(mark).append(this.getSuffix());
					try {
						message = StringUtils.replaceAll(message, expression.toString(),StringUtils.toString(this.beanReflector.get(bean, mark))); 
					} catch (Exception e) {} // 忽略异常继续处理
				}
			}
		}
		
		return message;
	}

}
