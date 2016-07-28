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
 * Create Date : 2015-1-29
 */

package org.workin.persistence.jpa.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.workin.commons.pagination.PagingQuery;
import org.workin.commons.pagination.PagingResult;
import org.workin.commons.pagination.result.SimplePagingResult;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.ClassUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.persistence.pagination.FilterChainPagingQuery;
import org.workin.persistence.pagination.FilterListPagingQuery;
import org.workin.persistence.util.PersistencePropertyFilter;
import org.workin.persistence.util.PersistencePropertyFilterChain;
import org.workin.persistence.util.PersistenceUtils;

/**
 * @description JPA2标准的持久化实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Repository
public class Jpa2PersistenceDaoImpl<T, PK extends Serializable> extends
		Jpa2DaoSupport implements JpaPersistenceDao<T, PK> {
	
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
	public void persist(T entity) {
		getEntityManager().persist(entity);
	}

	@Override
	public void batchPersist(List<T> entityList) {
		int max = entityList.size();
		for (int i = 0; i < max; i++) {
			getEntityManager().persist(entityList.get(i));
			// 最大1000条记录保存一次
			if (((i != 0) && (i % 1000 == 0)) || (i == max - 1))
				getEntityManager().flush();
		}
	}

	@Override
	public T merge(T entity) {
		return getEntityManager().merge(entity);
	}

	@Override
	public List<T> batchMerge(List<T> entityList) {
		int max = entityList.size();
		List<T> list = CollectionUtils.newArrayList();
		T entity;
		for (int i = 0; i < max; i++) {
			entity = entityList.get(i);
			if (entity != null)
				list.add(getEntityManager().merge(entityList.get(i)));
			// 最大1000条记录保存一次
			if (((i != 0) && (i % 1000 == 0)) || (i == max - 1))
				getEntityManager().flush();
		}
		return list;
	}

	@Override
	public void remove(T entity) {
		getEntityManager().remove(entity);
	}

	@Override
	public void remove(PK primaryKey) {
		T entity = getEntityManager().find(this.getEntityClass(), primaryKey);
		if (entity != null)
			remove(entity);
	}

	@Override
	public void batchRemove(List<T> entityList) {
		int max = entityList.size();
		T entity;
		for (int i = 0; i < max; i++) {
			entity = entityList.get(i);
			if (entity != null) {
				getEntityManager().refresh(entityList.get(i));
				getEntityManager().remove(entity);
			}
			if (((i != 0) && (i % 1000 == 0)) || (i == max - 1))
				getEntityManager().flush();	
		}
	}

	@Override
	public void refresh(T entity) {
		getEntityManager().refresh(entity);
	}

	@Override
	public void flush() {
		getEntityManager().flush();
	}

	@Override
	public void clear() {
		getEntityManager().clear();
	}

	@Override
	public boolean contains(T entity) {
		return getEntityManager().contains(entity);
	}
	
	@Override
	public boolean contains(PK primaryKey) {
		T entity = getEntityManager().find(this.getEntityClass(), primaryKey);
		return contains(entity);
	}

	@Override
	public int execute(String ql) {
		return execute(ql, (Object[])null);
	}

	@Override
	public int execute(String ql, Object[] values) {
		Query query = getEntityManager().createQuery(ql);
		setQueryParameters(query, values);
		return query.executeUpdate();
	}

	@Override
	public int execute(String ql, Map<String, ?> paramMap) {
		Query query = getEntityManager().createQuery(ql);
		setQueryNamedParameters(query, paramMap);
		return query.executeUpdate();
	}
	
	@Override
	public T findById(PK primaryKey) {
		return getEntityManager().find(this.getEntityClass(), primaryKey);
	}
	
	@Override
	public T findUniqueByProperty(String propertyName, Object propertyValue) {
		String ql = PersistenceUtils.buildQueryString(false, this.getEntityClass(), new String[] { propertyName });
		return findUniqueByQueryString(ql, new Object[] { propertyValue });
	}
	
	@Override
	public T findUniqueByPropertys(Map<String, ?> paramMap) {
		String ql = PersistenceUtils.buildNamedQueryString(false, this.getEntityClass(), paramMap);
		return findUniqueByQueryString(ql, paramMap);
	}
	
	@Override
	public T findUniqueByQueryString(String ql) {
		return findUniqueByQueryString(this.getEntityClass(), ql);
	}

	@Override
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql) {
		return findUniqueByQueryString(resultClass, ql, (Object[]) null);
	}

	@Override
	public T findUniqueByQueryString(String ql, Object[] values) {
		return findUniqueByQueryString(this.getEntityClass(), ql, values);
	}

	@Override
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql, Object[] values) {
		TypedQuery<R> query = getEntityManager().createQuery(ql, resultClass);
		setQueryParameters(query, values);
		return query.getSingleResult();
	}

	@Override
	public T findUniqueByQueryString(String ql, Map<String, ?> paramMap) {
		return findUniqueByQueryString(this.getEntityClass(), ql, paramMap); 
	}

	@Override
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql, Map<String, ?> paramMap) {
		TypedQuery<R> query = getEntityManager().createQuery(ql, resultClass);
		setQueryNamedParameters(query, paramMap);
		return query.getSingleResult();
	}

	@Override
	public List<T> find(String ql) {
		return find(this.getEntityClass(), ql);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql) {
		return find(resultClass, ql, (Object[]) null);
	}
	
	@Override
	public List<T> find(String ql, Object[] values) {
		return find(this.getEntityClass(), ql, values);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, Object[] values) {
		return find(resultClass, ql, values, -1, -1);
	}
	
	@Override
	public List<T> find(String ql, Map<String, ?> paramMap) {
		return find(this.getEntityClass(), ql, paramMap);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, Map<String, ?> paramMap) {
		return find(resultClass, ql, paramMap, -1, -1);
	}

	@Override
	public List<T> find(String ql, int start, int maxRows) {
		return find(this.getEntityClass(), ql, start, maxRows);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, int start, int maxRows) {
		return find(resultClass, ql, (Object[]) null, start, maxRows);
	}

	@Override
	public List<T> find(String ql, Object[] values, int start, int maxRows) {
		return find(this.getEntityClass(), ql, values, start, maxRows);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, Object[] values, int start, int maxRows) {
		TypedQuery<R> query = getEntityManager().createQuery(ql, resultClass);
		setQueryParameters(query, values, start, maxRows);
		return query.getResultList();
	}
	
	@Override
	public List<T> find(String ql, Map<String, ?> paramMap, int start, int maxRows) {
		return find(this.getEntityClass(), ql, paramMap, start, maxRows);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, Map<String, ?> paramMap, int start, int maxRows) {
		TypedQuery<R> query = getEntityManager().createQuery(ql, resultClass);
		setQueryNamedParameters(query, paramMap, start, maxRows);
		return query.getResultList();
	}
	
	@Override
	public List<T> find(int start, int maxRows) {
		return findByProperty(null, null, start, maxRows);
	}
	
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue) {
		return findByProperty(propertyName, propertyValue, -1, -1);
	}
		
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue, int start, int maxRows) {
		String ql = PersistenceUtils.buildQueryString(false, this.getEntityClass(), new String[]{ propertyName});
		return find(ql, new Object[] { propertyValue }, start, maxRows);
	}
				
	@Override
	public List<T> findByPropertys(Map<String, ?> paramMap) {
		return findByPropertys(paramMap, -1, -1);
	}
	
	@Override
	public List<T> findByPropertys(Map<String, ?> paramMap, int start, int maxRows) {
		String ql = PersistenceUtils.buildNamedQueryString(false, this.getEntityClass(), paramMap);
		return find(ql, paramMap, start, maxRows);
	}
	
	@Override
	public List<T> findAll() {
		String ql = PersistenceUtils.buildQueryString(false, this.getEntityClass()).toString();
		return find(ql);
	}
	
	@Override
	public List<T> findAllDistinct() {
		Collection<T> result = CollectionUtils.newLinkedHashSet(findAll());
		return CollectionUtils.newArrayList(result);
	}
	
	@Override
	public long countByProperty(String propertyName, Object propertyValue) {
		String ql = PersistenceUtils.buildQueryString(true, this.getEntityClass(), new String[] { propertyName });
		return findUniqueByQueryString(Long.class, ql, new Object[] { propertyValue });
	}
	
	@Override
	public long countByPropertys(Map<String, ?> paramMap) {
		String ql = PersistenceUtils.buildNamedQueryString(true, this.getEntityClass(), paramMap);
		return findUniqueByQueryString(Long.class, ql, paramMap);
	}

	@Override
	public long countByQueryString(String ql) {
		return countByQueryString(ql, (Object[]) null);
	}

	@Override
	public long countByQueryString(String ql, Object[] values) {
		return findUniqueByQueryString(Long.class, ql, values);
	}
	
	@Override
	public long countByQueryString(String ql, Map<String, ?> paramMap) {
		return findUniqueByQueryString(Long.class, ql, paramMap);
	}
	
	@Override
	public T findUniqueByNamedQuery(String queryName) {
		return findUniqueByNamedQuery(this.getEntityClass(), queryName);
	}
	
	@Override
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName) {
		return findUniqueByNamedQuery(resultClass, queryName, (Object[]) null);
	}

	@Override
	public T findUniqueByNamedQuery(String queryName, Object[] values) {
		return findUniqueByNamedQuery(this.getEntityClass(), queryName, values);
	}
	
	@Override
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName, Object[] values) {
		TypedQuery<R> query = getEntityManager().createNamedQuery(queryName, resultClass);	
		setQueryParameters(query, values);
		return query.getSingleResult();
	}

	@Override
	public T findUniqueByNamedQuery(String queryName, Map<String, ?> paramMap) {
		return findUniqueByNamedQuery(this.getEntityClass(), queryName, paramMap);
	}
	
	@Override
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName, Map<String, ?> paramMap) {
		TypedQuery<R> query = getEntityManager().createNamedQuery(queryName, resultClass);
		setQueryNamedParameters(query, paramMap);	
		return query.getSingleResult();
	}

	@Override
	public List<T> findByNamedQuery(String queryName) {
		return findByNamedQuery(this.getEntityClass(), queryName);
	}
	
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName) {
		return findByNamedQuery(resultClass, queryName, (Object[]) null);
	}
	
	@Override
	public List<T> findByNamedQuery(String queryName, int start, int maxRows) {
		return findByNamedQuery(this.getEntityClass(), queryName, start, maxRows);
	}
	
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, int start, int maxRows) {
		return findByNamedQuery(resultClass, queryName, (Object[])null, start, maxRows);
	}

	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values) {
		return findByNamedQuery(this.getEntityClass(), queryName, values);
	}
	
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Object[] values) {
		return findByNamedQuery(resultClass, queryName, values, -1, -1);
	}
	
	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values, int start, int maxRows) {
		return findByNamedQuery(this.getEntityClass(), queryName, values, start, maxRows);
	}
	
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Object[] values, int start, int maxRows) {
		TypedQuery<R> query = getEntityManager().createNamedQuery(queryName, resultClass);
		setQueryParameters(query, values, start, maxRows);
		return query.getResultList();
	}

	@Override
	public List<T> findByNamedQuery(String queryName, Map<String, ?> paramMap) {
		return findByNamedQuery(this.getEntityClass(), queryName, paramMap);
	}
	
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Map<String, ?> paramMap) {
		return findByNamedQuery(resultClass, queryName, paramMap, -1, -1);
	}
	
	@Override
	public List<T> findByNamedQuery(String queryName, Map<String, ?> paramMap, int start, int maxRows) {
		return findByNamedQuery(this.getEntityClass(), queryName, paramMap, start, maxRows);
	}
	
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Map<String, ?> paramMap, int start, int maxRows) {
		TypedQuery<R> query = getEntityManager().createNamedQuery(queryName, resultClass);
		setQueryNamedParameters(query, paramMap, start, maxRows);
		return query.getResultList();
	}

	@Override
	public int executeNamedQuery(String queryName) {
		return executeNamedQuery(queryName, (Object[]) null);
	}

	@Override
	public int executeNamedQuery(String queryName, Object[] values) {
		Query query = getEntityManager().createNamedQuery(queryName);
		setQueryParameters(query, values);
		return query.executeUpdate();
	}

	@Override
	public int executeNamedQuery(String queryName, Map<String, ?> paramMap) {
		Query query = getEntityManager().createNamedQuery(queryName);
		setQueryNamedParameters(query, paramMap);
		return query.executeUpdate();
	}
	
	@Override
	public long countByNamedQuery(String queryName) {
		return countByNamedQuery(queryName, (Object[]) null);
	}

	@Override
	public long countByNamedQuery(String queryName, Object[] values) {
		TypedQuery<Long> query = getEntityManager().createNamedQuery(queryName, Long.class);
		setQueryParameters(query, values);
		return query.getSingleResult();
	}

	@Override
	public long countByNamedQuery(String queryName, Map<String, ?> paramMap) {
		TypedQuery<Long> query = getEntityManager().createNamedQuery(queryName, Long.class);
		setQueryNamedParameters(query, paramMap);
		return query.getSingleResult();
	}
	
	@Override
	public T findUniqueByNativeQuery(String sql) {
		return findUniqueByNativeQuery(this.getEntityClass(), sql);
	}
	
	@Override
	public <R> R findUniqueByNativeQuery(Class<R> resultClass, String sql) {
		return findUniqueByNativeQuery(resultClass, sql, (Object[]) null);
	}
	
	@Override
	public T findUniqueByNativeQuery(String sql, Object[] values) {
		return findUniqueByNativeQuery(this.getEntityClass(), sql, values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> R findUniqueByNativeQuery(Class<R> resultClass, String sql, Object[] values) {
		Query query = getEntityManager().createNativeQuery(sql, resultClass);
		setQueryParameters(query, values);
		return (R) query.getSingleResult();
	}
	
	@Override
	public T findUniqueByNativeQuery(String sql, Map<String, ?> paramMap) {
		return findUniqueByNativeQuery(this.getEntityClass(), sql, paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> R findUniqueByNativeQuery(Class<R> resultClass, String sql, Map<String, ?> paramMap) {
		Query query = getEntityManager().createNativeQuery(sql, resultClass);
		setQueryNamedParameters(query, paramMap);
		return (R) query.getSingleResult();
	}
	
	@Override
	public List<T> findByNativeQuery(String sql) {
		return findByNativeQuery(this.getEntityClass(), sql);
	}

	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql) {
		return findByNativeQuery(resultClass, sql, (Object[]) null);
	}

	@Override
	public List<T> findByNativeQuery(String sql, Object[] values) {
		return findByNativeQuery(this.getEntityClass(), sql, values);
	}

	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql, Object[] values) {
		return findByNativeQuery(resultClass, sql, values, -1, -1);
	}

	@Override
	public List<T> findByNativeQuery(String sql, Map<String, ?> paramMap) {
		return findByNativeQuery(this.getEntityClass(), sql, paramMap);
	}

	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql, Map<String, ?> paramMap) {
		return findByNativeQuery(resultClass, sql, paramMap, -1, -1);
	}

	@Override
	public List<T> findByNativeQuery(String sql, int start, int maxRows) {
		return findByNativeQuery(this.getEntityClass(), sql, start, maxRows);
	}

	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql, int start, int maxRows) {
		return findByNativeQuery(resultClass, sql, (Object[]) null, start, maxRows);
	}

	@Override
	public List<T> findByNativeQuery(String sql, Object[] values, int start, int maxRows) {
		return findByNativeQuery(this.getEntityClass(), sql, values, start, maxRows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql,
			Object[] values, int start, int maxRows) {
		Query query = getEntityManager().createNativeQuery(sql, resultClass);
		setQueryParameters(query, values, start, maxRows);
		return query.getResultList();
	}

	@Override
	public List<T> findByNativeQuery(String sql, Map<String, ?> paramMap, int start, int maxRows) {
		return findByNativeQuery(this.getEntityClass(), sql, paramMap, start, maxRows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql,
			Map<String, ?> paramMap, int start, int maxRows) {
		Query query = getEntityManager().createNativeQuery(sql, resultClass);
		setQueryNamedParameters(query, paramMap, start, maxRows);
		return query.getResultList();
	}

	@Override
	public int executeByNativeQuery(String sql) {
		return executeByNativeQuery(sql, (Object[]) null);
	}

	@Override
	public int executeByNativeQuery(String sql, Object[] values) {
		Query query = getEntityManager().createNativeQuery(sql);
		setQueryParameters(query, values);
		return query.executeUpdate();
	}

	@Override
	public int executeByNativeQuery(String sql, Map<String, ?> paramMap) {
		Query query = getEntityManager().createNativeQuery(sql);
		setQueryNamedParameters(query, paramMap);
		return query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <P> T findUniqueByCriteria(P parameter, JpaCriteriaQueryCallback<T> callback) {
		AssertUtils.assertNotNull(callback, "JpaCriteriaQueryCallback object can not be null.");
		if (callback instanceof JpaCriteriaQueryCallbackDao) 
			((JpaCriteriaQueryCallbackDao<T, P>) callback).setParameter(parameter);
		
		Class<T> entityType = this.getEntityClass();
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<T> criteriaQuery = builder.createQuery(entityType);
		Root<T> entityRoot = criteriaQuery.from(entityType);
        criteriaQuery.select(entityRoot);
        
        Predicate[] predicates = callback.execute(builder, criteriaQuery, entityRoot);
        if (ArrayUtils.isNotEmpty(predicates))
        	criteriaQuery.where(predicates);
        else
        	criteriaQuery.where(builder.conjunction());
        
        return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public <P> List<T> findByCriteria(P parameter, JpaCriteriaQueryCallback<T> callback) {
		return findByCriteria(parameter, -1, -1, callback);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <P> List<T> findByCriteria(P parameter, int start, int maxRows, JpaCriteriaQueryCallback<T> callback) {
		AssertUtils.assertNotNull(callback, "JpaCriteriaQueryCallback object can not be null.");
		if (callback instanceof JpaCriteriaQueryCallbackDao) 
			((JpaCriteriaQueryCallbackDao<T, P>) callback).setParameter(parameter);
		
		Class<T> entityType = this.getEntityClass();
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<T> criteriaQuery = builder.createQuery(entityType);
		Root<T> entityRoot = criteriaQuery.from(entityType);
        criteriaQuery.select(entityRoot);
        
        Predicate[] predicates = callback.execute(builder, criteriaQuery, entityRoot);
        if (ArrayUtils.isNotEmpty(predicates))
        	criteriaQuery.where(predicates);
        else
        	criteriaQuery.where(builder.conjunction());
        
        TypedQuery<T> typeQuery = getEntityManager().createQuery(criteriaQuery);
        /* 无需在自定义的回调函数中设置分段参数,如下自动设置 */
        setOffsetQuery(typeQuery, start, maxRows);
		return typeQuery.getResultList();
	}
	
	@Override
	public <P> long countByCriteria(P parameter, JpaCriteriaQueryCallback<T> callback) {
		return countByCriteria(parameter, false, callback);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <P> long countByCriteria(P parameter, boolean distinct, JpaCriteriaQueryCallback<T> callback) {
		AssertUtils.assertNotNull(callback, "JpaCriteriaQueryCallback object can not be null.");
		if (callback instanceof JpaCriteriaQueryCallbackDao) 
			((JpaCriteriaQueryCallbackDao<T, P>) callback).setParameter(parameter);
		
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<T> entityRoot = criteriaQuery.from(this.getEntityClass());
		
		if (distinct)
			criteriaQuery.select(builder.countDistinct(entityRoot)); 
		else
			criteriaQuery.select(builder.count(entityRoot));
		
		Predicate[] predicates = callback.execute(builder, criteriaQuery, entityRoot);
		if (ArrayUtils.isNotEmpty(predicates))
        	criteriaQuery.where(predicates);
		else
        	criteriaQuery.where(builder.conjunction());
		
		// 忽略orderBy排序
		criteriaQuery.orderBy((Order[]) null);
		
	    TypedQuery<Long> typeQuery = getEntityManager().createQuery(criteriaQuery);
		return typeQuery.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PagingResult<T> pagingQuery(PagingQuery query, JpaCriteriaQueryCallback<T> callback) {
		AssertUtils.assertNotNull(callback, "JpaCriteriaQueryCallback object can not be null.");
		if (callback instanceof JpaCriteriaQueryCallbackDao) 
			((JpaCriteriaQueryCallbackDao<T, PagingQuery>) callback).setParameter(query);
			
		PagingResult<T> pagingResult;
		if (query instanceof PagingResult)
			// 分页条件中包含分页结果时，则直接强制转换
			pagingResult = (PagingResult<T>) query;
		else
			pagingResult = new SimplePagingResult<T>();
		
		/* 依次在结果对象中查询并设置符合当前条件的记录和总数 */
		pagingResult.setData(this.findByCriteria(query,
				new Long(query.getBegin()).intValue(), query.getPageSize(), callback));
		pagingResult.setTotal(this.countByCriteria(query, callback));
		return pagingResult;
	}

	@Override
	public T findUniqueByFilterList(List<PersistencePropertyFilter> filterList) {
		String ql = PersistenceUtils.buildQueryStringByFilterList(false, this.getEntityClass(), filterList);
		return findUniqueByQueryString(ql);
	}

	@Override
	public T findUniqueByFilterChain(PersistencePropertyFilterChain chain) {
		String ql = PersistenceUtils.buildQueryStringByFilterChain(false, this.getEntityClass(), chain);
		return findUniqueByQueryString(ql);
	}

	@Override
	public List<T> findByFilterList(List<PersistencePropertyFilter> filterList) {
		return findByFilterList(filterList, -1, -1);
	}
	
	@Override
	public List<T> findByFilterList(List<PersistencePropertyFilter> filterList, int start, int maxRows) {
		String ql = PersistenceUtils.buildQueryStringByFilterList(false, this.getEntityClass(), filterList);
		return find(ql, start, maxRows);
	}

	@Override
	public List<T> findByFilterChain(PersistencePropertyFilterChain chain) {
		return findByFilterChain(chain, -1, -1);
	}
	
	@Override
	public List<T> findByFilterChain(PersistencePropertyFilterChain chain, int start, int maxRows) {
		String ql = PersistenceUtils.buildQueryStringByFilterChain(false, this.getEntityClass(), chain);
		return find(ql, start, maxRows);
	}

	@Override
	public long countByFilterList(List<PersistencePropertyFilter> filterList) {
		String ql = PersistenceUtils.buildQueryStringByFilterList(true, this.getEntityClass(), filterList);
		return findUniqueByQueryString(Long.class, ql);
	}

	@Override
	public long countByFilterChain(PersistencePropertyFilterChain chain) {
		String ql = PersistenceUtils.buildQueryStringByFilterChain(true, this.getEntityClass(), chain);
		return findUniqueByQueryString(Long.class, ql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PagingResult<T> pagingQuery(FilterListPagingQuery query) {
		PagingResult<T> pagingResult;
		if (query instanceof PagingResult)
			// 分页条件中包含分页结果时，则直接强制转换
			pagingResult = (PagingResult<T>) query;
		else
			pagingResult = new SimplePagingResult<T>();
		
		pagingResult.setData(this.findByFilterList(query.getFilterList(), new Long(
				query.getBegin()).intValue(), query.getPageSize()));
		pagingResult.setTotal(this.countByFilterList(query.getFilterList()));
		return pagingResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PagingResult<T> pagingQuery(FilterChainPagingQuery query) {
		
		PagingResult<T> pagingResult;
		if (query instanceof PagingResult)
			// 分页条件中包含分页结果时，则直接强制转换
			pagingResult = (PagingResult<T>) query;
		else
			pagingResult = new SimplePagingResult<T>();
		
		pagingResult.setData(this.findByFilterChain(query.getFilterChain(), new Long(
				query.getBegin()).intValue(), query.getPageSize()));
		pagingResult.setTotal(this.countByFilterChain(query.getFilterChain()));
		return pagingResult;
	}
		
}
