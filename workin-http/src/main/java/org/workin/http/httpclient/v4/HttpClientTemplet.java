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

package org.workin.http.httpclient.v4;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.InitializingBean;
import org.workin.commons.util.MapUtils;
import org.workin.http.HttpSender;
import org.workin.http.httpclient.v4.factory.CloseableHttpClientFactory;
import org.workin.http.httpclient.v4.factory.DefaultCloseableHttpClientFactory;
import org.workin.http.register.HttpFormRegister;

/**
 * @description HttpClient4.x模板实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HttpClientTemplet implements HttpSender, InitializingBean {
	
	private CloseableHttpClientFactory httpClientFactory;
	
	private HttpFormRegister formRegister;
	
	private RequestConfig requestConfig;
	
	/** 模板所支持的请求方法映射集 */
	private static final Map<String, String> SUPPORT_REQUEST_METHOD;
	
	static {
		SUPPORT_REQUEST_METHOD = MapUtils.newHashMap();
		SUPPORT_REQUEST_METHOD.put("get", "doGetRequest");
		SUPPORT_REQUEST_METHOD.put("post", "doPostRequest");
		SUPPORT_REQUEST_METHOD.put("put", "doPutRequest");
		SUPPORT_REQUEST_METHOD.put("delete", "doDeleteRequest");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (formRegister == null)
			throw new IllegalArgumentException("");
		if (this.httpClientFactory == null) {
			this.httpClientFactory = new DefaultCloseableHttpClientFactory();
			this.httpClientFactory.setConnectionManager(new PoolingHttpClientConnectionManager());
		}
		if (this.requestConfig == null)
			this.requestConfig = RequestConfig.custom().build();
	}
	
	@Override
	public <T> T request(String name) throws IOException {
		return null;
	}

	@Override
	public <T> T request(String name, Map<String, String> parameters)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T request(String name, Object parameter) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @description 执行指定名称对应的表单GET请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	protected <T> T doGetRequest(String name, Object params) throws IOException {
		return null;
	}
	
	/**
	 * @description 执行指定名称对应的表单POST请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	protected <T> T doPostRequest(String name, Object params) throws IOException {
		return null;
	}
	
	/**
	 * @description 执行指定名称对应的表单PUT请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param params
	 * @return
	 * @throws IOException
	 */
	protected <T> T doPutRequest(String name, Object params) throws IOException {
		return null;
	}
	
	/**
	 * @description 执行指定名称对应的表单DELETE请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param params
	 * @return
	 * @throws IOException
	 */
	protected <T> T doDeleteRequest(String name, Object params) throws IOException {
		return null;
	}

}
