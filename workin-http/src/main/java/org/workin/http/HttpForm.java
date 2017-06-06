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

import java.util.Set;

import org.workin.beans.Typed;
import org.workin.beans.mapper.MapperRule;
import org.workin.http.handler.response.ResponseHandler;

/**
 * HTTP表单接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface HttpForm extends Typed {
	
	/**
	 * 判断是否为HTTPS协议的表单
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isHttps();
	
	/** 
	 * 设置否为HTTPS协议的表单
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param https 
	 */
	void setHttps(boolean https);
	
	/** 
	 * 设置主机IP/域名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param host
	 */
	public void setHost(String host);
	
	/**
	 * 获取主机IP/域名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getHost();
	
	/**
	 * 设置端口号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param port
	 */
	public void setPort(int port);
	
	/**
	 * 获取端口号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getPort();
	
	/**
	 * 设置上下文根路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param contextRoot
	 */
	public void setContextRoot(String contextRoot);
	
	/**
	 * 获取上下文根路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getContextRoot();
	
	/**
	 * 设置请求路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param action
	 */
	public void setAction(String action);
	
	/**
	 * 获取请求路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getAction();
	
	/**
	 * 设置请求参数名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void setParameterNames(String parameterNames);
	
	/**
	 * 获取请求参数名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getParameterNames();
	
	/**
	 * 设置发送请求的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param method
	 */
	public void setMethod(String method);
	
	/**
	 * 获取发送请求的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getMethod();
	
	/**
	 * 设置请求头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param header
	 */
	public void setHeader(HttpRequestHeader header);
	
	/**
	 * 获取请求头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public HttpRequestHeader getHeader();
	
	/**
	 * 设置mimeType
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mimeType
	 */
	public void setMimeType(String mimeType);
	
	/**
	 * 获取mimeType
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getMimeType();
		
	/**
	 * 设置字符集编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param encoding
	 */
	public void setEncoding(String encoding);
	
	/**
	 * 获取字符集编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getEncoding();
	
	/**
	 * 获取嵌套类型路径限定名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getNestedTypeClass();
	
	/**
	 * 设置嵌套类型路径限定名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param nestedTypeClass
	 * @throws Exception
	 */
	public void setNestedTypeClass(String nestedTypeClass) throws Exception;
	
	/**
	 * 获取嵌套类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Class<?> getNestedType();
	
	/**
	 * 设置嵌套类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param nestedType
	 */
	public void setNestedType(Class<?> nestedType);
	
	/** 
	 * 获取响应处理器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return 
	 */
	public ResponseHandler getResponseHandler();

	/** 
	 * 设置响应处理器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param responseHandler 
	 */
	public void setResponseHandler(ResponseHandler responseHandler);

	/** 
	 * 获取嵌套映射器规则集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return 
	 */
	public Set<MapperRule> getNestedMapperRules();

	/** 
	 * 设置嵌套映射器规则集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param nestedMapperRules 
	 */
	public void setNestedMapperRules(Set<MapperRule> nestedMapperRules);
	
}
