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

import org.sniper.commons.request.PagingQuery;

/**
 * 带属性过滤器链的分页查询对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface FilterChainPagingQuery extends PagingQuery {
	
	/**
	 * 设置属性过滤器链
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 */
	public void setFilterChain(PersistencePropertyFilterChain chain);
	
	/**
	 * 获取属性过滤器链
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public PersistencePropertyFilterChain getFilterChain();

}
