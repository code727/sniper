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
 * Create Date : 2017-5-27
 */

package org.sniper.http.handler.response.typed;

import org.sniper.http.handler.response.ResponseHandler;

/**
 * 类型化响应处理器接口
 * @author  Daniele
 * @version 1.0
 */
public interface TypedResponseHandler extends ResponseHandler {
	
	/**
	 * 处理字符串响应数据后返回指定类型的最终结果
	 * @author Daniele 
	 * @param response
	 * @param targetType 最终结果的类型
	 * @return
	 * @throws Exception
	 */
	public <T> T handleResponse(String response, Class<T> targetType) throws Exception;
	
}
