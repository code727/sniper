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
 * Create Date : 2015-2-2
 */

package org.workin.persistence.hibernate.dao.interfaces;

import java.io.Serializable;
import java.util.List;

import org.workin.persistence.jpa.dao.interfaces.JpaPersistence;

/**
 * @description Hibernate持久化接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface HibernatePersistence<T, PK extends Serializable> extends
		JpaPersistence<T, PK> { 
	
	/**
	 * @description 根据名称持久化指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 */
	public void persist(String entityName, T entity);
	
	/**
	 * @description 根据名称持久化指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entityList
	 */
	public void batchPersist(String entityName, List<T> entityList);
	
	/**
	 * @description 根据名称合并指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 * @return
	 */
	public T merge(String entityName, T entity);
	
	/**
	 * @description 根据名称批量合并指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entityList
	 * @return
	 */
	public List<T> batchMerge(String entityName, List<T> entityList);
	
	/**
	 * @description 根据名称删除指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 */
	public void remove(String entityName, T entity);

	/** 
	 * @description 根据名称批量删除指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entityList 
	 */
	void batchRemove(String entityName, List<T> entityList);
	
	/**
	 * @description 新增保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public PK save(T entity);
	
	/**
	 * @description 根据名称新增保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 */
	public PK save(String entityName, T entity);
	
	/**
	 * @description 更新保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * @description 根据名称更新保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 */
	public void update(String entityName, T entity);
	
	/**
	 * @description 新增/更新保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void saveOrUpdate(T entity);
	
	/**
	 * @description 根据名称新增/更新保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 */
	public void saveOrUpdate(String entityName, T entity);
		
}
