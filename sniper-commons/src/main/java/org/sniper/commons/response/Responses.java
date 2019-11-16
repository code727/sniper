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

package org.sniper.commons.response;

/**
 * 响应对象工厂实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class Responses {
	
	private Responses() {}
	
	/**
	 * 创建具备默认成功状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static BaseResponse successResponse() {
		return successResponse(BaseResponse.DEFAULT_SUCCESS_CODE);
	}
	
	/**
	 * 创建具备自定义成功状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static BaseResponse successResponse(Integer code) {
		return new BaseResponse(code);
	}
	
	/**
	 * 创建具备默认失败状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static BaseResponse failedResponse() {
		return failedResponse(BaseResponse.DEFAULT_FAILED_CODE);
	}
	
	/**
	 * 创建具备自定义失败状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static BaseResponse failedResponse(Integer code) {
		return new BaseResponse(code);
	}
	
	/**
	 * 创建具备默认异常状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static BaseResponse exceptionResponse() {
		return exceptionResponse(BaseResponse.DEFAULT_EXCEPTION_CODE);
	}
	
	/**
	 * 创建具备自定义异常状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static BaseResponse exceptionResponse(Integer code) {
		return new BaseResponse(code);
	}
	
	/**
	 * 创建具备默认成功状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static MessageResponse successMessageResponse() {
		return successMessageResponse(BaseResponse.DEFAULT_SUCCESS_CODE);
	}
	
	/**
	 * 创建具备默认成功状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static MessageResponse successMessageResponse(String message) {
		return successMessageResponse(BaseResponse.DEFAULT_SUCCESS_CODE, message);
	}
	
	/**
	 * 创建具备自定义成功状态码和默认信息的成功响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static MessageResponse successMessageResponse(Integer code) {
		return successMessageResponse(code, MessageResponse.DEFAULT_SUCCESS_MESSAGE);
	}
	
	/**
	 * 创建具备自定义成功状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @return
	 */
	public static MessageResponse successMessageResponse(Integer code, String message) {
		return new MessageResponse(code, message);
	}
	
	/**
	 * 创建具备默认失败状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static MessageResponse failedMessageResponse() {
		return failedMessageResponse(BaseResponse.DEFAULT_FAILED_CODE);
	}
	
	/**
	 * 创建具备默认失败状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static MessageResponse failedMessageResponse(String message) {
		return failedMessageResponse(BaseResponse.DEFAULT_FAILED_CODE, message);
	}
	
	/**
	 * 创建具备自定义失败状态码和默认信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static MessageResponse failedMessageResponse(Integer code) {
		return failedMessageResponse(code, MessageResponse.DEFAULT_FAILED_MESSAGE);
	}
	
	/**
	 * 创建具备自定义失败状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @return
	 */
	public static MessageResponse failedMessageResponse(Integer code, String message) {
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
		return exceptionMessageResponse(BaseResponse.DEFAULT_EXCEPTION_CODE);
	}
	
	/**
	 * 创建具备默认异常状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static MessageResponse exceptionMessageResponse(String message) {
		return exceptionMessageResponse(BaseResponse.DEFAULT_EXCEPTION_CODE, message);
	}
	
	/**
	 * 创建具备自定义异常状态码和默认信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static MessageResponse exceptionMessageResponse(Integer code) {
		return exceptionMessageResponse(code, MessageResponse.DEFAULT_EXCEPTION_MESSAGE);
	}
	
	/**
	 * 创建具备自定义异常状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @return
	 */
	public static MessageResponse exceptionMessageResponse(Integer code, String message) {
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
		return successDataResponse(BaseResponse.DEFAULT_SUCCESS_CODE, data);
	}
	
	/**
	 * 创建具备自定义成功状态码的空数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> DataResponse<T> successDataResponseByCode(Integer code) {
		return successDataResponse(code, null);
	}
	
	/**
	 * 创建具备自定义成功状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> DataResponse<T> successDataResponse(Integer code, T data) {
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
		return failedDataResponse(BaseResponse.DEFAULT_FAILED_CODE, data);
	}
	
	/**
	 * 创建具备指定失败状态码的空数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> DataResponse<T> failedDataResponseByCode(Integer code) {
		return failedDataResponse(code, null);
	}
	
	/**
	 * 创建具备自定义失败状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> DataResponse<T> failedDataResponse(Integer code, T data) {
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
		return exceptionDataResponse(BaseResponse.DEFAULT_EXCEPTION_CODE, data);
	}
	
	/**
	 * 创建具备自定义异常状态码的空数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> DataResponse<T> exceptionDataResponseByCode(Integer code) {
		return exceptionDataResponse(code, null);
	}
	
	/**
	 * 创建具备自定义异常状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> DataResponse<T> exceptionDataResponse(Integer code, T data) {
		return new DataResponse<T>(code, data);
	}
	
	/**
	 * 创建具备默认成功状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponse() {
		return successFullResponse(null);
	}
	
	/**
	 * 创建具备默认成功状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponse(T data) {
		return successFullResponse(BaseResponse.DEFAULT_SUCCESS_CODE, data);
	}
	
	/**
	 * 创建具备自定义成功状态码和默认信息的空数据全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponseByCode(Integer code) {
		return successFullResponse(code, null);
	}
	
	/**
	 * 创建具备默认成功状态码和自定义信息的空数据全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponseByMessage(String message) {
		return successFullResponse(message, null);
	}
	
	/**
	 * 创建具备自定义成功状态码和默认信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponse(Integer code, T data) {
		return successFullResponse(code, MessageResponse.DEFAULT_SUCCESS_MESSAGE, data);
	}
	
	/**
	 * 创建具备默认成功状态码和自定义信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponse(String message, T data) {
		return successFullResponse(BaseResponse.DEFAULT_SUCCESS_CODE, message, data);
	}
	
	/**
	 * 创建具备自定义成功状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> successFullResponse(Integer code, String message, T data) {
		return new FullResponse<T>(code, message, data);
	}
	
	/**
	 * 创建具备默认失败状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> FullResponse<T> failedFullResponse() {
		return failedFullResponse(null);
	}
	
	/**
	 * 创建具备默认失败状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> failedFullResponse(T data) {
		return failedFullResponseByCode(BaseResponse.DEFAULT_FAILED_CODE, data);
	}
	
	/**
	 * 创建具备自定义失败状态码和默认信息的空数据全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> FullResponse<T> failedFullResponseByCode(Integer code) {
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
	public static <T> FullResponse<T> failedFullResponseByCode(Integer code, T data) {
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
		return failedFullResponse(BaseResponse.DEFAULT_FAILED_CODE, message, data);
	}
	
	/**
	 * 创建具备自定义失败状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> failedFullResponse(Integer code, String message, T data) {
		return new FullResponse<T>(code, message, data);
	}
	
	/**
	 * 创建具备默认异常状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <T> FullResponse<T> exceptionFullResponse() {
		return exceptionFullResponse(null);
	}
	
	/**
	 * 创建具备默认异常状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> exceptionFullResponse(T data) {
		return exceptionFullResponseByCode(BaseResponse.DEFAULT_FAILED_CODE, data);
	}
	
	/**
	 * 创建具备自定义异常状态码和默认信息的空数据全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static <T> FullResponse<T> exceptionFullResponseByCode(Integer code) {
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
	public static <T> FullResponse<T> exceptionFullResponseByCode(Integer code, T data) {
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
		return exceptionFullResponse(BaseResponse.DEFAULT_EXCEPTION_CODE, message, data);
	}
	
	/**
	 * 创建具备自定义异常状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> FullResponse<T> exceptionFullResponse(Integer code, String message, T data) {
		return new FullResponse<T>(code, message, data);
	}
	
}
