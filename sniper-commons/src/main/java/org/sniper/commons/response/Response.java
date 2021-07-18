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
 * Create Date : 2021-7-3
 */

package org.sniper.commons.response;

import org.sniper.commons.enums.status.ResponseStatusEnum;
import org.sniper.commons.util.StringUtils;

/**
 * 响应对象
 * @author  Daniele
 * @version 1.0
 */
public class Response<T> extends AbstractDatamationResponse<Integer, T> {
	
	private static final long serialVersionUID = -2728403977784339577L;

	/** 默认成功响应码 */
	protected static final int DEFAULT_SUCCESS_CODE = ResponseStatusEnum.SUCCESS.getKey();
	
	/** 默认错误响应码  */
	protected static final int DEFAULT_ERROR_CODE = ResponseStatusEnum.ERROR.getKey();
	
	public Response() {
		this(null);
	}
	
	public Response(T data) {
		this(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, data);
	}
	
	public Response(int code, String message) {
		this(code, message, null);
	}
	
	public Response(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	@Override
	public boolean successed() {
		return successed(this.code);
	}
		
	@Override
	public boolean errored() {
		return errored(this.code);
	}
	
	/**
	 * 判断是否为success码
	 * @author Daniele 
	 * @param code
	 * @return
	 */
	static boolean successed(int code) {
		// 自定义成功码必须等于默认值
		return code == DEFAULT_SUCCESS_CODE;
	}
		
	/**
	 * 判断是否为error码
	 * @author Daniele 
	 * @param code
	 * @return
	 */
	static boolean errored(int code) {
		// 自定义错误码不能等于成功码默认值
		return code != DEFAULT_SUCCESS_CODE;
	}

	/**
	 * 格式化响应消息
	 * @author Daniele
	 * @param args
	 * @return 
	 */
	@Override
	public MessagingResponse<Integer> format(Object... args) {
		if (StringUtils.isNotBlank(message)) {
			this.message = String.format(this.message, args);
		}
		return this;
	}
	
	
	
}
