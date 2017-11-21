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
 * Create Date : 2017-3-22
 */

package org.sniper.commons.response;

import org.sniper.commons.util.StringUtils;

/**
 * 响应抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractResponse implements Response {
		
	private static final long serialVersionUID = 8451538043375748612L;
	
	/** 状态码 */
	private String code;
	
	public AbstractResponse() {
		this(DEFAULT_SUCCESS_STATUS);
	}
	
	public AbstractResponse(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public boolean wasSuccess() {
		return isMatchStatus(DEFAULT_SUCCESS_STATUS);
	}
	

	@Override
	public boolean wasSuccess(String successCode) {
		return isMatchStatus(successCode);
	}

	@Override
	public boolean wasFailed() {
		return isMatchStatus(DEFAULT_FAILED_STATUS);
	}

	@Override
	public boolean wasFailed(String failedCode) {
		return isMatchStatus(failedCode);
	}

	@Override
	public boolean wasException() {
		return isMatchStatus(DEFAULT_EXCEPTION_STATUS);
	}

	@Override
	public boolean wasException(String exceptionCode) {
		return isMatchStatus(exceptionCode);
	}
	
	/**
	 * 判断状态码是否匹配当前状态
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param status
	 * @return
	 */
	protected boolean isMatchStatus(String status) {
		return StringUtils.equalsIgnoreCase(status, code);
	}
	
}
