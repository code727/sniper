/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-1-26
 */

package org.workin.support.model.impl;

import org.workin.support.model.GenericsDataModel;
import org.workin.support.model.StatefulModel;

/**
 * @description 有状态记录的数据模型
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class StatefulDataModel<T> implements GenericsDataModel<T>, StatefulModel {
	
	/** 数据 */
	private T data;
	
	/** 状态 */
	private String status;

	@Override
	public T getData() {
		return this.data;
	}

	@Override
	public void setDate(T data) {
		this.data = data;
	}

	@Override
	public String getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

}
