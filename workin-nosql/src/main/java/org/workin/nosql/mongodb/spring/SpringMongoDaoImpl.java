/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-8-25
 */

package org.workin.nosql.mongodb.spring;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.workin.commons.util.StringUtils;

import com.mongodb.WriteResult;

/**
 * Spring MongoDB数据访问实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Repository
public class SpringMongoDaoImpl<T, PK extends Serializable> extends
		SpringMongoDaoSupport<T> implements SpringMongoDao<T, PK> {
	
	@Override
	public void insert(T entity) {
		insert(entity, null);
	}

	@Override
	public void insert(T entity, String collection) {
		if (StringUtils.isNotBlank(collection))
			getMongoTemplate().insert(entity, collection);
		else
			getMongoTemplate().insert(entity);
	}
	
	@Override
	public void batchInsert(Collection<T> entities) {
		batchInsert(entities, (String) null);
	}

	@Override
	public void batchInsert(Collection<T> entities, String collection) {
		if (StringUtils.isNotBlank(collection))
			getMongoTemplate().insert(entities, collection);
		else
			getMongoTemplate().insert(entities);
	}
	
	@Override
	public WriteResult update(Query query, Update update) {
		return update(query, update, null);
	}
	
	@Override
	public WriteResult update(Query query, Update update, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoTemplate()
				.updateFirst(query, update, getBeanClass(), collection)
				: getMongoTemplate().updateFirst(query, update, getBeanClass());
	}
	
	@Override
	public WriteResult batchUpdate(Query query, Update update) {
		return batchUpdate(query, update, null);
	}

	@Override
	public WriteResult batchUpdate(Query query, Update update, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoTemplate()
				.updateMulti(query, update, getBeanClass(), collection)
				: getMongoTemplate().updateMulti(query, update, getBeanClass());
	}

	@Override
	public WriteResult upsert(Query query, Update update) {
		return upsert(query, update, null);
	}

	@Override
	public WriteResult upsert(Query query, Update update, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoTemplate()
				.upsert(query, update, getBeanClass(), collection)
				: getMongoTemplate().upsert(query, update, getBeanClass());
	}
	
	@Override
	public void save(T entity) {
		save(entity, null);
	}
	
	@Override
	public void save(T entity, String collection) {
		if (StringUtils.isNotBlank(collection))
			getMongoTemplate().save(entity, collection);
		else
			getMongoTemplate().save(entity);
	}

	@Override
	public WriteResult remove(T entity) {
		return remove(entity, null);
	}

	@Override
	public WriteResult remove(T entity, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoTemplate().remove(
				entity, collection) : getMongoTemplate().remove(entity);
	}
	
	@Override
	public WriteResult remove(PK primaryKey) {
		return remove(primaryKey, null);
	}

	@Override
	public WriteResult remove(PK primaryKey, String collection) {
		T entity = findById(primaryKey, collection);
		if (entity != null)
			return getMongoTemplate().remove(entity);
		
		return null;
	}
	
	@Override
	public T findAndModify(Query query, Update update) {
		return findAndModify(query, update, null);
	}

	@Override
	public T findAndModify(Query query, Update update, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoTemplate()
				.findAndModify(query, update, getBeanClass(), collection)
				: getMongoTemplate().findAndModify(query, update, getBeanClass());
	}

	@Override
	public T findAndRemove(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findAndRemove(Query query, String collection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public T findById(PK primaryKey) {
		return findById(primaryKey, null);
	}
	
	@Override
	public T findById(PK primaryKey, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoTemplate()
				.findById(primaryKey, getBeanClass(), collection)
				: getMongoTemplate().findById(primaryKey, getBeanClass());
	}
	
	@Override
	public List<T> findAll() {
		return getMongoTemplate().findAll(getBeanClass());
	}

}
