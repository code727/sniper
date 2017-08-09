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
 * Create Date : 2015-7-8
 */

package org.sniper.http.form;

import java.util.Map;
import java.util.Map.Entry;

import org.sniper.commons.util.MapUtils;

/**
 * 默认HTTP表单转换器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultHttpFormConverter implements HttpFormConverter {
	
	/** 表单处理器 */
	private FormHandler formHandler;
	
	public DefaultHttpFormConverter() {
		this(null);
	}
	
	public DefaultHttpFormConverter(FormHandler formHandler) {
		this.formHandler = (formHandler != null ? formHandler : new DefaultFormHandler());
	}

	@Override
	public Map<String, String> convert(Map<String, HttpForm> formMap) {
		if (MapUtils.isEmpty(formMap))
			return null;
		
		Map<String, String> map = MapUtils.newHashMap();
		String name;
		for (Entry<String, HttpForm> entry : formMap.entrySet()) {
			name = entry.getKey();
			map.put(name, formHandler.handle(name, entry.getValue()));
		}
		
		return map;
	}
		
}
