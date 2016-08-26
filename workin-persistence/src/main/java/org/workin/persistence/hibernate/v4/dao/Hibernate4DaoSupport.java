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

package org.workin.persistence.hibernate.v4.dao;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.commons.util.StringUtils;
import org.workin.persistence.GenericDaoSupport;
import org.workin.persistence.hibernate.HibernateUtils;
import org.workin.persistence.hibernate.dao.HibernateDao;
import org.workin.persistence.hibernate.v4.Hibernate4CacheConfiguration;

/**
 * @description Hibernate4 DAO支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class Hibernate4DaoSupport<T, PK extends Serializable> extends
		GenericDaoSupport<T> implements HibernateDao<T, PK> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/** 模板对象 */
	private Hibernate4CacheConfiguration cacheConfiguration;
	
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Session openSession() {
		return this.sessionFactory.openSession();
	}
	
	@Override
	public Session getCurrentSession() {
		Session session;
		try {
			session = this.sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			// 无事务绑定时，打开一个新会话
			session = openSession();
		}
		return session;
	}
	
	public Hibernate4CacheConfiguration getCacheConfiguration() {
		return cacheConfiguration;
	}

	public void setCacheConfiguration(
			Hibernate4CacheConfiguration cacheConfiguration) {
		this.cacheConfiguration = cacheConfiguration;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (sessionFactory == null)
			throw new BeanCreationException("Property 'sessionFactory' must not be null");
	}

	/**
	 * @description 获取当前实体类型对应的元数据对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityClass
	 * @return
	 */
	protected ClassMetadata getClassMetadata() {
		return HibernateUtils.getClassMetadata(getSessionFactory(), getEntityClass());
	}
	
	/**
	 * @description 获取当前实体类型对应的名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityClass
	 * @return
	 */
	protected String entityName() {
		return getClassMetadata().getEntityName();
	}
	
	/**
	 * @description 获取当前实体类型对应的表名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityClass
	 * @return
	 */
	protected String getTableName() {
		return ((AbstractEntityPersister) getClassMetadata()).getTableName();
	}
			
	/**
	 * @description 获取当前实体类型对应的表的主键名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	protected String getPrimaryKeyName() {
		return getClassMetadata().getIdentifierPropertyName();
	}
		
	/**
	 * @description 根据缓存配置项设置Criteria对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param criteria
	 */
	protected void prepareCriteria(Criteria criteria) {
		if (this.cacheConfiguration != null) {
			if (this.cacheConfiguration.isCacheQueries()) {
				criteria.setCacheable(true);
				if (StringUtils.isNotBlank(this.cacheConfiguration.getQueryCacheRegion())) {
					criteria.setCacheRegion(this.cacheConfiguration.getQueryCacheRegion());
				}
			}
			
			if (this.cacheConfiguration.getFetchSize() > 0) 
				criteria.setFetchSize(this.cacheConfiguration.getFetchSize());
			
			if (this.cacheConfiguration.getMaxResults() > 0) 
				criteria.setMaxResults(this.cacheConfiguration.getMaxResults());
		}
	}
	
	/**
	 * @description 根据缓存配置项设置Query对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query
	 */
	protected void prepareQuery(Query query) {
		if (this.cacheConfiguration != null) {
			if (this.cacheConfiguration.isCacheQueries()) {
				query.setCacheable(true);
				if (StringUtils.isNotBlank(this.cacheConfiguration.getQueryCacheRegion())) {
					query.setCacheRegion(this.cacheConfiguration.getQueryCacheRegion());
				}
			}
			
			if (this.cacheConfiguration.getFetchSize() > 0) 
				query.setFetchSize(this.cacheConfiguration.getFetchSize());
			
			if (this.cacheConfiguration.getMaxResults() > 0) 
				query.setMaxResults(this.cacheConfiguration.getMaxResults());
		}
	}

}
