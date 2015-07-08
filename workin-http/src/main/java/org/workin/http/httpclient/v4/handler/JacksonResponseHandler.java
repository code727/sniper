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
 * Create Date : 2015-7-8
 */

package org.workin.http.httpclient.v4.handler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.map.ObjectMapper;
import org.workin.commons.util.StringUtils;


/**
 * @description JSON响应处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 * @param <T>
 */
public class JacksonResponseHandler<T> extends AbstractResponseHandler<T> {
	
	/** 返回的Bean类型字符串 */
	private String beanClass;
	
	private Class<?> beanType = Object.class;

	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		if (StringUtils.isNotBlank(beanClass)) {
			this.beanClass = beanClass.trim();
			try {
				this.beanType = Class.forName(beanClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		String jacksonContent = super.doResponse(response);
		if (StringUtils.isNotBlank(jacksonContent)) {
			ObjectMapper objectMapper = new ObjectMapper();
			return (T) objectMapper.readValue(jacksonContent, this.beanType);
		} else
			return null;
	}
	
}
