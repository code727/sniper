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

package org.workin.persistence.jpa.service;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.commons.util.ClassUtils;
import org.workin.persistence.jpa.dao.JpaDao;
import org.workin.persistence.sqlmap.dao.SqlMapQuery;

/**
 * @description JPA持久化服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractJpaPersistenceService<T, PK extends Serializable>
		implements JpaPersistenceService<T, PK>, InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractJpaPersistenceService.class);
	
	@Autowired
	protected JpaDao<T, PK> jpaDao;
	
	@Autowired(required = false)
	protected SqlMapQuery<T> sqlMapQuery;

	@Override
	public void setJpaDao(JpaDao<T, PK> jpaDao) {
		this.jpaDao = jpaDao;
	}
	
	@Override
	public JpaDao<T, PK> getJpaDao() {
		return this.jpaDao;
	}
	
	public SqlMapQuery<T> getSqlMapQuery() {
		return sqlMapQuery;
	}

	public void setSqlMapQuery(SqlMapQuery<T> sqlMapQuery) {
		this.sqlMapQuery = sqlMapQuery;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.jpaDao == null)
			throw new BeanCreationException("JpaDao object can not be null, please inject to spring container.");
		
		Class<T> entityType = (Class<T>) ClassUtils.getSuperClassGenricType(getClass());
		// 将当前服务类管理的实体类型传递给持久化DAO，使DAO接口的方法能正常工作
		this.jpaDao.setEntityClass(entityType);
		if (sqlMapQuery != null) {
			// 同时开启ibatis/mybatis的查询接口，弥补JPA针对复杂查询难以处理的问题
			sqlMapQuery.setEntityClass(entityType);
			logger.info("Successful enable SqlMapQuery interface,implements class is:"
					+ sqlMapQuery.getClass().getName());
		}
			
	}

}
