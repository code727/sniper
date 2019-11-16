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

/**
 * 全量响应实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 * @param <T>
 */
public class FullResponse<T> extends AbstractDatamationResponse<T>
		implements FullQuantizationResponse<Integer, T>, Serializable {

	private static final long serialVersionUID = 5105502275635899805L;
	
	/** 响应消息 */
	private String message;
	
	public FullResponse() {
		this((T) null);
	}
	
	public FullResponse(T data) {
		this(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, data);
	}
	
	public FullResponse(FullQuantizationResponse<Integer, T> response) {
		this(response.getCode(), response.getMessage(), response.getData());
	}
	
	public FullResponse(MessagingResponse<Integer> response) {
		this(response, null);
	}
	
	public FullResponse(MessagingResponse<Integer> response, T data) {
		this(response.getCode(), response.getMessage(), data);
	}
		
	public FullResponse(Integer code, String message, T data) {
		super(code, data);
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
	
}
