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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NetUtils;
import org.workin.http.HttpForm;
import org.workin.http.HttpRequestHeader;
import org.workin.http.HttpSender;
import org.workin.http.formatter.AdaptiveURLFormatter;
import org.workin.http.httpclient.HttpClientAccessor;
import org.workin.http.httpclient.v4.factory.CloseableHttpClientFactory;
import org.workin.http.httpclient.v4.factory.CloseableHttpClientFactoryBean;
import org.workin.http.httpclient.v4.handler.StringResponseHandler;
import org.workin.support.encoder.RawURLEncoder;
import org.workin.support.message.formatter.MessageFormatter;

/**
 * @description HttpClient4.x模板实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class HttpClientTemplet extends HttpClientAccessor implements HttpSender {
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientTemplet.class);
	
	private CloseableHttpClientFactory httpClientFactory;
	
	private RequestConfig requestConfig;
	
	private MessageFormatter<Object> urlFormatter;
	
	/** 是否自动进行参数值编码处理 */
	private boolean autoEncodingParameter = true;
	
	/** 全局的响应处理器 */
	private ResponseHandler<?> responseHandler;
	
	public void setHttpClientFactory(CloseableHttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}
	
	public void setUrlFormatter(MessageFormatter<Object> urlFormatter) {
		this.urlFormatter = urlFormatter;
	}

	public void setResponseHandler(ResponseHandler<?> responseHandler) {
		this.responseHandler = responseHandler;
	}
	
	public void setAutoEncodingParameter(boolean autoEncodingParameter) {
		this.autoEncodingParameter = autoEncodingParameter;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.httpClientFactory == null) 
			this.httpClientFactory = new CloseableHttpClientFactoryBean();
		
		if (this.requestConfig == null)
			this.requestConfig = RequestConfig.custom().build();
		
		if (this.urlFormatter == null) {
			this.urlFormatter = new AdaptiveURLFormatter();
			this.urlFormatter.setEncoder(new RawURLEncoder());
		}
		
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
		String url = this.urlFormatter.format(getFormRegister().findURL(name), param, 
				autoEncodingParameter ? super.getBoundEncoding(form) : null);
		
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
		String url = this.urlFormatter.format(getFormRegister().findURL(name), 
				param, autoEncodingParameter ? super.getBoundEncoding(form) : null);
		
		HttpPost httpPost = new HttpPost(NetUtils.getActionString(url));
		addHeader(httpPost, form);
		setConfig(httpPost);
		try {
			setRequestBody(httpPost, url, form);
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
		String url = this.urlFormatter.format(getFormRegister().findURL(name),
				param, autoEncodingParameter ? super.getBoundEncoding(form) : null);
		HttpPut httpPut = new HttpPut(NetUtils.getActionString(url));
		
		addHeader(httpPut, form);
		setConfig(httpPut);
		try {
			setRequestBody(httpPut, url, form);
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
		String url = this.urlFormatter.format(getFormRegister().findURL(name),
				param, autoEncodingParameter ? super.getBoundEncoding(form) : null);
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
	 * @description 根据url中的查询字符串构建NameValuePair对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @return
	 */
	protected List<NameValuePair> buildeNameValuePairByQueryString(String url) {
		Map<String, String> parameterMap = NetUtils.getParameterMap(url);
		List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
		if (MapUtils.isNotEmpty(parameterMap)) {
			Iterator<Entry<String, String>> iterator = parameterMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> parameter = iterator.next();
				nameValueList.add(new BasicNameValuePair(parameter.getKey(), parameter.getValue()));
			}
		}
		return nameValueList;
	}
	
	/**
	 * @description 设置RequestBody
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param httpRequest
	 * @param url
	 * @param form
	 * @throws UnsupportedEncodingException
	 */
	protected void setRequestBody(HttpEntityEnclosingRequestBase httpRequest, String url, HttpForm form) throws UnsupportedEncodingException {
		if (autoEncodingParameter) 
			httpRequest.setEntity(new StringEntity(NetUtils.getQueryString(url)));
		else {
			List<NameValuePair> nameValueList = buildeNameValuePairByQueryString(url);
			if (CollectionUtils.isNotEmpty(nameValueList)) 
				httpRequest.setEntity(new UrlEncodedFormEntity(nameValueList, super.getBoundEncoding(form))); 
		}
	}
	
}
