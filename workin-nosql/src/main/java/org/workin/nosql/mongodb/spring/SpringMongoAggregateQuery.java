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
	
	/**
	 * 聚合查询出满足条件的唯一记录<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param matchOperation match操作对象
	 * @return
	 */
	public T aggregateFindOne(MatchOperation matchOperation);
	
	/**
	 * 在目标集合中聚合查询出满足条件的唯一记录<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合 
	 * @param matchOperation match操作对象
	 * @return
	 */
	public T aggregateFindOne(String collection, MatchOperation matchOperation);
	
	/**
	 * 聚合查询出满足条件的唯一记录<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{matchOperation}})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{matchOperation}},{$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param matchOperation match操作对象
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> R aggregateFindOne(MatchOperation matchOperation, Class<R> resultClass);
	
	/**
	 * 在目标集合中根据据Match操作聚合查询出满足条件的唯一记录<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{matchOperation}})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{matchOperation}},{$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param matchOperation match操作对象
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> R aggregateFindOne(String collection, MatchOperation matchOperation, Class<R> resultClass);
	
	/**
	 * 聚合分页查询出记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{}},{"$skip":skipOperation},{"$limit":limitOperation});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param skipOperation 起始分割操作
	 * @param limitOperation 最大行数截取操作
	 * @return
	 */
	public List<T> aggregateFind(SkipOperation skipOperation, LimitOperation limitOperation);
	
	/**
	 * 在目标集合中聚合分页查询出记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{}},{"$skip":skipOperation},{"$limit":limitOperation});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param skipOperation 起始分割操作
	 * @param limitOperation 最大行数截取操作
	 * @return
	 */
	public List<T> aggregateFind(String collection, SkipOperation skipOperation, LimitOperation limitOperation);
	
	/**
	 * 聚合分页查询出指定类型的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{}},
	 * {"$skip":skipOperation},{"$limit":skipOperation});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{}},
	 * {"$skip":skipOperation},{"$limit":skipOperation},{$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param skipOperation 起始分割操作
	 * @param limitOperation 最大行数截取操作
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(SkipOperation skipOperation, LimitOperation limitOperation, Class<R> resultClass);
	
	/**
	 * 在目标集合中聚合分页查询出指定类型的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{}},
	 * {"$skip":skipOperation},{"$limit":skipOperation});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{}},
	 * {"$skip":skipOperation},{"$limit":skipOperation},{$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param skipOperation 起始分割操作
	 * @param limitOperation 最大行数截取操作
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(String collection, SkipOperation skipOperation, LimitOperation limitOperation, Class<R> resultClass);
	
	/**
	 * 聚合查询出记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param matchOperation match操作对象
	 * @return
	 */
	public List<T> aggregateFind(MatchOperation matchOperation);
	
	/**
	 * 在目标集合中聚合查询出记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param matchOperation match操作对象
	 * @return
	 */
	public List<T> aggregateFind(String collection, MatchOperation matchOperation);
	
	/**
	 * 聚合查询出指定类型的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}},
	 * {$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param matchOperation match操作对象
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(MatchOperation matchOperation, Class<R> resultClass);
	
	/**
	 * 在目标集合中聚合查询出指定类型的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}},
	 * {$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param matchOperation match操作对象
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(String collection, MatchOperation matchOperation, Class<R> resultClass);
	
	/**
	 * 聚合分页查询出记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}},{"$skip":skipOperation},{"$limit":skipOperation});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param matchOperation match操作对象
	 * @param skipOperation 起始分割操作
	 * @param limitOperation 最大行数截取操作
	 * @return
	 */
	public List<T> aggregateFind(MatchOperation matchOperation, SkipOperation skipOperation, LimitOperation limitOperation);
	
	/**
	 * 在目标集合中聚合分页查询出记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}},{"$skip":skipOperation},{"$limit":skipOperation});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param matchOperation match操作对象
	 * @param skipOperation 起始分割操作
	 * @param limitOperation 最大行数截取操作
	 * @return
	 */
	public List<T> aggregateFind(String collection, MatchOperation matchOperation, SkipOperation skipOperation, LimitOperation limitOperation);
	
	/**
	 * 聚合分页查询出指定类型的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}},
	 * {"$skip":skipOperation},{"$limit":skipOperation})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}},
	 * {"$skip":skipOperation},{"$limit":skipOperation},{$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param matchOperation match操作对象
	 * @param skipOperation 起始分割操作
	 * @param limitOperation 最大行数截取操作
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(MatchOperation matchOperation, SkipOperation skipOperation, LimitOperation limitOperation, Class<R> resultClass);
	
	/**
	 * 在目标集合中聚合分页查询出指定类型的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}},
	 * {"$skip":skipOperation},{"$limit":skipOperation})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{matchOperation}},
	 * {"$skip":skipOperation},{"$limit":skipOperation},{$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param matchOperation match操作对象
	 * @param skipOperation 起始分割操作
	 * @param limitOperation 最大行数截取操作
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(String collection, MatchOperation matchOperation, SkipOperation skipOperation, LimitOperation limitOperation, Class<R> resultClass);
	
}
