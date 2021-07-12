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

package org.sniper.commons.request;

import org.sniper.commons.util.NumberUtils;

/**
 * 分页查询请求
 * @author  Daniele
 * @version 1.0
 */
public class PagingRequest extends SortRequest implements PagingQuery {
	
	private static final long serialVersionUID = -3075094595070319133L;

	/** 每页条数 */
	private int pageSize = DEFAULT_PAGE_SIZE;
	
	/** 当前页数 */
	private int currentPage = DEFAULT_CURRENT_PAGE;
	
	/** 是否需要查询总数 */
	private boolean queryCount = true;
	
	/** 开始查询的位置 */
	private long start = START_POS;
	
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
	public boolean isQueryCount() {
		return queryCount;
	}

	@Override
	public void setQueryCount(boolean queryCount) {
		this.queryCount = queryCount;
	}

	@Override
	public long getStart() {
		// 开始位置不为默认起始位置时直接返回。
		if (this.start != START_POS) {
			return this.start;
		}

		// 根据当前页数和每页条数计算后返回
		return (this.currentPage -1) * this.pageSize;
	}

	@Override
	public void setStart(long start) {
		this.start = start;
	}

	@Override
	public long getEnd() {
		// 结束位置不为默认起始位置时直接返回
		if (this.end != START_POS) {
//			long begin = this.getStart();
//			return this.end > begin ? this.end : begin;
			return this.end;
		}
			
		// 根据起始位置和每页条数计算后返回
		return this.getStart() + this.pageSize;
	}			

	@Override
	public void setEnd(long end) {
		this.end = end;
	}
	
	/**
	 * 判断是否在查询上一页
	 * @author Daniele 
	 * @return
	 */
	public boolean isQueryPreviousPage() {
		return getEnd() <= getStart();
	}
	
	public String toString() {
		return String.format("{currentPage:%s,pageSize:%s,start:%s,end:%s}", 
				currentPage, pageSize, getStart(), getEnd());
	}
			
}
