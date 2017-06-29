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
 * 数据响应抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractDataResponse<T> extends AbstractResponse implements DataResponse<T> {
	
	/** 响应数据 */
	private T data;
	
	public AbstractDataResponse() {
		super();
	}
	
	public AbstractDataResponse(T data) {
		this(DEFAULT_SUCCESS_STATUS, data);
	}
	
	public AbstractDataResponse(DataResponse<T> response) {
		this(response.getCode(), response.getData());
	}
		
	public AbstractDataResponse(String code, T data) {
		super(code);
		this.data = data;
	}
	
	@Override
	public T getData() {
		return data;
	}

	@Override
	public void setData(T data) {
		this.data = data;
	}
	
}
