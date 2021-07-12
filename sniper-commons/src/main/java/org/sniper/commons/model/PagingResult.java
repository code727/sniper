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
 * Create Date : 2015-1-14
 */

package org.sniper.commons.model;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果接口
 * @author  Daniele
 * @version 1.0
 */
public interface PagingResult<T> extends Serializable {
	
	/**
	 * 获取分页结果列表
	 * @author Daniele 
	 * @return
	 */
	public List<T> getData();
	
	/**
	 * 设置分页结果列表
	 * @author Daniele 
	 * @param data
	 */
	public void setData(List<T> data);
	
	/**
	 * 获取符合分页条件的结果总数 
	 * @author Daniele 
	 * @return
	 */
	public long getCount();
	
	/**
	 * 设置符合分页条件的结果总数 
	 * @author Daniele 
	 * @param count
	 */
	public void setCount(long count);
	
}
