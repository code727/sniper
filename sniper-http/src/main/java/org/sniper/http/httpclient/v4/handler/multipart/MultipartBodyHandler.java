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
 * Create Date : 2017-10-13
 */

package org.sniper.http.httpclient.v4.handler.multipart;

import java.util.List;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

/**
 * Multipart请求体处理器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface MultipartBodyHandler {
	
	/**
	 * 获取当前处理器支持的所有Body类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<Class<?>> getSupportedBodyTypes();
		
	/**
	 * 判断当前处理器是否支持对指定请求体的处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param requestBody
	 * @return
	 */
	public boolean support(Object requestBody);
	
	/**
	 * 指定的请求体是否可以被当前处理器处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param requestBody
	 * @return
	 */
	public boolean canHandle(Object requestBody);
	
	/**
	 * 将requestBody和contentType以指定的参数名称添加到MultipartEntityBuilder对象中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name body参数名称
	 * @param requestBody 请求体
	 * @param contentType 请求类型
	 * @throws Exception
	 */
	public void addBodyPart(MultipartEntityBuilder builder, String name, Object requestBody, ContentType contentType) throws Exception;

}
