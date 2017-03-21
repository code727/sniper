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
 * Create Date : 2015-3-20
 */

package org.workin.persistence.sqlmap.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.workin.persistence.sqlmap.dao.SqlMapQuery;

/**
 * MyBatis查询DAO实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Repository
public class MyBatisQueryDaoImpl<T> extends MyBatisDaoSupport<T> implements SqlMapQuery<T> {

	@Override
	public long countBySqlMap(String statement) {
		return countBySqlMap(statement, null);
	}

	@Override
	public long countBySqlMap(String statement, Object parameter) {
		return getSqlSession().selectOne(namespace + statement, parameter);
	}

	@Override
	public T queryUniqueBySqlMap(String statement) {
		return queryUniqueBySqlMap(statement, null);
	}

	@Override
	public T queryUniqueBySqlMap(String statement, Object parameter) {
		return queryUniqueBySqlMap(this.getBeanClass(), statement, parameter);
	}

	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String statement) {
		return queryUniqueBySqlMap(resultClass, statement, null);
	}

	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String statement, Object parameter) {
		return getSqlSession().selectOne(namespace + statement, parameter);
	}

	@Override
	public List<T> queryListBySqlMap(String statement) {
		return queryListBySqlMap(statement, null);
	}

	@Override
	public List<T> queryListBySqlMap(String statement, Object parameter) {
		return queryListBySqlMap(this.getBeanClass(), statement, parameter);
	}

	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String statement) {
		return queryListBySqlMap(resultClass, statement, null);
	}

	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String statement, Object parameter) {
		return getSqlSession().selectList(namespace + statement, parameter);
	}

	@Override
	public <K,V> Map<K, V> queryMapBySqlMap(String statement) {
		return queryMapBySqlMap(statement, DEFAULT_KEY_PROPERTY);
	}

	@Override
	public <K,V> Map<K, V> queryMapBySqlMap(String statement, Object parameter) {
		return queryMapBySqlMap(statement, parameter, DEFAULT_KEY_PROPERTY);
	}

	@Override
	public <K,V> Map<K, V> queryMapBySqlMap(String statement, String keyProperty) {
		return queryMapBySqlMap(statement, null, keyProperty);
	}

	@Override
	public <K,V> Map<K, V> queryMapBySqlMap(String statement, Object parameter, String keyProperty) {
		return getSqlSession().selectMap(namespace + statement, parameter, keyProperty);
	}

}
