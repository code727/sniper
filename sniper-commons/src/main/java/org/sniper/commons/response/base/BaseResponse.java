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

package org.sniper.commons.response.base;

import java.io.Serializable;

import org.sniper.commons.enums.status.BizStatus;
import org.sniper.commons.response.AbstractResponse;
import org.sniper.commons.response.Response;
import org.sniper.commons.util.StringUtils;

/**
 * 基本的响应实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BaseResponse extends AbstractResponse<String> implements Serializable {
	
	private static final long serialVersionUID = 2773282924720586568L;
	
	/** 默认成功消息 */
	public static final String DEFAULT_SUCCESS_CODE = BizStatus.SUCCESS.getKey();
	
	/** 默认失败消息 */
	public static final String DEFAULT_FAILED_CODE = BizStatus.FAILED.getKey();
	
	/** 默认异常消息 */
	public static final String DEFAULT_EXCEPTION_CODE = BizStatus.EXCEPTION.getKey();
		
	public BaseResponse() {
		this(DEFAULT_SUCCESS_CODE);
	}
	
	public BaseResponse(Response<?> response) {
		// 要求response对象以及code值不为空
		this(response.getCode().toString());
	}
	
	public BaseResponse(String code) {
		super(code);
	}
	
	@Override
	public boolean wasSuccess() {
		return isMatchStatus(DEFAULT_SUCCESS_CODE);
	}

	@Override
	public boolean wasFailed() {
		return isMatchStatus(DEFAULT_FAILED_CODE);
	}

	@Override
	public boolean wasException() {
		return isMatchStatus(DEFAULT_EXCEPTION_CODE);
	}
	
	@Override
	protected boolean isMatchStatus(String code) {
		return StringUtils.equalsIgnoreCase(code, this.getCode());
	}
	
	/**
	 * 创建具备默认成功状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static BaseResponse success() {
		return success(DEFAULT_SUCCESS_CODE);
	}
	
	/**
	 * 创建具备自定义成功状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static BaseResponse success(String code) {
		return new BaseResponse(code);
	}
	
	/**
	 * 创建具备默认失败状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static BaseResponse failed() {
		return failed(DEFAULT_FAILED_CODE);
	}
	
	/**
	 * 创建具备自定义失败状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static BaseResponse failed(String code) {
		return new BaseResponse(code);
	}
	
	/**
	 * 创建具备默认异常状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static BaseResponse exception() {
		return exception(DEFAULT_EXCEPTION_CODE);
	}
	
	/**
	 * 创建具备自定义异常状态码的响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static BaseResponse exception(String code) {
		return new BaseResponse(code);
	}
	
}
