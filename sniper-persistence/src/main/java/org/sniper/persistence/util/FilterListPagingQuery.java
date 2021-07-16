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

package org.sniper.persistence.util;

import java.util.List;

import org.sniper.commons.request.PageQuery;

/**
 * 带属性过滤器列表的分页查询对象
 * @author  Daniele
 * @version 1.0
 */
public interface FilterListPagingQuery extends PageQuery {
	
	/**
	 * 设置属性过滤器列表
	 * @author Daniele 
	 * @param filterList
	 */
	public void setFilterList(List<PersistencePropertyFilter> filterList);
	
	/**
	 * 获取属性过滤器列表
	 * @author Daniele 
	 * @return
	 */
	public List<PersistencePropertyFilter> getFilterList();

}
