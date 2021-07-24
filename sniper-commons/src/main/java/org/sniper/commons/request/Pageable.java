/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-7-8
 */

package org.sniper.commons.request;

/**
 * 可分页的接口
 * @author  Daniele
 * @version 1.0
 */
public interface Pageable {
	
	/** 默认的当前页数 */
	public static final int DEFAULT_CURRENT_PAGE = 1;
	
	/** 默认的每页条数 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 获取每页条数
	 * @author Daniele 
	 * @return
	 */
	public int getPageSize();
	
	/**
	 * 设置每页条数
	 * @author Daniele 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize);
	
	/**
	 * 获取当前页数
	 * @author Daniele 
	 * @return
	 */
	public int getCurrentPage();
	
	/**
	 * 设置当前页数
	 * @author Daniele 
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage);

}
