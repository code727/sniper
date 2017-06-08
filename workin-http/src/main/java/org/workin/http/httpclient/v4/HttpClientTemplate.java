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

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.workin.commons.util.NetUtils;
import org.workin.http.HttpAccessor;
import org.workin.http.HttpForm;
import org.workin.http.HttpRequestHeader;
import org.workin.http.httpclient.v4.factory.CloseableHttpClientFactoryBean;
import org.workin.http.httpclient.v4.factory.HttpClientFactory;
import org.workin.http.httpclient.v4.handler.request.DefualtRequestHandler;
import org.workin.http.httpclient.v4.handler.request.RequestHandler;
import org.workin.http.httpclient.v4.handler.response.DefaultResponseHandler;

/**
 * HttpClient4.x模板实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class HttpClientTemplate extends HttpAccessor {
		
	private HttpClientFactory httpClientFactory;
	
	private RequestConfig requestConfig;
	
	/** 全局的请求处理器 */
	private RequestHandler requestHandler;
	
	/** 全局的响应处理器 */
	private org.apache.http.client.ResponseHandler<String> responseHandler;
	
	public void setHttpClientFactory(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}
	
	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}
	
	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	public void setResponseHandler(org.apache.http.client.ResponseHandler<String> responseHandler) {
		this.responseHandler = responseHandler;
	}

	@Override
	protected void init() throws Exception {
		super.init();
		
		if (this.httpClientFactory == null) 
			this.httpClientFactory = new CloseableHttpClientFactoryBean();
		
		if (this.requestConfig == null)
			this.requestConfig = RequestConfig.custom().build();
		
		if (this.requestHandler == null)
			this.requestHandler = new DefualtRequestHandler();
		
		if (this.responseHandler == null)
			this.responseHandler = new DefaultResponseHandler();
	}
		
	/**
	 * 执行指定名称对应的表单GET请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected <T> T doGetRequest(String name, Object param) throws Exception {
		String url = format(name, param);
		HttpForm form = formRegister.find(name);
		
		// 已进行编码后的URL
		String encodedUrl = urlEncoder.encode(url, form.getEncoding());
		HttpGet httpGet = new HttpGet(encodedUrl);
		httpGet.setConfig(requestConfig);
		addHeader(httpGet, form);
		
		try {
			logger.info("Execute {} request [{}] from form [{}]", HttpGet.METHOD_NAME, url, name);
			String response = httpClientFactory.create().execute(httpGet, responseHandler);
			return handleResponse(form, response);
		} finally {
			if (httpGet != null) 
				httpGet.releaseConnection();
		}
	}
	
	/**
	 * 执行指定名称对应的表单POST请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected <T> T doPostRequest(String name, Object param) throws Exception {
		String url = format(name, param);
		HttpForm form = formRegister.find(name);
		
		// 已进行编码后的Action，不包含参数字符串部分
		String encodedAction = urlEncoder.encode(NetUtils.getActionString(url), form.getEncoding());
		HttpPost httpPost = new HttpPost(encodedAction);
		httpPost.setConfig(requestConfig);
		addHeader(httpPost, form);
		
		try {
			requestHandler.handle(httpPost, url, form);
			logger.info("Execute {} request [{}] from form [{}]", HttpPost.METHOD_NAME, url, name);
			String response = httpClientFactory.create().execute(httpPost, responseHandler);
			return handleResponse(form, response);
		}  finally {
			if (httpPost != null) 
				httpPost.releaseConnection();
		}
	}
	
	/**
	 * 执行指定名称对应的表单PUT请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected <T> T doPutRequest(String name, Object param) throws Exception {
		String url = format(name, param);
		HttpForm form = formRegister.find(name);
		
		// 已进行编码后的Action，不包含参数字符串部分
		String encodedAction = urlEncoder.encode(NetUtils.getActionString(url), form.getEncoding());
		HttpPut httpPut = new HttpPut(encodedAction);
		httpPut.setConfig(requestConfig);
		addHeader(httpPut, form);
		
		try {
			requestHandler.handle(httpPut, url, form);
			logger.info("Execute {} request [{}] from form [{}]", HttpPut.METHOD_NAME, url, name);
			String response = httpClientFactory.create().execute(httpPut, responseHandler);
			return handleResponse(form, response);
		}  finally {
			if (httpPut != null)
				httpPut.releaseConnection();
		}
	}
	
	/**
	 * 执行指定名称对应的表单DELETE请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected <T> T doDeleteRequest(String name, Object param) throws Exception {
		String url = format(name, param);
		HttpForm form = formRegister.find(name);
		
		// 已进行编码后的URL
		String encodedUrl = urlEncoder.encode(url, form.getEncoding());
		HttpDelete httpDelete = new HttpDelete(encodedUrl);
		httpDelete.setConfig(requestConfig);
		addHeader(httpDelete, form);
		
		try {
			logger.info("Execute {} request [{}] from form [{}]", HttpDelete.METHOD_NAME, url, name);
			String response = httpClientFactory.create().execute(httpDelete, responseHandler);
			return handleResponse(form, response);
		}  finally {
			if (httpDelete != null)
				httpDelete.releaseConnection();
		}
	}
		
	/**
	 * 为HttpRequestBase对象添加表单绑定的Header
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param httpGet
	 */
	protected void addHeader(HttpRequestBase httpRequest, HttpForm form) {
		HttpRequestHeader header = form.getHeader();
		if (header != null) {
			Iterator<Entry<String, Object>> headerItem = header.getAttributes().entrySet().iterator();
			while (headerItem.hasNext()) {
				Entry<String, Object> item = headerItem.next();
				httpRequest.addHeader(item.getKey(), item.getKey());
			}
		} 
	}
					
}
