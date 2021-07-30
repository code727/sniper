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

import java.text.MessageFormat;

import org.sniper.commons.util.StringUtils;

/**
 * 抽象响应类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractResponse<C> implements GenericResponse<C> {
		
	private static final long serialVersionUID = 8451538043375748612L;
	
	/** 响应码 */
	protected C code;
	
	/** 响应消息 */
	protected String message;
	
	@Override
	public C getCode() {
		return code;
	}

	@Override
	public void setCode(C code) {
		this.code = code;
	}
	
	@Override
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public GenericResponse<C> format(Object... params) {
		if (StringUtils.isNotBlank(message)) {
			this.message = MessageFormat.format(message, params);
		}
		return this;
	}
	
}
