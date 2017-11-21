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

package org.sniper.commons.response;

import java.io.Serializable;

/**
 * 基本的消息响应实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BaseMessageResponse extends AbstractResponse implements Serializable, MessageResponse {
	
	private static final long serialVersionUID = -3819338460091380325L;
	
	/** 响应消息 */
	private String message;
	
	public BaseMessageResponse() {
		this(DEFAULT_SUCCESS_STATUS, DEFAULT_SUCCESS_MESSAGE);
	}
	
	public BaseMessageResponse(MessageResponse response) {
		this(response.getCode(), response.getMessage());
	}
		
	public BaseMessageResponse(String code, String message) {
		super(code);
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
	 * 创建具备默认成功状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static BaseMessageResponse success() {
		return successByCode(DEFAULT_SUCCESS_STATUS);
	}
	
	/**
	 * 创建具备自定义成功状态码和默认信息的成功响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse successByCode(String code) {
		return success(code, DEFAULT_SUCCESS_MESSAGE);
	}
	
	/**
	 * 创建具备默认成功状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse successByMessage(String message) {
		return success(DEFAULT_SUCCESS_STATUS, message);
	}
	
	/**
	 * 创建具备自定义成功状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse success(String code, String message) {
		return new BaseMessageResponse(code, message);
	}
	
	/**
	 * 创建具备默认失败状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static BaseMessageResponse failed() {
		return failedByCode(DEFAULT_FAILED_STATUS);
	}
	
	/**
	 * 创建具备自定义失败状态码和默认信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static BaseMessageResponse failedByCode(String code) {
		return failed(code, DEFAULT_FAILED_MESSAGE);
	}
	
	/**
	 * 创建具备默认失败状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse failedByMessage(String message) {
		return failed(DEFAULT_FAILED_STATUS, message);
	}
	
	/**
	 * 创建具备自定义失败状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse failed(String code, String message) {
		return new BaseMessageResponse(code, message);
	}
	
	/**
	 * 创建具备默认异常状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse exception() {
		return exceptionByCode(DEFAULT_EXCEPTION_STATUS);
	}
	
	/**
	 * 创建具备自定义异常状态码和默认信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static BaseMessageResponse exceptionByCode(String code) {
		return exception(code, DEFAULT_EXCEPTION_MESSAGE);
	}
	
	/**
	 * 创建具备默认异常状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse exceptionByMessage(String message) {
		return exception(DEFAULT_EXCEPTION_STATUS, message);
	}
	
	/**
	 * 创建具备自定义异常状态码和信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse exception(String code, String message) {
		return new BaseMessageResponse(code, message);
	}
			
}
