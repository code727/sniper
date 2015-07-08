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
 * Create Date : 2015-7-7
 */

package org.workin.http;

import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.NetUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description HTTP表单接口实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SimpleHttpForm implements HttpForm {
	
	private String host;
	
	private int port = NetUtils.DEFAULT_PORT;
	
	private String action;
	
	private String parameterNames;
	
	private String method;
	
	private HttpRequestHeader header;

	@Override
	public void setHost(String host) {
		AssertUtils.assertTrue(StringUtils.isNotBlank(host), "Host can not be null or blank.");
		this.host = host;
	}

	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public void setPort(int port) {
		AssertUtils.assertTrue(port > -1 && port < 65536, "Illegal port [" + port + "],Valid scope [0-65535].");
		this.port = port;
	}

	@Override
	public int getPort() {
		return this.port;
	}

	@Override
	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String getAction() {
		return this.action;
	}

	@Override
	public void setParameterNames(String parameterNames) {
		this.parameterNames = parameterNames;
	}

	@Override
	public String getParameterNames() {
		return this.parameterNames;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public void setHeader(HttpRequestHeader header) {
		this.header = header;
	}

	@Override
	public HttpRequestHeader getHeader() {
		return this.header;
	}
	
}
