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
 * Create Date : 2015-3-24
 */

package org.sniper.sqlmap.dao;

/**
 * SQL映射DAO接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SqlMapDao<T> extends SqlMapQuery<T> {
	
	/**
	 * 执行statement对应的insert语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @return
	 */
	public Object insert(String statement);
	
	/**
	 * 执行statement对应的insert语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public Object insert(String statement, Object parameter);
	
	/**
	 * 执行statement对应的update语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @return
	 */
	public int update(String statement);
	
	/**
	 * 执行statement对应的update语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int update(String statement, Object parameter);
	
	/**
	 * 执行statement对应的delete语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 */
	public int delete(String statement);
	
	/**
	 * 执行statement对应的delete语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param statement
	 * @param parameter
	 */
	public int delete(String statement, Object parameter);

}
