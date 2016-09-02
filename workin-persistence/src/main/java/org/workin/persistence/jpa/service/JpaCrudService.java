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
 * Create Date : 2015-3-10
 */

package org.workin.persistence.jpa.service;

import java.io.Serializable;
import java.util.List;

/**
 * 具备基本增删改查功能的JPA持久化服务接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface JpaCrudService<T, PK extends Serializable> {
	
	/**
	 * 新增持久化实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void persist(T entity);
	
	/**
	 * 批量新增持久化实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityList
	 */
	public void batchPersist(List<T> entityList);
	
	/**
	 * 合并(新增/更新)实体对象 - 实体与数据库同步
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 * @return
	 */
	public T merge(T entity);
	
	/**
	 * 批量合并(新增/更新)实体对象  - 实体与数据库同步
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityList
	 * @return
	 */
	public List<T> batchMerge(List<T> entityList);
	
	/**
	 * 删除实体对象 - 实体游离
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void remove(T entity);
	
	/**
	 * 删除指定主键ID对应的实体对象 - 实体游离
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 */
	public void remove(PK id);
	
	/**
	 * 批量删除实体对象 - 实体游离
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityList
	 */
	public void batchRemove(List<T> entityList);
	
	/**
	 * 根据主键ID查询当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public T findById(PK id);

	/**
	 * 查询当前类型的所有实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 查询当前类型所有不重复的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<T> findAllDistinct();

}
