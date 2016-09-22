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

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.ClassUtils;
import org.workin.commons.util.ReflectionUtils;
import org.workin.commons.util.StringUtils;
import org.workin.nosql.mongodb.MapReduceResultModel;

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
		insert(null, entity);
	}

	@Override
	public void insert(String collection, T entity) {
		if (StringUtils.isNotBlank(collection))
			getMongoOperations().insert(entity, collection);
		else
			getMongoOperations().insert(entity);
	}
	
	@Override
	public void batchInsert(Collection<T> entities) {
		batchInsert(null, entities);
	}

	@Override
	public void batchInsert(String collection, Collection<T> entities) {
		if (StringUtils.isNotBlank(collection))
			getMongoOperations().insert(entities, collection);
		else
			getMongoOperations().insert(entities, getBeanClass());
	}
	
	@Override
	public WriteResult updateById(PK id, Update update) {
		return updateById(null, id, update);
	}

	@Override
	public WriteResult updateById(String collection, PK id, Update update) {
		return updateFirst(collection, buildIdQuery(id), update);
	}
		
	@Override
	public WriteResult updateFirst(Query query, Update update) {
		return updateFirst(null, query, update);
	}
	
	@Override
	public WriteResult updateFirst(String collection, Query query, Update update) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.updateFirst(query, update, getBeanClass(), collection)
				: getMongoOperations().updateFirst(query, update, getBeanClass());
	}
	
	@Override
	public WriteResult updateMulti(Query query, Update update) {
		return updateMulti(null, query, update);
	}

	@Override
	public WriteResult updateMulti(String collection, Query query, Update update) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.updateMulti(query, update, getBeanClass(), collection)
				: getMongoOperations().updateMulti(query, update, getBeanClass());
	}
	
	@Override
	public WriteResult upsertById(PK id, Update update) {
		return upsertById(null, id, update);
	}

	@Override
	public WriteResult upsertById(String collection, PK id, Update update) {
		return upsertOne(collection, buildIdQuery(id), update);
	}

	@Override
	public WriteResult upsertOne(Query query, Update update) {
		return upsertOne(null, query, update);
	}
	
	@Override
	public WriteResult upsertOne(String collection, Query query, Update update) {
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
//		return null;
//	}
	
	@Override
	public void save(T entity) {
		save(null, entity);
	}
	
	@Override
	public void save(String collection, T entity) {
		if (StringUtils.isNotBlank(collection))
			getMongoOperations().save(entity, collection);
		else
			getMongoOperations().save(entity);
	}

	@Override
	public WriteResult remove(T entity) {
		return remove(null, entity);
	}

	@Override
	public WriteResult remove(String collection, T entity) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations().remove(
				entity, collection) : getMongoOperations().remove(entity);
	}
	
	@Override
	public WriteResult remove(PK id) {
		return remove(null, id);
	}

	@Override
	public WriteResult remove(String collection, PK id) {
		T entity = findById(collection, id);
		if (entity != null)
			return getMongoOperations().remove(entity, collection);
		
		return null;
	}
	
	@Override
	public T findAndModify(PK id, Update update) {
		return findAndModify(null, id, update);
	}

	@Override
	public T findAndModify(String collection, PK id, Update update) {
		return findAndModify(collection, buildIdQuery(id), update);
	}
	
	@Override
	public T findAndModify(Query query, Update update) {
		return findAndModify(null, query, update);
	}

	@Override
	public T findAndModify(String collection, Query query, Update update) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.findAndModify(query, update, getBeanClass(), collection)
				: getMongoOperations().findAndModify(query, update, getBeanClass());
	}
	
	@Override
	public T findAndRemove(PK id) {
		return findAndRemove(null, id);
	}

	@Override
	public T findAndRemove(String collection, PK id) {
		return findAndRemove(collection, buildIdQuery(id));
	}
	
	@Override
	public T findAndRemove(String propertyName, Object propertyValue) {
		return findAndRemove(null, propertyName, propertyValue);
	}

	@Override
	public T findAndRemove(String collection, String propertyName, Object propertyValue) {
		return findAndRemove(collection, buildPropertyQuery(propertyName, propertyValue));
	}
	
	@Override
	public T findAndRemove(Map<String, ?> propertyMap) {
		return findAndRemove(null, propertyMap);
	}

	@Override
	public T findAndRemove(String collection, Map<String, ?> propertyMap) {
		return findAndRemove(collection, buildPropertiesAndQuery(propertyMap));
	}

	@Override
	public T findAndRemove(Query query) {
		return findAndRemove(null, query);
	}

	@Override
	public T findAndRemove(String collection, Query query) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.findAndRemove(query, getBeanClass(), collection)
				: getMongoOperations().findAndRemove(query, getBeanClass());
	}
	
	@Override
	public List<T> findAllAndRemove(String propertyName, Object propertyValue) {
		return findAllAndRemove(null, propertyName, propertyValue);
	}

	@Override
	public List<T> findAllAndRemove(String collection, String propertyName, Object propertyValue) {
		return findAllAndRemove(collection, buildPropertyQuery(propertyName, propertyValue));
	}

	@Override
	public List<T> findAllAndRemove(Map<String, ?> propertyMap) {
		return findAllAndRemove(null, propertyMap);
	}

	@Override
	public List<T> findAllAndRemove(String collection, Map<String, ?> propertyMap) {
		return findAllAndRemove(collection, buildPropertiesAndQuery(propertyMap));
	}
	
	@Override
	public List<T> findAllAndRemove(Query query) {
		return findAllAndRemove(null, query);
	}

	@Override
	public List<T> findAllAndRemove(String collection, Query query) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.findAllAndRemove(query, getBeanClass(), collection)
				: getMongoOperations().findAllAndRemove(query, getBeanClass());
	}
	
	@Override
	public T findById(PK id) {
		return findById(null, id);
	}
	
	@Override
	public T findById(String collection, PK id) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.findById(id, getBeanClass(), collection)
				: getMongoOperations().findById(id, getBeanClass());
	}
	
	@Override
	public List<T> findAll() {
		return findAll(null);
	}
	
	@Override
	public List<T> findAll(String collection) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.findAll(getBeanClass(), collection) : getMongoOperations()
				.findAll(getBeanClass());
	}

	@Override
	public T findOne(String propertyName, Object propertyValue) {
		return findOne(null, propertyName, propertyValue);
	}

	@Override
	public T findOne(String collection, String propertyName, Object propertyValue) {
		return findOne(collection, buildPropertyQuery(propertyName, propertyValue));
	}

	@Override
	public T findOne(Map<String, ?> propertyMap) {
		return findOne(null, propertyMap);
	}

	@Override
	public T findOne(String collection, Map<String, ?> propertyMap) {
		return findOne(collection, buildPropertiesAndQuery(propertyMap));
	}
	
	@Override
	public T findOne(Query query) {
		return findOne(null, query);
	}

	@Override
	public T findOne(String collection, Query query) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.findOne(query, getBeanClass(), collection)
				: getMongoOperations().findOne(query, getBeanClass());
	}
	
	@Override
	public List<T> find(int start, int maxRows) {
		return find((String) null, start, maxRows);
	}

	@Override
	public List<T> find(String collection, int start, int maxRows) {
		Query query = buildOffsetQuery(start, maxRows);
		return StringUtils.isNotBlank(collection) ? getMongoOperations().find(
				query, getBeanClass(), collection) : getMongoOperations().find(
				query, getBeanClass());
	}
	
	@Override
	public List<T> find(String propertyName, Object propertyValue) {
		return find(null, propertyName, propertyValue);
	}

	@Override
	public List<T> find(String collection, String propertyName, Object propertyValue) {
		return find(collection, buildPropertyQuery(propertyName, propertyValue));
	}
	
	@Override
	public List<T> find(String propertyName, Object propertyValue, int start, int maxRows) {
		return find(null, propertyName, propertyValue, start, maxRows);
	}

	@Override
	public List<T> find(String collection, String propertyName, Object propertyValue, int start, int maxRows) {
		return find(collection, buildPropertyQuery(propertyName, propertyValue, start, maxRows));
	}

	@Override
	public List<T> find(Map<String, ?> propertyMap) {
		return find(null, propertyMap);
	}

	@Override
	public List<T> find(String collection, Map<String, ?> propertyMap) {
		return find(collection, buildPropertiesAndQuery(propertyMap));
	}
	
	@Override
	public List<T> find(Map<String, ?> propertyMap, int start, int maxRows) {
		return find(null, propertyMap, start, maxRows);
	}

	@Override
	public List<T> find(String collection, Map<String, ?> propertyMap, int start, int maxRows) {
		return find(collection, buildPropertiesAndQuery(propertyMap, start, maxRows));
	}

	@Override
	public List<T> find(Query query) {
		return find(null, query);
	}

	@Override
	public List<T> find(String collection, Query query) {
		return StringUtils.isNotBlank(collection) ? getMongoOperations()
				.find(query, getBeanClass(), collection) 
				: getMongoOperations().find(query, getBeanClass());
	}
	
	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction, String reduceFunction) {
		return mapReduce(null, mapFunction, reduceFunction);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection, String mapFunction, String reduceFunction) {
		return mapReduce(collection, mapFunction, reduceFunction, (Query) null, null);
	}
	
	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction, String reduceFunction, int limit) {
		return mapReduce(null, mapFunction, reduceFunction, limit);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection,
			String mapFunction, String reduceFunction, int limit) {
		
		return mapReduce(collection, mapFunction, reduceFunction, (Query) null, limit);
	}
	
	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction, String reduceFunction, 
			String queryPropertyName, Object queryPropertyValue) {
			
		return mapReduce(null, mapFunction, reduceFunction, queryPropertyName, queryPropertyValue);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection, String mapFunction,
			String reduceFunction, String queryPropertyName, Object queryPropertyValue) {
		
		return mapReduce(collection, mapFunction, reduceFunction,
				buildPropertyQuery(queryPropertyName, queryPropertyValue), null);
	}
	
	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction, String reduceFunction, 
			String queryPropertyName, Object queryPropertyValue, int limit) {
			
		return mapReduce(null, mapFunction, reduceFunction, queryPropertyName, queryPropertyValue, limit);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection, String mapFunction,
			String reduceFunction, String queryPropertyName, Object queryPropertyValue, int limit) {
		
		return mapReduce(collection, mapFunction, reduceFunction,
				buildPropertyQuery(queryPropertyName, queryPropertyValue), limit);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction,
			String reduceFunction, Map<String, ?> queryProperties) {
		
		return mapReduce(null, mapFunction, reduceFunction, queryProperties);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection,
			String mapFunction, String reduceFunction, Map<String, ?> queryProperties) {
		
		return mapReduce(collection, mapFunction, reduceFunction, queryProperties, null);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction,
			String reduceFunction, Map<String, ?> queryProperties, int limit) {
		
		return mapReduce(null, mapFunction, reduceFunction, queryProperties, limit);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection, String mapFunction, 
			String reduceFunction, Map<String, ?> queryProperties, int limit) {
		
		return mapReduce(collection, mapFunction, reduceFunction,
				buildPropertiesAndQuery(queryProperties), limit);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction,
			String reduceFunction, Query query) {
		
		return mapReduce(null, mapFunction, reduceFunction, query);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection,
			String mapFunction, String reduceFunction, Query query) {
		
		return mapReduce(collection, mapFunction, reduceFunction, query, null);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction,
			String reduceFunction, Query query, int limit) {
		
		return mapReduce(null, mapFunction, reduceFunction, query, limit);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection,
			String mapFunction, String reduceFunction, Query query, int limit) {
		
		return mapReduce(collection, mapFunction, reduceFunction, query,
				new MapReduceOptions().limit(limit).outputTypeInline());
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction,
			String reduceFunction, MapReduceOptions mapReduceOptions) {
		
		return mapReduce(null, mapFunction, reduceFunction, mapReduceOptions);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection,
			String mapFunction, String reduceFunction, MapReduceOptions mapReduceOptions) {
			
		return mapReduce(collection, mapFunction, reduceFunction, (Query) null, mapReduceOptions);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction, String reduceFunction, 
			String queryPropertyName, Object queryPropertyValue, MapReduceOptions mapReduceOptions) {
		
		return mapReduce(null, mapFunction, reduceFunction, queryPropertyName,
				queryPropertyValue, mapReduceOptions);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection, String mapFunction, String reduceFunction,
			String queryPropertyName, Object queryPropertyValue, MapReduceOptions mapReduceOptions) {
			
		return mapReduce(collection, mapFunction, reduceFunction,
				buildPropertyQuery(queryPropertyName, queryPropertyValue), mapReduceOptions);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction, String reduceFunction, 
			Map<String, ?> queryProperties, MapReduceOptions mapReduceOptions) {
			
		return mapReduce(null, mapFunction, reduceFunction, queryProperties, mapReduceOptions);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection, String mapFunction, String reduceFunction,
			Map<String, ?> queryProperties, MapReduceOptions mapReduceOptions) {
		
		return mapReduce(collection, mapFunction, reduceFunction, 
				buildPropertiesAndQuery(queryProperties), mapReduceOptions);
	}

	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String mapFunction, 
			String reduceFunction, Query query, MapReduceOptions mapReduceOptions) {
			
		return mapReduce(null, mapFunction, reduceFunction, query, mapReduceOptions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K,V> MapReduceResults<MapReduceResultModel<K,V>> mapReduce(String collection, String mapFunction, 
			String reduceFunction, Query query, MapReduceOptions mapReduceOptions) {
			
		if (StringUtils.isBlank(collection))
			collection = getCollectionName();
		
		if (mapReduceOptions == null)
			mapReduceOptions = new MapReduceOptions().outputTypeInline();
		
		return (MapReduceResults<MapReduceResultModel<K, V>>) getMongoOperations()
				.mapReduce(query, collection, mapFunction, reduceFunction,
						mapReduceOptions, ClassUtils.getDeclaredClass(MapReduceResultModel.class));
	}

	@Override
	public GroupByResults<T> group(GroupBy groupBy) {
		return group((String) null, groupBy);
	}

	@Override
	public GroupByResults<T> group(String collection, GroupBy groupBy) {
		return group(collection, groupBy, getBeanClass());
	}

	@Override
	public <R> GroupByResults<R> group(GroupBy groupBy, Class<R> resultClass) {
		return group((String) null, groupBy, resultClass);
	}

	@Override
	public <R> GroupByResults<R> group(String collection, GroupBy groupBy, Class<R> resultClass) {
		return group(collection, (Criteria) null, groupBy, resultClass);
	}
	
	@Override
	public GroupByResults<T> group(String propertyName, Object propertyValue, GroupBy groupBy) {
		return group(null, propertyName, propertyValue, groupBy);
	}

	@Override
	public GroupByResults<T> group(String collection, String propertyName,
			Object propertyValue, GroupBy groupBy) {
		
		return group(collection, propertyName, propertyValue, groupBy, getBeanClass());
	}

	@Override
	public <R> GroupByResults<R> group(String propertyName,
			Object propertyValue, GroupBy groupBy, Class<R> resultClass) {
		
		return group(null, propertyName, propertyValue, groupBy, resultClass);
	}

	@Override
	public <R> GroupByResults<R> group(String collection, String propertyName,
			Object propertyValue, GroupBy groupBy, Class<R> resultClass) {
		
		return group(collection, buildWhereIsCriteria(propertyName, propertyValue), groupBy, resultClass);
	}

	@Override
	public GroupByResults<T> group(Map<String, ?> properties, GroupBy groupBy) {
		return group(null, properties, groupBy);
	}

	@Override
	public GroupByResults<T> group(String collection,
			Map<String, ?> properties, GroupBy groupBy) {
		
		return group(collection, properties, groupBy, getBeanClass());
	}

	@Override
	public <R> GroupByResults<R> group(Map<String, ?> properties,
			GroupBy groupBy, Class<R> resultClass) {
		
		return group(null, properties, groupBy, resultClass);
	}

	@Override
	public <R> GroupByResults<R> group(String collection,
			Map<String, ?> properties, GroupBy groupBy, Class<R> resultClass) {
		
		return group(collection, buildWhereIsCriteria(properties), groupBy, resultClass);
	}

	@Override
	public GroupByResults<T> group(Criteria criteria, GroupBy groupBy) {
		return group(null, criteria, groupBy);
	}

	@Override
	public GroupByResults<T> group(String collection, Criteria criteria, GroupBy groupBy) {
		return group(collection, criteria, groupBy, getBeanClass());
	}

	@Override
	public <R> GroupByResults<R> group(Criteria criteria, GroupBy groupBy, Class<R> resultClass) {
		return group(null, criteria, groupBy, resultClass);
	}
	
	@Override
	public <R> GroupByResults<R> group(String collection, Criteria criteria, GroupBy groupBy, Class<R> resultClass) {
		
		AssertUtils.assertNotNull(groupBy, "group parameter must not be null.");
		AssertUtils.assertNotNull(resultClass, "group result class parameter must not be null.");
		
		try {
			String reduce = ReflectionUtils.getFieldValue(groupBy, "reduce");
			AssertUtils.assertNotBlank(reduce, "group reduce function must not be null or blank");
			
			String initial = ReflectionUtils.getFieldValue(groupBy, "initial");
			if (StringUtils.isBlank(initial))
				groupBy.initialDocument("{}");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (StringUtils.isBlank(collection))
			collection = getCollectionName();
			
		return getMongoOperations().group(criteria, collection, groupBy, resultClass);
	}
	
	@Override
	public AggregationResults<T> aggregate(List<AggregationOperation> operations) {
		return aggregate(null, operations);
	}

	@Override
	public AggregationResults<T> aggregate(String collection, List<AggregationOperation> operations) {
		return aggregate(collection, operations, getBeanClass());
	}

	@Override
	public <R> AggregationResults<R> aggregate(List<AggregationOperation> operations, Class<R> resultClass) {
		return aggregate(null, operations, resultClass);
	}

	@Override
	public <R> AggregationResults<R> aggregate(String collection, List<AggregationOperation> operations, Class<R> resultClass) {
		
		AssertUtils.assertNotEmpty(operations, "Aggregation operations must not be null or empty.");
		return aggregate(collection, Aggregation.newAggregation(operations), resultClass);
	}

	@Override
	public AggregationResults<T> aggregate(Aggregation aggregation) {
		return aggregate(null, aggregation);
	}

	@Override
	public AggregationResults<T> aggregate(String collection, Aggregation aggregation) {
		return aggregate(collection, aggregation, getBeanClass());
	}

	@Override
	public <R> AggregationResults<R> aggregate(Aggregation aggregation, Class<R> resultClass) {
		return aggregate(null, aggregation, resultClass);
	}

	@Override
	public <R> AggregationResults<R> aggregate(String collection, Aggregation aggregation, Class<R> resultClass) {
		
		AssertUtils.assertNotNull(aggregation, "aggregation parameter must not be null.");
		AssertUtils.assertNotNull(resultClass, "out type parameter must not be null.");
		
		if (StringUtils.isBlank(collection))
			collection = getCollectionName();
		
		return getMongoOperations().aggregate(aggregation, collection, resultClass);
	}

}
