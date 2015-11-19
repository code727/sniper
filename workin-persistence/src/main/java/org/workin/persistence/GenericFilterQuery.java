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
 * Create Date : 2015年3月2日
 */

package org.workin.persistence;

import java.util.List;

import org.workin.persistence.pagination.FilterChainPagingQuery;
import org.workin.persistence.pagination.FilterListPagingQuery;
import org.workin.persistence.util.PersistencePropertyFilter;
import org.workin.persistence.util.PersistencePropertyFilterChain;
import org.workin.support.pagination.PagingResult;

/**
 * @description 泛型过滤器查询接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface GenericFilterQuery<T> {
	
	/**
	 * @description 根据过滤器列表查询出唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @return
	 */
	public T findUniqueByFilterList(List<PersistencePropertyFilter> filterList);
	
	/**
	 * @description 根据过滤器链查询出唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @return
	 */
	public T findUniqueByFilterChain(PersistencePropertyFilterChain chain);
	
	/**
	 * @description 根据过滤器列表查询出实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @return
	 */
	public List<T> findByFilterList(List<PersistencePropertyFilter> filterList);
	
	/**
	 * @description 根据过滤器列表分段查询出实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public List<T> findByFilterList(List<PersistencePropertyFilter> filterList, int start, int maxRows);
	
	/**
	 * @description 根据过滤器链查询出指定类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @return
	 */
	public List<T> findByFilterChain(PersistencePropertyFilterChain chain);
	
	/**
	 * @description 根据过滤器链分段查询出实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public List<T> findByFilterChain(PersistencePropertyFilterChain chain, int start, int maxRows);
			
	/**
	 * @description 根据过滤器列表查询出实体对象个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @return
	 */
	public int countByFilterList(List<PersistencePropertyFilter> filterList);
	
	/**
	 * @description 根据过滤器链查询出指定类型的实体对象个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @return
	 */
	public int countByFilterChain(PersistencePropertyFilterChain chain);
	
	/**
	 * @description 根据带属性过滤器列表的对象分页查询出实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query
	 * @return
	 */
	public PagingResult<List<T>> pagingQuery(FilterListPagingQuery query);
	
	/**
	 * @description 根据带属性过滤器链的对象分页查询出实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query
	 * @return
	 */
	public PagingResult<List<T>> pagingQuery(FilterChainPagingQuery query);

}
