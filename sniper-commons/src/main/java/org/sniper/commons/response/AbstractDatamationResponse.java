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
public abstract class AbstractDatamationResponse<C, T> extends AbstractResponse<C> implements DatamationResponse<C, T> {
	
	private static final long serialVersionUID = 3520505219697443564L;
	
	/** 响应数据 */
	protected T data;
	
	@Override
	public void setData(T data) {
		this.data = data;
	}
	
	@Override
	public T getData() {
		return data;
	}

}
