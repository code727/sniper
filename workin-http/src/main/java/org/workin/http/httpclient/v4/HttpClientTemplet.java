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
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.workin.commons.util.NetUtils;
import org.workin.http.HttpForm;
import org.workin.http.HttpRequestHeader;
import org.workin.http.HttpSender;
import org.workin.http.formatter.AdaptiveURLFormatter;
import org.workin.http.formatter.URLFormatter;
import org.workin.http.httpclient.HttpClientAccessor;
import org.workin.http.httpclient.v4.factory.CloseableHttpClientFactory;
import org.workin.http.httpclient.v4.factory.DefaultCloseableHttpClientFactory;

/**
 * @description HttpClient4.x模板实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HttpClientTemplet extends HttpClientAccessor implements HttpSender {
	
	private CloseableHttpClientFactory httpClientFactory;
	
	private RequestConfig requestConfig;
	
	private URLFormatter<Object> urlFormatter;
	
	/** 全局的响应处理器 */
	private ResponseHandler<?> responseHandler;
	
	public void setHttpClientFactory(CloseableHttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}
	
	public void setUrlFormatter(URLFormatter<Object> urlFormatter) {
		this.urlFormatter = urlFormatter;
	}
	
	public void setResponseHandler(ResponseHandler<?> responseHandler) {
		this.responseHandler = responseHandler;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.httpClientFactory == null) {
			this.httpClientFactory = new DefaultCloseableHttpClientFactory();
			this.httpClientFactory.setConnectionManager(new PoolingHttpClientConnectionManager());
		}
		if (this.requestConfig == null)
			this.requestConfig = RequestConfig.custom().build();
		
		if (this.urlFormatter == null)
			this.urlFormatter = new AdaptiveURLFormatter();
		
	}
	
	@Override
	public <T> T request(String name) throws Exception {
		return super.requestByName(name, null);
	}

	@Override
	public <T> T request(String name, Map<String, String> parameters) throws Exception {
		return super.requestByName(name, parameters);
	}

	@Override
	public <T> T request(String name, Object parameter) throws Exception {
		return super.requestByName(name, parameter);
	}
	
	/**
	 * @description 执行指定名称对应的表单GET请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected <T> T doGetRequest(String name, Object param) throws IOException {
		String url = this.urlFormatter.format(getFormRegister().findURL(name), param);
		HttpGet httpget = new HttpGet(url);
		HttpForm form = getFormRegister().find(name);
		addHeader(httpget, form);
		setConfig(httpget);
		try {
			return (T) httpClientFactory.create().execute(httpget, getResponseHandlerByForm(form));
		}  catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (httpget != null)
				httpget.releaseConnection();
		}
	}
	
	/**
	 * @description 执行指定名称对应的表单POST请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws IOException
	 */
	protected <T> T doPostRequest(String name, Object param) throws IOException {
		String url = this.urlFormatter.format(getFormRegister().findURL(name), param);
		HttpPost httpPost = new HttpPost(NetUtils.getActionString(url));
		
		return null;
	}
	
	/**
	 * @description 执行指定名称对应的表单PUT请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws IOException
	 */
	protected <T> T doPutRequest(String name, Object param) throws IOException {
		return null;
	}
	
	/**
	 * @description 执行指定名称对应的表单DELETE请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws IOException
	 */
	protected <T> T doDeleteRequest(String name, Object param) throws IOException {
		return null;
	}
	
	/**
	 * @description 为HttpRequestBase对象添加表单绑定的Header
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
	
	/**
	 * @description 为HttpRequestBase对象设置配置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param httpRequest
	 */
	protected void setConfig(HttpRequestBase httpRequest) {
		httpRequest.setConfig(this.requestConfig);
	}
	
	/**
	 * @description 根据HttpForm表单获取相关的响应处理器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param form
	 * @return
	 */
	protected ResponseHandler<?> getResponseHandlerByForm(HttpForm form) {
		if (!(form instanceof HttpClientForm))
			return this.responseHandler;
		
		HttpClientForm hcForm = (HttpClientForm) form;
		ResponseHandler<?> responseHandler = hcForm.getResponseHandler();
		return responseHandler != null ? responseHandler : this.responseHandler;
	}
	
}
