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

package org.sniper.orm.jpa.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.ClassUtils;
import org.sniper.orm.jpa.dao.JpaDao;
import org.sniper.spring.beans.CheckableInitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 具备基本增删改查功能的JPA服务抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractJpaService<T, PK extends Serializable> extends CheckableInitializingBean
		implements JpaBeanService<T, PK>, JpaService<T, PK> {
	
	protected final Logger logger;
	
	@Autowired
	protected JpaDao<T, PK> jpaDao;
	
	public AbstractJpaService() {
		this.logger = LoggerFactory.getLogger(getClass());
	}

	@Override
	public JpaDao<T, PK> getJpaDao() {
		return jpaDao;
	}

	@Override
	public void setJpaDao(JpaDao<T, PK> jpaDao) {
		this.jpaDao = jpaDao;
	}

	@Override
	protected void checkProperties() {
		AssertUtils.assertNotNull(this.jpaDao, "Property 'jpaDao' is required");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void init() throws Exception {
		Class<T> entityType = (Class<T>) ClassUtils.getSuperclassGenricType(getClass());
		// 将当前服务类管理的实体类型传递给持久化DAO，使DAO接口的方法能正常工作
		this.jpaDao.setTargetType(entityType);
	}
		
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
	public void remove(PK id) {
		this.jpaDao.remove(id);
	}

	@Override
	public void batchRemove(List<T> entityList) {
		this.jpaDao.batchRemove(entityList);
	}

	@Override
	public T findById(PK id) {
		return this.jpaDao.findById(id);
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
