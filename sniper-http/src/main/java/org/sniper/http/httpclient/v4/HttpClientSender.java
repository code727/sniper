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
 * Create Date : 2015-7-9
 */

package org.sniper.http.httpclient.v4;

import java.util.Map;

import org.sniper.http.HttpSender;
import org.sniper.spring.beans.CheckableInitializingBeanAdapter;

/**
 * HttpClient4.x发送器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HttpClientSender extends CheckableInitializingBeanAdapter implements HttpSender {
	
	private HttpClientTemplate httpClientTemplate;
	
	@Override
	protected void checkProperties() {
		if (httpClientTemplate == null)
			throw new IllegalArgumentException("Property 'httpClientTemplate' is required");
	}
	
	public HttpClientTemplate getHttpClientTemplate() {
		return httpClientTemplate;
	}

	public void setHttpClientTemplate(HttpClientTemplate httpClientTemplate) {
		this.httpClientTemplate = httpClientTemplate;
	}

	@Override
	public <T> T request(String name) throws Exception {
		return httpClientTemplate.request(name);
	}

	@Override
	public <V, T> T request(String name, Map<String, V> parameters) throws Exception {
		return httpClientTemplate.request(name, parameters);
	}

	@Override
	public <T> T request(String name, Object parameter) throws Exception {
		return httpClientTemplate.request(name, parameter);
	}

	@Override
	public <T> T requestByBody(String name, Object requestBody) throws Exception {
		return httpClientTemplate.requestByBody(name, requestBody);
	}

	@Override
	public <V, T> T requestByBody(String name, Object requestBody, Map<String, V> parameters) throws Exception {
		return httpClientTemplate.requestByBody(name, requestBody, parameters);
	}

	@Override
	public <T> T requestByBody(String name, Object requestBody, Object parameter) throws Exception {
		return  httpClientTemplate.requestByBody(name, requestBody, parameter);
	}

}
