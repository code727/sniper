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
 * Create Date : 2015-7-5
 */

package org.sniper.http.form;


import org.sniper.beans.Typed;
import org.sniper.codec.Codecable;
import org.sniper.commons.enums.http.HttpMethodEnum;
import org.sniper.http.handler.response.ResponseHandler;
import org.sniper.http.headers.request.HttpRequestHeaders;

/**
 * HTTP表单接口
 * @author  Daniele
 * @version 1.0
 */
public interface HttpForm extends Typed, Codecable {
	
	/**
	 * 设置URL请求地址
	 * @author Daniele 
	 * @param url
	 */
	public void setAddress(String url);
	
	/**
	 * 获取URL请求地址
	 * @author Daniele 
	 * @return
	 */
	public String getAddress();
	
	/**
	 * 设置请求路径
	 * @author Daniele 
	 * @param action
	 */
	public void setAction(String action);
	
	/**
	 * 获取请求路径
	 * @author Daniele 
	 * @return
	 */
	public String getAction();
				
	/**
	 * 设置发送请求的方法
	 * @author Daniele 
	 * @param method
	 */
	public void setMethod(HttpMethodEnum method);
	
	/**
	 * 获取发送请求的方法
	 * @author Daniele 
	 * @return
	 */
	public HttpMethodEnum getMethod();
	
	/**
	 * 设置请求头
	 * @author Daniele 
	 * @param header
	 */
	public void setHeaders(HttpRequestHeaders headers);
	
	/**
	 * 获取请求头
	 * @author Daniele 
	 * @return
	 */
	public HttpRequestHeaders getHeaders();
					
	/**
	 * 获取嵌套类型
	 * @author Daniele 
	 * @return
	 */
	public Class<?> getNestedType();
	
	/**
	 * 设置嵌套类型
	 * @author Daniele 
	 * @param nestedType
	 */
	public void setNestedType(Class<?> nestedType);
	
	/** 
	 * 获取响应处理器
	 * @author Daniele 
	 * @return 
	 */
	public ResponseHandler getResponseHandler();

	/** 
	 * 设置响应处理器
	 * @author Daniele 
	 * @param responseHandler 
	 */
	public void setResponseHandler(ResponseHandler responseHandler);
	
}
