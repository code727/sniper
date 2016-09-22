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
 * Create Date : 2016-9-23
 */

package org.workin.nosql.mongodb.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * MongoDB聚合查询接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface MongoAggregateQuery<T, PK extends Serializable> {
	
	public T aggregateFindById(PK id);
	
	public T aggregateFindById(String collection, PK id);
	
	public <R> R aggregateFindById(PK id, Class<R> resultClass);
	
	public <R> R aggregateFindById(String collection, PK id, Class<R> resultClass);
	
	public T aggregateFindOne(String propertyName, Object propertyValue);
	
	public T aggregateFindOne(String collection, String propertyName, Object propertyValue);
	
	public <R> R aggregateFindOne(String propertyName, Object propertyValue, Class<R> resultClass);
	
	public <R> R aggregateFindOne(String collection, String propertyName, Object propertyValue, Class<R> resultClass);
	
	public T aggregateFindOne(Map<String, ?> propertyMap);
	
	public T aggregateFindOne(String collection, Map<String, ?> propertyMap);
	
	public <R> R aggregateFindOne(Map<String, ?> propertyMap, Class<R> resultClass);
	
	public <R> R aggregateFindOne(String collection, Map<String, ?> propertyMap, Class<R> resultClass);
	
	public List<T> aggregateFindAll();
	
	public List<T> aggregateFindAll(String collection);
	
	public <R> List<R> aggregateFindAll(Class<R> resultClass);
	
	public <R> List<R> aggregateFindAll(String collection, Class<R> resultClass);
	
	public List<T> aggregateFind(int start, int maxRows);
	
	public List<T> aggregateFind(String collection, int start, int maxRows);
	
	public <R> List<R> aggregateFind(int start, int maxRows, Class<R> resultClass);
	
	public <R> List<R> aggregateFind(String collection, int start, int maxRows, Class<R> resultClass);
	
	public List<T> aggregateFind(String propertyName, Object propertyValue);
	
	public List<T> aggregateFind(String collection, String propertyName, Object propertyValue);
	
	public List<T> aggregateFind(String propertyName, Object propertyValue, int start, int maxRows);
	
	public List<T> aggregateFind(String collection, String propertyName, Object propertyValue, int start, int maxRows);
	
	public <R> List<R> aggregateFind(String propertyName, Object propertyValue, Class<R> resultClass);
	
	public <R> List<R> aggregateFind(String collection, String propertyName, Object propertyValue, Class<R> resultClass);
	
	public <R> List<R> aggregateFind(String propertyName, Object propertyValue, int start, int maxRows, Class<R> resultClass);
	
	public <R> List<R> aggregateFind(String collection, String propertyName, Object propertyValue, int start, int maxRows, Class<R> resultClass);
	
	public List<T> aggregateFind(Map<String, ?> propertyMap);
	
	public List<T> aggregateFind(String collection, Map<String, ?> propertyMap);
	
	public List<T> aggregateFind(Map<String, ?> propertyMap, int start, int maxRows);
	
	public List<T> aggregateFind(String collection, Map<String, ?> propertyMap, int start, int maxRows);
	
	public <R> List<R> aggregateFind(Map<String, ?> propertyMap, Class<R> resultClass);
	
	public <R> List<R> aggregateFind(String collection, Map<String, ?> propertyMap, Class<R> resultClass);
	
	public <R> List<R> aggregateFind(Map<String, ?> propertyMap, int start, int maxRows, Class<R> resultClass);
	
	public <R> List<R> aggregateFind(String collection, Map<String, ?> propertyMap, int start, int maxRows, Class<R> resultClass);
	
}
