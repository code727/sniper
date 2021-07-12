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
 * Create Date : 2015-1-13
 */

package org.sniper.commons.request;

/**
 * 分页查询接口
 * @author  Daniele
 * @version 1.0
 */
public interface PagingQuery extends Pageable, SortableRequest {
	
	/** 查询前的默认起始位置 */
	public static final long START_POS = 0;
	
	/**
	 * 判断是否需要查询总数
	 * @author Daniele 
	 * @return
	 */
	public boolean isQueryCount();
	
	/**
	 * 设置是否需要查询总数
	 * @author Daniele 
	 * @param queryCount
	 */
	public void setQueryCount(boolean queryCount);
			
	/**
	 * 获取执行查询的起始位置
	 * @author Daniele 
	 * @return
	 */
	public long getStart();

	/**
	 * 设置执行查询的起始位置
	 * @author Daniele 
	 * @param start
	 */
	public void setStart(long start);
	
	/**
	 * 获取执行查询的结束位置
	 * @author Daniele 
	 * @return
	 */
	public long getEnd();

	/**
	 * 设置执行查询的结束位置
	 * @author Daniele 
	 * @param end
	 */
	public void setEnd(long end);

}
