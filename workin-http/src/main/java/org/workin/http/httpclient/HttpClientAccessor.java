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

package org.workin.http.httpclient;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.MessageUtils;
import org.workin.commons.util.ReflectionUtils;
import org.workin.commons.util.StringUtils;
import org.workin.http.HttpForm;
import org.workin.http.exception.HttpFormNotFoundException;
import org.workin.http.exception.NoSuchHttpMethodException;
import org.workin.http.register.HttpFormRegister;

/**
 * @description HttpClient访问器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class HttpClientAccessor implements InitializingBean {
	
	private HttpFormRegister formRegister;
	
	/** 全局的字符集编码，默认为UTF-8 */
	private String encoding = MessageUtils.UTF8_ENCODING;
	
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
	
	public void setEncoding(String encoding) {
		if (StringUtils.isNotBlank(encoding))
			this.encoding = encoding;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (formRegister == null)
			throw new IllegalArgumentException("Property 'formRegister' is required");
	}
	
	/**
	 * @description 执行指定名称对应的表单的请求方法
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
		if (StringUtils.isNotBlank(methodName)) {
			String executedMethod = SUPPORT_REQUEST_METHOD.get(methodName.trim().toLowerCase());
			if (executedMethod != null) {
				try {
					return (T) ReflectionUtils.invokeMethod(this, executedMethod, 
							new Class<?>[]{String.class, Object.class}, new Object[]{name, param});
				} catch (NoSuchMethodException e) {
					throw new NoSuchHttpMethodException(
							"No such http method [" + methodName + "] in current version of workin-http framework.");
				}
			} else
				throw new NoSuchHttpMethodException(
						"No such http method [" + methodName + "] in current version of workin-http framework.");
		}
		try {
			return (T) ReflectionUtils.invokeMethod(this, SUPPORT_REQUEST_METHOD.get("get"), 
					new Class<?>[]{String.class, Object.class}, new Object[]{name, param});
		} catch (NoSuchMethodException e) {
			throw new NoSuchHttpMethodException(
					"No such http method [get] in current version of workin-http framework.");
		}
	}
	
	/**
	 * @description 获取表单绑定的字符集编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param form
	 * @return
	 */
	protected String getBoundEncoding(HttpForm form) {
		String encoding = form.getEncoding();
		return StringUtils.isNotBlank(encoding) ? encoding : this.encoding;
	}
	
	/**
	 * @description 执行HTTP GET方法后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T doGetRequest(String name, Object param) throws Exception;
	
	/**
	 * @description 执行HTTP POST方法后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T doPostRequest(String name, Object param) throws Exception;
	
	/**
	 * @description 执行HTTP PUT方法后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T doPutRequest(String name, Object param) throws Exception;
	
	/**
	 * @description 执行HTTP DELETE方法后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T doDeleteRequest(String name, Object param) throws Exception;
	
}
