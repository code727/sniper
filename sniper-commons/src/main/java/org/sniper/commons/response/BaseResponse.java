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

import org.sniper.commons.constant.status.ExecutionStatus;
import org.sniper.commons.util.ObjectUtils;

/**
 * 基础响应实现类，不携带具体的消息和数据
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BaseResponse extends AbstractResponse<Integer> implements Serializable {
	
	private static final long serialVersionUID = -6718497429123443765L;

	/** 默认成功响应码 */
	public static final int DEFAULT_SUCCESS_CODE = ExecutionStatus.SUCCESS.getKey();
	
	/** 默认失败响应码  */
	public static final int DEFAULT_FAILED_CODE = ExecutionStatus.FAILED.getKey();
	
	/** 默认异常响应码  */
	public static final int DEFAULT_EXCEPTION_CODE = ExecutionStatus.EXCEPTION.getKey();
		
	public BaseResponse() {
		this(DEFAULT_SUCCESS_CODE);
	}
	
	public BaseResponse(GenericResponse<Integer> response) {
		this(response.getCode());
	}
	
	public BaseResponse(Integer code) {
		super(code);
	}
	
	@Override
	public boolean wasSuccess() {
		return matches(DEFAULT_SUCCESS_CODE);
	}

	@Override
	public boolean wasFailed() {
		return matches(DEFAULT_FAILED_CODE);
	}

	@Override
	public boolean wasException() {
		return matches(DEFAULT_EXCEPTION_CODE);
	}
	
	@Override
	protected boolean matches(Integer code) {
		return ObjectUtils.equals(code, this.getCode());
	}
			
}
