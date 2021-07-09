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
 * Create Date : 2015-1-29
 */

package org.sniper.orm.jpa.dao;

import java.util.List;
import java.util.Map;

/**
 * JPA命名空间查询接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface JpaNamedQuery<T> {
	
	/**
	 * 执行空间名称对应的查询语句后返回当前类型的唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @return
	 */
	public T findUniqueByNamedQuery(String queryName);
	
	/**
	 * 执行空间名称对应的查询语句后返回指定类型的唯一结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass 
	 * @param queryName
	 * @return
	 */
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName);
	
	/**
	 * 执行空间名称对应的带占位符(?)参数的查询语句后返回当前类型的唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param values
	 * @return
	 */
	public T findUniqueByNamedQuery(String queryName, Object[] values);
	
	/**
	 * 执行空间名称对应的带占位符(?)参数的查询语句后返回指定类型的唯一结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param values
	 * @return
	 */
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName, Object[] values);

	/**
	 * 执行空间名称对应的带命名(=:name)参数的查询语句后返回当前类型的唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param paramMap
	 * @return
	 */
	public T findUniqueByNamedQuery(String queryName, Map<String, ?> paramMap);
	
	/**
	 * 执行空间名称对应的带命名(=:name)参数的查询语句后返回指定类型的唯一结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass 
	 * @param queryName
	 * @param paramMap
	 * @return
	 */
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName, Map<String, ?> paramMap);
	
	/**
	 * 执行空间名称对应的查询语句后返回当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @return 
	 */
	public List<T> findByNamedQuery(String queryName);
	
	/**
	 * 执行空间名称对应的查询语句后返回指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @return
	 */
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName);
	
	/**
	 * 从起始位置开始执行空间名称对应的查询语句后， 得到最大行数当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	public List<T> findByNamedQuery(String queryName, int start, int maxRows);
	
	/**
	 * 从起始位置开始执行空间名称对应的查询语句后， 得到最大行数指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, int start, int maxRows);
	
	/**
	 * 执行空间名称对应的带占位符(?)参数的查询语句后返回当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param values
	 * @return 
	 */
	public List<T> findByNamedQuery(String queryName, Object[] values);
	
	/**
	 * 执行空间名称对应的带占位符(?)参数的查询语句后返回指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param values
	 * @return
	 */
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Object[] values);
	
	/**
	 * 从起始位置开始执行空间名称对应的带占位符(?)参数的查询语句后，得到最大行数当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param values
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	public List<T> findByNamedQuery(String queryName, Object[] values, int start, int maxRows);
	
	/**
	 * 从起始位置开始执行空间名称对应的带占位符(?)参数的查询语句后，得到最大行数指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param values
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Object[] values, int start, int maxRows);

	/**
	 * 执行空间名称对应的带命名(=:name)参数的查询语句后返回当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param paramMap
	 * @return 。
	 */
	public List<T> findByNamedQuery(String queryName, Map<String, ?> paramMap);
	
	/**
	 * 执行空间名称对应的带命名(=:name)参数的查询语句后返回指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param paramMap
	 * @return
	 */
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Map<String, ?> paramMap);
	
	/**
	 * 从起始位置开始执行空间名称对应的带命名(=:name)参数的查询语句后，得到最大行数当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	public List<T> findByNamedQuery(String queryName, Map<String, ?> paramMap, int start, int maxRows);
	
	/**
	 * 从起始位置开始执行空间名称对应的带命名(=:name)参数的查询语句后，得到最大行数指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Map<String, ?> paramMap, int start, int maxRows);
	
	/**
	 * 执行空间名称对应的语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @return
	 */
	public int executeNamedQuery(String queryName);

	/**
	 * 执行空间名称对应的带占位符(?)参数语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param values
	 * @return
	 */
	public int executeNamedQuery(String queryName, Object[] values);

	/**
	 * 执行空间名称对应的带命名(=:name)参数语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param paramMap
	 * @return
	 */
	public int executeNamedQuery(String queryName, Map<String, ?> paramMap);
	
	/**
	 * 执行空间名称对应的总计查询语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @return
	 */
	public long countByNamedQuery(String queryName);
	
	/**
	 * 执行空间名称对应的带占位符(?)参数的总计查询语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param values
	 * @return
	 */
	public long countByNamedQuery(String queryName, Object[] values);
	
	/**
	 * 执行空间名称对应的带命名(=:name)参数的总计查询语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param paramMap
	 * @return
	 */
	public long countByNamedQuery(String queryName, Map<String, ?> paramMap);

}
