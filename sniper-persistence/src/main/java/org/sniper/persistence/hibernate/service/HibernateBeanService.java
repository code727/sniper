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
 * Create Date : 2015-3-10
 */

package org.sniper.persistence.hibernate.service;

import java.io.Serializable;

import org.sniper.persistence.hibernate.dao.HibernateDao;

/**
 * Hibernate对象服务接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface HibernateBeanService<T, PK extends Serializable> {
	
	/**
	 * 设置Hibernate持久化数据访问接口
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param hibernateDao
	 */
	public void setHibernateDao(HibernateDao<T, PK> hibernateDao);
	
	/**
	 * 获取Hibernate持久化数据访问接口
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public HibernateDao<T, PK> getHibernateDao();

}
