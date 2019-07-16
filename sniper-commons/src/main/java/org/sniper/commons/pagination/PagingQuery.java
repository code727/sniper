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

package org.sniper.commons.pagination;

import java.io.Serializable;

/**
 * 分页查询条件接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface PagingQuery extends Serializable {
	
	/** 执行查询的默认起始位置 */
	public static final long START_POS = 0;
	
	/** 默认的当前页数 */
	public static final int DEFAULT_CURRENT_PAGE = 1;
	
	/** 默认的每页条数 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 获取每页条数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getPageSize();
	
	/**
	 * 设置每页条数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize);
	
	/**
	 * 获取当前页数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getCurrentPage();
	
	/**
	 * 设置当前页数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage);
			
	/**
	 * 获取执行查询的起始位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public long getBegin();

	/**
	 * 设置执行查询的起始位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param begin
	 */
	public void setBegin(long begin);
	
	/**
	 * 获取执行查询的结束位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public long getEnd();

	/**
	 * 设置执行查询的结束位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param end
	 */
	public void setEnd(long end);

}
