/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-5-26
 */

package org.sniper.http.handler.response;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 响应处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractResponseHandler implements ResponseHandler {
	
	/** 是否允许为空 */
	private boolean allowEmpty;
	
	/** 当响应结果返回为空时指定的默认值 */
	private String defaultValue;
	
	protected AbstractResponseHandler() {
		this(true);
	}
	
	protected AbstractResponseHandler(boolean allowEmpty) {
		this(allowEmpty, StringUtils.EMPTY_STRING);
	}
	
	protected AbstractResponseHandler(String defaultValue) {
		this(true, defaultValue);
	}
	
	protected AbstractResponseHandler(boolean allowEmpty, String defaultValue) {
		setAllowEmpty(allowEmpty);
		setDefaultValue(defaultValue);
	}
	
	public boolean isAllowEmpty() {
		return allowEmpty;
	}

	public void setAllowEmpty(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		check(defaultValue);
		this.defaultValue = defaultValue;
	}
	
	/**
	 * 检查默认值的合法性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param defaultValue
	 * @throws IllegalArgumentException
	 */
	protected void check(String defaultValue) throws IllegalArgumentException {
		AssertUtils.assertNotNull(defaultValue, "String default value must not be null");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T handleResponse(String response) throws Exception {
		if (response != null) 
			return handle(response);
		
		return (T) (!isAllowEmpty() ? handle(defaultValue) : null);
	}
	
	/**
	 * 非空响应的处理方法，返回处理后的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param response
	 * @return
	 */
	protected abstract <T> T handle(String response);
	
}
