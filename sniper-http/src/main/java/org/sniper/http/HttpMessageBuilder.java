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
 * Create Date : 2017-9-27
 */

package org.sniper.http;

import java.util.List;

import org.sniper.http.headers.MediaType;

/**
 * HTTP消息构建器
 * @author  Daniele
 * @version 1.0
 */
public interface HttpMessageBuilder<T> {
		
	/**
	 * 获取当前构建器支持的所有Media类型
	 * @author Daniele 
	 * @return
	 */
	public List<MediaType> getSupportedMediaTypes();
	
	/**
	 * 获取当前构建器支持的默认Media类型
	 * @author Daniele 
	 * @return
	 */
	public MediaType getDefaultMediaType();
	
	/**
	 * 判断当前构建器是否支持指定请求体和Media类型的处理
	 * @author Daniele 
	 * @param requestBody
	 * @return
	 */
	public boolean support(Object requestBody, MediaType mediaType);
	
	/**
	 * 构建消息结果
	 * @author Daniele 
	 * @param url
	 * @param requestBody
	 * @param mediaType
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public T build(String url, Object requestBody, MediaType mediaType, String encoding) throws Exception;
}
