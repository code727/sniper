/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-8-27
 */

package org.workin.persistence.sqlmap.service;

import org.workin.persistence.sqlmap.dao.SqlMapDao;

/**
 * SQL映射对象服务接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SqlMapBeanService<T> {
	
	/**
	 * 设置持久化DAO接口
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sqlMapDao
	 */
	public void setSqlMapDao(SqlMapDao<T> sqlMapDao);
	
	/**
	 * 获取持久化DAO接口
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public SqlMapDao<T> getSqlMapDao();

}