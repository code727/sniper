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
 * Create Date : 2015-2-12
 */

package org.sniper.persistence;

import java.util.List;

import org.sniper.commons.pagination.PagingResult;
import org.sniper.persistence.pagination.FilterChainPagingQuery;
import org.sniper.persistence.pagination.FilterListPagingQuery;
import org.sniper.persistence.util.PersistencePropertyFilter;
import org.sniper.persistence.util.PersistencePropertyFilterChain;

/**
 * 过滤器查询接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface FilterQuery<T> {
	
	/**
	 * 根据过滤器列表查询出当前类型的唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @return
	 */
	public T findUniqueByFilterList(List<PersistencePropertyFilter> filterList);
	
	/**
	 * 根据过滤器链查询出当前类型的唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @return
	 */
	public T findUniqueByFilterChain(PersistencePropertyFilterChain chain);
	
	/**
	 * 根据过滤器列表查询出当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @return
	 */
	public List<T> findByFilterList(List<PersistencePropertyFilter> filterList);
	
	/**
	 * 根据过滤器列表分段查询出当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public List<T> findByFilterList(List<PersistencePropertyFilter> filterList, int start, int maxRows);
	
	/**
	 * 根据过滤器链查询出当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @return
	 */
	public List<T> findByFilterChain(PersistencePropertyFilterChain chain);
	
	/**
	 * 根据过滤器链分段查询出当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public List<T> findByFilterChain(PersistencePropertyFilterChain chain, int start, int maxRows);
			
	/**
	 * 根据过滤器列表查询出当前类型的实体对象个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @return
	 */
	public long countByFilterList(List<PersistencePropertyFilter> filterList);
	
	/**
	 * 根据过滤器链查询出当前类型的实体对象个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @return
	 */
	public long countByFilterChain(PersistencePropertyFilterChain chain);
	
	/**
	 * 根据带属性过滤器列表的对象分页查询出当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query
	 * @return
	 */
	public PagingResult<T> pagingQuery(FilterListPagingQuery query);
	
	/**
	 * 根据带属性过滤器链的对象分页查询出当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query
	 * @return
	 */
	public PagingResult<T> pagingQuery(FilterChainPagingQuery query);

}
