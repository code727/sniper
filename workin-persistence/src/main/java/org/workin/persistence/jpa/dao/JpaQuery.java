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

package org.workin.persistence.jpa.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @description JPA查询接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface JpaQuery<T, PK extends Serializable> {
	
	/**
	 * @description 根据主键ID查询当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param primaryKey
	 * @return
	 */
	public T findById(PK primaryKey);
		
	/**
	 * @description 根据属性名和值查询出当前类型的唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public T findUniqueByProperty(String propertyName, Object propertyValue);
		
	/**
	 * @description 根据属性名值映射集查询出当前类型的唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param paramMap
	 * @return
	 */
	public T findUniqueByPropertys(Map<String, ?> paramMap);
		
	/**
	 * @description 执行查询语句后返回当前类型的唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @return
	 */
	public T findUniqueByQueryString(String ql);
	
	/**
	 * @description 执行查询语句后返回指定类型的唯一结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass 
	 * @param ql
	 * @return
	 */
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql);
	
	/**
	 * @description 执行带占位符(?)参数的查询语句后返回当前类型的唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param values
	 * @return
	 */
	public T findUniqueByQueryString(String ql, Object[] values);
	
	/**
	 * @description 执行带占位符(?)参数的查询语句后返回指定类型的唯一结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass 
	 * @param ql
	 * @param values
	 * @return
	 */
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql, Object[] values);
	
	/**
	 * @description 执行带命名(=:name)参数的查询语句后返回当前类型的唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param paramMap
	 * @return
	 */
	public T findUniqueByQueryString(String ql, Map<String, ?> paramMap);
	
	/**
	 * @description 执行带命名(=:name)参数的查询语句后返回指定类型的唯一结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param paramMap
	 * @return
	 */
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql, Map<String, ?> paramMap);
	
	/**
	 * @description 执行查询语句后返回当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @return 
	 */
	public List<T> find(String ql);
	
	/**
	 * @description 执行查询语句后返回指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @return
	 */
	public <R> List<R> find(Class<R> resultClass, String ql);
	
	/**
	 * @description 执行带占位符(?)参数的查询语句后返回当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param values
	 * @return 
	 */
	public List<T> find(String ql, Object[] values);
	
	/**
	 * @description 执行带占位符(?)参数的查询语句后返回指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param values
	 * @return
	 */
	public <R> List<R> find(Class<R> resultClass, String ql, Object[] values);
	
	/**
	 * @description 执行带命名(=:name)参数的查询语句后返回当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param paramMap
	 * @return
	 */
	public List<T> find(String ql, Map<String, ?> paramMap);
	
	/**
	 * @description 执行带命名(=:name)参数的查询语句后返回指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param paramMap
	 * @return
	 */
	public <R> List<R> find(Class<R> resultClass, String ql, Map<String, ?> paramMap);
	
	/**
	 * @description 从起始位置开始执行查询后，得到最大行数当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	public List<T> find(String ql, int start, int maxRows);
	
	/**
	 * @description 从起始位置开始执行查询后，得到最大行数指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public <R> List<R> find(Class<R> resultClass, String ql, int start, int maxRows);
	
	/**
	 * @description 从起始位置开始执行带占位符(?)参数的查询后，得到最大行数当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql 
	 * @param values
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public List<T> find(String ql, Object[] values, int start, int maxRows);
	
	/**
	 * @description 从起始位置开始执行带占位符(?)参数的查询后，得到最大行数指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param values
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public <R> List<R> find(Class<R> resultClass, String ql, Object[] values, int start, int maxRows);
	
	/**
	 * @description 从起始位置开始执行带命名(=:name)参数的查询后，得到最大行数当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql 
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	public List<T> find(String ql, Map<String, ?> paramMap, int start, int maxRows);
	
	/**
	 * @description 从起始位置开始执行带命名(=:name)参数的查询后，得到最大行数指定类型的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public <R> List<R> find(Class<R> resultClass, String ql, Map<String, ?> paramMap, int start, int maxRows);
	
	/**
	 * @description 从起始位置开始查询，得到最大行数当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public List<T> find(int start, int maxRows);
		
	/**
	 * @description 根据属性名和值查询出当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object propertyValue);
			
	/**
	 * @description 根据属性名和值从起始位置开始查询，得到最大行数当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param propertyValue
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object propertyValue, int start, int maxRows);
		
	/**
	 * @description 根据属性名值映射集查询出当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param paramMap
	 * @return
	 */
	public List<T> findByPropertys(Map<String, ?> paramMap);
		
	/**
	 * @description 根据属性名值映射集从起始位置开始查询，得到最大行数当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public List<T> findByPropertys(Map<String, ?> paramMap, int start, int maxRows);
	
	/**
	 * @description 查询当前类型的所有实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<T> findAll();
		
	/**
	 * @description 查询当前类型所有不重复的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<T> findAllDistinct();
	
	/**
	 * @description 根据属性名和值查询出当前类型实体对象总数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public long countByProperty(String propertyName, Object propertyValue);
		
	/**
	 * @description 根据属性名值映射集查询出当前类型实体对象的总数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param paramMap
	 * @return
	 */
	public long countByPropertys(Map<String, ?> paramMap);
	
	/**
	 * @description 执行总计查询语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @return
	 */
	public long countByQueryString(String ql);

	/**
	 * @description 执行带占位符(?)参数的总计查询语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param values
	 * @return
	 */
	public long countByQueryString(String ql, Object[] values);
	
	/**
	 * @description 执行带命名(=:name)参数的总计查询语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param paramMap
	 * @return
	 */
	public long countByQueryString(String ql, Map<String, ?> paramMap);

}
