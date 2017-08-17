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
 * Create Date : 2015-7-7
 */

package org.sniper.http.form;

import org.sniper.beans.DefaultTypedBean;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CodecUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.http.enums.MimeTypeEnum;
import org.sniper.http.handler.response.ResponseHandler;

/**
 * HTTP表单实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SimpleHttpForm extends DefaultTypedBean implements HttpForm {
		
	/** 请求地址 */
	private String address;
	
	/** Action(请求路径) */
	private String action;
	
	/** 请求方法 */
	private String method;
	
	/** 请求头对象 */
//	private HttpRequestHeader header;
	
	/** Mime-Type */
	private String mimeType = MimeTypeEnum.APPLICATION_FORM_URLENCODED.getType();
	
	/** 字符串编码 */
	private String encoding = CodecUtils.DEFAULT_ENCODING;
	
	/** 响应处理器 */
	private ResponseHandler responseHandler;
	
	/** 嵌套类型路径限定名 */
	private String nestedTypeClass;
	
	/** 嵌套类型 */
	private Class<?> nestedType;
	
	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String getAction() {
		return action;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

//	@Override
//	public void setHeader(HttpRequestHeader header) {
//		this.header = header;
//	}
//
//	@Override
//	public HttpRequestHeader getHeader() {
//		return this.header;
//	}
	
	@Override
	public void setMimeType(String mimeType) {
		if (StringUtils.isNotBlank(mimeType))
			this.mimeType = mimeType;
	}

	@Override
	public String getMimeType() {
		return this.mimeType;
	}
	
	@Override
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public String getEncoding() {
		return this.encoding;
	}
	
	@Override
	public ResponseHandler getResponseHandler() {
		return responseHandler;
	}

	@Override
	public void setResponseHandler(ResponseHandler responseHandler) {
		this.responseHandler = responseHandler;
	}
	
	@Override
	public String getNestedTypeClass() {
		return nestedTypeClass;
	}

	@Override
	public void setNestedTypeClass(String nestedTypeClass) throws Exception {
		AssertUtils.assertNotBlank(nestedTypeClass, "Nested type class must not be null or blank");
		
		// 先尝试加载类型
		this.nestedType = Class.forName(nestedTypeClass);
		this.nestedTypeClass = nestedTypeClass;
	}

	@Override
	public Class<?> getNestedType() {
		return nestedType;
	}

	@Override
	public void setNestedType(Class<?> nestedType) {
		AssertUtils.assertNotNull(nestedType, "Nested type must not be null or blank");
		
		this.nestedType = nestedType;
		this.nestedTypeClass = nestedType.getName();
	}


}
