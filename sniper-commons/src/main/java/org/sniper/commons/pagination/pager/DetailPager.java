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

package org.sniper.commons.pagination.pager;

import java.util.List;

import org.sniper.commons.pagination.PagingDetailResult;
import org.sniper.commons.pagination.PagingResult;
import org.sniper.commons.pagination.SortablePagingQuery;
import org.sniper.commons.pagination.result.JQueryEasyUIPagingResult;
import org.sniper.commons.pagination.result.SimplePagingResult;
import org.sniper.commons.request.Sort;
import org.sniper.commons.util.CollectionUtils;

/**
 * 多功能详情分页器实现类。它既可以接收查询参数，
 * 又可以根据查询参数返回比SimplePagingResult更为详细的分页结果。
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DetailPager<T> implements SortablePagingQuery, PagingDetailResult<T> {

	private static final long serialVersionUID = -6132358308660100965L;

	private SortablePagingQuery pager;
	
	private PagingResult<T> result;
	
	public DetailPager() {
		this(null, null);
	}
	
	public DetailPager(SortablePagingQuery pager) {
		this(pager, null);
	}
	
	public DetailPager(PagingResult<T> result) {
		this(null, result);
	}
	
	public DetailPager(SortablePagingQuery pager, PagingResult<T> result) {
		if (pager == null)
			this.pager = new SimplePager();
		else
			this.pager = pager;
		
		if (result == null)
			this.result = new SimplePagingResult<T>();
		else
			this.result = result;
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
	public List<T> getData() {
		if (this.result instanceof JQueryEasyUIPagingResult)
			return ((JQueryEasyUIPagingResult<T>) result).getRows();
		
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
	
	@Override
	public List<Sort> getSortes() {
		return this.pager.getSortes();
	}

	@Override
	public void setSortes(List<Sort> sortes) {
		this.pager.setSortes(sortes);
	}

	@Override
	public void add(Sort sort) {
		this.pager.add(sort);
	}

}
