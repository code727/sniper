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

import java.util.List;

import org.sniper.commons.request.PageQuery;
import org.sniper.commons.request.PageRequest;
import org.sniper.commons.util.CollectionUtils;

/**
 * 分页明细结果模型
 * @author  Daniele
 * @version 1.0
 */
public class PageDetailModel<T> extends PageModel<T> implements PageDetailResult<T> {

	private static final long serialVersionUID = -6132358308660100965L;
	
	private final PageQuery pageQuery = new PageRequest();
	
	public PageDetailModel() {
		this(null);
	}
	
	public PageDetailModel(PageQuery pageQuery) {
		this(pageQuery, null, 0);
	}
	
	public PageDetailModel(List<T> pageList, long count) {
		this(null, pageList, 0);
	}
	
	public PageDetailModel(PageQuery pageQuery, List<T> pageList, long count) {
		super(pageList, count);
		if (pageQuery != null) {
			setCurrentPage(pageQuery.getCurrentPage());
			setPageSize(pageQuery.getPageSize());
		}
	}
		
	@Override
	public long getPages() {
		long count = getCount();
		int pageSize = getPageSize();
		return count / pageSize + (count % pageSize == 0 ? 0 : 1);
	}
	
	@Override
	public long getFrom() {
		return getCount() > 0 && CollectionUtils.isNotEmpty(getPageList()) ? 
				(getCurrentPage() - 1) * getPageSize() + 1 : 0;
	}

	@Override
	public long getTo() {
		long from = getFrom();
		return from > 0 ? from + CollectionUtils.size(getPageList()) - 1 : from;
	}

	@Override
	public int getPageSize() {
		return pageQuery.getPageSize();
	}

	@Override
	public void setPageSize(int pageSize) {
		pageQuery.setPageSize(pageSize);
	}

	@Override
	public int getCurrentPage() {
		return pageQuery.getCurrentPage();
	}

	@Override
	public void setCurrentPage(int currentPage) {
		pageQuery.setCurrentPage(currentPage);
	}

}
