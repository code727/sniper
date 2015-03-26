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

package org.workin.persistence.hibernate.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;

/**
 * @description 具备基本增删改查功能的Hibernate持久化服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractHibernateCrudService<T, PK extends Serializable> extends
		AbstractHibernatePersistenceService<T, PK> implements HibernateCrudService<T, PK> {

	@Override
	public void persist(T entity) {
		this.hibernatePersistenceDao.persist(entity);
	}
	
	@Override
	public void persist(String entityName, T entity) {
		this.hibernatePersistenceDao.persist(entityName, entity);
	}

	@Override
	public void batchPersist(List<T> entityList) {
		this.hibernatePersistenceDao.batchPersist(entityList);
	}
	
	@Override
	public void batchPersist(String entityName, List<T> entityList) {
		this.hibernatePersistenceDao.batchPersist(entityName, entityList);
	}

	@Override
	public T merge(T entity) {
		return this.hibernatePersistenceDao.merge(entity);
	}
	
	@Override
	public T merge(String entityName, T entity) {
		return this.hibernatePersistenceDao.merge(entityName, entity);
	}

	@Override
	public List<T> batchMerge(List<T> entityList) {
		return this.hibernatePersistenceDao.batchMerge(entityList);
	}
	
	@Override
	public List<T> batchMerge(String entityName, List<T> entityList) {
		return this.hibernatePersistenceDao.batchMerge(entityName, entityList);
	}

	@Override
	public void remove(T entity) {
		this.hibernatePersistenceDao.remove(entity);
	}
	
	@Override
	public void remove(String entityName, T entity) {
		this.hibernatePersistenceDao.remove(entityName, entity); 
	}

	@Override
	public void remove(PK primaryKey) {
		this.hibernatePersistenceDao.remove(primaryKey);
	}

	@Override
	public void batchRemove(List<T> entityList) {
		this.hibernatePersistenceDao.batchRemove(entityList);
	}
	
	@Override
	public void batchRemove(String entityName, List<T> entityList) {
		this.hibernatePersistenceDao.batchRemove(entityName, entityList);
	}

	@Override
	public T findById(PK primaryKey) {
		return this.hibernatePersistenceDao.findById(primaryKey);
	}

	@Override
	public List<T> findAll() {
		return this.hibernatePersistenceDao.findAll();
	}

	@Override
	public List<T> findAllDistinct() {
		return this.hibernatePersistenceDao.findAllDistinct();
	}

	@Override
	public PK save(T entity) {
		return this.hibernatePersistenceDao.save(entity);
	}

	@Override
	public PK save(String entityName, T entity) {
		return this.hibernatePersistenceDao.save(entityName, entity);
	}

	@Override
	public void update(T entity) {
		this.hibernatePersistenceDao.update(entity);
	}

	@Override
	public void update(String entityName, T entity) {
		this.hibernatePersistenceDao.update(entityName, entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.hibernatePersistenceDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdate(String entityName, T entity) {
		this.hibernatePersistenceDao.saveOrUpdate(entityName, entity);
	}

	@Override
	public T loadById(PK primaryKey) {
		return this.hibernatePersistenceDao.loadById(primaryKey);
	}

	@Override
	public T loadById(PK primaryKey, LockMode lockMode) {
		return this.hibernatePersistenceDao.loadById(primaryKey, lockMode);
	}

	@Override
	public T loadById(PK primaryKey, LockOptions lockOptions) {
		return this.hibernatePersistenceDao.loadById(primaryKey, lockOptions);
	}

	@Override
	public T loadById(String entityName, PK primaryKey) {
		return this.hibernatePersistenceDao.loadById(entityName, primaryKey);
	}

	@Override
	public T loadById(String entityName, PK primaryKey, LockMode lockMode) {
		return this.hibernatePersistenceDao.loadById(entityName, primaryKey, lockMode);
	}

	@Override
	public T loadById(String entityName, PK primaryKey, LockOptions lockOptions) {
		return this.hibernatePersistenceDao.loadById(entityName, primaryKey, lockOptions);
	}

	@Override
	public List<T> loadAll() {
		return this.hibernatePersistenceDao.loadAll();
	}

	@Override
	public List<T> loadAllDistinct() {
		return this.hibernatePersistenceDao.loadAllDistinct();
	}
	
	@Override
	public T getById(PK primaryKey) {
		return this.hibernatePersistenceDao.getById(primaryKey);
	}

	@Override
	public T getById(PK primaryKey, LockMode lockMode) {
		return this.hibernatePersistenceDao.getById(primaryKey, lockMode);
	}

	@Override
	public T getById(PK primaryKey, LockOptions lockOptions) {
		return this.hibernatePersistenceDao.getById(primaryKey, lockOptions);
	}

	@Override
	public T getById(String entityName, PK primaryKey) {
		return this.hibernatePersistenceDao.getById(entityName, primaryKey);
	}

	@Override
	public T getById(String entityName, PK primaryKey, LockMode lockMode) {
		return this.hibernatePersistenceDao.getById(entityName, primaryKey, lockMode);
	}

	@Override
	public T getById(String entityName, PK primaryKey, LockOptions lockOptions) {
		return this.hibernatePersistenceDao.getById(entityName, primaryKey, lockOptions);
	}

}
