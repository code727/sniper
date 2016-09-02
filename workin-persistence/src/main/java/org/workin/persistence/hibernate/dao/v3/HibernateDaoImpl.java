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
 * Create Date : 2015-2-2
 */

package org.workin.persistence.hibernate.dao.v3;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.workin.commons.pagination.PagingResult;
import org.workin.commons.pagination.result.SimplePagingResult;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.StringUtils;
import org.workin.persistence.hibernate.HibernateUtils;
import org.workin.persistence.hibernate.dao.HibernateCriteriaQueryCallback;
import org.workin.persistence.hibernate.dao.HibernateCriteriaQueryCallbackDao;
import org.workin.persistence.hibernate.dao.HibernateDao;
import org.workin.persistence.pagination.FilterChainPagingQuery;
import org.workin.persistence.pagination.FilterListPagingQuery;
import org.workin.persistence.util.PersistencePropertyFilter;
import org.workin.persistence.util.PersistencePropertyFilterChain;
import org.workin.persistence.util.PersistenceUtils;

/**
 * Hibernate3 DAO实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Repository
public class HibernateDaoImpl<T, PK extends Serializable> extends
		HibernateDaoSupport<T> implements HibernateDao<T, PK> {
		
	@Override
	public void persist(T entity) {
		persist(null, entity);
	}
	
	@Override
	public void persist(String entityName, T entity) {
		if (StringUtils.isNotBlank(entityName))
			getHibernateTemplate().persist(entityName.trim(), entity);
		else
			getHibernateTemplate().persist(entity);
	};

	@Override
	public void batchPersist(final List<T> entityList) {
		batchPersist(null, entityList);
	}
	
	@Override
	public void batchPersist(final String entityName, final List<T> entityList) {
		getHibernateTemplate().execute(new HibernateCallback<T>() {

			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				int max = entityList.size() - 1;
				
				if (StringUtils.isNotBlank(entityName)) {
					String name = entityName.trim();
					
					for (int i = 0; i <= max; i++) {
						session.persist(name, entityList.get(i));
						// 最大1000条记录保存一次
						if (((i != 0) && (i % 1000 == 0)) || (i == max))
							session.flush();
					}
				} else {
					for (int i = 0; i <= max; i++) {
						session.persist(entityList.get(i));
						// 最大1000条记录保存一次
						if (((i != 0) && (i % 1000 == 0)) || (i == max))
							session.flush();
					}
				}
				return null;
			}
			
		});
	}

	@Override
	public T merge(final T entity) {
		return getHibernateTemplate().execute(new HibernateCallback<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T doInHibernate(Session session) throws HibernateException,
					SQLException {
				return (T) session.merge(entity);
			}
		});
	}
	
	@Override
	public T merge(String entityName, T entity) {
		return StringUtils.isNotBlank(entityName) ? getHibernateTemplate()
				.merge(entityName.trim(), entity) : getHibernateTemplate().merge(entity);
	}

	@Override
	public List<T> batchMerge(List<T> entityList) {
		return batchMerge(null, entityList);
	}
	
	@Override
	public List<T> batchMerge(final String entityName, final List<T> entityList) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				int max = entityList.size() - 1;
				List<T> list = CollectionUtils.newArrayList();
				
				if (StringUtils.isNotBlank(entityName)) {
					String name = entityName.trim();
					
					for (int i = 0; i <= max; i++) {
						list.add((T) session.merge(name, entityList.get(i)));
						// 最大1000条记录保存一次
						if (((i != 0) && (i % 1000 == 0)) || (i == max))
							session.flush();
					}
				} else {
					for (int i = 0; i <= max; i++) {
						list.add((T) session.merge(entityList.get(i)));
						// 最大1000条记录保存一次
						if (((i != 0) && (i % 1000 == 0)) || (i == max))
							session.flush();
					}
				}
				return list;
			}
		});
	}

	@Override
	public void remove(T entity) {
		remove(null, entity);
	}
	
	@Override
	public void remove(PK id) {
		T entity = getById(id);
		if (entity != null)
			remove(entity);
	}
	
	@Override
	public void remove(String entityName, T entity) {
		if (StringUtils.isNotBlank(entityName))
			getHibernateTemplate().delete(entityName, entity);
		else
			getHibernateTemplate().delete(entity);
	}
	
	@Override
	public void batchRemove(final List<T> entityList) {
		batchRemove(null, entityList);
	}

	@Override
	public void batchRemove(final String entityName, final List<T> entityList) {
		getHibernateTemplate().execute(new HibernateCallback<T>() {

			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				int max = entityList.size() - 1;
				T entity;
				
				if (StringUtils.isNotBlank(entityName)) {
					String name = entityName.trim();
					
					for (int i = 0; i <= max; i++) {
						entity = entityList.get(i);
						session.refresh(name, entity);
						session.delete(name, entity);
						
						// 最大1000条记录保存一次
						if (((i != 0) && (i % 1000 == 0)) || (i == max))
							session.flush();
					}
				} else {
					for (int i = 0; i <= max; i++) {
						entity = entityList.get(i);
						session.refresh(entity);
						session.delete(entity);
						
						// 最大1000条记录保存一次
						if (((i != 0) && (i % 1000 == 0)) || (i == max))
							session.flush();
					}
				}
				return null;
			}
		});
	}
	
	@Override
	public void refresh(T entity) {
		getHibernateTemplate().refresh(entity);
	}

	@Override
	public void flush() {
		getHibernateTemplate().flush();
	}

	@Override
	public void clear() {
		getHibernateTemplate().clear();
	}

	@Override
	public boolean contains(T entity) {
		return getHibernateTemplate().contains(entity);
	}
	
	@Override
	public boolean contains(PK id) {
		T entity = getById(id);
		return contains(entity);
	}

	@Override
	public int execute(String ql) {
		return execute(ql, (Object[]) null);
	}

	@Override
	public int execute(final String ql, final Object[] values) {
		
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(ql);
				HibernateUtils.setQueryParameters(query, values);
				return query.executeUpdate();
			}
		});
	}

	@Override
	public int execute(final String ql, final Map<String, ?> paramMap) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(ql);
				HibernateUtils.setQueryNamedParameters(query, paramMap);
				return query.executeUpdate();
			}
		});
	}

	@Override
	public PK save(T entity) {
		return save(null, entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PK save(String entityName, T entity) {
		return (PK) (StringUtils.isNotBlank(entityName) ? getHibernateTemplate()
				.save(entityName, entity) : getHibernateTemplate().save(entity));
	}

	@Override
	public void update(T entity) {
		update(null, entity);
	}

	@Override
	public void update(String entityName, T entity) {
		if (StringUtils.isNotBlank(entityName))
			getHibernateTemplate().update(entityName, entity);
		else
			getHibernateTemplate().update(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		saveOrUpdate(null, entity);
	}

	@Override
	public void saveOrUpdate(String entityName, T entity) {
		if (StringUtils.isNotBlank(entityName))
			getHibernateTemplate().saveOrUpdate(entityName, entity);
		else
			getHibernateTemplate().saveOrUpdate(entity);
	}
	
	@Override
	public T loadById(PK id) {
		return loadById(id, (LockMode) null);
	}

	@Override
	public T loadById(PK id, LockMode lockMode) {
		return loadById(null, id, lockMode);
	}

	@Override
	public T loadById(PK id, LockOptions lockOptions) {
		return loadById(null, id, lockOptions);
	}

	@Override
	public T loadById(String entityName, PK id) {
		return loadById(entityName, id, (LockMode) null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T loadById(String entityName, PK id, LockMode lockMode) {
		return StringUtils.isNotBlank(entityName) ? 
				(T) getHibernateTemplate().load(entityName.trim(), id, lockMode) :
					getHibernateTemplate().load(this.getBeanClass(), id, lockMode);
	}

	@Override
	public T loadById(final String entityName, final PK id, final LockOptions lockOptions) {
		return getHibernateTemplate().execute(new HibernateCallback<T>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				if (StringUtils.isNotBlank(entityName)) 
					return (T) (lockOptions != null ? 
							session.load(entityName.trim(), id, lockOptions) : 
								session.load(entityName.trim(), id));
				else
					return (T) (lockOptions != null ? 
							session.load(getBeanClass(), id, lockOptions) :
								session.load(getBeanClass(), id));
			}
		});
	}

	@Override
	public List<T> loadAll() {
		return getHibernateTemplate().loadAll(this.getBeanClass());
	}

	@Override
	public List<T> loadAllDistinct() {
		Collection<T> result = CollectionUtils.newLinkedHashSet(loadAll());
		return CollectionUtils.newArrayList(result);
	}
	
	@Override
	public T getById(PK id) {
		return getById(id, (LockMode) null);
	}

	@Override
	public T getById(PK id, LockMode lockMode) {
		return getById(null, id, lockMode);
	}

	@Override
	public T getById(PK id, LockOptions lockOptions) {
		return getById(null, id, lockOptions);
	}

	@Override
	public T getById(String entityName, PK id) {
		return getById(entityName, id, (LockMode) null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(String entityName, PK id, LockMode lockMode) {
		return StringUtils.isNotBlank(entityName) ? 
				(T) getHibernateTemplate().get(entityName.trim(), id, lockMode) :
					getHibernateTemplate().get(this.getBeanClass(), id, lockMode);
	}

	@Override
	public T getById(final String entityName, final PK id, final LockOptions lockOptions) {
		return getHibernateTemplate().execute(new HibernateCallback<T>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				if (StringUtils.isNotBlank(entityName)) 
					return (T) (lockOptions != null ? 
							session.get(entityName.trim(), id, lockOptions) : 
								session.load(entityName.trim(), id));
				else
					return (T) (lockOptions != null ? 
							session.get(getBeanClass(), id, lockOptions) :
								session.load(getBeanClass(), id));
			}
		});
	}
	
	@Override
	public T findById(PK id) {
		return findUniqueByProperty(getIdPropertyName(), id);
	}
	
	@Override
	public T findUniqueByProperty(String propertyName, Object propertyValue) {
		String ql = PersistenceUtils.buildQueryString(false, this.getBeanClass(), new String[] { propertyName });
		return findUniqueByQueryString(ql, new Object[] { propertyValue });
	}
		
	@Override
	public T findUniqueByProperties(Map<String, ?> paramMap) {
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
	public <R> R findUniqueByQueryString(Class<R> resultClass, final String ql, final Object[] values) {
			
		return getHibernateTemplate().execute(new HibernateCallback<R>() {

			@SuppressWarnings("unchecked")
			@Override
			public R doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(ql);
				HibernateUtils.setQueryParameters(query, values);
				return (R) query.uniqueResult();
			}
		});
	}

	@Override
	public T findUniqueByQueryString(String ql, Map<String, ?> paramMap) {
		return findUniqueByQueryString(this.getBeanClass(), ql, paramMap); 
	}

	@Override
	public <R> R findUniqueByQueryString(Class<R> resultClass, final String ql, final Map<String, ?> paramMap) {
			
		return getHibernateTemplate().execute(new HibernateCallback<R>() {

			@SuppressWarnings("unchecked")
			@Override
			public R doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(ql);
				HibernateUtils.setQueryNamedParameters(query, paramMap);
				return (R) query.uniqueResult();
			}
		});
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
	public <R> List<R> find(Class<R> resultClass, String ql, int start, int maxRows) {
		return find(resultClass, ql, (Object[]) null, start, maxRows);
	}
	
	@Override
	public List<T> find(String ql, int start, int maxRows) {
		return find(this.getBeanClass(), ql, start, maxRows);
	}

	@Override
	public List<T> find(String ql, Object[] values,int start, int maxRows) {
		return find(this.getBeanClass(), ql, values, start, maxRows);	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> find(Class<R> resultClass, final String ql,
			final Object[] values, final int start, final int maxRows) {
		
		return getHibernateTemplate().executeFind(new HibernateCallback<List<R>>() {

			@Override
			public List<R> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(ql);
				HibernateUtils.setQueryParameters(query, values, start, maxRows);
				return query.list();
			}
		});
	}
	
	@Override
	public List<T> find(String ql, Map<String, ?> paramMap, int start, int maxRows) {
		return find(this.getBeanClass(), ql, paramMap, start, maxRows);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> find(Class<R> resultClass, final String ql,
			final Map<String, ?> paramMap, final int start, final int maxRows) {
		
		return getHibernateTemplate().executeFind(new HibernateCallback<List<R>>() {

			@Override
			public List<R> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(ql);
				HibernateUtils.setQueryNamedParameters(query, paramMap, start, maxRows);
				return query.list();
			}
		});
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
	public List<T> findByProperties(Map<String, ?> paramMap) {
		return findByProperties(paramMap, -1, -1);
	}

	@Override
	public List<T> findByProperties(Map<String, ?> paramMap, int start, int maxRows) {
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
	public long countByProperties(Map<String, ?> paramMap) {
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
	public <R> R findUniqueByNamedQuery(Class<R> resultClass,
			final String queryName, final Object[] values) {
		
		return getHibernateTemplate().execute(new HibernateCallback<R>() {

			@SuppressWarnings("unchecked")
			@Override
			public R doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.getNamedQuery(queryName);
				HibernateUtils.setQueryParameters(query, values);
				return (R) query.uniqueResult();
			}
		});
	}

	@Override
	public T findUniqueByNamedQuery(final String queryName, final Map<String, ?> paramMap) {
		return findUniqueByNamedQuery(this.getBeanClass(), queryName, paramMap);
	}

	@Override
	public <R> R findUniqueByNamedQuery(Class<R> resultClass,
			final String queryName, final Map<String, ?> paramMap) {
		
		return getHibernateTemplate().execute(new HibernateCallback<R>() {

			@SuppressWarnings("unchecked")
			@Override
			public R doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.getNamedQuery(queryName);
				HibernateUtils.setQueryNamedParameters(query, paramMap);
				return (R) query.uniqueResult();
			}
		});
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
		
	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass,
			final String queryName, final Object[] values, final int start,
			final int maxRows) {
		
		return getHibernateTemplate().executeFind(new HibernateCallback<List<R>>() {

			@Override
			public List<R> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.getNamedQuery(queryName);
				HibernateUtils.setQueryParameters(query, values, start, maxRows);
				return query.list();
			}
		});
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
	
	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> findByNamedQuery(Class<R> resultClass,
			final String queryName, final Map<String, ?> paramMap,
			final int start, final int maxRows) {
		
		return getHibernateTemplate().executeFind(new HibernateCallback<List<R>>() {

			@Override
			public List<R> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.getNamedQuery(queryName);
				HibernateUtils.setQueryNamedParameters(query, paramMap, start, maxRows);
				return query.list();
			}
		});
	}

	@Override
	public int executeNamedQuery(String queryName) {
		return executeNamedQuery(queryName, (Object[]) null);
	}

	@Override
	public int executeNamedQuery(final String queryName, final Object[] values) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.getNamedQuery(queryName);
				HibernateUtils.setQueryParameters(query, values);
				return query.executeUpdate();
			}
			
		});
	}

	@Override
	public int executeNamedQuery(final String queryName, final Map<String, ?> paramMap) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.getNamedQuery(queryName);
				HibernateUtils.setQueryNamedParameters(query, paramMap);
				return query.executeUpdate();
			}
			
		});
	}
	
	@Override
	public long countByNamedQuery(String queryName) {
		return countByNamedQuery(queryName, (Object[]) null);
	}
	
	@Override
	public long countByNamedQuery(final String queryName, final Object[] values) {
		
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.getNamedQuery(queryName);
				HibernateUtils.setQueryParameters(query, values);
				return Long.valueOf(String.valueOf(query.uniqueResult()));
			}
		});
	}
	
	@Override
	public long countByNamedQuery(final String queryName, final Map<String, ?> paramMap) {
		
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.getNamedQuery(queryName);
				HibernateUtils.setQueryNamedParameters(query, paramMap);
				return Long.valueOf(String.valueOf(query.uniqueResult()));
			}
		});
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
	public <R> R findUniqueByNativeQuery(final Class<R> resultClass,
			final String sql, final Object[] values) {
		
		return getHibernateTemplate().execute(new HibernateCallback<R>() {

			@Override
			public R doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.addEntity(resultClass);
				HibernateUtils.setQueryParameters(query, values);
				return (R) query.uniqueResult();
			}
		});
	}
	
	@Override
	public T findUniqueByNativeQuery(String sql, Map<String, ?> paramMap) {
		return findUniqueByNativeQuery(this.getBeanClass(), sql, paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <R> R findUniqueByNativeQuery(final Class<R> resultClass,
			final String sql, final Map<String, ?> paramMap) {
		
		return getHibernateTemplate().execute(new HibernateCallback<R>() {

			@Override
			public R doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.addEntity(resultClass);
				HibernateUtils.setQueryNamedParameters(query, paramMap);
				return (R) query.uniqueResult();
			}
		});
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
	public <R> List<R> findByNativeQuery(final Class<R> resultClass, final String sql,
			final Object[] values, final int start, final int maxRows) {
		
		return getHibernateTemplate().executeFind(new HibernateCallback<List<R>>() {
			@Override
			public List<R> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.addEntity(resultClass);
				HibernateUtils.setQueryParameters(query, values, start, maxRows);
				return query.list();
			}
		});
	}
	
	@Override
	public List<T> findByNativeQuery(String sql, Map<String, ?> paramMap, int start, int maxRows) {
		return findByNativeQuery(this.getBeanClass(), sql, paramMap, start, maxRows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> findByNativeQuery(Class<R> resultClass, final String sql,
			final Map<String, ?> paramMap, final int start, final int maxRows) {
		
		return getHibernateTemplate().executeFind(new HibernateCallback<List<R>>() {

			@Override
			public List<R> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				HibernateUtils.setQueryNamedParameters(query, paramMap, start, maxRows);
				return query.list();
			}
		});
	}

	@Override
	public int executeByNativeQuery(String sql) {
		return executeByNativeQuery(sql, (Object[]) null);
	}

	@Override
	public int executeByNativeQuery(final String sql, final Object[] values) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				HibernateUtils.setQueryParameters(query, values);
				return query.executeUpdate();
			}
		});
	}

	@Override
	public int executeByNativeQuery(final String sql, final Map<String, ?> paramMap) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				HibernateUtils.setQueryNamedParameters(query, paramMap);
				return query.executeUpdate();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <P> T findUniqueByCriteria(P parameter, final HibernateCriteriaQueryCallback callback) {
		AssertUtils.assertNotNull(callback, "HibernateCriteriaQueryCallback object can not be null.");
		if (callback instanceof HibernateCriteriaQueryCallbackDao)
			((HibernateCriteriaQueryCallbackDao<P>)callback).setParameter(parameter);
		
		return getHibernateTemplate().execute(new HibernateCallback<T>() {
			
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(getBeanClass());
				callback.execute(criteria);
				return (T) criteria.uniqueResult();
			}
		});
	}
	
	@Override
	public T findUniqueByCriteria(final DetachedCriteria criteria) {
		
		return getHibernateTemplate().execute(new HibernateCallback<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria executableCriteria = criteria.getExecutableCriteria(session);
				return (T) executableCriteria.uniqueResult();
			}
		});
	}
	
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) {
		return findByCriteria(criteria, -1, -1);
	}
	
	@Override
	public <P> List<T> findByCriteria(P parameter, HibernateCriteriaQueryCallback callback) {
		return findByCriteria(parameter, -1, -1, callback);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <P> List<T> findByCriteria(P parameter, final int start,
			final int maxRows, final HibernateCriteriaQueryCallback callback) {
		AssertUtils.assertNotNull(callback, "HibernateCriteriaQueryCallback object can not be null.");
		if (callback instanceof HibernateCriteriaQueryCallbackDao)
			((HibernateCriteriaQueryCallbackDao<P>)callback).setParameter(parameter);
		
		return getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(getBeanClass());
				callback.execute(criteria);
				HibernateUtils.setOffsetCriteria(criteria, start, maxRows);
				return criteria.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria, int start, int maxRows) {
		return getHibernateTemplate().findByCriteria(criteria, start, maxRows);
	}
	
	@Override
	public <P> long countByCriteria(P parameter, HibernateCriteriaQueryCallback callback) {
		return countByCriteria(parameter, false, callback);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <P> long countByCriteria(P parameter, final boolean distinct,
			final HibernateCriteriaQueryCallback callback) {
		AssertUtils.assertNotNull(callback, "HibernateCriteriaQueryCallback object can not be null.");
		if (callback instanceof HibernateCriteriaQueryCallbackDao)
			((HibernateCriteriaQueryCallbackDao<P>) callback).setParameter(parameter);
		
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(getBeanClass());
				if (distinct)
					criteria.setProjection(Projections.distinct(Projections.rowCount()));
				else
					criteria.setProjection(Projections.rowCount());  
				
				callback.execute(criteria);
				return Long.valueOf(String.valueOf(criteria.uniqueResult()));
			}
			
		});
	}

	@Override
	public long countByCriteria(DetachedCriteria criteria) {
		return countByCriteria(criteria, false);
	}
	
	@Override
	public long countByCriteria(final DetachedCriteria criteria, final boolean distinct) {
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				if (distinct)
					criteria.setProjection(Projections.distinct(Projections.rowCount()));
				else
					criteria.setProjection(Projections.rowCount());  
				Criteria executableCriteria = criteria.getExecutableCriteria(session);
				return (Long) executableCriteria.uniqueResult();
			}
		});
	}
	
	@Override
	public List<T> findByExample(T entity) {
		return findByExample(null, entity, -1, -1);
	}

	@Override
	public List<T> findByExample(String entityName, T entity) {
		return findByExample(entityName, entity, -1, -1);
	}

	@Override
	public List<T> findByExample(T entity, int start, int maxRows) {
		return findByExample(null, entity, start, maxRows);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByExample(String entityName, T entity, int start, int maxRows) {
		return StringUtils.isNotBlank(entityName) ? 
			getHibernateTemplate().findByExample(entityName, entity, start, maxRows) :
				getHibernateTemplate().findByExample(entity, start, maxRows);
	}

	@Override
	public T findUniqueByFilterList(final List<PersistencePropertyFilter> filterList) {
		
		return getHibernateTemplate().execute(new HibernateCallback<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(PersistenceUtils.buildQueryStringByFilterList(
						false, getBeanClass(), filterList));
				return (T) query.uniqueResult();
			}
		});
	}

	@Override
	public T findUniqueByFilterChain(final PersistencePropertyFilterChain chain) {
		
		return getHibernateTemplate().execute(new HibernateCallback<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(PersistenceUtils.buildQueryStringByFilterChain(
						false, getBeanClass(), chain));
				return (T) query.uniqueResult();
			}
		});
	}

	@Override
	public List<T> findByFilterList(List<PersistencePropertyFilter> filterList) {
		return findByFilterList(filterList, -1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByFilterList(final List<PersistencePropertyFilter> filterList, final int start, final int maxRows) {
			
		return getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(PersistenceUtils.buildQueryStringByFilterList(
						false, getBeanClass(), filterList));
				HibernateUtils.setOffsetQuery(query, start, maxRows);
				return query.list();
			}
		});
	}
	
	@Override
	public List<T> findByFilterChain(PersistencePropertyFilterChain chain) {
		return findByFilterChain(chain, -1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByFilterChain(final PersistencePropertyFilterChain chain, final int start, final int maxRows) {
			
		return getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {

			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(PersistenceUtils.buildQueryStringByFilterChain(
						false, getBeanClass(), chain));
				HibernateUtils.setOffsetQuery(query, start, maxRows);
				return query.list();
			}
		});
	}

	@Override
	public long countByFilterList(final List<PersistencePropertyFilter> filterList) {
		
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {
			
			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(PersistenceUtils.buildQueryStringByFilterList(
						true, getBeanClass(), filterList));
				return Long.valueOf(String.valueOf(query.uniqueResult()));
			}
		});
	}

	@Override
	public long countByFilterChain(final PersistencePropertyFilterChain chain) {
		
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(PersistenceUtils.buildQueryStringByFilterChain(
						true, getBeanClass(), chain));
				return Long.valueOf(String.valueOf(query.uniqueResult()));
			}
		});
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
