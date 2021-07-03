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
 * Create Date : 2021年7月3日
 */

package org.sniper.commons.response;

import org.sniper.commons.constant.status.ResponseStatus;

/**
 * 基础响应对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class Response<T> extends AbstractDatamationResponse<Integer, T> {
	
	private static final long serialVersionUID = 4441492052857555509L;

	/** 默认成功响应码 */
	protected static final int DEFAULT_SUCCESS_CODE = ResponseStatus.SUCCESS.getKey();
	
	/** 默认失败响应码  */
	protected static final int DEFAULT_FAILED_CODE = ResponseStatus.FAILED.getKey();
	
	/** 默认异常响应码  */
	protected static final int DEFAULT_EXCEPTION_CODE = ResponseStatus.EXCEPTION.getKey();
	
	public Response() {
		this(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE);
	}
	
	public Response(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public boolean wasSuccess() {
		return matches(DEFAULT_SUCCESS_CODE);
	}
	
	@Override
	public boolean wasFailed() {
		return this.code > DEFAULT_SUCCESS_CODE;
	}
	
	@Override
	public boolean wasException() {
		return this.code < DEFAULT_SUCCESS_CODE;
	}
	
}
