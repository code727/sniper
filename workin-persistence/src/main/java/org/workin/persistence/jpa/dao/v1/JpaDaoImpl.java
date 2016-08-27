/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-8-27
 */

package org.workin.persistence.jpa.dao.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;
import org.workin.commons.pagination.PagingQuery;
import org.workin.commons.pagination.PagingResult;
import org.workin.commons.util.CollectionUtils;
import org.workin.persistence.jpa.dao.JpaDao;
import org.workin.persistence.jpa.dao.interfaces.JpaCriteriaQueryCallback;
import org.workin.persistence.pagination.FilterChainPagingQuery;
import org.workin.persistence.pagination.FilterListPagingQuery;
import org.workin.persistence.util.PersistencePropertyFilter;
import org.workin.persistence.util.PersistencePropertyFilterChain;

/**
 * @description JPA标准的DAO实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@Repository
public class JpaDaoImpl<T, PK extends Serializable> extends JpaDaoSupport<T>
		implements JpaDao<T, PK> {

	@Override
	public void persist(T entity) {
		getJpaTemplate().persist(entity);
	}

	@Override
	public void batchPersist(final List<T> entityList) {
		getJpaTemplate().execute(new JpaCallback<T>() {

			@Override
			public T doInJpa(EntityManager em) throws PersistenceException {
				int max = entityList.size() - 1;
				
				for (int i = 0; i <= max; i++) {
					em.persist(entityList.get(i));
					// 最大1000条记录保存一次
					if (((i != 0) && (i % 1000 == 0)) || (i == max))
						em.flush();
				}
				return null;
			}
		});
	}

	@Override
	public T merge(T entity) {
		return getJpaTemplate().merge(entity);
	}

	@Override
	public List<T> batchMerge(final List<T> entityList) {
		return getJpaTemplate().execute(new JpaCallback<List<T>>() {

			@Override
			public List<T> doInJpa(EntityManager em) throws PersistenceException {
				int max = entityList.size() - 1;
				List<T> list = CollectionUtils.newArrayList();
				
				for (int i = 0; i <= max; i++) {
					list.add(em.merge(entityList.get(i)));
					// 最大1000条记录保存一次
					if (((i != 0) && (i % 1000 == 0)) || (i == max))
						em.flush();
				}
				
				return list;
			}
		});
	}

	@Override
	public void remove(T entity) {
		getJpaTemplate().remove(entity);
	}

	@Override
	public void remove(PK primaryKey) {
		T entity = findById(primaryKey);
		if (entity != null)
			remove(entity);
	}

	@Override
	public void batchRemove(final List<T> entityList) {
		getJpaTemplate().execute(new JpaCallback<T>() {

			@Override
			public T doInJpa(EntityManager em) throws PersistenceException {
				int max = entityList.size() - 1;
				T entity;
				
				for (int i = 0; i <= max; i++) {
					entity = entityList.get(i);
					em.refresh(entity);
					em.remove(entity);
						
					if (((i != 0) && (i % 1000 == 0)) || (i == max))
						em.flush();	
				}
				
				return null;
			}
		});
		
	}

	@Override
	public void refresh(T entity) {
		getJpaTemplate().refresh(entity);
	}

	@Override
	public void flush() {
		getJpaTemplate().flush();
	}

	@Override
	public void clear() {
		getJpaTemplate().execute(new JpaCallback<T>() {
			
			@Override
			public T doInJpa(EntityManager em) throws PersistenceException {
				em.clear();
				return null;
			}
		});
	}

	@Override
	public boolean contains(T entity) {
		return getJpaTemplate().contains(entity);
	}

	@Override
	public boolean contains(PK primaryKey) {
		return contains(findById(primaryKey));
	}

	@Override
	public int execute(String ql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int execute(String ql, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param paramMap
	 * @return 
	 */
	@Override
	public int execute(String ql, Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @return 
	 */
	@Override
	public int executeByNativeQuery(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @param values
	 * @return 
	 */
	@Override
	public int executeByNativeQuery(String sql, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @param paramMap
	 * @return 
	 */
	@Override
	public int executeByNativeQuery(String sql, Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param primaryKey
	 * @return 
	 */
	@Override
	public T findById(PK primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param propertyValue
	 * @return 
	 */
	@Override
	public T findUniqueByProperty(String propertyName, Object propertyValue) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param paramMap
	 * @return 
	 */
	@Override
	public T findUniqueByPropertys(Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @return 
	 */
	@Override
	public T findUniqueByQueryString(String ql) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @return 
	 */
	@Override
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param values
	 * @return 
	 */
	@Override
	public T findUniqueByQueryString(String ql, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param values
	 * @return 
	 */
	@Override
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql,
			Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param paramMap
	 * @return 
	 */
	@Override
	public T findUniqueByQueryString(String ql, Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param paramMap
	 * @return 
	 */
	@Override
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql,
			Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @return 
	 */
	@Override
	public List<T> find(String ql) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @return 
	 */
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param values
	 * @return 
	 */
	@Override
	public List<T> find(String ql, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param values
	 * @return 
	 */
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param paramMap
	 * @return 
	 */
	@Override
	public List<T> find(String ql, Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param paramMap
	 * @return 
	 */
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql,
			Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> find(String ql, int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, int start,
			int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param values
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> find(String ql, Object[] values, int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param values
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, Object[] values,
			int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> find(String ql, Map<String, ?> paramMap, int start,
			int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param ql
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql,
			Map<String, ?> paramMap, int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> find(int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param propertyValue
	 * @return 
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param propertyValue
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue,
			int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param paramMap
	 * @return 
	 */
	@Override
	public List<T> findByPropertys(Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> findByPropertys(Map<String, ?> paramMap, int start,
			int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return 
	 */
	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return 
	 */
	@Override
	public List<T> findAllDistinct() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param propertyValue
	 * @return 
	 */
	@Override
	public long countByProperty(String propertyName, Object propertyValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param paramMap
	 * @return 
	 */
	@Override
	public long countByPropertys(Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @return 
	 */
	@Override
	public long countByQueryString(String ql) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param values
	 * @return 
	 */
	@Override
	public long countByQueryString(String ql, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ql
	 * @param paramMap
	 * @return 
	 */
	@Override
	public long countByQueryString(String ql, Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @return 
	 */
	@Override
	public T findUniqueByNamedQuery(String queryName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @return 
	 */
	@Override
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param values
	 * @return 
	 */
	@Override
	public T findUniqueByNamedQuery(String queryName, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param values
	 * @return 
	 */
	@Override
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName,
			Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param paramMap
	 * @return 
	 */
	@Override
	public T findUniqueByNamedQuery(String queryName, Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param paramMap
	 * @return 
	 */
	@Override
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName,
			Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @return 
	 */
	@Override
	public List<T> findByNamedQuery(String queryName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @return 
	 */
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> findByNamedQuery(String queryName, int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName,
			int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param values
	 * @return 
	 */
	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param values
	 * @return 
	 */
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName,
			Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param values
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values,
			int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param values
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName,
			Object[] values, int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param paramMap
	 * @return 
	 */
	@Override
	public List<T> findByNamedQuery(String queryName, Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param paramMap
	 * @return 
	 */
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName,
			Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> findByNamedQuery(String queryName, Map<String, ?> paramMap,
			int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param queryName
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName,
			Map<String, ?> paramMap, int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @return 
	 */
	@Override
	public int executeNamedQuery(String queryName) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param values
	 * @return 
	 */
	@Override
	public int executeNamedQuery(String queryName, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param paramMap
	 * @return 
	 */
	@Override
	public int executeNamedQuery(String queryName, Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @return 
	 */
	@Override
	public long countByNamedQuery(String queryName) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param values
	 * @return 
	 */
	@Override
	public long countByNamedQuery(String queryName, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryName
	 * @param paramMap
	 * @return 
	 */
	@Override
	public long countByNamedQuery(String queryName, Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @return 
	 */
	@Override
	public T findUniqueByNativeQuery(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param sql
	 * @return 
	 */
	@Override
	public <R> R findUniqueByNativeQuery(Class<R> resultClass, String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @param values
	 * @return 
	 */
	@Override
	public T findUniqueByNativeQuery(String sql, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param sql
	 * @param values
	 * @return 
	 */
	@Override
	public <R> R findUniqueByNativeQuery(Class<R> resultClass, String sql,
			Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @param paramMap
	 * @return 
	 */
	@Override
	public T findUniqueByNativeQuery(String sql, Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param sql
	 * @param paramMap
	 * @return 
	 */
	@Override
	public <R> R findUniqueByNativeQuery(Class<R> resultClass, String sql,
			Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @return 
	 */
	@Override
	public List<T> findByNativeQuery(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param sql
	 * @return 
	 */
	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @param values
	 * @return 
	 */
	@Override
	public List<T> findByNativeQuery(String sql, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param sql
	 * @param values
	 * @return 
	 */
	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql,
			Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @param paramMap
	 * @return 
	 */
	@Override
	public List<T> findByNativeQuery(String sql, Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param sql
	 * @param paramMap
	 * @return 
	 */
	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql,
			Map<String, ?> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> findByNativeQuery(String sql, int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param sql
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql,
			int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @param values
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> findByNativeQuery(String sql, Object[] values, int start,
			int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param sql
	 * @param values
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql,
			Object[] values, int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sql
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> findByNativeQuery(String sql, Map<String, ?> paramMap,
			int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultClass
	 * @param sql
	 * @param paramMap
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql,
			Map<String, ?> paramMap, int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @param callback
	 * @return 
	 */
	@Override
	public <P> T findUniqueByCriteria(P parameter,
			JpaCriteriaQueryCallback<T> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @param callback
	 * @return 
	 */
	@Override
	public <P> List<T> findByCriteria(P parameter,
			JpaCriteriaQueryCallback<T> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @param start
	 * @param maxRows
	 * @param callback
	 * @return 
	 */
	@Override
	public <P> List<T> findByCriteria(P parameter, int start, int maxRows,
			JpaCriteriaQueryCallback<T> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @param callback
	 * @return 
	 */
	@Override
	public <P> long countByCriteria(P parameter,
			JpaCriteriaQueryCallback<T> callback) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @param distinct
	 * @param callback
	 * @return 
	 */
	@Override
	public <P> long countByCriteria(P parameter, boolean distinct,
			JpaCriteriaQueryCallback<T> callback) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query
	 * @param callback
	 * @return 
	 */
	@Override
	public PagingResult<T> pagingQuery(PagingQuery query,
			JpaCriteriaQueryCallback<T> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @return 
	 */
	@Override
	public T findUniqueByFilterList(List<PersistencePropertyFilter> filterList) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @return 
	 */
	@Override
	public T findUniqueByFilterChain(PersistencePropertyFilterChain chain) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @return 
	 */
	@Override
	public List<T> findByFilterList(List<PersistencePropertyFilter> filterList) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> findByFilterList(List<PersistencePropertyFilter> filterList,
			int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @return 
	 */
	@Override
	public List<T> findByFilterChain(PersistencePropertyFilterChain chain) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @param start
	 * @param maxRows
	 * @return 
	 */
	@Override
	public List<T> findByFilterChain(PersistencePropertyFilterChain chain,
			int start, int maxRows) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterList
	 * @return 
	 */
	@Override
	public long countByFilterList(List<PersistencePropertyFilter> filterList) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param chain
	 * @return 
	 */
	@Override
	public long countByFilterChain(PersistencePropertyFilterChain chain) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query
	 * @return 
	 */
	@Override
	public PagingResult<T> pagingQuery(FilterListPagingQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query
	 * @return 
	 */
	@Override
	public PagingResult<T> pagingQuery(FilterChainPagingQuery query) {
		// TODO Auto-generated method stub
		return null;
	}


}
