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

package org.sniper.orm.hibernate.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.sniper.orm.jpa.service.JpaCrudService;

/**
 * 具备基本增删改查功能的Hibernate持久化服务接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface HibernateCrudService<T, PK extends Serializable> extends JpaCrudService<T, PK> {
	
	/**
	 * 根据名称持久化指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 */
	public void persist(String entityName, T entity);
	
	/**
	 * 根据名称持久化指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entityList
	 */
	public void batchPersist(String entityName, List<T> entityList);
	
	/**
	 * 根据名称合并指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 * @return
	 */
	public T merge(String entityName, T entity);
	
	/**
	 * 根据名称批量合并指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entityList
	 * @return
	 */
	public List<T> batchMerge(String entityName, List<T> entityList);
	
	/**
	 * 根据名称删除指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 */
	public void remove(String entityName, T entity);

	/** 
	 * 根据名称批量删除指定的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entityList 
	 */
	void batchRemove(String entityName, List<T> entityList);
	
	/**
	 * 新增保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public PK save(T entity);
	
	/**
	 * 根据名称新增保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 */
	public PK save(String entityName, T entity);
	
	/**
	 * 更新保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * 根据名称更新保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 */
	public void update(String entityName, T entity);
	
	/**
	 * 新增/更新保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void saveOrUpdate(T entity);
	
	/**
	 * 根据名称新增/更新保存实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param entity
	 */
	public void saveOrUpdate(String entityName, T entity);
	
	/**
	 * 根据主键ID加载当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public T loadById(PK id);
	
	/**
	 * 根据主键ID按指定的锁模式锁住并加载当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param lockMode
	 * @return
	 */
	public T loadById(PK id, LockMode lockMode);
	
	/**
	 * 根据主键ID按指定的锁选项锁住并加载当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param lockOptions
	 * @return
	 */
	public T loadById(PK id, LockOptions lockOptions);
	
	/**
	 * 根据名称和主键ID加载当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param id
	 * @return
	 */
	public T loadById(String entityName, PK id);
	
	/**
	 * 根据名称和主键ID按指定的锁模式锁住并加载当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param id
	 * @param lockMode
	 * @return
	 */
	public T loadById(String entityName, PK id, LockMode lockMode);
	
	/**
	 * 根据名称和主键ID按指定的锁选项锁住并加载当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param id
	 * @param lockOptions
	 * @return
	 */
	public T loadById(String entityName, PK id, LockOptions lockOptions);
	
	/**
	 * 加载当前类型的所有实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<T> loadAll();
	
	/**
	 * 加载当前类型所有不重复的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<T> loadAllDistinct();
	
	/**
	 * 根据主键ID获取当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public T getById(PK id);
	
	/**
	 * 根据主键ID按指定的锁模式锁住并获取当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param lockMode
	 * @return
	 */
	public T getById(PK id, LockMode lockMode);
	
	/**
	 * 根据主键ID按指定的锁选项锁住并获取当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param lockOptions
	 * @return
	 */
	public T getById(PK id, LockOptions lockOptions);
	
	/**
	 * 根据名称和主键ID获取当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param id
	 * @return
	 */
	public T getById(String entityName, PK id);
	
	/**
	 * 根据名称和主键ID按指定的锁模式锁住并获取当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param id
	 * @param lockMode
	 * @return
	 */
	public T getById(String entityName, PK id, LockMode lockMode);
	
	/**
	 * 根据名称和主键ID按指定的锁选项锁住并获取当前类型的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityName
	 * @param id
	 * @param lockOptions
	 * @return
	 */
	public T getById(String entityName, PK id, LockOptions lockOptions);

}
