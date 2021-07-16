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

import org.sniper.commons.request.PageQuery;
import org.sniper.commons.request.PageRequest;
import org.sniper.commons.util.CollectionUtils;

/**
 * 分页明细结果模型
 * @author  Daniele
 * @version 1.0
 */
public class DetailPageModel<T> extends PageModel<T> implements DetailPageResult<T> {

	private static final long serialVersionUID = -6132358308660100965L;
	
	private final PageQuery pageableQuery = new PageRequest();
	
	public DetailPageModel() {
		
	}
	
	public DetailPageModel(PageQuery pageQuery) {
		this(pageQuery.getCurrentPage(), pageQuery.getPageSize());
	}
	
	public DetailPageModel(int currentPage, int pageSize) {
		setCurrentPage(currentPage);
		setPageSize(pageSize);
	}
	
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
		this.pageableQuery.setCurrentPage(currentPage);
	}

}
