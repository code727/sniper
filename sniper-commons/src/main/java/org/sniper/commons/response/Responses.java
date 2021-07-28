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

import org.sniper.commons.enums.http.HttpStatusEnum;
import org.sniper.commons.enums.status.ResponseStatusEnum;
import org.sniper.commons.util.AssertUtils;

/**
 * Response工厂类
 * @author  Daniele
 * @version 1.0
 */
public final class Responses {
	
	private Responses() {}
	
	/**
	 * 构造默认的Success响应对象
	 * @author Daniele 
	 * @return
	 */
	public static <T> Response<T> success() {
		return success((T) null);
	}
	
	/**
	 * 构造带数据的默认Success响应对象
	 * @author Daniele 
	 * @param data
	 * @return
	 */
	public static <T> Response<T> success(T data) {
		return success(Response.DEFAULT_SUCCESS_MESSAGE, data);
	}
	
	/**
	 * 根据消息和数据构造默认的Success响应对象
	 * @author Daniele 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> Response<T> success(String message, T data) {
		return build(Response.DEFAULT_SUCCESS_CODE, message, data);
	}
	
	/**
	 * 根据成功码和消息构造Success响应对象
	 * @author Daniele 
	 * @param successCode
	 * @param message
	 * @return
	 */
	public static <T> Response<T> success(int successCode, String message) {
		return success(successCode, message, null);
	}
	
	/**
	 * 根据响应状态枚举构造Success响应对象
	 * @author Daniele
	 * @param status
	 * @return
	 */
	public static <T> Response<T> success(ResponseStatusEnum status) {
		return success(status, null);
	}
	
	/**
	 * 根据响应状态枚举和数据构造Success响应对象
	 * @author Daniele
	 * @param status
	 * @param data
	 * @return
	 */
	public static <T> Response<T> success(ResponseStatusEnum status, T data) {
		AssertUtils.assertNotNull(status, "Response status must not be null");
		return success(status.getCode(), status.getMessage(), data);
	}
	
	/**
	 * 根据成功码、消息和数据构造Success响应对象
	 * @author Daniele
	 * @param successCode
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> Response<T> success(int successCode, String message, T data) {
		AssertUtils.assertTrue(Response.isSuccessCode(successCode), String.format("Invalid successCode '%s'", successCode));
		return build(successCode, message, data);
	}
		
	/**
	 * 根据HTTP状态枚举构造Success响应对象
	 * @author Daniele
	 * @param status
	 * @return
	 */
	public static <T> Response<T> success(HttpStatusEnum status) {
		return success(status, null);
	}
	
	/**
	 * 根据HTTP状态枚举和数据构造Success响应对象
	 * @author Daniele
	 * @param status
	 * @param data
	 * @return
	 */
	public static <T> Response<T> success(HttpStatusEnum status, T data) {
		AssertUtils.assertNotNull(status, "HTTP response status must not be null");
		return success(status.getCode(), status.getMessage(), data);
	}
	
	/**
	 * 构造默认的Error响应对象
	 * @author Daniele 
	 * @return
	 */
	public static <T> Response<T> error() {
		return error((T) null);
	}
	
	/**
	 * 构造带数据的默认Error响应对象
	 * @author Daniele 
	 * @param data
	 * @return
	 */
	public static <T> Response<T> error(T data) {
		return error(Response.DEFAULT_ERROR_MESSAGE, data);
	}
	
	/**
	 * 根据消息和数据构造默认的Error响应对象
	 * @author Daniele 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> Response<T> error(String message, T data) {
		return build(Response.DEFAULT_ERROR_CODE, message, data);
	}
	
	/**
	 * 根据错误码和消息构造Error响应对象
	 * @author Daniele 
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static <T> Response<T> error(int errorCode, String message) {
		return error(errorCode, message, null);
	}
	
	/**
	 * 根据错误码、消息和数据构造Error响应对象
	 * @author Daniele 
	 * @param errorCode
	 * @param data
	 * @return
	 */
	public static <T> Response<T> error(int errorCode, String message, T data) {
		AssertUtils.assertTrue(Response.isErrorCode(errorCode), String.format("Invalid errorCode '%s'", errorCode));
		return build(errorCode, message, data);
	}
	
	/**
	 * 根据响应状态枚举构造Error响应对象
	 * @author Daniele
	 * @param status
	 * @return
	 */
	public static <T> Response<T> error(ResponseStatusEnum status) {
		return error(status, null);
	}
	
	/**
	 * 根据响应状态枚举和数据构造Error响应对象
	 * @author Daniele
	 * @param status
	 * @param data
	 * @return
	 */
	public static <T> Response<T> error(ResponseStatusEnum status, T data) {
		AssertUtils.assertNotNull(status, "Response status must not be null");
		return error(status.getCode(), status.getMessage(), data);
	}
	
	/**
	 * 根据HTTP状态枚举构造Error响应对象
	 * @author Daniele
	 * @param status
	 * @return
	 */
	public static <T> Response<T> error(HttpStatusEnum status) {
		return error(status, null);
	}
	
	/**
	 * 根据HTTP状态枚举和数据构造Error响应对象
	 * @author Daniele
	 * @param status
	 * @param data
	 * @return
	 */
	public static <T> Response<T> error(HttpStatusEnum status, T data) {
		AssertUtils.assertNotNull(status, "HTTP response status must not be null");
		return error(status.getCode(), status.getMessage(), data);
	}
	
	/**
	 * 构造HTTP默认的Success响应对象
	 * @author Daniele 
	 * @return
	 */
	public static <T> Response<T> httpSuccess() {
		return httpSuccess(null);
	}
	
	/**
	 * 构造带数据的HTTP默认Success响应对象
	 * @author Daniele 
	 * @param data
	 * @return
	 */
	public static <T> Response<T> httpSuccess(T data) {
		return build(HttpStatusEnum.OK.getCode(), HttpStatusEnum.OK.getMessage(), data);
	}
	
	/**
	 * 构造HTTP默认的Error响应对象
	 * @author Daniele
	 * @return
	 */
	public static <T> Response<T> httpError() {
		return httpError(null);
	}
	
	/**
	 * 构造带数据的HTTP默认Error响应对象
	 * @author Daniele
	 * @param data
	 * @return
	 */
	public static <T> Response<T> httpError(T data) {
		return build(HttpStatusEnum.BAD_REQUEST.getCode(), HttpStatusEnum.BAD_REQUEST.getMessage(), data);
	}
	
	/**
	 * 根据响应状态枚举构造响应对象
	 * @author Daniele 
	 * @param status
	 * @return
	 */
	public static <T> Response<T> build(ResponseStatusEnum status) {
		return build(status, null);
	}
	
	/**
	 * 根据响应状态枚举和数据构造响应对象
	 * @author Daniele 
	 * @param status
	 * @param data
	 * @return
	 */
	public static <T> Response<T> build(ResponseStatusEnum status, T data) {
		AssertUtils.assertNotNull(status, "Response status must not be null");
		return build(status.getCode(), status.getMessage(), data);
	}
	
	/**
	 * 根据HTTP状态枚举构造响应对象
	 * @author Daniele
	 * @param status
	 * @return
	 */
	public static <T> Response<T> build(HttpStatusEnum status) {
		return build(status, null);
	}
	
	/**
	 * 根据HTTP状态枚举和数据构造响应对象
	 * @author Daniele
	 * @param status
	 * @param data
	 * @return
	 */
	public static <T> Response<T> build(HttpStatusEnum status, T data) {
		AssertUtils.assertNotNull(status, "HTTP response status must not be null");
		return build(status.getCode(), status.getMessage(), data);
	}
	
	/**
	 * 根据响应码和消息构造响应对象
	 * @author Daniele
	 * @param code
	 * @param message
	 * @return
	 */
	public static <T> Response<T> build(int code, String message) {
		return build(code, message, null);
	}
	
	/**
	 * 根据响应码、消息和数据构造响应对象
	 * @author Daniele
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> Response<T> build(int code, String message, T data) {
		return new Response<T>(code, message, data);
	}
	
}
