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

import java.util.List;

import org.sniper.commons.util.CollectionUtils;

/**
 * 自适应参数对象消息格式化实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class AdaptiveMessageFormatter extends PlaceholderMessageFormatter<Object> implements MessageFormatter<Object> {
	
	protected final List<MessageFormatter> members;
	
	public AdaptiveMessageFormatter() {
		this(null);
	}
	
	public AdaptiveMessageFormatter(List<MessageFormatter> members) {
		if (CollectionUtils.isNotEmpty(members))
			this.members = members;
		else {
			this.members = CollectionUtils.newArrayList(4);
			this.members.add(new MapMessageFormatter<Object>());
			this.members.add(new JdkMessageFormatter());
			this.members.add(new JdkStringFormatter());
			this.members.add(new BeanMessageFormatter());
		}
	}
	
	@Override
	public void setPrefix(String prefix) {
		for (MessageFormatter member : this.members) {
			if (member instanceof PlaceholderMessageFormatter) {
				((PlaceholderMessageFormatter<?>) member).setPrefix(prefix);
			}
		}

		super.setPrefix(prefix);
	}
	
	@Override
	public void setSuffix(String suffix) {
		for (MessageFormatter member : this.members) {
			if (member instanceof PlaceholderMessageFormatter) {
				((PlaceholderMessageFormatter) member).setSuffix(suffix);
			}
		}

		super.setSuffix(suffix);
	}
	
	@Override
	public boolean support(String message, Object param) {
		for (MessageFormatter member : this.members) {
			if (member.support(message, param))
				return true;
		}
		return false;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String format(String message, Object param) {
		for (MessageFormatter member : this.members) {
			if (member.support(message, param))
				return member.format(message, param);
		}
		
		return message;
	}
	
}
