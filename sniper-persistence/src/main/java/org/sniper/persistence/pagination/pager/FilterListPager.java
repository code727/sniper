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
 * Create Date : 2015-2-26
 */

package org.sniper.persistence.pagination.pager;

import java.util.List;

import org.sniper.commons.request.PagingRequest;
import org.sniper.persistence.pagination.FilterListPagingQuery;
import org.sniper.persistence.util.PersistencePropertyFilter;

/**
 * 带属性过滤器列表的分页器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FilterListPager extends PagingRequest implements FilterListPagingQuery {
	
	private static final long serialVersionUID = -8143607622421839319L;
	
	/** 属性过滤器列表 */
	private List<PersistencePropertyFilter> filterList;

	@Override
	public void setFilterList(List<PersistencePropertyFilter> filterList) {
		this.filterList = filterList;
	}

	@Override
	public List<PersistencePropertyFilter> getFilterList() {
		return this.filterList;
	}

}
