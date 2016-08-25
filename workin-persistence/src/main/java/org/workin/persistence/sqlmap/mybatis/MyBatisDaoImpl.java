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
 * Create Date : 2015-3-24
 */

package org.workin.persistence.sqlmap.mybatis;

import org.workin.persistence.sqlmap.dao.SqlMapDao;

/**
 * @description MyBatis DAO实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MyBatisDaoImpl<T> extends MyBatisQueryDaoImpl<T>
		implements SqlMapDao<T> {

	@Override
	public Integer insert(String id) {
		return insert(id, null);
	}

	@Override
	public Integer insert(String id, Object parameter) {
		return parameter != null ? getSqlSession().insert(id, parameter) : getSqlSession().insert(id);
	}

	@Override
	public int update(String id) {
		return update(id, null);
	}

	@Override
	public int update(String id, Object parameter) {
		return parameter != null ? getSqlSession().update(id,
				parameter) : getSqlSession().update(id);
	}

	@Override
	public int delete(String id) {
		return delete(id, null);
	}

	@Override
	public int delete(String id, Object parameter) {
		return parameter != null ? getSqlSession().delete(id, parameter)
				: getSqlSession().delete(id);
	}

}
