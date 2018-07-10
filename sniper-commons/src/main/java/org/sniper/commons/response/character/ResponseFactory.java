/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-7-9
 */

package org.sniper.commons.response.character;

/**
 * 响应对应工厂实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ResponseFactory {
	
	private ResponseFactory() {}
	
	/**
	 * 创建具备默认成功状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Response successResponse() {
		return successResponse(Response.DEFAULT_SUCCESS_CODE);
	}
	
	/**
	 * 创建具备自定义成功状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static Response successResponse(String code) {
		return new Response(code);
	}
	
	/**
	 * 创建具备默认失败状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Response failedResponse() {
		return failedResponse(Response.DEFAULT_FAILED_CODE);
	}
	
	/**
	 * 创建具备自定义失败状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Response failedResponse(String code) {
		return new Response(code);
	}
	
	/**
	 * 创建具备默认异常状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Response exceptionResponse() {
		return exceptionResponse(Response.DEFAULT_EXCEPTION_CODE);
	}
	
	/**
	 * 创建具备自定义异常状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static Response exceptionResponse(String code) {
		return new Response(code);
	}
	
	/**
	 * 创建具备默认成功状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static MessageResponse successMessageResponse() {
		return successMessageResponseByCode(Response.DEFAULT_SUCCESS_CODE);
	}
	
	/**
	 * 创建具备默认成功状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static MessageResponse successMessageResponse(String message) {
		return successMessageResponse(Response.DEFAULT_SUCCESS_CODE, message);
	}
	
	/**
	 * 创建具备自定义成功状态码和默认信息的成功响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static MessageResponse successMessageResponseByCode(String code) {
		return successMessageResponse(code, MessageResponse.DEFAULT_SUCCESS_MESSAGE);
	}
	
	/**
	 * 创建具备自定义成功状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @return
	 */
	public static MessageResponse successMessageResponse(String code, String message) {
		return new MessageResponse(code, message);
	}
	
	/**
	 * 创建具备默认失败状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static MessageResponse failedMessageResponse() {
		return failedMessageResponseByCode(Response.DEFAULT_FAILED_CODE);
	}
	
	/**
	 * 创建具备默认失败状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static MessageResponse failedMessageResponse(String message) {
		return failedMessageResponse(Response.DEFAULT_FAILED_CODE, message);
	}
	
	/**
	 * 创建具备自定义失败状态码和默认信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static MessageResponse failedMessageResponseByCode(String code) {
		return failedMessageResponse(code, MessageResponse.DEFAULT_FAILED_MESSAGE);
	}
	
	/**
	 * 创建具备自定义失败状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @return
	 */
	public static MessageResponse failedMessageResponse(String code, String message) {
		return new MessageResponse(code, message);
	}
	
	/**
	 * 创建具备默认异常状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @return
	 */
	public static MessageResponse exceptionMessageResponse() {
		return exceptionMessageResponseByCode(Response.DEFAULT_EXCEPTION_CODE);
	}
	
	/**
	 * 创建具备默认异常状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static MessageResponse exceptionMessageResponse(String message) {
		return exceptionMessageResponse(Response.DEFAULT_EXCEPTION_CODE, message);
	}
	
	/**
	 * 创建具备自定义异常状态码和默认信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static MessageResponse exceptionMessageResponseByCode(String code) {
		return exceptionMessageResponse(code, MessageResponse.DEFAULT_EXCEPTION_MESSAGE);
	}
	
	/**
	 * 创建具备自定义异常状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @return
	 */
	public static MessageResponse exceptionMessageResponse(String code, String message) {
		return new MessageResponse(code, message);
	}
	
	/**
	 * 创建具备默认成功状态码的空数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> DataResponse<T> successDataResponse() {
		return successDataResponse(null);
	}
	
	/**
	 * 创建具备默认成功状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> DataResponse<T> successDataResponse(T data) {
		return successDataResponse(Response.DEFAULT_SUCCESS_CODE, data);
	}
	
	/**
	 * 创建具备自定义成功状态码的空数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> DataResponse<T> successDataResponseByCode(String code) {
		return successDataResponse(code, null);
	}
	
	/**
	 * 创建具备自定义成功状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> DataResponse<T> successDataResponse(String code, T data) {
		return new DataResponse<T>(code, data);
	}
	
	/**
	 * 创建具备默认失败状态码的空数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> DataResponse<T> failedDataResponse() {
		return failedDataResponse(null);
	}
	
	/**
	 * 创建具备默认失败状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> DataResponse<T> failedDataResponse(T data) {
		return failedDataResponse(Response.DEFAULT_FAILED_CODE, data);
	}
	
	/**
	 * 创建具备指定失败状态码的空数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> DataResponse<T> failedDataResponseByCode(String code) {
		return failedDataResponse(code, null);
	}
	
	/**
	 * 创建具备自定义失败状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> DataResponse<T> failedDataResponse(String code, T data) {
		return new DataResponse<T>(code, data);
	}
	
	/**
	 * 创建具备默认异常状态码的空数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> DataResponse<T> exceptionDataResponse() {
		return exceptionDataResponse(null);
	}
	
	/**
	 * 创建具备默认异常状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> DataResponse<T> exceptionDataResponse(T data) {
		return exceptionDataResponse(Response.DEFAULT_EXCEPTION_CODE, data);
	}
	
	/**
	 * 创建具备自定义异常状态码的空数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> DataResponse<T> exceptionDataResponseByCode(String code) {
		return exceptionDataResponse(code, null);
	}
	
	/**
	 * 创建具备自定义异常状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> DataResponse<T> exceptionDataResponse(String code, T data) {
		return new DataResponse<T>(code, data);
	}
	
	/**
	 * 创建具备默认成功状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponse(T data) {
		return successFullResponseByCode(Response.DEFAULT_SUCCESS_CODE, data);
	}
	
	/**
	 * 创建具备自定义成功状态码和默认信息的空数据全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponseByCode(String code) {
		return successFullResponseByCode(code, null);
	}
	
	/**
	 * 创建具备默认成功状态码和自定义信息的空数据全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponseByMessage(String message) {
		return successFullResponseByMessage(message, null);
	}
	
	/**
	 * 创建具备自定义成功状态码和默认信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponseByCode(String code, T data) {
		return successFullResponse(code, MessageResponse.DEFAULT_SUCCESS_MESSAGE, data);
	}
	
	/**
	 * 创建具备默认成功状态码和自定义信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponseByMessage(String message, T data) {
		return successFullResponse(Response.DEFAULT_SUCCESS_CODE, message, data);
	}
	
	/**
	 * 创建具备自定义成功状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponse(String code, String message, T data) {
		return new FullResponse<T>(code, message, data);
	}
	
	/**
	 * 创建具备默认失败状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> failedFullResponse(T data) {
		return failedFullResponseByCode(Response.DEFAULT_FAILED_CODE, data);
	}
	
	/**
	 * 创建具备自定义失败状态码和默认信息的空数据全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> FullResponse<T> failedFullResponseByCode(String code) {
		return failedFullResponseByCode(code, null);
	}
	
	/**
	 * 创建具备默认失败状态码和自定义信息的空数据全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static <T> FullResponse<T> failedFullResponseByMessage(String message) {
		return failedFullResponseByMessage(message, null);
	}
	
	/**
	 * 创建具备自定义失败状态码和默认信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> failedFullResponseByCode(String code, T data) {
		return failedFullResponse(code, MessageResponse.DEFAULT_FAILED_MESSAGE, data);
	}
	
	/**
	 * 创建具备默认失败状态码和自定义信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> failedFullResponseByMessage(String message, T data) {
		return failedFullResponse(Response.DEFAULT_FAILED_CODE, message, data);
	}
	
	/**
	 * 创建具备自定义失败状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> failedFullResponse(String code, String message, T data) {
		return new FullResponse<T>(code, message, data);
	}
	
	/**
	 * 创建具备默认异常状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> exceptionFullResponse(T data) {
		return exceptionFullResponseByCode(Response.DEFAULT_FAILED_CODE, data);
	}
	
	/**
	 * 创建具备自定义异常状态码和默认信息的空数据全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> FullResponse<T> exceptionFullResponseByCode(String code) {
		return exceptionFullResponseByCode(code, null);
	}
	
	/**
	 * 创建具备默认异常状态码和自定义信息的空数据全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static <T> FullResponse<T> exceptionFullResponseByMessage(String message) {
		return exceptionFullResponseByMessage(message, null);
	}
	
	/**
	 * 创建具备自定义异常状态码和默认信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> exceptionFullResponseByCode(String code, T data) {
		return exceptionFullResponse(code, MessageResponse.DEFAULT_EXCEPTION_MESSAGE, data);
	}
	
	/**
	 * 创建具备默认异常状态码和自定义信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> exceptionFullResponseByMessage(String message, T data) {
		return exceptionFullResponse(Response.DEFAULT_EXCEPTION_CODE, message, data);
	}
	
	/**
	 * 创建具备自定义异常状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> exceptionFullResponse(String code, String message, T data) {
		return new FullResponse<T>(code, message, data);
	}
	
}
