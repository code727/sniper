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
 * @description 具备基本增删改查功能的JPA持久化服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractJpaCrudService<T, PK extends Serializable> extends
		AbstractJpaService<T, PK> implements JpaCrudService<T, PK> {
		
	@Override
	public void persist(T entity) {
		this.jpaDao.persist(entity);
	}

	@Override
	public void batchPersist(List<T> entityList) {
		this.jpaDao.batchPersist(entityList);
	}

	@Override
	public T merge(T entity) {
		return this.jpaDao.merge(entity);
	}

	@Override
	public List<T> batchMerge(List<T> entityList) {
		return this.jpaDao.batchMerge(entityList);
	}

	@Override
	public void remove(T entity) {
		this.jpaDao.remove(entity);
	}

	@Override
	public void remove(PK primaryKey) {
		this.jpaDao.remove(primaryKey);
	}

	@Override
	public void batchRemove(List<T> entityList) {
		this.jpaDao.batchRemove(entityList);
	}

	@Override
	public T findById(PK primaryKey) {
		return this.jpaDao.findById(primaryKey);
	}

	@Override
	public List<T> findAll() {
		return this.jpaDao.findAll();
	}

	@Override
	public List<T> findAllDistinct() {
		return this.jpaDao.findAllDistinct();
	}

}
