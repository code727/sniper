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

/**
 * 具备基本增删改查功能的Hibernate持久化服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractHibernateCrudService<T, PK extends Serializable> extends
		AbstractHibernateService<T, PK> implements HibernateCrudService<T, PK> {

	@Override
	public void persist(T entity) {
		this.hibernateDao.persist(entity);
	}
	
	@Override
	public void persist(String entityName, T entity) {
		this.hibernateDao.persist(entityName, entity);
	}

	@Override
	public void batchPersist(List<T> entityList) {
		this.hibernateDao.batchPersist(entityList);
	}
	
	@Override
	public void batchPersist(String entityName, List<T> entityList) {
		this.hibernateDao.batchPersist(entityName, entityList);
	}

	@Override
	public T merge(T entity) {
		return this.hibernateDao.merge(entity);
	}
	
	@Override
	public T merge(String entityName, T entity) {
		return this.hibernateDao.merge(entityName, entity);
	}

	@Override
	public List<T> batchMerge(List<T> entityList) {
		return this.hibernateDao.batchMerge(entityList);
	}
	
	@Override
	public List<T> batchMerge(String entityName, List<T> entityList) {
		return this.hibernateDao.batchMerge(entityName, entityList);
	}

	@Override
	public void remove(T entity) {
		this.hibernateDao.remove(entity);
	}
	
	@Override
	public void remove(String entityName, T entity) {
		this.hibernateDao.remove(entityName, entity); 
	}

	@Override
	public void remove(PK id) {
		this.hibernateDao.remove(id);
	}

	@Override
	public void batchRemove(List<T> entityList) {
		this.hibernateDao.batchRemove(entityList);
	}
	
	@Override
	public void batchRemove(String entityName, List<T> entityList) {
		this.hibernateDao.batchRemove(entityName, entityList);
	}

	@Override
	public T findById(PK id) {
		return this.hibernateDao.findById(id);
	}

	@Override
	public List<T> findAll() {
		return this.hibernateDao.findAll();
	}

	@Override
	public List<T> findAllDistinct() {
		return this.hibernateDao.findAllDistinct();
	}

	@Override
	public PK save(T entity) {
		return this.hibernateDao.save(entity);
	}

	@Override
	public PK save(String entityName, T entity) {
		return this.hibernateDao.save(entityName, entity);
	}

	@Override
	public void update(T entity) {
		this.hibernateDao.update(entity);
	}

	@Override
	public void update(String entityName, T entity) {
		this.hibernateDao.update(entityName, entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.hibernateDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdate(String entityName, T entity) {
		this.hibernateDao.saveOrUpdate(entityName, entity);
	}

	@Override
	public T loadById(PK id) {
		return this.hibernateDao.loadById(id);
	}

	@Override
	public T loadById(PK id, LockMode lockMode) {
		return this.hibernateDao.loadById(id, lockMode);
	}

	@Override
	public T loadById(PK id, LockOptions lockOptions) {
		return this.hibernateDao.loadById(id, lockOptions);
	}

	@Override
	public T loadById(String entityName, PK id) {
		return this.hibernateDao.loadById(entityName, id);
	}

	@Override
	public T loadById(String entityName, PK id, LockMode lockMode) {
		return this.hibernateDao.loadById(entityName, id, lockMode);
	}

	@Override
	public T loadById(String entityName, PK id, LockOptions lockOptions) {
		return this.hibernateDao.loadById(entityName, id, lockOptions);
	}

	@Override
	public List<T> loadAll() {
		return this.hibernateDao.loadAll();
	}

	@Override
	public List<T> loadAllDistinct() {
		return this.hibernateDao.loadAllDistinct();
	}
	
	@Override
	public T getById(PK id) {
		return this.hibernateDao.getById(id);
	}

	@Override
	public T getById(PK id, LockMode lockMode) {
		return this.hibernateDao.getById(id, lockMode);
	}

	@Override
	public T getById(PK id, LockOptions lockOptions) {
		return this.hibernateDao.getById(id, lockOptions);
	}

	@Override
	public T getById(String entityName, PK id) {
		return this.hibernateDao.getById(entityName, id);
	}

	@Override
	public T getById(String entityName, PK id, LockMode lockMode) {
		return this.hibernateDao.getById(entityName, id, lockMode);
	}

	@Override
	public T getById(String entityName, PK id, LockOptions lockOptions) {
		return this.hibernateDao.getById(entityName, id, lockOptions);
	}

}
