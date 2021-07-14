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
 * Create Date : 2016-8-26
 */

package org.sniper.nosql.mongodb.spring;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.spring.beans.AbstractGenricBean;

/**
 * Spring MongoDB数据访问支持类
 * @author  Daniele
 * @version 1.0
 */
public abstract class SpringMongoDaoSupport<T, PK extends Serializable> extends AbstractGenricBean<T> {
		
	public static final String ID_KEY_NAME = "_id";
	
	@Autowired
	private MongoOperations mongoOperations;

	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}

	public void setMongoOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	@Override
	protected void checkProperties() {
		if (this.mongoOperations == null)
			throw new IllegalArgumentException("Property 'mongoOperations' is required");
	}
	
	/**
	 * 获取当前实体对象类型对应的集合名称
	 * @author Daniele 
	 * @return
	 */
	protected String getCollectionName() {
		return mongoOperations.getCollectionName(getTargetType());
	}
	
	/**
	 * 获取转换器
	 * @author Daniele 
	 * @return
	 */
	protected MongoConverter getConverter() {
		return mongoOperations.getConverter();
	}
	
	/**
	 * 获取映射上下文
	 * @author Daniele 
	 * @return
	 */
	protected MappingContext<?, ?> getMappingContext() {
		return getConverter().getMappingContext();
	}
	
	/**
	 * 获取当前实体对象类型对应的MongoDB持久化实体对象
	 * @author Daniele 
	 * @return
	 */
	protected MongoPersistentEntity<?> getMongoPersistentEntity() {
		return (MongoPersistentEntity<?>) getMappingContext().getPersistentEntity(getTargetType());
	}
		
	/**
	 * 获取当前实体对象对应的MongoDB ID键名称
	 * @author Daniele 
	 * @return
	 */
	protected String getIdKeyName() {		
		MongoPersistentEntity<?> persistentEntity = getMongoPersistentEntity();
		MongoPersistentProperty idProperty = (persistentEntity != null ? persistentEntity
				.getIdProperty() : null);
		
		return idProperty != null ? idProperty.getFieldName() : ID_KEY_NAME;
	}
	
	/**
	 * 获取当前实体对象属性对应的MongoDB键名称
	 * @author Daniele 
	 * @param propertyName 属性名称
	 * @return
	 */
	protected String getKeyName(String propertyName) {
		if (StringUtils.isBlank(propertyName))
			return StringUtils.EMPTY;
		
		MongoPersistentEntity<?> persistentEntity = getMongoPersistentEntity();
		MongoPersistentProperty property = (persistentEntity != null ? persistentEntity
				.getPersistentProperty(propertyName) : null);
		return property != null ? property.getFieldName() : propertyName;
	}
	
	/**
	 * 构建ID查询对象
	 * @author Daniele 
	 * @param id
	 * @return
	 */
	protected Query buildIdQuery(PK id) {
		return new Query(Criteria.where(getIdKeyName()).is(id));
	}
	
	/**
	 * 构建具备分段能力的查询对象
	 * @author Daniele 
	 * @param start
	 * @param maxRows
	 * @return
	 */
	protected Query buildOffsetQuery(int start, int maxRows) {
		Query query = new Query();
		setOffsetQuery(query, start, maxRows);
		return query;
	}
	
	/**
	 * 构建属性查询对象
	 * @author Daniele 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return
	 */
	protected Query buildPropertyQuery(String propertyName, Object propertyValue) {
		return buildPropertyQuery(propertyName, propertyValue, -1, -1);
	}
	
	/**
	 * 构建具备分段能力的属性查询对象
	 * @author Daniele 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	protected Query buildPropertyQuery(String propertyName, Object propertyValue, int start, int maxRows) {
		Query query = new Query();
		query.addCriteria(buildWhereIsCriteria(propertyName, propertyValue));
		setOffsetQuery(query, start, maxRows);
		return query;
	}
	
	/**
	 * 构建属性映射组"逻辑与"查询对象
	 * @author Daniele 
	 * @param propertyMap 属性映射组
	 * @return
	 */
	protected Query buildPropertiesAndQuery(Map<String, ?> propertyMap) {
		return buildPropertiesAndQuery(propertyMap, -1, -1);
	}
	
	/**
	 * 构建具备分段能力的属性映射组"逻辑与"查询对象
	 * @author Daniele 
	 * @param propertyMap 属性映射组
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	protected Query buildPropertiesAndQuery(Map<String, ?> propertyMap, int start, int maxRows) {
		Query query = new Query();
		query.addCriteria(buildWhereIsAndCriteria(propertyMap));
		setOffsetQuery(query, start, maxRows);
		return query;
	}
	
	/**
	 * 构建属性映射组"逻辑或"查询对象
	 * @author Daniele 
	 * @param propertyMap 属性映射组
	 * @return
	 */
	protected Query buildPropertiesOrQuery(Map<String, ?> propertyMap) {
		return buildPropertiesOrQuery(propertyMap, -1, -1);
	}
	
	/**
	 * 构建具备分段能力的属性映射组"逻辑或"查询对象
	 * @author Daniele 
	 * @param propertyMap 属性映射组
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	protected Query buildPropertiesOrQuery(Map<String, ?> propertyMap, int start, int maxRows) {
		Query query = new Query();
		query.addCriteria(buildWhereIsOrCriteria(propertyMap));
		setOffsetQuery(query, start, maxRows);
		return query;
	}
		
	/**
	 * 将属性名和属性值构建成where-is条件
	 * @author Daniele 
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	protected Criteria buildWhereIsCriteria(String propertyName, Object propertyValue) {
		String keyName = getKeyName(propertyName);
		return StringUtils.isNotEmpty(keyName) ? Criteria.where(
				getKeyName(propertyName)).is(propertyValue) : new Criteria();
	}
	
	/**
	 * 将属性映射组构建成多个where-is"逻辑与"条件列表
	 * @author Daniele 
	 * @param propertyMap
	 * @return
	 */
	protected Criteria buildWhereIsAndCriteria(Map<String, ?> propertyMap) {
		List<Criteria> whereIsCriteria = buildWhereIsCriteria(propertyMap);
		
		Criteria criteria = null;
		if (CollectionUtils.isNotEmpty(whereIsCriteria)) {
			if (whereIsCriteria.size() > 1) {
				criteria = new Criteria();
				criteria.andOperator(CollectionUtils.toArray(whereIsCriteria, Criteria.class));
			} else
				criteria = whereIsCriteria.get(0);
		}
		
		return criteria != null ? criteria : new Criteria();
	}
	
	/**
	 * 将属性映射组构建成多个where-is"逻辑或"条件列表
	 * @author Daniele 
	 * @param propertyMap
	 * @return
	 */
	protected Criteria buildWhereIsOrCriteria(Map<String, ?> propertyMap) {
		List<Criteria> whereIsCriteria = buildWhereIsCriteria(propertyMap);
		
		Criteria criteria = null;
		if (CollectionUtils.isNotEmpty(whereIsCriteria)) {
			if (whereIsCriteria.size() > 1) {
				criteria = new Criteria();
				criteria.orOperator(CollectionUtils.toArray(whereIsCriteria, Criteria.class));
			} else
				criteria = whereIsCriteria.get(0);
		}
		
		return criteria != null ? criteria : new Criteria();
	}
	
	/**
	 * 将属性映射组构建成多个where-is条件列表
	 * @author Daniele 
	 * @param propertyMap 属性映射组
	 * @return
	 */
	protected List<Criteria> buildWhereIsCriteria(Map<String, ?> propertyMap) {
		List<Criteria> whereIsCriteria = CollectionUtils.newArrayList();
		
		if (MapUtils.isNotEmpty(propertyMap)) {
			
			Iterator<?> iterator = propertyMap.entrySet().iterator();
			Entry<?, ?> entry;
			String propertyName;
			
			while (iterator.hasNext()) {
				entry = (Entry<?, ?>) iterator.next();
				propertyName = (String) entry.getKey();
				if (StringUtils.isNotBlank(propertyName))
					whereIsCriteria.add(Criteria.where(getKeyName(propertyName)).is(entry.getValue()));
			}
		}
		
		return whereIsCriteria;
	}
	
	/**
	 * 设置分段查询参数
	 * @author Daniele 
	 * @param query 查询对象
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 */
	protected void setOffsetQuery(Query query, int start, int maxRows) {
		if (start > -1)
			query.skip(start);
		
		if (maxRows > 0)
			query.limit(maxRows);
	}
	
	/**
	 * 将ID值构建成where-is条件的MatchOperation对象
	 * @author Daniele 
	 * @param id
	 * @return
	 */
	protected MatchOperation buildWhereIsMatchOperation(PK id) {
		
		Criteria criteria = Criteria.where(getIdKeyName());
		
		if (id != null && ObjectId.isValid(id.toString()))
			criteria.is(new ObjectId(id.toString()));
		else
			criteria.is(id);
		
		return new MatchOperation(criteria);
	}
	
	/**
	 * 将属性名和属性值构建成where-is条件的MatchOperation对象
	 * @author Daniele 
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	protected MatchOperation buildWhereIsMatchOperation(String propertyName, Object propertyValue) {
		return new MatchOperation(buildWhereIsCriteria(propertyName, propertyValue));
	}
	
	/**
	 * 将属性映射组构建成多个where-is"逻辑与"条件的MatchOperation对象
	 * @author Daniele 
	 * @param propertyMap
	 * @return
	 */
	protected MatchOperation buildWhereIsMatchAndOperation(Map<String, ?> propertyMap) {
		return new MatchOperation(buildWhereIsAndCriteria(propertyMap));
	}
	
	/**
	 * 根据起始位置构建SkipOperation对象
	 * @author Daniele 
	 * @param start
	 * @return
	 */
	protected SkipOperation buildSkipOperation(int start) {
		return start >= 0 ? new SkipOperation(start) : null;
	}
	
	/**
	 * 根据最大行数构建LimitOperation对象
	 * @author Daniele 
	 * @param maxRows
	 * @return
	 */
	protected LimitOperation buildLimitOperation(int maxRows) {
		return maxRows >= 0 ? new LimitOperation(maxRows) : null;
	}
	
	/**
	 * 根据结果类型构建ProjectionOperation对象
	 * @author Daniele 
	 * @param resultClass
	 * @return
	 */
	protected <R> ProjectionOperation buildProjectionOperation(Class<R> resultClass) {
		ProjectionOperation projectionOperation = null;
		
		List<String> propertyNames = ReflectionUtils.getDeclaredFieldNames(resultClass);
		if (CollectionUtils.isNotEmpty(propertyNames)) {
			List<String> keyNames = CollectionUtils.newArrayList();
			for (String propertyName : propertyNames) {
				keyNames.add(getKeyName(propertyName));
			}
			projectionOperation = Aggregation.project(CollectionUtils.toArray(keyNames, String.class));
		}
		
		return projectionOperation;
	}
		
}
