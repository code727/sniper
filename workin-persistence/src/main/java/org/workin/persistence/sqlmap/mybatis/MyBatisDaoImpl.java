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

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.workin.persistence.sqlmap.dao.SqlMapDao;

/**
 * MyBatis DAO实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Repository
public class MyBatisDaoImpl<T> extends MyBatisQueryDaoImpl<T>
		implements SqlMapDao<T> {

	@Override
	public Integer insert(String statement) {
		return insert(statement, null);
	}

	@Override
	public Integer insert(String statement, Object parameter) {
		SqlSession sqlSession = getSqlSession();
		return parameter != null ? sqlSession.insert(namespace + statement,
				parameter) : sqlSession.insert(namespace + statement);
	}

	@Override
	public int update(String statement) {
		return update(statement, null);
	}

	@Override
	public int update(String statement, Object parameter) {
		SqlSession sqlSession = getSqlSession();
		return parameter != null ? sqlSession.update(namespace + statement,
				parameter) : sqlSession.update(namespace + statement);
	}

	@Override
	public int delete(String statement) {
		return delete(statement, null);
	}

	@Override
	public int delete(String statement, Object parameter) {
		SqlSession sqlSession = getSqlSession();
		return parameter != null ? sqlSession.delete(namespace + statement,
				parameter) : sqlSession.delete(namespace + statement);
	}

}
