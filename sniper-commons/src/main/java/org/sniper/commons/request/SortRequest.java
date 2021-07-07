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

package org.sniper.commons.request;

/**
 * 排序请求
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SortRequest implements SortableRequest {

	private static final long serialVersionUID = -3569581291289010870L;

	/** 需要排序的字段名称 */
	private String sortName;
	
	/** 排序模式 */
	private int sortMode;
	
	@Override
	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	
	@Override
	public int getSortMode() {
		return sortMode;
	}

	@Override
	public void setSortMode(int sortMode) {
		this.sortMode = sortMode;
	}

	@Override
	public String getSortSchema() {
		return null;
	}
	
}
