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

package org.sniper.commons.response.character;

import java.io.Serializable;

import org.sniper.commons.constant.status.BizStatus;
import org.sniper.commons.response.AbstractResponse;
import org.sniper.commons.response.GenericResponse;
import org.sniper.commons.util.StringUtils;

/**
 * 响应实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class Response extends AbstractResponse<String> implements Serializable {
	
	private static final long serialVersionUID = 2773282924720586568L;
	
	/** 默认成功响应码  */
	public static final String DEFAULT_SUCCESS_CODE = BizStatus.SUCCESS.getKey();
	
	/** 默认失败响应码  */
	public static final String DEFAULT_FAILED_CODE = BizStatus.FAILED.getKey();
	
	/** 默认异常响应码  */
	public static final String DEFAULT_EXCEPTION_CODE = BizStatus.EXCEPTION.getKey();
		
	public Response() {
		this(DEFAULT_SUCCESS_CODE);
	}
	
	public Response(GenericResponse<?> response) {
		// 要求response对象以及code值不为空
		this(response.getCode().toString());
	}
	
	public Response(String code) {
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
	protected boolean matches(String code) {
		return StringUtils.equalsIgnoreCase(code, this.getCode());
	}
		
}
