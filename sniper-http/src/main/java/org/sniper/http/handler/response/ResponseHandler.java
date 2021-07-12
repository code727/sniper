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
 * Create Date : 2017年5月26日
 */

package org.sniper.http.handler.response;

/**
 * 响应处理器
 * @author  Daniele
 * @version 1.0
 */
public interface ResponseHandler {
	
	/**
	 * 处理字符串响应数据后返回最终结果
	 * @author Daniele 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public <T> T handleResponse(String response) throws Exception;
	
}
