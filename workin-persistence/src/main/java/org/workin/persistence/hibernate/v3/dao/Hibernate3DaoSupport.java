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

package org.workin.persistence.hibernate.v3.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.workin.commons.util.ClassUtils;
import org.workin.persistence.hibernate.HibernateUtils;
import org.workin.persistence.hibernate.dao.HibernateDao;

/**
 * @description Hibernate3 DAO支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class Hibernate3DaoSupport<T, PK extends Serializable> extends
		HibernateDaoSupport implements HibernateDao<T, PK> {
	
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
			this.entityClass = (Class<T>) ClassUtils.getSuperClassGenricType(getClass());
		
		return this.entityClass;
	}
	
	/**
	 * @description 打开一个新会话
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	@Override
	public Session openSession() {
		return getSessionFactory().openSession();
	}
	
	/**
	 * @description 获取当前会话
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	@Override
	public Session getCurrentSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
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
	
}
