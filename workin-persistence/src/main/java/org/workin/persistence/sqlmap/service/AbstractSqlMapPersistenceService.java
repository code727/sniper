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
 * Create Date : 2015-3-24
 */

package org.workin.persistence.sqlmap.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.persistence.sqlmap.dao.SqlMapPersistenceDao;

/**
 * @description SQL映射持久化服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AbstractSqlMapPersistenceService<T> implements
		SqlMapPersistenceService<T>, InitializingBean {
	
	@Autowired
	protected SqlMapPersistenceDao<T> sqlMapPersistenceDao;

	@Override
	public void setSqlMapPersistenceDao(
			SqlMapPersistenceDao<T> sqlMapPersistenceDao) {
		this.sqlMapPersistenceDao = sqlMapPersistenceDao;
	}

	@Override
	public SqlMapPersistenceDao<T> getSqlMapPersistenceDao() {
		return this.sqlMapPersistenceDao;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.sqlMapPersistenceDao == null)
			throw new BeanCreationException("SqlMapPersistenceDao object can not be null, please inject to spring container.");
	}

	@Override
	public Object insert(String id) {
		return this.sqlMapPersistenceDao.insert(id);
	}

	@Override
	public Object insert(String id, Object parameter) {
		return this.sqlMapPersistenceDao.insert(id, parameter);
	}

	@Override
	public int update(String id) {
		return this.sqlMapPersistenceDao.update(id);
	}

	@Override
	public int update(String id, Object parameter) {
		return this.sqlMapPersistenceDao.update(id, parameter);
	}

	@Override
	public int delete(String id) {
		return this.sqlMapPersistenceDao.delete(id);
	}

	@Override
	public int delete(String id, Object parameter) {
		return this.sqlMapPersistenceDao.delete(id, parameter);
	}

	@Override
	public long countBySqlMap(String id) {
		return this.sqlMapPersistenceDao.countBySqlMap(id);
	}

	@Override
	public long countBySqlMap(String id, Object parameter) {
		return this.sqlMapPersistenceDao.countBySqlMap(id, parameter);
	}

	@Override
	public T queryUniqueBySqlMap(String id) {
		return this.sqlMapPersistenceDao.queryUniqueBySqlMap(id);
	}

	@Override
	public T queryUniqueBySqlMap(String id, Object parameter) {
		return this.sqlMapPersistenceDao.queryUniqueBySqlMap(id, parameter);
	}

	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String id) {
		return this.sqlMapPersistenceDao.queryUniqueBySqlMap(resultClass, id);
	}

	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String id,
			Object parameter) {
		return this.sqlMapPersistenceDao.queryUniqueBySqlMap(resultClass, id, parameter);
	}

	@Override
	public List<T> queryListBySqlMap(String id) {
		return this.sqlMapPersistenceDao.queryListBySqlMap(id);
	}

	@Override
	public List<T> queryListBySqlMap(String id, Object parameter) {
		return this.sqlMapPersistenceDao.queryListBySqlMap(id, parameter);
	}

	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String id) {
		return this.sqlMapPersistenceDao.queryListBySqlMap(resultClass, id);
	}

	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String id, Object parameter) {
		return this.sqlMapPersistenceDao.queryListBySqlMap(resultClass, id, parameter);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String id) {
		return this.sqlMapPersistenceDao.queryMapBySqlMap(id);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String id, Object parameter) {
		return this.sqlMapPersistenceDao.queryMapBySqlMap(id, parameter);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String id, String keyProperty) {
		return this.sqlMapPersistenceDao.queryMapBySqlMap(id, keyProperty);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String id, Object parameter, String keyProperty) {
		return this.sqlMapPersistenceDao.queryMapBySqlMap(id, parameter, keyProperty);
	}

}
