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
 * Create Date : 2017-3-16
 */

package org.workin.commons.response;

/**
 * 全量响应实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 * @param <T>
 */
public class BaseFullResponse<T> extends AbstractDataResponse<T> implements FullResponse<T> {

	/** 响应消息 */
	private String message;
	
	public BaseFullResponse() {
		super();
	}
	
	public BaseFullResponse(T data) {
		this(DEFAULT_SUCCESS_STATUS, DEFAULT_SUCCESS_MESSAGE, data);
	}
	
	public BaseFullResponse(FullResponse<T> response) {
		this(response.getCode(), response.getMessage(), response.getData());
	}
	
	public BaseFullResponse(MessageResponse response, T data) {
		this(response.getCode(), response.getMessage(), data);
	}
		
	public BaseFullResponse(String code, String message, T data) {
		super(code, data);
		this.message = message;
	}
		
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 创建具备默认成功状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> success(T data) {
		return successByCode(DEFAULT_SUCCESS_STATUS, data);
	}
	
	/**
	 * 创建具备自定义成功状态码和默认信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> successByCode(String code, T data) {
		return success(code, DEFAULT_SUCCESS_MESSAGE, data);
	}
	
	/**
	 * 创建具备默认成功状态码和自定义信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> successByMessage(String message, T data) {
		return success(DEFAULT_SUCCESS_STATUS, message, data);
	}
	
	/**
	 * 创建具备自定义成功状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> success(String code, String message, T data) {
		return new BaseFullResponse<T>(code, message, data);
	}
	
	/**
	 * 创建具备默认失败状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> failed(T data) {
		return failedByCode(DEFAULT_FAILED_STATUS, data);
	}
		
	/**
	 * 创建具备自定义失败状态码和默认信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> failedByCode(String code, T data) {
		return failed(code, DEFAULT_FAILED_MESSAGE, data);
	}
	
	/**
	 * 创建具备默认失败状态码和自定义信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> failedByMessage(String message, T data) {
		return failed(DEFAULT_FAILED_STATUS, message, data);
	}
	
	/**
	 * 创建具备自定义失败状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> failed(String code, String message, T data) {
		return new BaseFullResponse<T>(code, message, data);
	}
	
	/**
	 * 创建具备默认异常状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> exception(T data) {
		return exceptionByCode(DEFAULT_EXCEPTION_STATUS, data);
	}
	
	/**
	 * 创建具备自定义异常状态码和默认信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> exceptionByCode(String code, T data) {
		return exception(code, DEFAULT_EXCEPTION_MESSAGE, data);
	}
	
	/**
	 * 创建具备默认异常状态码和自定义信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> exceptionByMessage(String message, T data) {
		return exception(DEFAULT_EXCEPTION_STATUS, message, data);
	}
	
	/**
	 * 创建具备自定义异常状态码和信息的全量响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static <T> BaseFullResponse<T> exception(String code, String message, T data) {
		return new BaseFullResponse<T>(code, message, data);
	}
	
}
