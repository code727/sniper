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

package org.sniper.persistence.jpa.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * JPA持久化接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface JpaPersistence<T, PK extends Serializable> {
	
	/**
	 * 新增实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 * @return
	 */
	public void persist(T entity);
			
	/**
	 * 批量新增实体对象
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
	 * 同步实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void refresh(T entity);
	
	/**
	 * 同步所有当前正在被托管的实体到数据库
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void flush();

	/**
	 * 分离所有当前正在被托管的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void clear();
	
	/**
	 * 判断实体当前是否被管理中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 * @return
	 */
	public boolean contains(T entity);
	
	/**
	 * 判断ID对应的实体当前是否被管理中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public boolean contains(PK id);
	
	/**
	 * 执行指定的语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @return
	 */
	public int execute(String ql);

	/**
	 * 执行指定带占位符(?)参数的语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param values 参数值组
	 * @return
	 */
	public int execute(String ql, Object[] values);

	/**
	 * 执行指定带命名(=:name)参数的语句后返回受影响的行数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param paramMap
	 * @return
	 */
	public int execute(String ql, Map<String, ?> paramMap);
	
}
