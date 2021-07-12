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

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 默认HTTP表单转换器实现类
 * @author  Daniele
 * @version 1.0
 */
public class DefaultHttpFormConverter implements HttpFormConverter {
		
	@Override
	public Map<String, String> convert(Map<String, HttpForm> formMap) {
		if (MapUtils.isEmpty(formMap))
			return null;
		
		Map<String, String> map = MapUtils.newLinkedHashMap();
		String name;
		for (Entry<String, HttpForm> entry : formMap.entrySet()) {
			name = entry.getKey();
			map.put(name, convert(name, entry.getValue()));
		}
		
		return map;
	}
	
	@Override
	public String convert(String name, HttpForm form) {
		StringBuilder url = new StringBuilder();
		appendAddress(url, form);
		appendAction(url, form);
		check(name, url);
		return url.toString();
	}
		
	/**
	 * 拼接表单中的请求地址
	 * @author Daniele 
	 * @param url
	 * @param form
	 */
	private void appendAddress(StringBuilder url, HttpForm form) {
		String address = form.getAddress();
		if (StringUtils.isNotBlank(address)) 
			url.append(address.trim());
	}
	
	/**
	 * 拼接表单中的Action请求路径
	 * @author Daniele 
	 * @param url
	 * @param form
	 */
	private void appendAction(StringBuilder url, HttpForm form) {
		String action = form.getAction();
		
		/* 添加Action请求路径 */
		if (StringUtils.isNotBlank(action)) {
			action = action.trim();
			if (!action.startsWith(StringUtils.FORWARD_SLASH) && !url.toString().endsWith(StringUtils.FORWARD_SLASH)) 
				url.append(StringUtils.FORWARD_SLASH);
			
			url.append(action);
		}
	}
	
	/**
	 * 检查表单URL的合法性
	 * @author Daniele 
	 * @param name
	 * @param url
	 */
	private void check(String name, StringBuilder url) {
		AssertUtils.assertTrue(url.length() > 0, "Handle form [" + name
				+ "] error, must ensure that at least one of the parameters 'address' and 'action' can not be empty");
	}
		
}
