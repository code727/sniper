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

package org.sniper.commons.pagination.pager;

/**
 * JQuery EasyUI分页器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JQueryEasyUIPager extends SimplePager {
	
	private static final long serialVersionUID = -4724476082340689425L;
	
	/**
	 * 获取JQuery EasyUI分页器每页条数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getRows() {
		return super.getPageSize();
	}

	/**
	 * 设置JQuery EasyUI分页器每页条数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param rows
	 */
	public void setRows(int rows) {
		super.setPageSize(rows);
	}

	/**
	 * 获取JQuery EasyUI分页器当前页数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getPage() {
		return super.getCurrentPage();
	}

	/**
	 * 设置JQuery EasyUI分页器当前页数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param page
	 */
	public void setPage(int page) {
		super.setCurrentPage(page);
	}
	
}
