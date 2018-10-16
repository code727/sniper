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

import org.sniper.commons.response.DatamationResponse;

/**
 * 数据响应实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DataResponse<T> extends AbstractDatamationResponse<T> implements Serializable {
	
	private static final long serialVersionUID = 7831515870882940776L;
	
	public DataResponse() {
		this((T) null);
	}
		
	public DataResponse(T data) {
		this(DEFAULT_SUCCESS_CODE, data);
	}
	
	public DataResponse(DatamationResponse<String, T> response) {
		super(response);
	}
		
	public DataResponse(String code, T data) {
		super(code, data);
	}
	
}
