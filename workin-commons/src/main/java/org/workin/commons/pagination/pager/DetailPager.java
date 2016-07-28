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
 * Create Date : 2015-1-15
 */

package org.workin.commons.pagination.pager;

import java.util.List;

import org.workin.commons.pagination.PagingDetailResult;
import org.workin.commons.pagination.PagingResult;
import org.workin.commons.pagination.SortObject;
import org.workin.commons.pagination.SortablePagingQuery;
import org.workin.commons.pagination.result.SimplePagingResult;
import org.workin.commons.util.CollectionUtils;

/**
 * @description 多功能详情分页器实现类。它既可以接收查询参数，
 * 				又可以根据查询参数返回比SimplePagingResult更为详细的分页结果。
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DetailPager<T> implements SortablePagingQuery, PagingDetailResult<T> {

	private SortablePagingQuery pager;
	
	private PagingResult<T> result;
	
	public DetailPager() {
		this.pager = new SimplePager();
		this.result = new SimplePagingResult<T>();
	}
	
	@Override
	public int getPageSize() {
		return this.pager.getPageSize();
	}

	@Override
	public void setPageSize(int pageSize) {
		this.pager.setPageSize(pageSize);
	}

	@Override
	public int getCurrentPage() {
		return this.pager.getCurrentPage();
	}

	@Override
	public void setCurrentPage(int page) {
		 this.pager.setCurrentPage(page);
	}

	@Override
	public long getBegin() {
		return this.pager.getBegin();
	}

	@Override
	public void setBegin(long begin) {
		this.pager.setBegin(begin);
	}

	@Override
	public long getEnd() {
		return this.pager.getEnd();
	}	
	
	@Override
	public void setEnd(long end) {
		this.pager.setEnd(end);
	}
	
	@Override
	public void setSortObject(SortObject sort) {
		this.pager.setSortObject(sort);
	}

	@Override
	public SortObject getSortObject() {
		return this.pager.getSortObject();
	}

	@Override
	public List<T> getData() {
		return this.result.getData();
	}

	@Override
	public void setData(List<T> data) {
		this.result.setData(data);
	}

	@Override
	public long getTotal() {
		return this.result.getTotal();
	}

	@Override
	public void setTotal(long total) {
		this.result.setTotal(total);
	}
	
	@Override
	public long getFrom() {
		return this.getTotal() > 0 ? (this.getCurrentPage() - 1) * this.getPageSize() + 1 : 0;
	}

	@Override
	public long getTo() {
		long from = this.getFrom();
		return from > 0 ? from +  CollectionUtils.size(this.getData()) - 1 : from;
	}

	@Override
	public long getPages() {
		return getTotal() / getPageSize() + (getTotal() % getPageSize() != 0 ? 1 : 0);
	}

}
