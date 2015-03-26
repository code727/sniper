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

package org.workin.persistence.sqlmap.dao;

/**
 * @description SQL映射持久化DAO接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SqlMapPersistenceDao<T> extends SqlMapQuery<T> {
	
	/**
	 * @description 执行id对应的insert语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public Object insert(String id);
	
	/**
	 * @description 执行id对应的insert语句后返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param parameter
	 * @return
	 */
	public Object insert(String id, Object parameter);
	
	/**
	 * @description 执行id对应的update语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public int update(String id);
	
	/**
	 * @description 执行id对应的update语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param parameter
	 * @return
	 */
	public int update(String id, Object parameter);
	
	/**
	 * @description 执行id对应的delete语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 */
	public int delete(String id);
	
	/**
	 * @description 执行id对应的delete语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param parameter
	 */
	public int delete(String id, Object parameter);

}
