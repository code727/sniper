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

package org.workin.http.handler;

import org.workin.commons.util.NetUtils;
import org.workin.commons.util.StringUtils;
import org.workin.http.HttpForm;

/**
 * @description 默认表单处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultFormHandler implements FormHandler {
	
	@Override
	public void append(StringBuffer url, HttpForm form) {
		String host = form.getHost().trim();
		int port = form.getPort();
		url.append(NetUtils.toURL((form.isHttps() ? NetUtils.HTTPS_PROTOCOL : NetUtils.HTTP_PROTOCOL), host, port));
		appendContextRoot(url, form);
		appendAction(url, form);
		appendQueryString(url, form);
	}
	
	/**
	 * @description 拼接表单中的上下文根路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param form
	 */
	protected void appendContextRoot(StringBuffer url, HttpForm form) {
		String cntextRoot = form.getContextRoot();
		/* 添加Action请求路径 */
		if (StringUtils.isNotBlank(cntextRoot)) {
			cntextRoot = cntextRoot.trim();
			if (!cntextRoot.startsWith("/"))
				url.append("/");
			url.append(cntextRoot);
		}
	}
	
	/**
	 * @description 拼接表单中的Action请求路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param form
	 */
	protected void appendAction(StringBuffer url, HttpForm form) {
		String action = form.getAction();
		/* 添加Action请求路径 */
		if (StringUtils.isNotBlank(action)) {
			action = action.trim();
			if (!action.startsWith("/"))
				url.append("/");
			url.append(action);
		}
	}
	
	/**
	 * @description 拼接表单中的查询字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param queryString
	 */
	protected void appendQueryString(StringBuffer url, HttpForm form) {
		String queryString = formatQueryString(form);
		if (StringUtils.isNotBlank(queryString)) {
			int index = url.lastIndexOf("?");
			if (index > -1) {
				// ?号不在最后一位时，认为原字符串后面部分已有一些查询字符串存在，则需再加上一个&
				if (index != (url.length() - 1))
					url.append("&");
			} else
				url.append("?");
			url.append(queryString);
		} 
	}

	/**
	 * @description 格式化表单中的查询字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param form
	 * @return
	 */
	protected String formatQueryString(HttpForm form) {
		String parameterNames;
		if (form == null || StringUtils.isBlank((parameterNames = form.getParameterNames())))
			return StringUtils.EMPTY_STRING;
		
		String[] names = parameterNames.split(",");
		StringBuffer queryString = new StringBuffer();
		for (String name : names) {
			if (StringUtils.isNotBlank(name)) {
				name = name.trim();
				queryString.append(name).append("={")
					.append(name).append("}").append("&");
			}
		}
		
		return queryString.length() != 0 ? queryString.deleteCharAt(
				queryString.lastIndexOf("&")).toString() : queryString.toString();
	}

}
