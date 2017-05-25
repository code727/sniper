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

package org.workin.http;

import java.util.Map;

import org.workin.codec.encoder.RawURLEncoder;
import org.workin.codec.encoder.StringEncoder;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.ReflectionUtils;
import org.workin.commons.util.StringUtils;
import org.workin.http.exception.HttpFormNotFoundException;
import org.workin.http.exception.NoSuchHttpMethodException;
import org.workin.http.formatter.AdaptiveURLFormatter;
import org.workin.http.register.HttpFormRegister;
import org.workin.spring.beans.CheckableInitializingBeanAdapter;
import org.workin.templet.message.formatter.MessageFormatter;

/**
 * HTTP访问器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class HttpAccessor extends CheckableInitializingBeanAdapter implements HttpSender {
	
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
	 * 将指定名称对应的表单对象加上请求参数后格式化成完成的URL
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
		return requestByName(name, null);
	}

	@Override
	public <T> T request(String name, Map<String, Object> parameters) throws Exception {
		return requestByName(name, parameters);
	}

	@Override
	public <T> T request(String name, Object parameter) throws Exception {
		return requestByName(name, parameter);
	}
	
	/**
	 * 执行指定名称对应的表单的请求方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws NoSuchHttpMethodException 
	 */
	@SuppressWarnings("unchecked")
	protected <T> T requestByName(String name, Object param) throws Exception {
		HttpForm form = formRegister.find(name);
		if (form == null)
			throw new HttpFormNotFoundException("Form [name=" + name +"] not found in register.");
		
		String methodName = form.getMethod();
		if (StringUtils.isBlank(methodName))
			methodName = "get";
		
		String executedMethod = SUPPORT_REQUEST_METHOD.get(methodName.trim().toLowerCase());
		if (StringUtils.isNotBlank(methodName)) {
			try {
				return (T) ReflectionUtils.invokeMethod(this, executedMethod,
						new Class<?>[] { String.class, Object.class }, new Object[] { name, param });
			} catch (NoSuchMethodException e) {
				throw new NoSuchHttpMethodException("No such http method ["
						+ methodName + "] in current version of workin-http framework.");
			}
		} else
			throw new NoSuchHttpMethodException("No such http method ["
					+ methodName + "] in current version of workin-http framework.");
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
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T doPostRequest(String name, Object param) throws Exception;
	
	/**
	 * 执行HTTP PUT方法后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T doPutRequest(String name, Object param) throws Exception;
	
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