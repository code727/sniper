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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.workin.commons.util.MessageUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description 响应处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractResponseHandler<T> implements ResponseHandler<T> {
	
	/** 响应结果的字符集编码 */
	private String encoding = MessageUtils.UTF8_ENCODING;
	
	/** 当响应结果返回为空时指定的默认值 */
	private String defaultValue;

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		if (StringUtils.isNotBlank(encoding))
			this.encoding = encoding;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @description 处理响应并返回字符串结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	protected String doResponse(HttpResponse response) throws IOException {
		final StatusLine statusLine = response.getStatusLine();
		final HttpEntity entity = response.getEntity();
		if (statusLine.getStatusCode() >= 300) {
			EntityUtils.consume(entity);
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		return entity != null ?  EntityUtils.toString(entity, this.encoding) : null;
	}

}
