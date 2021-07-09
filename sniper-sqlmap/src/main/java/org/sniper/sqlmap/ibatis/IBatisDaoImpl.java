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

package org.sniper.sqlmap.ibatis;

import java.sql.SQLException;

import org.sniper.sqlmap.dao.SqlMapDao;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * IBatis DAO实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@Repository
public class IBatisDaoImpl<T> extends IBatisQueryDaoImpl<T>
		implements SqlMapDao<T> {
	
	@Override
	public T insert(String statement) {
		return insert(statement, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T insert(String statement, Object parameter) {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		return (T) (parameter != null ? template.insert(namespace + statement,
				parameter) : template.insert(namespace + statement));
				
	}

	@Override
	public int update(String statement) {
		return update(statement, null);
	}

	@Override
	public int update(String statement, Object parameter) {
		SqlMapClientTemplate template = getSqlMapClientTemplate();
		return parameter != null ? template.update(namespace + statement,
				parameter) : template.update(namespace + statement);
				
	}

	@Override
	public int delete(String statement) {
		return delete(statement, null);
	}

	@Override
	public int delete(final String statement, final Object parameter) {
		return getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {

			public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				return parameter != null ? executor.delete(namespace
						+ statement, parameter) : executor.delete(namespace + statement);
			}
		});
	}

}
