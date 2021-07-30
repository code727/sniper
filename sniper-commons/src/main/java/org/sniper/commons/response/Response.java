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

/**
 * 响应对象
 * @author  Daniele
 * @version 1.0
 */
public final class Response<T> extends AbstractDatamationResponse<Integer, T> {
	
	private static final long serialVersionUID = -2728403977784339577L;

	/** 默认成功响应码 */
	public static final int DEFAULT_SUCCESS_CODE = ResponseStatusEnum.SUCCESS.getCode();
	
	/** 默认错误响应码  */
	public static final int DEFAULT_ERROR_CODE = ResponseStatusEnum.ERROR.getCode();
	
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
		return isSuccessCode(code);
	}
		
	@Override
	public boolean errored() {
		return isErrorCode(code);
	}
	
	/**
	 * 判断是否为success码
	 * @author Daniele 
	 * @param code
	 * @return
	 */
	public static boolean isSuccessCode(int code) {
		return ResponseStatusEnum.isSuccessful(code);
	}
			
	/**
	 * 判断是否为error码
	 * @author Daniele 
	 * @param code
	 * @return
	 */
	public static boolean isErrorCode(int code) {
		return ResponseStatusEnum.isIncorrect(code);
	}
		
}
