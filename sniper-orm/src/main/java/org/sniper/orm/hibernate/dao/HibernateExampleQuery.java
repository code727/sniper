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
 * Create Date : 2015-2-4
 */

package org.sniper.orm.hibernate.dao;

import java.util.List;

/**
 * Hibernate示例查询接口
 * @author  Daniele
 * @version 1.0
 */
public interface HibernateExampleQuery<T> {
	
	/**
	 * 以实体为查询条件查询出实体结果集
	 * @author Daniele 
	 * @param entity
	 * @return
	 */
	public List<T> findByExample(T entity);
	
	/**
	 * 以实体为查询条件查询出实体结果集
	 * @author Daniele 
	 * @param entityName
	 * @param entity
	 * @return
	 */
	public List<T> findByExample(String entityName, T entity);
	
	/**
	 * 以实体为查询条件分段查询出实体结果集
	 * @author Daniele 
	 * @param entity
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public List<T> findByExample(T entity, int start, int maxRows);
	
	/**
	 * 以实体为查询条件分段查询出实体结果集
	 * @author Daniele 
	 * @param entityName
	 * @param entity
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public List<T> findByExample(String entityName, T entity, int start, int maxRows);

}
