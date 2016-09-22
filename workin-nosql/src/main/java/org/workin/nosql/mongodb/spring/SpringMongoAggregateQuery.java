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

package org.workin.nosql.mongodb.spring;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.workin.nosql.mongodb.dao.MongoAggregateQuery;

/**
 * Spring MongoDB聚合查询接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SpringMongoAggregateQuery<T, PK extends Serializable> extends
		MongoAggregateQuery<T, PK> {
	
	public T aggregateFindOne(MatchOperation matchOperation);
	
	public T aggregateFindOne(String collection, MatchOperation matchOperation);
	
	public <R> R aggregateFindOne(MatchOperation matchOperation, Class<R> resultClass);
	
	public <R> R aggregateFindOne(String collection, MatchOperation matchOperation, Class<R> resultClass);
	
	public List<T> aggregateFind(SkipOperation skipOperation, LimitOperation limitOperation);
	
	public List<T> aggregateFind(String collection, SkipOperation skipOperation, LimitOperation limitOperation);
	
	public <R> List<R> aggregateFind(SkipOperation skipOperation, LimitOperation limitOperation, Class<R> resultClass);
	
	public <R> List<R> aggregateFind(String collection, SkipOperation skipOperation, LimitOperation limitOperation, Class<R> resultClass);
	
	public List<T> aggregateFind(MatchOperation matchOperation);
	
	public List<T> aggregateFind(String collection, MatchOperation matchOperation);
	
	public <R> List<R> aggregateFind(MatchOperation matchOperation, Class<R> resultClass);
	
	public <R> List<R> aggregateFind(String collection, MatchOperation matchOperation, Class<R> resultClass);
	
	public List<T> aggregateFind(MatchOperation matchOperation, SkipOperation skipOperation, LimitOperation limitOperation);
	
	public List<T> aggregateFind(String collection, MatchOperation matchOperation, SkipOperation skipOperation, LimitOperation limitOperation);
	
	public <R> List<R> aggregateFind(MatchOperation matchOperation, SkipOperation skipOperation, LimitOperation limitOperation, Class<R> resultClass);
	
	public <R> List<R> aggregateFind(String collection, MatchOperation matchOperation, SkipOperation skipOperation, LimitOperation limitOperation, Class<R> resultClass);
	
}
