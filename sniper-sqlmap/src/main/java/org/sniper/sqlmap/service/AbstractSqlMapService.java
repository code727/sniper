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

package org.sniper.sqlmap.service;

import java.util.List;
import java.util.Map;

/**
 * SQL映射持久化服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractSqlMapService<T> extends SqlMapServiceSupport<T> implements SqlMapService<T> {
		
	@Override
	public Object insert(String statement) {
		return this.sqlMapDao.insert(namespace + statement);
	}

	@Override
	public Object insert(String statement, Object parameter) {
		return this.sqlMapDao.insert(namespace + statement, parameter);
	}

	@Override
	public int update(String statement) {
		return this.sqlMapDao.update(namespace + statement);
	}

	@Override
	public int update(String statement, Object parameter) {
		return this.sqlMapDao.update(namespace + statement, parameter);
	}

	@Override
	public int delete(String statement) {
		return this.sqlMapDao.delete(namespace + statement);
	}

	@Override
	public int delete(String statement, Object parameter) {
		return this.sqlMapDao.delete(namespace + statement, parameter);
	}

	@Override
	public long countBySqlMap(String statement) {
		return this.sqlMapDao.countBySqlMap(namespace + statement);
	}

	@Override
	public long countBySqlMap(String statement, Object parameter) {
		return this.sqlMapDao.countBySqlMap(namespace + statement, parameter);
	}

	@Override
	public T queryUniqueBySqlMap(String statement) {
		return this.sqlMapDao.queryUniqueBySqlMap(namespace + statement);
	}

	@Override
	public T queryUniqueBySqlMap(String statement, Object parameter) {
		return this.sqlMapDao.queryUniqueBySqlMap(namespace + statement, parameter);
	}

	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String statement) {
		return this.sqlMapDao.queryUniqueBySqlMap(resultClass, namespace + statement);
	}

	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String statement,
			Object parameter) {
		return this.sqlMapDao.queryUniqueBySqlMap(resultClass, namespace + statement, parameter);
	}

	@Override
	public List<T> queryListBySqlMap(String statement) {
		return this.sqlMapDao.queryListBySqlMap(namespace + statement);
	}

	@Override
	public List<T> queryListBySqlMap(String statement, Object parameter) {
		return this.sqlMapDao.queryListBySqlMap(namespace + statement, parameter);
	}

	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String statement) {
		return this.sqlMapDao.queryListBySqlMap(resultClass, namespace + statement);
	}

	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String statement, Object parameter) {
		return this.sqlMapDao.queryListBySqlMap(resultClass, namespace + statement, parameter);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String statement) {
		return this.sqlMapDao.queryMapBySqlMap(namespace + statement);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String statement, Object parameter) {
		return this.sqlMapDao.queryMapBySqlMap(namespace + statement, parameter);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String statement, String keyProperty) {
		return this.sqlMapDao.queryMapBySqlMap(namespace + statement, keyProperty);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String statement, Object parameter, String keyProperty) {
		return this.sqlMapDao.queryMapBySqlMap(namespace + statement, parameter, keyProperty);
	}

}
