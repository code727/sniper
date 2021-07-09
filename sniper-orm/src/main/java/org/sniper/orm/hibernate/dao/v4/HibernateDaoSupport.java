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

package org.sniper.orm.hibernate.dao.v4;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.sniper.commons.util.StringUtils;
import org.sniper.orm.hibernate.HibernateCacheConfiguration;
import org.sniper.orm.hibernate.HibernateUtils;
import org.sniper.spring.beans.AbstractGenricBean;

/**
 * Hibernate4 DAO支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class HibernateDaoSupport<T> extends AbstractGenricBean<T> {
		
	@Autowired
	private SessionFactory sessionFactory;
	
	/** 模板对象 */
	private HibernateCacheConfiguration cacheConfiguration;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public HibernateCacheConfiguration getCacheConfiguration() {
		return cacheConfiguration;
	}

	public void setCacheConfiguration(HibernateCacheConfiguration cacheConfiguration) {
		this.cacheConfiguration = cacheConfiguration;
	}
	
	@Override
	protected void checkProperties() {
		if (this.sessionFactory == null) 
			throw new IllegalArgumentException("Property 'sessionFactory' is required");
	}
	
	public Session openSession() {
		return this.sessionFactory.openSession();
	}
	
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
	
	/**
	 * 获取当前实体类型对应的元数据对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityClass
	 * @return
	 */
	protected ClassMetadata getClassMetadata() {
		return HibernateUtils.getClassMetadata(getSessionFactory(), getTargetType());
	}
	
	/**
	 * 获取当前实体类型对应的名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityClass
	 * @return
	 */
	protected String entityName() {
		return getClassMetadata().getEntityName();
	}
	
	/**
	 * 获取当前实体类型对应的表名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityClass
	 * @return
	 */
	protected String getTableName() {
		return ((AbstractEntityPersister) getClassMetadata()).getTableName();
	}
			
	/**
	 * 获取当前实体类型对应的ID属性名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	protected String getIdPropertyName() {
		return getClassMetadata().getIdentifierPropertyName();
	}
		
	/**
	 * 根据缓存配置项设置Criteria对象
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
	 * 根据缓存配置项设置Query对象
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
