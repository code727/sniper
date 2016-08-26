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
 * Create Date : 2015-3-18
 */

package org.workin.persistence.sqlmap.dao;

import java.util.List;
import java.util.Map;

import org.workin.support.bean.GenericBean;

/**
 * @description SQL映射查询接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SqlMapQuery<T> extends GenericBean<T> {
	
	/** 默认的结果映射集属性键的名称 */
	public static final String DEFAULT_KEY_PROPERTY = "id";
	
	/**
	 * @description 执行id对应的count语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @return
	 */
	public long countBySqlMap(String statement);
	
	/**
	 * @description 执行statement对应的count语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public long countBySqlMap(String statement, Object parameter);
	
	/**
	 * @description 执行statement对应的查询语句后返回当前类型唯一的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @return
	 */
	public T queryUniqueBySqlMap(String statement);
	
	/**
	 * @description 执行statement对应的查询语句后返回当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public T queryUniqueBySqlMap(String statement, Object parameter);
	
	/**
	 * @description 执行statement对应的查询语句后返回指定类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param statement
	 * @return
	 */
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String statement);
	
	/**
	 * @description 执行statement对应的查询语句后返回指定类型的唯一结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String statement, Object parameter);
	
	/**
	 * @description 执行statement对应的查询语句后返回当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @return
	 */
	public List<T> queryListBySqlMap(String statement);
	
	/**
	 * @description 执行statement对应的查询语句后返回当前类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public List<T> queryListBySqlMap(String statement, Object parameter);
		
	/**
	 * @description 执行statement对应的查询语句后返回指定类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param statement
	 * @return
	 */
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String statement);
	
	/**
	 * @description 执行statement对应的查询语句后返回指定类型的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String statement, Object parameter);
	
	/**
	 * @description 执行statement对应的查询语句后返回结果映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement 
	 * @return
	 */
	public <K,V> Map<K, V> queryMapBySqlMap(String statement);
	
	/**
	 * @description 执行statement对应的查询语句后返回结果映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public <K,V> Map<K, V> queryMapBySqlMap(String statement, Object parameter);
	
	/**
	 * @description 执行statement对应的查询语句后返回结果映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @param keyProperty 结果集的键值
	 * @return
	 */
	public <K,V> Map<K, V> queryMapBySqlMap(String statement, String keyProperty);
	
	/**
	 * @description 执行statement对应的查询语句后返回结果映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @param parameter
	 * @param keyProperty 结果集的键值
	 * @return
	 */
	public <K,V> Map<K, V> queryMapBySqlMap(String statement, Object parameter, String keyProperty);
	
}
