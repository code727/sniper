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

package org.workin.http.converter;

import java.util.Map;
import java.util.Set;

import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NetUtils;
import org.workin.commons.util.StringUtils;
import org.workin.http.HttpForm;
import org.workin.http.handler.DefaultParameterHandler;
import org.workin.http.handler.ParameterHandler;

/**
 * @description 默认HTTP表单转换器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultHttpFormConverter implements HttpFormConverter {
	
	private static final String HTTP_PROTOCOL = "http";
	
	private static final String HTTPS_PROTOCOL = "https";
	
	/** 参数处理器 */
	private ParameterHandler parameterHandler = new DefaultParameterHandler();
	
	public void setParameterHandler(ParameterHandler parameterHandler) {
		if (parameterHandler != null)
			this.parameterHandler = parameterHandler;
	}

	@Override
	public Map<String, String> convertUrlMap(Map<String, HttpForm> formMap) {
		if (formMap == null)
			return null;
		
		Map<String, String> map = MapUtils.newHashMap();
		StringBuffer url = new StringBuffer();
		Set<String> names = formMap.keySet();
		for (String name : names) {
			HttpForm form = formMap.get(name);
			String queryString = parameterHandler.formatQueryString(form);
			url.setLength(0); 
			appendHostAndPort(url, form);
			appendAction(url, form);
			appendQueryString(url, queryString);
			map.put(name, url.toString());
		}
		return map;
	}
	
	/**
	 * @description 拼接协议、主机IP/域名和端口号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param form
	 */
	protected void appendHostAndPort(StringBuffer url, HttpForm form) {
		String host = form.getHost().trim();
		int port = form.getPort();
		if (!form.isHttps()) {
			url.append(HTTP_PROTOCOL).append("://").append(host);
			if (NetUtils.isValidPort(port) && port != NetUtils.DEFAULT_HTTP_PORT)
				url.append(":").append(port);
		} else {
			url.append(HTTPS_PROTOCOL).append("://").append(host);
			if (NetUtils.isValidPort(port) && port != NetUtils.DEFAULT_HTTPS_PORT)
				url.append(":").append(port);
		}
	}
		
	/**
	 * @description 拼接Action请求路径
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
	 * @description 拼接查询字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param queryString
	 */
	protected void appendQueryString(StringBuffer url, String queryString) {
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
	
}
