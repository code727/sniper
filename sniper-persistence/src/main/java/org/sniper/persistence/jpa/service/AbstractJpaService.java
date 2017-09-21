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

package org.sniper.persistence.jpa.service;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.sniper.commons.util.ClassUtils;
import org.sniper.persistence.jpa.dao.JpaDao;
import org.sniper.persistence.sqlmap.dao.SqlMapQuery;
import org.sniper.spring.beans.CheckableInitializingBean;

/**
 * JPA持久化服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractJpaService<T, PK extends Serializable> extends
		CheckableInitializingBean implements JpaBeanService<T, PK> {
		
	private static final Logger logger = LoggerFactory.getLogger(AbstractJpaService.class);
	
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
	
	@Override
	protected void checkProperties() {
		if (this.jpaDao == null)
			throw new IllegalArgumentException("Property 'jpaDao' is required");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void init() throws Exception {
		Class<T> entityType = (Class<T>) ClassUtils.getSuperClassGenricType(getClass());
		// 将当前服务类管理的实体类型传递给持久化DAO，使DAO接口的方法能正常工作
		this.jpaDao.setTargetType(entityType);
		
		/* 开启ibatis/mybatis的查询接口，弥补JPA针对复杂查询难以处理的问题 */
		if (sqlMapQuery != null) {
			sqlMapQuery.setTargetType(entityType);
			
			logger.info("Success enable SqlMapQuery interface,implements class is:"
					+ sqlMapQuery.getClass().getName());
		}
		
	}

}
