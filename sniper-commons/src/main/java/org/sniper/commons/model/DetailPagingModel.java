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

package org.sniper.commons.model;

import org.sniper.commons.request.PagingQuery;
import org.sniper.commons.request.PagingRequest;
import org.sniper.commons.util.CollectionUtils;

/**
 * 分页明细结果模型
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DetailPagingModel<T> extends PagingModel<T> implements PagingDetailResult<T> {

	private static final long serialVersionUID = -6132358308660100965L;
	
	private final PagingQuery pageableQuery = new PagingRequest();
	
	@Override
	public long getFrom() {
		return getCount() > 0 ? (getCurrentPage() - 1) * getPageSize() + 1 : 0;
	}

	@Override
	public long getTo() {
		long from = this.getFrom();
		return from > 0 ? from + CollectionUtils.size(this.getData()) - 1 : from;
	}

	@Override
	public long getPages() {
		return getCount() / getPageSize() + (getCount() % getPageSize() != 0 ? 1 : 0);
	}

	@Override
	public int getPageSize() {
		return this.pageableQuery.getPageSize();
	}

	@Override
	public void setPageSize(int pageSize) {
		this.pageableQuery.setPageSize(pageSize);
	}

	@Override
	public int getCurrentPage() {
		return this.pageableQuery.getCurrentPage();
	}

	@Override
	public void setCurrentPage(int currentPage) {
		this.setCurrentPage(currentPage);
	}
	
}
