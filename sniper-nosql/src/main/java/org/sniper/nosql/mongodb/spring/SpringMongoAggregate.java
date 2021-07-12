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
 * Create Date : 2016-9-19
 */

package org.sniper.nosql.mongodb.spring;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

/**
 * Spring MongoDB聚合接口
 * @author  Daniele
 * @version 1.0
 */
public interface SpringMongoAggregate<T, PK extends Serializable> extends
		SpringMongoAggregateQuery<T, PK> {
	
	/**
	 * 按列表中的操作进行聚合运算后返回结果<P>
	 * 实际执行的语句为:db.collection.aggregate({"operationName1":{operation1},
	 * "operationName2":{operation2},"operationNameN":{operationN}});
	 * @author Daniele
	 * @param operations 聚合操作列表
	 * @return
	 */
	public AggregationResults<T> aggregate(List<AggregationOperation> operations);
	
	/**
	 * 在目标集合中按列表中的操作进行聚合运算后返回结果<P>
	 * 实际执行的语句为:db.collection.aggregate({"operationName1":{operation1},
	 * "operationName2":{operation2},"operationNameN":{operationN}});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param operations 聚合操作列表
	 * @return
	 */
	public AggregationResults<T> aggregate(String collection, List<AggregationOperation> operations);
	
	/**
	 * 按列表中的操作进行聚合运算后以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.aggregate({"operationName1":{operation1},
	 * "operationName2":{operation2},"operationNameN":{operationN}});
	 * @author Daniele 
	 * @param operations 聚合操作列表
	 * @param resultClass 聚合结果数据类型
	 * @return
	 */
	public <R> AggregationResults<R> aggregate(List<AggregationOperation> operations, Class<R> resultClass);
	
	/**
	 * 在目标集合中按列表中的操作进行聚合运算后以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.aggregate({"operationName1":{operation1},
	 * "operationName2":{operation2},"operationNameN":{operationN}});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param operations 聚合操作列表
	 * @param resultClass 聚合结果数据类型
	 * @return
	 */
	public <R> AggregationResults<R> aggregate(String collection, List<AggregationOperation> operations, Class<R> resultClass);
	
	/**
	 * 执行聚合运算P>
	 * 实际执行的语句为:db.collection.aggregate({"operationName1":{operation1},
	 * "operationName2":{operation2},"operationNameN":{operationN}});
	 * @author Daniele 
	 * @param aggregation 聚合运算
	 * @return
	 */
	public AggregationResults<T> aggregate(Aggregation aggregation);
	
	/**
	 * 在目标集合中执行聚合运算P>
	 * 实际执行的语句为:db.collection.aggregate({"operationName1":{operation1},
	 * "operationName2":{operation2},"operationNameN":{operationN}});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param aggregation 聚合运算
	 * @return
	 */
	public AggregationResults<T> aggregate(String collection, Aggregation aggregation);
	
	/**
	 * 执行聚合运算后以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.aggregate({"operationName1":{operation1},
	 * "operationName2":{operation2},"operationNameN":{operationN}});
	 * @author Daniele 
	 * @param aggregation 聚合运算
	 * @param resultClass 聚合结果数据类型
	 * @return
	 */
	public <R> AggregationResults<R> aggregate(Aggregation aggregation, Class<R> resultClass);
	
	/**
	 * 在目标集合中执行聚合运算后以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.aggregate({"operationName1":{operation1},
	 * "operationName2":{operation2},"operationNameN":{operationN}});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param aggregation 聚合运算
	 * @param resultClass 聚合结果数据类型
	 * @return
	 */
	public <R> AggregationResults<R> aggregate(String collection, Aggregation aggregation, Class<R> resultClass);
	
}
