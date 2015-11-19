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

package org.workin.persistence.pagination;

import java.util.List;

import org.workin.commons.pagination.PagingQuery;
import org.workin.persistence.util.PersistencePropertyFilter;

/**
 * @description 带属性过滤器列表的分页查询对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface FilterListPagingQuery extends PagingQuery {
	
	/**
	 * @description 设置属性过滤器列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 */
	public void setFilterList(List<PersistencePropertyFilter> filterList);
	
	/**
	 * @description 获取属性过滤器列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<PersistencePropertyFilter> getFilterList();

}
