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

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 默认表单处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultFormHandler implements FormHandler {
	
	@Override
	public String handle(String name, HttpForm form) {
		StringBuilder url = new StringBuilder();
		appendAddress(url, form);
		appendAction(url, form);
		check(name, url);
		return url.toString();
	}
		
	private void check(String name, StringBuilder url) {
		AssertUtils.assertTrue(url.length() > 0, "Handle form [" + name
				+ "] error, must ensure that at least one of the parameters 'address' and 'action' can not be empty ");
	}

	/**
	 * 拼接表单中的请求地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param form
	 */
	private void appendAddress(StringBuilder url, HttpForm form) {
		url.append(form.getAddress().trim());
	}
	
	/**
	 * 拼接表单中的Action请求路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param form
	 */
	private void appendAction(StringBuilder url, HttpForm form) {
		String action = form.getAction();
		
		/* 添加Action请求路径 */
		if (StringUtils.isNotBlank(action)) {
			action = action.trim();
			if (!action.startsWith("/") && !url.toString().endsWith("/"))
				url.append("/");
			
			url.append(action);
		}
	}

}
