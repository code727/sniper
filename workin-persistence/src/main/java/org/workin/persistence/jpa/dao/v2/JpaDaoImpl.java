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

package org.workin.persistence.jpa.dao.v2;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
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
import org.workin.commons.util.CollectionUtils;
import org.workin.persistence.jpa.JpaUtils;
import org.workin.persistence.jpa.dao.JpaDao;
import org.workin.persistence.jpa.dao.interfaces.JpaCriteriaQueryCallback;
import org.workin.persistence.jpa.dao.interfaces.JpaCriteriaQueryCallbackDao;
import org.workin.persistence.pagination.FilterChainPagingQuery;
import org.workin.persistence.pagination.FilterListPagingQuery;
import org.workin.persistence.util.PersistencePropertyFilter;
import org.workin.persistence.util.PersistencePropertyFilterChain;
import org.workin.persistence.util.PersistenceUtils;

/**
 * @description JPA2标准的DAO实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Repository
public class JpaDaoImpl<T, PK extends Serializable> extends JpaDaoSupport<T>
		implements JpaDao<T, PK> {
	
	@Override
	public void persist(T entity) {
		getEntityManager().persist(entity);
	}

	@Override
	public void batchPersist(List<T> entityList) {
		int max = entityList.size() - 1;
		EntityManager em = getEntityManager();
		
		for (int i = 0; i <= max; i++) {
			em.persist(entityList.get(i));
			// 最大1000条记录保存一次
			if (((i != 0) && (i % 1000 == 0)) || (i == max))
				em.flush();
		}
	}

	@Override
	public T merge(T entity) {
		return getEntityManager().merge(entity);
	}

	@Override
	public List<T> batchMerge(List<T> entityList) {
		int max = entityList.size() - 1;
		EntityManager em = getEntityManager();
		List<T> list = CollectionUtils.newArrayList();
		
		for (int i = 0; i <= max; i++) {
			list.add(em.merge(entityList.get(i)));
			// 最大1000条记录保存一次
			if (((i != 0) && (i % 1000 == 0)) || (i == max))
				em.flush();
		}
		
		return list;
	}

	@Override
	public void remove(T entity) {
		getEntityManager().remove(entity);
	}

	@Override
	public void remove(PK primaryKey) {
		T entity = findById(primaryKey);
		if (entity != null)
			remove(entity);
	}

	@Override
	public void batchRemove(List<T> entityList) {
		int max = entityList.size() - 1;
		EntityManager em = getEntityManager();
		T entity;
		
		for (int i = 0; i <= max; i++) {
			entity = entityList.get(i);
			em.refresh(entity);
			em.remove(entity);
				
			if (((i != 0) && (i % 1000 == 0)) || (i == max))
				em.flush();	
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
		T entity = getEntityManager().find(this.getBeanClass(), primaryKey);
		return contains(entity);
	}

	@Override
	public int execute(String ql) {
		return execute(ql, (Object[])null);
	}

	@Override
	public int execute(String ql, Object[] values) {
		Query query = getEntityManager().createQuery(ql);
		JpaUtils.setQueryParameters(query, values);
		return query.executeUpdate();
	}

	@Override
	public int execute(String ql, Map<String, ?> paramMap) {
		Query query = getEntityManager().createQuery(ql);
		JpaUtils.setQueryNamedParameters(query, paramMap);
		return query.executeUpdate();
	}
	
	@Override
	public T findById(PK primaryKey) {
		return getEntityManager().find(this.getBeanClass(), primaryKey);
	}
	
	@Override
	public T findUniqueByProperty(String propertyName, Object propertyValue) {
		String ql = PersistenceUtils.buildQueryString(false, this.getBeanClass(), new String[] { propertyName });
		return findUniqueByQueryString(ql, new Object[] { propertyValue });
	}
	
	@Override
	public T findUniqueByPropertys(Map<String, ?> paramMap) {
		String ql = PersistenceUtils.buildNamedQueryString(false, this.getBeanClass(), paramMap);
		return findUniqueByQueryString(ql, paramMap);
	}
	
	@Override
	public T findUniqueByQueryString(String ql) {
		return findUniqueByQueryString(this.getBeanClass(), ql);
	}

	@Override
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql) {
		return findUniqueByQueryString(resultClass, ql, (Object[]) null);
	}

	@Override
	public T findUniqueByQueryString(String ql, Object[] values) {
		return findUniqueByQueryString(this.getBeanClass(), ql, values);
	}

	@Override
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql, Object[] values) {
		TypedQuery<R> query = getEntityManager().createQuery(ql, resultClass);
		JpaUtils.setQueryParameters(query, values);
		return query.getSingleResult();
	}

	@Override
	public T findUniqueByQueryString(String ql, Map<String, ?> paramMap) {
		return findUniqueByQueryString(this.getBeanClass(), ql, paramMap); 
	}

	@Override
	public <R> R findUniqueByQueryString(Class<R> resultClass, String ql, Map<String, ?> paramMap) {
		TypedQuery<R> query = getEntityManager().createQuery(ql, resultClass);
		JpaUtils.setQueryNamedParameters(query, paramMap);
		return query.getSingleResult();
	}

	@Override
	public List<T> find(String ql) {
		return find(this.getBeanClass(), ql);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql) {
		return find(resultClass, ql, (Object[]) null);
	}
	
	@Override
	public List<T> find(String ql, Object[] values) {
		return find(this.getBeanClass(), ql, values);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, Object[] values) {
		return find(resultClass, ql, values, -1, -1);
	}
	
	@Override
	public List<T> find(String ql, Map<String, ?> paramMap) {
		return find(this.getBeanClass(), ql, paramMap);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, Map<String, ?> paramMap) {
		return find(resultClass, ql, paramMap, -1, -1);
	}

	@Override
	public List<T> find(String ql, int start, int maxRows) {
		return find(this.getBeanClass(), ql, start, maxRows);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, int start, int maxRows) {
		return find(resultClass, ql, (Object[]) null, start, maxRows);
	}

	@Override
	public List<T> find(String ql, Object[] values, int start, int maxRows) {
		return find(this.getBeanClass(), ql, values, start, maxRows);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, Object[] values, int start, int maxRows) {
		TypedQuery<R> query = getEntityManager().createQuery(ql, resultClass);
		JpaUtils.setQueryParameters(query, values, start, maxRows);
		return query.getResultList();
	}
	
	@Override
	public List<T> find(String ql, Map<String, ?> paramMap, int start, int maxRows) {
		return find(this.getBeanClass(), ql, paramMap, start, maxRows);
	}
	
	@Override
	public <R> List<R> find(Class<R> resultClass, String ql, Map<String, ?> paramMap, int start, int maxRows) {
		TypedQuery<R> query = getEntityManager().createQuery(ql, resultClass);
		JpaUtils.setQueryNamedParameters(query, paramMap, start, maxRows);
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
		String ql = PersistenceUtils.buildQueryString(false, this.getBeanClass(), new String[]{ propertyName});
		return find(ql, new Object[] { propertyValue }, start, maxRows);
	}
				
	@Override
	public List<T> findByPropertys(Map<String, ?> paramMap) {
		return findByPropertys(paramMap, -1, -1);
	}
	
	@Override
	public List<T> findByPropertys(Map<String, ?> paramMap, int start, int maxRows) {
		String ql = PersistenceUtils.buildNamedQueryString(false, this.getBeanClass(), paramMap);
		return find(ql, paramMap, start, maxRows);
	}
	
	@Override
	public List<T> findAll() {
		String ql = PersistenceUtils.buildQueryString(false, this.getBeanClass()).toString();
		return find(ql);
	}
	
	@Override
	public List<T> findAllDistinct() {
		Collection<T> result = CollectionUtils.newLinkedHashSet(findAll());
		return CollectionUtils.newArrayList(result);
	}
	
	@Override
	public long countByProperty(String propertyName, Object propertyValue) {
		String ql = PersistenceUtils.buildQueryString(true, this.getBeanClass(), new String[] { propertyName });
		return findUniqueByQueryString(Long.class, ql, new Object[] { propertyValue });
	}
	
	@Override
	public long countByPropertys(Map<String, ?> paramMap) {
		String ql = PersistenceUtils.buildNamedQueryString(true, this.getBeanClass(), paramMap);
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
		return findUniqueByNamedQuery(this.getBeanClass(), queryName);
	}
	
	@Override
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName) {
		return findUniqueByNamedQuery(resultClass, queryName, (Object[]) null);
	}

	@Override
	public T findUniqueByNamedQuery(String queryName, Object[] values) {
		return findUniqueByNamedQuery(this.getBeanClass(), queryName, values);
	}
	
	@Override
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName, Object[] values) {
		TypedQuery<R> query = getEntityManager().createNamedQuery(queryName, resultClass);	
		JpaUtils.setQueryParameters(query, values);
		return query.getSingleResult();
	}

	@Override
	public T findUniqueByNamedQuery(String queryName, Map<String, ?> paramMap) {
		return findUniqueByNamedQuery(this.getBeanClass(), queryName, paramMap);
	}
	
	@Override
	public <R> R findUniqueByNamedQuery(Class<R> resultClass, String queryName, Map<String, ?> paramMap) {
		TypedQuery<R> query = getEntityManager().createNamedQuery(queryName, resultClass);
		JpaUtils.setQueryNamedParameters(query, paramMap);	
		return query.getSingleResult();
	}

	@Override
	public List<T> findByNamedQuery(String queryName) {
		return findByNamedQuery(this.getBeanClass(), queryName);
	}
	
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName) {
		return findByNamedQuery(resultClass, queryName, (Object[]) null);
	}
	
	@Override
	public List<T> findByNamedQuery(String queryName, int start, int maxRows) {
		return findByNamedQuery(this.getBeanClass(), queryName, start, maxRows);
	}
	
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, int start, int maxRows) {
		return findByNamedQuery(resultClass, queryName, (Object[])null, start, maxRows);
	}

	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values) {
		return findByNamedQuery(this.getBeanClass(), queryName, values);
	}
	
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Object[] values) {
		return findByNamedQuery(resultClass, queryName, values, -1, -1);
	}
	
	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values, int start, int maxRows) {
		return findByNamedQuery(this.getBeanClass(), queryName, values, start, maxRows);
	}
	
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Object[] values, int start, int maxRows) {
		TypedQuery<R> query = getEntityManager().createNamedQuery(queryName, resultClass);
		JpaUtils.setQueryParameters(query, values, start, maxRows);
		return query.getResultList();
	}

	@Override
	public List<T> findByNamedQuery(String queryName, Map<String, ?> paramMap) {
		return findByNamedQuery(this.getBeanClass(), queryName, paramMap);
	}
	
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Map<String, ?> paramMap) {
		return findByNamedQuery(resultClass, queryName, paramMap, -1, -1);
	}
	
	@Override
	public List<T> findByNamedQuery(String queryName, Map<String, ?> paramMap, int start, int maxRows) {
		return findByNamedQuery(this.getBeanClass(), queryName, paramMap, start, maxRows);
	}
	
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass, String queryName, Map<String, ?> paramMap, int start, int maxRows) {
		TypedQuery<R> query = getEntityManager().createNamedQuery(queryName, resultClass);
		JpaUtils.setQueryNamedParameters(query, paramMap, start, maxRows);
		return query.getResultList();
	}

	@Override
	public int executeNamedQuery(String queryName) {
		return executeNamedQuery(queryName, (Object[]) null);
	}

	@Override
	public int executeNamedQuery(String queryName, Object[] values) {
		Query query = getEntityManager().createNamedQuery(queryName);
		JpaUtils.setQueryParameters(query, values);
		return query.executeUpdate();
	}

	@Override
	public int executeNamedQuery(String queryName, Map<String, ?> paramMap) {
		Query query = getEntityManager().createNamedQuery(queryName);
		JpaUtils.setQueryNamedParameters(query, paramMap);
		return query.executeUpdate();
	}
	
	@Override
	public long countByNamedQuery(String queryName) {
		return countByNamedQuery(queryName, (Object[]) null);
	}

	@Override
	public long countByNamedQuery(String queryName, Object[] values) {
		TypedQuery<Long> query = getEntityManager().createNamedQuery(queryName, Long.class);
		JpaUtils.setQueryParameters(query, values);
		return query.getSingleResult();
	}

	@Override
	public long countByNamedQuery(String queryName, Map<String, ?> paramMap) {
		TypedQuery<Long> query = getEntityManager().createNamedQuery(queryName, Long.class);
		JpaUtils.setQueryNamedParameters(query, paramMap);
		return query.getSingleResult();
	}
	
	@Override
	public T findUniqueByNativeQuery(String sql) {
		return findUniqueByNativeQuery(this.getBeanClass(), sql);
	}
	
	@Override
	public <R> R findUniqueByNativeQuery(Class<R> resultClass, String sql) {
		return findUniqueByNativeQuery(resultClass, sql, (Object[]) null);
	}
	
	@Override
	public T findUniqueByNativeQuery(String sql, Object[] values) {
		return findUniqueByNativeQuery(this.getBeanClass(), sql, values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> R findUniqueByNativeQuery(Class<R> resultClass, String sql, Object[] values) {
		Query query = getEntityManager().createNativeQuery(sql, resultClass);
		JpaUtils.setQueryParameters(query, values);
		return (R) query.getSingleResult();
	}
	
	@Override
	public T findUniqueByNativeQuery(String sql, Map<String, ?> paramMap) {
		return findUniqueByNativeQuery(this.getBeanClass(), sql, paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> R findUniqueByNativeQuery(Class<R> resultClass, String sql, Map<String, ?> paramMap) {
		Query query = getEntityManager().createNativeQuery(sql, resultClass);
		JpaUtils.setQueryNamedParameters(query, paramMap);
		return (R) query.getSingleResult();
	}
	
	@Override
	public List<T> findByNativeQuery(String sql) {
		return findByNativeQuery(this.getBeanClass(), sql);
	}

	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql) {
		return findByNativeQuery(resultClass, sql, (Object[]) null);
	}

	@Override
	public List<T> findByNativeQuery(String sql, Object[] values) {
		return findByNativeQuery(this.getBeanClass(), sql, values);
	}

	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql, Object[] values) {
		return findByNativeQuery(resultClass, sql, values, -1, -1);
	}

	@Override
	public List<T> findByNativeQuery(String sql, Map<String, ?> paramMap) {
		return findByNativeQuery(this.getBeanClass(), sql, paramMap);
	}

	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql, Map<String, ?> paramMap) {
		return findByNativeQuery(resultClass, sql, paramMap, -1, -1);
	}

	@Override
	public List<T> findByNativeQuery(String sql, int start, int maxRows) {
		return findByNativeQuery(this.getBeanClass(), sql, start, maxRows);
	}

	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql, int start, int maxRows) {
		return findByNativeQuery(resultClass, sql, (Object[]) null, start, maxRows);
	}

	@Override
	public List<T> findByNativeQuery(String sql, Object[] values, int start, int maxRows) {
		return findByNativeQuery(this.getBeanClass(), sql, values, start, maxRows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql,
			Object[] values, int start, int maxRows) {
		Query query = getEntityManager().createNativeQuery(sql, resultClass);
		JpaUtils.setQueryParameters(query, values, start, maxRows);
		return query.getResultList();
	}

	@Override
	public List<T> findByNativeQuery(String sql, Map<String, ?> paramMap, int start, int maxRows) {
		return findByNativeQuery(this.getBeanClass(), sql, paramMap, start, maxRows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, String sql,
			Map<String, ?> paramMap, int start, int maxRows) {
		Query query = getEntityManager().createNativeQuery(sql, resultClass);
		JpaUtils.setQueryNamedParameters(query, paramMap, start, maxRows);
		return query.getResultList();
	}

	@Override
	public int executeByNativeQuery(String sql) {
		return executeByNativeQuery(sql, (Object[]) null);
	}

	@Override
	public int executeByNativeQuery(String sql, Object[] values) {
		Query query = getEntityManager().createNativeQuery(sql);
		JpaUtils.setQueryParameters(query, values);
		return query.executeUpdate();
	}

	@Override
	public int executeByNativeQuery(String sql, Map<String, ?> paramMap) {
		Query query = getEntityManager().createNativeQuery(sql);
		JpaUtils.setQueryNamedParameters(query, paramMap);
		return query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <P> T findUniqueByCriteria(P parameter, JpaCriteriaQueryCallback<T> callback) {
		AssertUtils.assertNotNull(callback, "JpaCriteriaQueryCallback object can not be null.");
		if (callback instanceof JpaCriteriaQueryCallbackDao) 
			((JpaCriteriaQueryCallbackDao<T, P>) callback).setParameter(parameter);
		
		Class<T> entityType = this.getBeanClass();
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
		
		Class<T> entityType = this.getBeanClass();
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
        JpaUtils.setOffsetQuery(typeQuery, start, maxRows);
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
		Root<T> entityRoot = criteriaQuery.from(this.getBeanClass());
		
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
		String ql = PersistenceUtils.buildQueryStringByFilterList(false, this.getBeanClass(), filterList);
		return findUniqueByQueryString(ql);
	}

	@Override
	public T findUniqueByFilterChain(PersistencePropertyFilterChain chain) {
		String ql = PersistenceUtils.buildQueryStringByFilterChain(false, this.getBeanClass(), chain);
		return findUniqueByQueryString(ql);
	}

	@Override
	public List<T> findByFilterList(List<PersistencePropertyFilter> filterList) {
		return findByFilterList(filterList, -1, -1);
	}
	
	@Override
	public List<T> findByFilterList(List<PersistencePropertyFilter> filterList, int start, int maxRows) {
		String ql = PersistenceUtils.buildQueryStringByFilterList(false, this.getBeanClass(), filterList);
		return find(ql, start, maxRows);
	}

	@Override
	public List<T> findByFilterChain(PersistencePropertyFilterChain chain) {
		return findByFilterChain(chain, -1, -1);
	}
	
	@Override
	public List<T> findByFilterChain(PersistencePropertyFilterChain chain, int start, int maxRows) {
		String ql = PersistenceUtils.buildQueryStringByFilterChain(false, this.getBeanClass(), chain);
		return find(ql, start, maxRows);
	}

	@Override
	public long countByFilterList(List<PersistencePropertyFilter> filterList) {
		String ql = PersistenceUtils.buildQueryStringByFilterList(true, this.getBeanClass(), filterList);
		return findUniqueByQueryString(Long.class, ql);
	}

	@Override
	public long countByFilterChain(PersistencePropertyFilterChain chain) {
		String ql = PersistenceUtils.buildQueryStringByFilterChain(true, this.getBeanClass(), chain);
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
