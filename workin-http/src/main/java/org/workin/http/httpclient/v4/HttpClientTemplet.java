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
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.commons.util.NetUtils;
import org.workin.http.HttpForm;
import org.workin.http.HttpRequestHeader;
import org.workin.http.HttpSender;
import org.workin.http.httpclient.HttpClientAccessor;
import org.workin.http.httpclient.v4.factory.CloseableHttpClientFactory;
import org.workin.http.httpclient.v4.factory.CloseableHttpClientFactoryBean;
import org.workin.http.httpclient.v4.handler.request.DefualtRequestHandler;
import org.workin.http.httpclient.v4.handler.request.RequestHandler;
import org.workin.http.httpclient.v4.handler.response.StringResponseHandler;

/**
 * @description HttpClient4.x模板实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class HttpClientTemplet extends HttpClientAccessor implements HttpSender {
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientTemplet.class);
	
	private CloseableHttpClientFactory httpClientFactory;
	
	private RequestConfig requestConfig;
	
	/** 全局的请求处理器 */
	private RequestHandler requestHandler;
	
	/** 全局的响应处理器 */
	private ResponseHandler<?> responseHandler;
	
	public void setHttpClientFactory(CloseableHttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}
	
	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}
	
	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	public void setResponseHandler(ResponseHandler<?> responseHandler) {
		this.responseHandler = responseHandler;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.httpClientFactory == null) 
			this.httpClientFactory = new CloseableHttpClientFactoryBean();
		
		if (this.requestConfig == null)
			this.requestConfig = RequestConfig.custom().build();
		
		if (this.requestHandler == null)
			this.requestHandler = new DefualtRequestHandler();
		
		if (this.responseHandler == null)
			this.responseHandler = new StringResponseHandler();
	}
	
	@Override
	public <T> T request(String name) throws Exception {
		return super.requestByName(name, null);
	}

	@Override
	public <T> T request(String name, Map<String, Object> parameters) throws Exception {
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
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected <T> T doGetRequest(String name, Object param) throws Exception {
		HttpForm form = getFormRegister().find(name);
		String url = formatToURL(form, name, param);
		
		HttpGet httpGet = new HttpGet(url);
		addHeader(httpGet, form);
		setConfig(httpGet);
		try {
			logger.info("Request form [" + name + "] url [" + url + "] method:[GET].");
			return (T) this.httpClientFactory.create().execute(httpGet, getBoundResponseHandler(form));
		}  catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (httpGet != null)
				httpGet.releaseConnection();
		}
	}
	
	/**
	 * @description 执行指定名称对应的表单POST请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected <T> T doPostRequest(String name, Object param) throws Exception {
		HttpForm form = getFormRegister().find(name);
		String url = formatToURL(form, name, param);
		
		HttpPost httpPost = new HttpPost(NetUtils.getActionString(url));
		addHeader(httpPost, form);
		setConfig(httpPost);
		try {
			getBoundRequestHandler(form).setRequestBody(httpPost, url, form);
			logger.info("Request form [" + name + "] url [" + url + "] method:[POST].");
			return (T) this.httpClientFactory.create().execute(httpPost, getBoundResponseHandler(form));
		}  catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (httpPost != null)
				httpPost.releaseConnection();
		}
	}
	
	/**
	 * @description 执行指定名称对应的表单PUT请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected <T> T doPutRequest(String name, Object param) throws Exception {
		HttpForm form = getFormRegister().find(name);
		String url = formatToURL(form, name, param);
		HttpPut httpPut = new HttpPut(NetUtils.getActionString(url));
		
		addHeader(httpPut, form);
		setConfig(httpPut);
		try {
			getBoundRequestHandler(form).setRequestBody(httpPut, url, form);
			logger.info("Request form [" + name + "] url [" + url + "] method:[PUT].");
			return (T) this.httpClientFactory.create().execute(httpPut, getBoundResponseHandler(form));
		}  catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (httpPut != null)
				httpPut.releaseConnection();
		}
	}
	
	/**
	 * @description 执行指定名称对应的表单DELETE请求，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected <T> T doDeleteRequest(String name, Object param) throws Exception {
		HttpForm form = getFormRegister().find(name);
		String url = formatToURL(form, name, param);
		HttpDelete httpDelete = new HttpDelete(url);
		
		addHeader(httpDelete, form);
		setConfig(httpDelete);
		try {
			logger.info("Request form [" + name + "] url [" + url + "] method:[DELETE].");
			return (T) this.httpClientFactory.create().execute(httpDelete, getBoundResponseHandler(form));
		}  catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (httpDelete != null)
				httpDelete.releaseConnection();
		}
	}
	
	@Override
	protected String formatToURL(HttpForm form, String name, Object param) throws Exception {
		String url = getFormRegister().findURL(name);
		url = getUrlFormatter().format(url, param, form.isAutoEncoding() ? getBoundEncoding(form) : null);
		return url;
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
	 * @description 获取HttpForm表单绑定的请求处理器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param form
	 * @return
	 */
	protected RequestHandler getBoundRequestHandler(HttpForm form) {
		if (!(form instanceof HttpClientForm))
			return this.requestHandler;
		
		HttpClientForm hcForm = (HttpClientForm) form;
		RequestHandler requestHandler = hcForm.getRequestHandler();
		return requestHandler != null ? requestHandler : this.requestHandler;
	}
	
	/**
	 * @description 获取HttpForm表单绑定的响应处理器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param form
	 * @return
	 */
	protected ResponseHandler<?> getBoundResponseHandler(HttpForm form) {
		if (!(form instanceof HttpClientForm))
			return this.responseHandler;
		
		HttpClientForm hcForm = (HttpClientForm) form;
		ResponseHandler<?> responseHandler = hcForm.getResponseHandler();
		return responseHandler != null ? responseHandler : this.responseHandler;
	}
	
	/**
	 * @description 获取HttpForm表单绑定的字符集编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param form
	 * @return
	 */
	protected String getBoundEncoding(HttpForm form) {
		return getBoundRequestHandler(form).getEncoding(form);
	}
		
}
