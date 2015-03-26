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

package org.workin.persistence.sqlmap.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.workin.commons.util.ClassUtils;
import org.workin.persistence.sqlmap.dao.SqlMapQuery;

/**
 * @description IBatis查询DAO实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class IBatisQueryDaoImpl<T> extends SqlMapClientDaoSupport
		implements SqlMapQuery<T> {
	
	/** 当前DAO所关联的实体类型 */
	private Class<T> entityClass;
	
	@Override
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getEntityClass() {
		if (this.entityClass == null)
			this.setEntityClass((Class<T>) ClassUtils.getSuperClassGenricType(getClass()));
		return this.entityClass;
	}
	
	@Override
	public long countBySqlMap(String id) {
		return countBySqlMap(id, null);
	}

	@Override
	public long countBySqlMap(String id, Object parameter) {
		return queryUniqueBySqlMap(Long.class, id, parameter);
	}

	@Override
	public T queryUniqueBySqlMap(String id) {
		return queryUniqueBySqlMap(id, null);
	}

	@Override
	public T queryUniqueBySqlMap(String id, Object parameter) {
		return queryUniqueBySqlMap(this.getEntityClass(), id, parameter);
	}

	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String id) {
		return queryUniqueBySqlMap(resultClass, id, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> R queryUniqueBySqlMap(Class<R> resultClass, String id, Object parameter) {
		return (R) getSqlMapClientTemplate().queryForObject(id, parameter);
	}

	@Override
	public List<T> queryListBySqlMap(String id) {
		return queryListBySqlMap(id, null);
	}

	@Override
	public List<T> queryListBySqlMap(String id, Object parameter) {
		return queryListBySqlMap(this.getEntityClass(), id, parameter);
	}
	
	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String id) {
		return queryListBySqlMap(resultClass, id, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> queryListBySqlMap(Class<R> resultClass, String id, Object parameter) {
		return getSqlMapClientTemplate().queryForList(id, parameter);
	}
	
	@Override
	public <K,V> Map<K, V>  queryMapBySqlMap(String id) {
		return queryMapBySqlMap(id, (Object) null);
	}
	
	@Override
	public <K,V> Map<K, V> queryMapBySqlMap(String id, Object parameter) {
		return queryMapBySqlMap(id, parameter, DEFAULT_KEY_PROPERTY);
	}

	@Override
	public <K,V> Map<K, V> queryMapBySqlMap(String id, String keyProperty) {
		return queryMapBySqlMap(id, null, keyProperty);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K,V> Map<K, V> queryMapBySqlMap(String id, Object parameter, String keyProperty) {
		return getSqlMapClientTemplate().queryForMap(id, parameter, keyProperty);
	}

}
