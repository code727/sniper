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

import java.io.Serializable;

/**
 * 可排序的请求
 * @author  Daniele
 * @version 1.0
 */
public interface SortableRequest extends Serializable {
	
	/**
	 * 获取需要排序的字段名称
	 * @author Daniele 
	 * @return
	 */
	public String getSortName();

	/**
	 * 设置需要排序的字段名称
	 * @author Daniele 
	 * @param sortName
	 */
	public void setSortName(String sortName);
	
	/**
	 * 设置排序模式
	 * @author Daniele 
	 * @param sortMode
	 * @return
	 */
	public void setSortMode(int sortMode);
	
	/**
	 * 获取排序模式
	 * @author Daniele 
	 * @return
	 */
	public int getSortMode();

	/**
	 * 获取排序方案
	 * @author Daniele 
	 * @return
	 */
	public String getSortSchema();

}
