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

package org.workin.persistence.pagination.impl;

import org.workin.commons.pagination.pager.SimplePager;
import org.workin.persistence.pagination.FilterChainPagingQuery;
import org.workin.persistence.util.PersistencePropertyFilterChain;

/**
 * @description 带属性过滤器链的分页器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FilterChainPager extends SimplePager implements FilterChainPagingQuery {
	
	/** 属性过滤器链 */
	private PersistencePropertyFilterChain chain;

	@Override
	public void setFilterChain(PersistencePropertyFilterChain chain) {
		this.chain = chain;
	}

	@Override
	public PersistencePropertyFilterChain getFilterChain() {
		return this.chain;
	}

}
