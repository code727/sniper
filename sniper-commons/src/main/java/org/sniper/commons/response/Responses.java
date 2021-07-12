/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-7-5
 */

package org.sniper.commons.response;

import org.sniper.commons.constant.status.ResponseStatus;
import org.sniper.commons.util.AssertUtils;

/**
 * Response工厂类
 * @author  Daniele
 * @version 1.0
 */
public final class Responses {
	
	private Responses() {}
	
	/**
	 * 构造success响应对象
	 * @author Daniele 
	 * @return
	 */
	public static <T> Response<T> success() {
		return success(null);
	}
	
	/**
	 * 构造带数据的success响应对象
	 * @author Daniele 
	 * @param data
	 * @return
	 */
	public static <T> Response<T> success(T data) {
		return success(Response.DEFAULT_SUCCESS_MESSAGE, data);
	}
		
	/**
	 * 构造带自定义消息和数据的success响应对象
	 * @author Daniele 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> Response<T> success(String message, T data) {
		return new Response<T>(Response.DEFAULT_SUCCESS_CODE, message, data);
	}
		
	/**
	 * 构造error响应对象
	 * @author Daniele 
	 * @return
	 */
	public static <T> Response<T> error() {
		return error(null);
	}
	
	/**
	 * 构造带数据的error响应对象
	 * @author Daniele 
	 * @param data
	 * @return
	 */
	public static <T> Response<T> error(T data) {
		return error(Response.DEFAULT_ERROR_MESSAGE, data);
	}
	
	/**
	 * 构造带消息的自定义error响应对象
	 * @author Daniele 
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static <T> Response<T> error(int errorCode, String message) {
		return error(errorCode, message, null);
	}
	
	/**
	 * 构造带自定义消息和数据的error响应对象
	 * @author Daniele 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> Response<T> error(String message, T data) {
		return new Response<T>(Response.DEFAULT_ERROR_CODE, message, data);
	}
	
	/**
	 * 构造带消息和数据的自定义error响应对象
	 * @author Daniele 
	 * @param errorCode
	 * @param data
	 * @return
	 */
	public static <T> Response<T> error(int errorCode, String message, T data) {
		AssertUtils.assertTrue(Response.errored(errorCode),
				String.format("Invalid response errorCode '%s'", errorCode));
		return new Response<T>(errorCode, Response.DEFAULT_ERROR_MESSAGE, data);
	}
	
	/**
	 * 根据指定的响应状态构建响应对象
	 * @author Daniele 
	 * @param status
	 * @return
	 */
	public static <T> Response<T> buildResponse(ResponseStatus status) {
		return buildResponse(status, null);
	}
	
	/**
	 * 根据指定的响应状态构建带数据的响应对象
	 * @author Daniele 
	 * @param status
	 * @param data
	 * @return
	 */
	public static <T> Response<T> buildResponse(ResponseStatus status, T data) {
		AssertUtils.assertNotNull(status, "Response status must not be null");
		return new Response<T>(status.getKey(), status.getMessage(), data);
	}
	
}
