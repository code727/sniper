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

import org.sniper.commons.pagination.SortablePagingQuery;
import org.sniper.commons.request.SortRequest;
import org.sniper.commons.util.NumberUtils;

/**
 * 简单的分页器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SimplePager extends SortRequest implements SortablePagingQuery {
	
	private static final long serialVersionUID = -3075094595070319133L;

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
		this.pageSize = NumberUtils.minLimit(pageSize, 1);
	}

	@Override
	public int getCurrentPage() {
		return this.currentPage;
	}

	@Override
	public void setCurrentPage(int currentPage) {
		this.currentPage = NumberUtils.minLimit(currentPage, 1);
	}

	@Override
	public long getBegin() {
		// 开始位置不为默认起始位置时(例如按ID偏移进行分页时，设置此值为起始ID)，则直接返回。
		if (this.begin != START_POS)
			return this.begin;

		// 根据当前页数和每页条数计算后返回
		return (this.currentPage -1) * this.pageSize;
	}

	@Override
	public void setBegin(long begin) {
		this.begin = begin;
	}

	@Override
	public long getEnd() {
		// 结束位置不为默认起始位置时(例如按ID偏移进行分页时，设置此值为结束ID)，则直接返回。
		if (this.end != START_POS) {
			long begin = this.getBegin();
			return this.end > begin ? this.end : begin;
		}
			
		// 根据起始位置和每页条数计算后返回
		return this.getBegin() + this.pageSize;
	}			

	@Override
	public void setEnd(long end) {
		this.end = end;
	}
			
}
