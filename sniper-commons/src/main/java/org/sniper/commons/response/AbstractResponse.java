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

/**
 * 响应抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractResponse<C> implements GenericResponse<C> {
		
	private static final long serialVersionUID = 8451538043375748612L;
	
	/** 状态码 */
	private C code;
	
	protected AbstractResponse(C code) {
		this.code = code;
	}
		
	@Override
	public C getCode() {
		return code;
	}

	@Override
	public void setCode(C code) {
		this.code = code;
	}
	
	@Override
	public boolean wasSuccess(C code) {
		return matches(code);
	}

	@Override
	public boolean wasFailed(C code) {
		return matches(code);
	}

	@Override
	public boolean wasException(C code) {
		return matches(code);
	}
		
	/**
	 * 判断指定的状态码是否与当前状态码匹配
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	protected abstract boolean matches(C code); 
	
}
