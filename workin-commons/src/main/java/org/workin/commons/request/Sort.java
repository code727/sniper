/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-7-28
 */

package org.workin.commons.request;

import org.workin.commons.util.StringUtils;

/**
 * 排序对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class Sort implements Sortable {

	private static final long serialVersionUID = -3569581291289010870L;

	/** 需要排序的字段名称 */
	private String name;
	
	/** 排序方法，例如RDMS的ASC和DESC关键字，MongoDB的0和1等 */
	private String order;
	
	public Sort() {
		
	}
	
	public Sort(String name) {
		this(name, null);
	}
	
	public Sort(String name, String order) {
		this.name = name;
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	/**
	 * 判断是否为有效排序对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean effective() {
		return StringUtils.isNotBlank(name);
	}

}
