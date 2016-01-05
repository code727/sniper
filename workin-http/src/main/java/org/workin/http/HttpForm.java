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

package org.workin.http;

/**
 * @description HTTP表单接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface HttpForm {
	
	/**
	 * @description 判断是否为HTTPS协议的表单
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isHttps();
	
	/** 
	 * @description 设置否为HTTPS协议的表单
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param https 
	 */
	void setHttps(boolean https);
	
	/** 
	 * @description 设置主机IP/域名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param host
	 */
	public void setHost(String host);
	
	/**
	 * @description 获取主机IP/域名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getHost();
	
	/**
	 * @description 设置端口号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param port
	 */
	public void setPort(int port);
	
	/**
	 * @description 获取端口号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getPort();
	
	/**
	 * @description 设置上下文根路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param contextRoot
	 */
	public void setContextRoot(String contextRoot);
	
	/**
	 * @description 获取上下文根路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getContextRoot();
	
	/**
	 * @description 设置请求路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param action
	 */
	public void setAction(String action);
	
	/**
	 * @description 获取请求路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getAction();
	
	/**
	 * @description 设置请求参数名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void setParameterNames(String parameterNames);
	
	/**
	 * @description 获取请求参数名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getParameterNames();
	
	/**
	 * @description 设置请求参数格式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameterFormat
	 */
	public void setParameterFormat(String parameterFormat);
	
	/**
	 * @description 获取请求参数格式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getParameterFormat();
	
	/**
	 * @description 设置发送请求的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param method
	 */
	public void setMethod(String method);
	
	/**
	 * @description 获取发送请求的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getMethod();
	
	/**
	 * @description 设置请求头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param header
	 */
	public void setHeader(HttpRequestHeader header);
	
	/**
	 * @description 获取请求头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public HttpRequestHeader getHeader();
	
	/**
	 * @description 判断表单是否自动编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param autoEncoding
	 */
	public boolean isAutoEncoding();
	
	/**
	 * @description 设置表单是否自动编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param autoEncoding
	 */
	public void setAutoEncoding(boolean autoEncoding);
	
	/**
	 * @description 设置字符集编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param encoding
	 */
	public void setEncoding(String encoding);
	
	/**
	 * @description 获取字符集编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getEncoding();
	
}
