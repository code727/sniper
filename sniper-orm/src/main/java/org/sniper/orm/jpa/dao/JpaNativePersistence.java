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

import java.util.Map;

/**
 * JPA本地持久化接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface JpaNativePersistence {
	
	/**
	 * 执行本地持久化SQL语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @return
	 */
	public int executeByNativeQuery(String sql);
	
	/**
	 * 执行带占位符(?)参数的本地持久化SQL语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @param values
	 * @return
	 */
	public int executeByNativeQuery(String sql, Object[] values);

	/**
	 * 执行带命名(=:name)参数的本地持久化SQL语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public int executeByNativeQuery(String sql, Map<String, ?> paramMap);

}
