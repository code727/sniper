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

import org.sniper.commons.util.StringUtils;
import org.sniper.sqlmap.dao.SqlMapQuery;

/**
 * SQL映射持久化服务抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractSqlMapService<T> extends SqlMapServiceSupport<T> implements SqlMapService<T> {
		
	@Override
	public int insert(String statement) {
		return insert(statement, null);
	}

	@Override
	public int insert(String statement, Object parameter) {
		return sqlMapDao.insert(namespace + statement, parameter);
	}

	@Override
	public int update(String statement) {
		return update(statement, null);
	}

	@Override
	public int update(String statement, Object parameter) {
		return sqlMapDao.update(namespace + statement, parameter);
	}

	@Override
	public int delete(String statement) {
		return delete(statement, null);
	}

	@Override
	public int delete(String statement, Object parameter) {
		return sqlMapDao.delete(namespace + statement, parameter);
	}

	@Override
	public long countBySqlMap(String statement) {
		return countBySqlMap(statement, null);
	}

	@Override
	public long countBySqlMap(String statement, Object parameter) {
		return sqlMapDao.countBySqlMap(namespace + statement, parameter);
	}

	@Override
	public T queryUniqueBySqlMap(String statement) {
		return queryUniqueBySqlMap(statement, null);
	}

	@Override
	public T queryUniqueBySqlMap(String statement, Object parameter) {
		return sqlMapDao.queryUniqueBySqlMap(namespace + statement, parameter);
	}

	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String statement) {
		return queryUniqueBySqlMap(resultClass, statement, null);
	}

	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String statement, Object parameter) {
		return sqlMapDao.queryUniqueBySqlMap(resultClass, namespace + statement, parameter);
	}

	@Override
	public List<T> queryListBySqlMap(String statement) {
		return queryListBySqlMap(statement, null);
	}

	@Override
	public List<T> queryListBySqlMap(String statement, Object parameter) {
		return sqlMapDao.queryListBySqlMap(namespace + statement, parameter);
	}

	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String statement) {
		return queryListBySqlMap(resultClass, namespace + statement, null);
	}

	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String statement, Object parameter) {
		return sqlMapDao.queryListBySqlMap(resultClass, namespace + statement, parameter);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String statement) {
		return queryMapBySqlMap(statement, (Object) null);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String statement, Object parameter) {
		return queryMapBySqlMap(namespace + statement, parameter, null);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String statement, String keyProperty) {
		return queryMapBySqlMap(namespace + statement, null, keyProperty);
	}

	@Override
	public <K, V> Map<K, V> queryMapBySqlMap(String statement, Object parameter, String keyProperty) {
		return sqlMapDao.queryMapBySqlMap(namespace + statement, parameter,
				StringUtils.isNotBlank(keyProperty) ? keyProperty : SqlMapQuery.DEFAULT_KEY_PROPERTY);
	}

}
