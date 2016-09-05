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
import java.util.Map;

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
		SpringMongoDaoSupport<T, PK> implements SpringMongoDao<T, PK> {
	
	@Override
	public void insert(T entity) {
		insert(entity, null);
	}

	@Override
	public void insert(T entity, String collection) {
		if (StringUtils.isNotBlank(collection))
			getMongoOperations().insert(entity, collection);
		else
			getMongoOperations().insert(entity);
	}
	
	@Override
	public void batchInsert(Collection<T> entities) {
		batchInsert(entities, (String) null);
	}

	@Override
	public void batchInsert(Collection<T> entities, String collection) {
		if (StringUtils.isNotBlank(collection))
			getMongoOperations().insert(entities, collection);
		else
			getMongoOperations().insert(entities, getBeanClass());
	}
	
	@Override
	public WriteResult updateById(PK id, Update update) {
		return updateById(id, update, null);
	}

	@Override
	public WriteResult updateById(PK id, Update update, String collection) {
		return updateFirst(buildIdQuery(id), update, collection);
	}
		
	@Override
	public WriteResult updateFirst(Query query, Update update) {
		return updateFirst(query, update, null);
	}
	
	@Override
	public WriteResult updateFirst(Query query, Update update, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.updateFirst(query, update, getBeanClass(), collection)
				: getMongoOperations().updateFirst(query, update, getBeanClass());
	}
	
	@Override
	public WriteResult updateMulti(Query query, Update update) {
		return updateMulti(query, update, null);
	}

	@Override
	public WriteResult updateMulti(Query query, Update update, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.updateMulti(query, update, getBeanClass(), collection)
				: getMongoOperations().updateMulti(query, update, getBeanClass());
	}
	
	@Override
	public WriteResult upsertById(PK id, Update update) {
		return upsertById(id, update, null);
	}

	@Override
	public WriteResult upsertById(PK id, Update update, String collection) {
		return upsertOne(buildIdQuery(id), update, collection);
	}

	@Override
	public WriteResult upsertOne(Query query, Update update) {
		return upsertOne(query, update, null);
	}
	
	@Override
	public WriteResult upsertOne(Query query, Update update, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.upsert(query, update, getBeanClass(), collection)
				: getMongoOperations().upsert(query, update, getBeanClass());
	}
	
//	@Override
//	public WriteResult upsertMulti(Query query, Update update) {
//		return upsertMulti(query, update, null);
//	}
//
//	@Override
//	public WriteResult upsertMulti(Query query, Update update, String collection) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Override
	public void save(T entity) {
		save(entity, null);
	}
	
	@Override
	public void save(T entity, String collection) {
		if (StringUtils.isNotBlank(collection))
			getMongoOperations().save(entity, collection);
		else
			getMongoOperations().save(entity);
	}

	@Override
	public WriteResult remove(T entity) {
		return remove(entity, null);
	}

	@Override
	public WriteResult remove(T entity, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations().remove(
				entity, collection) : getMongoOperations().remove(entity);
	}
	
	@Override
	public WriteResult remove(PK id) {
		return remove(id, null);
	}

	@Override
	public WriteResult remove(PK id, String collection) {
		T entity = findById(id, collection);
		if (entity != null)
			return getMongoOperations().remove(entity);
		
		return null;
	}
	
	@Override
	public T findAndModify(PK id, Update update) {
		return findAndModify(id, update, null);
	}

	@Override
	public T findAndModify(PK id, Update update, String collection) {
		return findAndModify(buildIdQuery(id), update, collection);
	}
	
	@Override
	public T findAndModify(Query query, Update update) {
		return findAndModify(query, update, null);
	}

	@Override
	public T findAndModify(Query query, Update update, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.findAndModify(query, update, getBeanClass(), collection)
				: getMongoOperations().findAndModify(query, update, getBeanClass());
	}
	
	@Override
	public T findAndRemove(PK id) {
		return findAndRemove(id, null);
	}

	@Override
	public T findAndRemove(PK id, String collection) {
		return findAndRemove(buildIdQuery(id), collection);
	}

	@Override
	public T findAndRemove(Query query) {
		return findAndRemove(query, null);
	}

	@Override
	public T findAndRemove(Query query, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.findAndRemove(query, getBeanClass(), collection)
				: getMongoOperations().findAndRemove(query, getBeanClass());
	}
	
	@Override
	public List<T> findAllAndRemove(Query query) {
		return findAllAndRemove(query, null);
	}

	@Override
	public List<T> findAllAndRemove(Query query, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.findAllAndRemove(query, getBeanClass(), collection)
				: getMongoOperations().findAllAndRemove(query, getBeanClass());
	}
	
	@Override
	public T findById(PK id) {
		return findById(id, null);
	}
	
	@Override
	public T findById(PK id, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.findById(id, getBeanClass(), collection)
				: getMongoOperations().findById(id, getBeanClass());
	}
	
	@Override
	public List<T> findAll() {
		return getMongoOperations().findAll(getBeanClass());
	}
	
	@Override
	public T findOne(String propertyName, Object propertyValue) {
		return findOne(propertyName, propertyValue, null);
	}

	@Override
	public T findOne(String propertyName, Object propertyValue, String collection) {
		return findOne(buildPropertyQuery(propertyName, propertyValue), collection);
	}

	@Override
	public T findOne(Map<String, ?> propertyMap) {
		return findOne(propertyMap, null);
	}

	@Override
	public T findOne(Map<String, ?> propertyMap, String collection) {
		return findOne(buildPropertiesAndQuery(propertyMap), collection);
	}

	@Override
	public T findOne(Query query) {
		return findOne(query, null);
	}

	@Override
	public T findOne(Query query, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.findOne(query, getBeanClass(), collection)
				: getMongoOperations().findOne(query, getBeanClass());
	}
	
	@Override
	public List<T> find(String propertyName, Object propertyValue) {
		return find(propertyName, propertyValue, null);
	}

	@Override
	public List<T> find(String propertyName, Object propertyValue, String collection) {
		return find(buildPropertyQuery(propertyName, propertyValue), collection);
	}

	@Override
	public List<T> find(Map<String, ?> propertyMap) {
		return find(propertyMap, null);
	}

	@Override
	public List<T> find(Map<String, ?> propertyMap, String collection) {
		return find(buildPropertiesAndQuery(propertyMap), collection);
	}

	@Override
	public List<T> find(Query query) {
		return find(query, null);
	}

	@Override
	public List<T> find(Query query, String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.find(query, getBeanClass(), collection) 
				: getMongoOperations().find(query, getBeanClass());
	}

}
