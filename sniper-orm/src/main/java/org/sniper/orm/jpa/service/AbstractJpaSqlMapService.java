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

package org.sniper.orm.jpa.service;

import java.io.Serializable;

import org.sniper.commons.util.AssertUtils;
import org.sniper.sqlmap.dao.SqlMapQuery;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * JPA+SqlMap混合服务抽象类，作用如下：</P>
 * 通过注入SqlMapQuery接口实例后开启ibatis/mybatis的查询功能，以弥补JPA针对动态查询时需拼接SQL的问题，改用ibatis/mybatis的XML配置方式实现 
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractJpaSqlMapService<T, PK extends Serializable> extends AbstractJpaService<T, PK> {
			
	@Autowired
	protected SqlMapQuery<T> sqlMapQuery;
	
	public SqlMapQuery<T> getSqlMapQuery() {
		return sqlMapQuery;
	}

	public void setSqlMapQuery(SqlMapQuery<T> sqlMapQuery) {
		this.sqlMapQuery = sqlMapQuery;
	}
	
	@Override
	protected void checkProperties() {
		super.checkProperties();
		AssertUtils.assertNotNull(this.sqlMapQuery, "Property 'sqlMapQuery' is required");
	}
	
	@Override
	protected void init() throws Exception {
		super.init();
		sqlMapQuery.setTargetType(jpaDao.getTargetType());
		logger.info("Success enable sqlMapQuery:{}", sqlMapQuery.getClass().getName());
	}

}
