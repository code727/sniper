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

package org.sniper.http;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.codec.encoder.RawURLEncoder;
import org.sniper.codec.encoder.StringEncoder;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.http.exception.HttpFormNotFoundException;
import org.sniper.http.exception.NoSuchHttpMethodException;
import org.sniper.http.formatter.AdaptiveURLFormatter;
import org.sniper.http.handler.response.ResponseHandler;
import org.sniper.http.handler.response.typed.TypedNestedResponseHandler;
import org.sniper.http.handler.response.typed.TypedResponseHandler;
import org.sniper.http.register.HttpFormRegister;
import org.sniper.spring.beans.CheckableInitializingBeanAdapter;
import org.sniper.templet.message.formatter.MessageFormatter;

/**
 * HTTP访问器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class HttpAccessor extends CheckableInitializingBeanAdapter implements HttpSender {
	
	protected final transient Logger logger;
	
	protected HttpFormRegister formRegister;
	
	protected MessageFormatter<Object> urlFormatter;
	
	protected StringEncoder urlEncoder;
		
	/** 模板所支持的请求方法映射集 */
	private static final Map<String, String> SUPPORT_REQUEST_METHOD;
	
	static {
		SUPPORT_REQUEST_METHOD = MapUtils.newHashMap();
		SUPPORT_REQUEST_METHOD.put("get", "doGetRequest");
		SUPPORT_REQUEST_METHOD.put("post", "doPostRequest");
		SUPPORT_REQUEST_METHOD.put("put", "doPutRequest");
		SUPPORT_REQUEST_METHOD.put("delete", "doDeleteRequest");
	}
	
	protected HttpAccessor() {
		this.logger = LoggerFactory.getLogger(getClass());
	}
	
	public void setFormRegister(HttpFormRegister formRegister) {
		this.formRegister = formRegister;
	}
	
	public HttpFormRegister getFormRegister() {
		return formRegister;
	}
	
	public void setUrlFormatter(MessageFormatter<Object> urlFormatter) {
		this.urlFormatter = urlFormatter;
	}
	
	public MessageFormatter<Object> getUrlFormatter() {
		return urlFormatter;
	}
	
	public StringEncoder getUrlEncoder() {
		return urlEncoder;
	}

	public void setUrlEncoder(StringEncoder urlEncoder) {
		this.urlEncoder = urlEncoder;
	}
	
	@Override
	protected void checkProperties() {
		if (formRegister == null)
			throw new IllegalArgumentException("Property 'formRegister' is required");
	}
	
	@Override
	protected void init() throws Exception {
		if (this.urlFormatter == null) 
			this.urlFormatter = new AdaptiveURLFormatter();
		
		if (this.urlEncoder == null)
			this.urlEncoder = new RawURLEncoder();
	}
		
	/**
	 * 将指定名称对应的表单对象加上请求参数后格式化成完整的URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected String format(String name, Object param) {
		String url = formRegister.findURL(name);
		url = urlFormatter.format(url, param);
		return url;
	}
	
	@Override
	public <T> T request(String name) throws Exception {
		return requestByName(name, null, null);
	}

	@Override
	public <V, T> T request(String name, Map<String, V> parameters) throws Exception {
		return requestByName(name, null, parameters);
	}

	@Override
	public <T> T request(String name, Object parameter) throws Exception {
		return requestByName(name, null, parameter);
	}
	
	@Override
	public <T> T requestByBody(String name, Object requestBody) throws Exception {
		return requestByName(name, requestBody, null);
	}

	@Override
	public <V, T> T requestByBody(String name, Object requestBody, Map<String, V> parameters) throws Exception {
		return requestByName(name, requestBody, parameters);
	}

	@Override
	public <T> T requestByBody(String name, Object requestBody, Object parameter) throws Exception {
		return requestByName(name, requestBody, parameter);
	}
	
	/**
	 * 执行指定名称对应的表单的请求方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param requestBody
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected <T> T requestByName(String name, Object requestBody, Object param) throws Exception {
		HttpForm form = formRegister.find(name);
		if (form == null)
			throw new HttpFormNotFoundException("Form [name=" + name +"] not found in register");
		
		String methodName = form.getMethod();
		if (StringUtils.isBlank(methodName))
			methodName = "get";
		
		String executedMethod = SUPPORT_REQUEST_METHOD.get(methodName.trim().toLowerCase());
		if (StringUtils.isNotBlank(methodName)) {
			try {
				return (T) ReflectionUtils.invokeMethod(this, executedMethod,
						new Class<?>[] { String.class, Object.class, Object.class }, new Object[] { name, requestBody, param });
			} catch (NoSuchMethodException e) {
				throw new NoSuchHttpMethodException("No such http method ["
						+ methodName + "] in current version of sniper-http framework");
			}
		} else
			throw new NoSuchHttpMethodException("No such http method ["
					+ methodName + "] in current version of sniper-http framework");
	}
	
	/**
	 * 根据表单所绑定的响应处理器处理字符串响应后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param form
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected <T> T handleResponse(HttpForm form, String response) throws Exception {
		logger.debug("Http string response:{}", response);
		
		ResponseHandler handler = form.getResponseHandler();
		
		if (handler == null)
			return (T) response;
		
		if (handler instanceof TypedResponseHandler) {
			if (handler instanceof TypedNestedResponseHandler) 
				return (T) ((TypedNestedResponseHandler) handler).handleNestedResponse(response, form.getType(), form.getNestedType());
			
			return (T) ((TypedResponseHandler) handler).handleResponse(response, form.getType());
		}
			
		return handler.handleResponse(response);
	}
	
	/**
	 * 执行HTTP GET方法后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param requestBody
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected <T> T doGetRequest(String name, Object requestBody, Object param) throws Exception {
		// HTTP Get不能设置RequestBody，因此忽略掉
		return doGetRequest(name, param);
	}
	
	/**
	 * 执行HTTP DELETE方法后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param requestBody
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected <T> T doDeleteRequest(String name, Object requestBody, Object param) throws Exception {
		// HTTP Delete不能设置RequestBody，因此忽略掉
		return doGetRequest(name, param);
	}
		
	/**
	 * 执行HTTP GET方法后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T doGetRequest(String name, Object param) throws Exception;
	
	/**
	 * 执行HTTP POST方法后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param requestBody
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T doPostRequest(String name, Object requestBody, Object param) throws Exception;
	
	/**
	 * 执行HTTP PUT方法后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param requestBody
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T doPutRequest(String name, Object requestBody, Object param) throws Exception;
	
	/**
	 * 执行HTTP DELETE方法后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T doDeleteRequest(String name, Object param) throws Exception;

}
