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

package org.workin.support.pagination.impl;

import org.workin.support.pagination.PagingQuery;

/**
 * @description 简单分页器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SimplePager implements PagingQuery {
	
	/** 每页条数 */
	private int pageSize = DEFAULT_PAGE_SIZE;
	
	/** 当前页数 */
	private int currentPage = DEFAULT_CURRENT_PAGE;
	
	/** 开始查询的位置 */
	private long begin = START_POS;
	
	/** 结束查询的位置 */
	private long end = START_POS;

	@Override
	public int getPageSize() {
		return this.pageSize;
	}

	@Override
	public void setPageSize(int pageSize) {
		if (pageSize > 0)
			this.pageSize = pageSize;
	}

	@Override
	public int getCurrentPage() {
		return this.currentPage;
	}

	@Override
	public void setCurrentPage(int page) {
		if (page > DEFAULT_CURRENT_PAGE)
			this.currentPage = page;
	}

	@Override
	public long getBegin() {
		// 开始位置不为默认起始位置时(例如按ID偏移进行分页时，设置此值为起始ID)，则直接返回。
		// 否则根据当前页数和每页条数计算后返回
		if (this.begin != START_POS && this.currentPage == DEFAULT_CURRENT_PAGE)
			return this.begin;
		
		return (this.currentPage -1) * this.pageSize;
	}

	@Override
	public void setBegin(long begin) {
		this.begin = begin;
	}

	@Override
	public long getEnd() {
		// 结束位置不为默认起始位置时(例如按ID偏移进行分页时，设置此值为结束ID)，则直接返回。
		// 否则根据起始位置和每页条数计算后返回
		if (this.end != START_POS)
			return this.end > this.getBegin() ? this.end : this.getBegin();
		
		return this.getBegin() + this.pageSize;
	}			

	@Override
	public void setEnd(long end) {
		this.end = end;
	}

}
