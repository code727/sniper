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

import java.io.IOException;
import java.util.Map;

/**
 * 已映射的HTTP请求发送器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface MappedHttpSender {
	
	/**
	 * 执行指定名称且未带参数的HTTP表单请求，并返回响应处理结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public <T> T request(String name) throws Exception;
	
	/**
	 * 执行指定名称且带参数的HTTP表单请求，并返回响应处理结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public <V, T> T request(String name, Map<String, V> parameters) throws Exception;
	
	/**
	 * 执行指定名称且带参数的HTTP表单请求，并返回响应处理结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param parameter
	 * @return
	 * @throws IOException
	 */
	public <T> T request(String name, Object parameter) throws Exception;
	
	/**
	 * 执行指定名称且带RequestBody的HTTP表单请求，并返回响应处理结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	public <T> T requestByBody(String name, Object requestBody) throws Exception;
	
	/**
	 * 执行指定名称且带RequestBody和参数的HTTP表单请求，并返回响应处理结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param requestBody
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public <V, T> T requestByBody(String name, Object requestBody, Map<String, V> parameters) throws Exception;
	
	/**
	 * 执行指定名称且带RequestBody和参数的HTTP表单请求，并返回响应处理结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param requestBody
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public <T> T requestByBody(String name, Object requestBody, Object parameter) throws Exception;

}
