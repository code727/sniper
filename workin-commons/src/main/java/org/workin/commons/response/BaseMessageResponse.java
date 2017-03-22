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

import org.workin.commons.enums.status.BizStatus;

/**
 * 基本的消息响应实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BaseMessageResponse extends AbstractResponse implements MessageResponse {
	
	/** 响应消息 */
	private String message;
	
	public BaseMessageResponse() {
		this(BizStatus.SUCCESS.getKey(), BizStatus.SUCCESS.getMessage());
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
		return successByCode(BizStatus.SUCCESS.getKey());
	}
	
	/**
	 * 创建具备自定义成功状态码和默认信息的成功响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse successByCode(String code) {
		return success(code, BizStatus.SUCCESS.getMessage());
	}
	
	/**
	 * 创建具备默认成功状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse successByMessage(String message) {
		return success(BizStatus.SUCCESS.getKey(), message);
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
		return failedByCode(BizStatus.FAILED.getKey());
	}
	
	/**
	 * 创建具备自定义失败状态码和默认信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static BaseMessageResponse failedByCode(String code) {
		return failed(code, BizStatus.FAILED.getMessage());
	}
	
	/**
	 * 创建具备默认失败状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse failedByMessage(String message) {
		return failed(BizStatus.FAILED.getKey(), message);
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
		return exceptionByCode(BizStatus.EXCEPTION.getKey());
	}
	
	/**
	 * 创建具备自定义异常状态码和默认信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static BaseMessageResponse exceptionByCode(String code) {
		return exception(code, BizStatus.EXCEPTION.getMessage());
	}
	
	/**
	 * 创建具备默认异常状态码和自定义信息的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param message
	 * @return
	 */
	public static BaseMessageResponse exceptionByMessage(String message) {
		return exception(BizStatus.EXCEPTION.getKey(), message);
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
