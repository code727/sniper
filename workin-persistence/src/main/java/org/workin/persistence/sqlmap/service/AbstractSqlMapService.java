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

import org.springframework.beans.factory.annotation.Autowired;
import org.workin.persistence.sqlmap.dao.SqlMapDao;
import org.workin.spring.beans.CheckableInitializingBeanAdapter;

/**
 * @description SQL映射持久化服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AbstractSqlMapService<T> extends CheckableInitializingBeanAdapter
		implements SqlMapService<T>, SqlMapBeanService<T> {
		
	@Autowired
	protected SqlMapDao<T> sqlMapDao;

	@Override
	public void setSqlMapDao(SqlMapDao<T> sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}

	@Override
	public SqlMapDao<T> getSqlMapDao() {
		return this.sqlMapDao;
	}
	
	@Override
	protected void checkProperties() {
		if (this.sqlMapDao == null)
			throw new IllegalArgumentException("Property 'sqlMapDao' is required");
	}

	@Override
	public Object insert(String id) {
		return this.sqlMapDao.insert(id);
	}

	@Override
	public Object insert(String id, Object parameter) {
		return this.sqlMapDao.insert(id, parameter);
	}

	@Override
	public int update(String id) {
		return this.sqlMapDao.update(id);
	}

	@Override
	public int update(String id, Object parameter) {
		return this.sqlMapDao.update(id, parameter);
	}

	@Override
	public int delete(String id) {
		return this.sqlMapDao.delete(id);
	}

	@Override
	public int delete(String id, Object parameter) {
		return this.sqlMapDao.delete(id, parameter);
	}

	@Override
	public long countBySqlMap(String id) {
		return this.sqlMapDao.countBySqlMap(id);
	}

	@Override
	public long countBySqlMap(String id, Object parameter) {
		return this.sqlMapDao.countBySqlMap(id, parameter);
	}

	@Override
	public T queryUniqueBySqlMap(String id) {
		return this.sqlMapDao.queryUniqueBySqlMap(id);
	}

	@Override
	public T queryUniqueBySqlMap(String id, Object parameter) {
		return this.sqlMapDao.queryUniqueBySqlMap(id, parameter);
	}

	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String id) {
		return this.sqlMapDao.queryUniqueBySqlMap(resultClass, id);
	}

	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String id,
			Object parameter) {
		return this.sqlMapDao.queryUniqueBySqlMap(resultClass, id, parameter);
	}

	@Override
	public List<T> queryListBySqlMap(String id) {
		return this.sqlMapDao.queryListBySqlMap(id);
	}

	@Override
	public List<T> queryListBySqlMap(String id, Object parameter) {
		return this.sqlMapDao.queryListBySqlMap(id, parameter);
	}

	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String id) {
		return this.sqlMapDao.queryListBySqlMap(resultClass, id);
	}

	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String id, Object parameter) {
		return this.sqlMapDao.queryListBySqlMap(resultClass, id, parameter);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String id) {
		return this.sqlMapDao.queryMapBySqlMap(id);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String id, Object parameter) {
		return this.sqlMapDao.queryMapBySqlMap(id, parameter);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String id, String keyProperty) {
		return this.sqlMapDao.queryMapBySqlMap(id, keyProperty);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String id, Object parameter, String keyProperty) {
		return this.sqlMapDao.queryMapBySqlMap(id, parameter, keyProperty);
	}

}
