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

import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.workin.commons.util.ClassUtils;
import org.workin.persistence.hibernate.HibernateUtils;
import org.workin.support.bean.GenericBean;

/**
 * Hibernate3 DAO支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class HibernateDaoSupport<T> extends
	org.springframework.orm.hibernate3.support.HibernateDaoSupport implements GenericBean<T> {
	
	/** 当前DAO所关联的实体类型 */
	private Class<T> beanClass;
	
	@Override
	public void setBeanClass(Class<T> beanClass) {
		this.beanClass = beanClass;
	}
	
	@Override
	public Class<T> getBeanClass() {
		return beanClass;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initDao() throws Exception {
		this.beanClass = (Class<T>) ClassUtils.getSuperClassGenricType(getClass());
	}
	
	/**
	 * 打开一个新会话
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Session openSession() {
		return getSessionFactory().openSession();
	}
	
	/**
	 * 获取当前会话
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Session getCurrentSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}
	
	/**
	 * 获取当前实体类型对应的元数据对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityClass
	 * @return
	 */
	protected ClassMetadata getClassMetadata() {
		return HibernateUtils.getClassMetadata(getSessionFactory(), getBeanClass());
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
	
}
