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

package org.sniper.nosql.mongodb.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * MongoDB聚合查询接口
 * @author  Daniele
 * @version 1.0
 */
public interface MongoAggregateQuery<T, PK extends Serializable> {
	
	/**
	 * 根据主键聚合查询数据对象<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"_id":id}})
	 * @author Daniele 
	 * @param id 主键ID
	 * @return
	 */
	public T aggregateFindById(PK id);
	
	/**
	 * 在目标集合中根据主键聚合查询数据对象<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"_id":id}})
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param id 主键ID
	 * @return
	 */
	public T aggregateFindById(String collection, PK id);
	
	/**
	 * 根据主键聚合查询出指定类型的数据对象<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"_id":id}})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"_id":id}},{$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}})
	 * @author Daniele
	 * @param id 主键ID
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> R aggregateFindById(PK id, Class<R> resultClass);
	
	/**
	 * 在目标集合中根据主键聚合查询出指定类型的数据对象 <P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"_id":id}})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"_id":id}},{$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}})
	 * @author Daniele
	 * @param collection 目标集合
	 * @param id 主键ID
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> R aggregateFindById(String collection, PK id, Class<R> resultClass);
	
	/**
	 * 根据属性聚合查询出满足条件的唯一记录<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}})
	 * @author Daniele 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return
	 */
	public T aggregateFindOne(String propertyName, Object propertyValue);
	
	/**
	 * 在目标集合中根据属性聚合查询出满足条件的唯一记录<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}})
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return
	 */
	public T aggregateFindOne(String collection, String propertyName, Object propertyValue);
	
	/**
	 * 根据属性聚合查询出满足条件的唯一指定类型的数据对象<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"propertyKey":propertyValue}})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"propertyKey":propertyValue}},{$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,
	 * "beanPropertyKeyNameN":1}})
	 * @author Daniele 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> R aggregateFindOne(String propertyName, Object propertyValue, Class<R> resultClass);
	
	/**
	 * 在目标集合中根据属性聚合查询出满足条件的唯一指定类型的数据对象<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"propertyKey":propertyValue}})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"propertyKey":propertyValue}},{$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,
	 * "beanPropertyKeyNameN":1}})
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> R aggregateFindOne(String collection, String propertyName, Object propertyValue, Class<R> resultClass);
	
	/**
	 * 根据属性映射组聚合查询出满足条件的唯一记录<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}})
	 * @author Daniele 
	 * @param propertyMap 属性映射组
	 * @return
	 */
	public T aggregateFindOne(Map<String, ?> propertyMap);
	
	/**
	 * 在目标集合中根据属性映射组聚合查询出满足条件的唯一记录<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}})
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyMap 属性映射组
	 * @return
	 */
	public T aggregateFindOne(String collection, Map<String, ?> propertyMap);
	
	/**
	 * 根据属性映射组聚合查询出满足条件的唯一指定类型的数据对象<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"$and":[{"propertyKey1":propertyValue1},{"propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"$and":[{"propertyKey1":propertyValue1},{"propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}},
	 * {$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}})
	 * @author Daniele
	 * @param propertyMap 属性映射组
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> R aggregateFindOne(Map<String, ?> propertyMap, Class<R> resultClass);
	
	/**
	 * 在目标集合中根据属性映射组聚合查询出满足条件的唯一指定类型的数据对象<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"$and":[{"propertyKey1":propertyValue1},{"propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}})
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({
	 * "$match":{"$and":[{"propertyKey1":propertyValue1},{"propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}},
	 * {$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}})
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyMap 属性映射组
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> R aggregateFindOne(String collection, Map<String, ?> propertyMap, Class<R> resultClass);
	
	/**
	 * 聚合查询出所有数据对象<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{}});
	 * @author Daniele 
	 * @return
	 */
	public List<T> aggregateFindAll();
	
	/**
	 * 聚合查询出所有数据对象<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{}});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @return
	 */
	public List<T> aggregateFindAll(String collection);
	
	/**
	 * 聚合查询出指定类型的所有数据对象<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{}});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{}},
	 * {$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author Daniele
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFindAll(Class<R> resultClass);
	
	/**
	 * 在目标集合中聚合查询出指定类型的所有数据对象<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{}});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{}},
	 * {$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param resultClass 返回的数据类型
	 * @return 
	 */
	public <R> List<R> aggregateFindAll(String collection, Class<R> resultClass);
	
	/**
	 * 聚合分页查询出记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{}},{"$skip":start},{"$limit":maxRows});
	 * @author Daniele 
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	public List<T> aggregateFind(int start, int maxRows);
	
	/**
	 * 在目标集合中聚合分页查询出记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{}},{"$skip":start},{"$limit":maxRows});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	public List<T> aggregateFind(String collection, int start, int maxRows);
	
	/**
	 * 聚合分页查询出指定类型的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{}},{"$skip":start},{"$limit":maxRows});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{}},{"$skip":start},{"$limit":maxRows},
	 * {$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author Daniele 
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(int start, int maxRows, Class<R> resultClass);
	
	/**
	 * 在目标集合中聚合分页查询出指定类型的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{}},{"$skip":start},{"$limit":maxRows});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{}},{"$skip":start},{"$limit":maxRows},
	 * {$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(String collection, int start, int maxRows, Class<R> resultClass);
	
	/**
	 * 根据属性聚合查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}});
	 * @author Daniele 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return
	 */
	public List<T> aggregateFind(String propertyName, Object propertyValue);
	
	/**
	 * 在目标集合中根据聚合属性查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return
	 */
	public List<T> aggregateFind(String collection, String propertyName, Object propertyValue);
	
	/**
	 * 根据属性聚合分页查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}},{"$skip":start},{"$limit":maxRows});
	 * @author Daniele 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	public List<T> aggregateFind(String propertyName, Object propertyValue, int start, int maxRows);
	
	/**
	 * 在目标集合中根据属性聚合分页查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}},{"$skip":start},{"$limit":maxRows});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	public List<T> aggregateFind(String collection, String propertyName, Object propertyValue, int start, int maxRows);
	
	/**
	 * 根据属性聚合查询出满足条件的指定类型的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}},
	 * {$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author Daniele 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(String propertyName, Object propertyValue, Class<R> resultClass);
	
	/**
	 * 在目标集合中根据属性聚合查询出满足条件的指定类型的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}},
	 * {$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(String collection, String propertyName, Object propertyValue, Class<R> resultClass);
	
	/**
	 * 根据属性聚合分页查询出满足条件的指定类型的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}},
	 * {"$skip":start},{"$limit":maxRows});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$match":{"propertyKey":propertyValue}},
	 * {"$skip":start},{"$limit":maxRows},{$project:{"beanPropertyKeyName1":1,"beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author Daniele 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(String propertyName, Object propertyValue, int start, int maxRows, Class<R> resultClass);
	
	/**
	 * 在目标集合中根据属性聚合分页查询出满足条件的指定类型的记录列表<P>
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(String collection, String propertyName, Object propertyValue, int start, int maxRows, Class<R> resultClass);
	
	/**
	 * 根据属性映射组聚合查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}})
	 * @author Daniele 
	 * @param propertyMap 属性映射组
	 * @return
	 */
	public List<T> aggregateFind(Map<String, ?> propertyMap);
	
	/**
	 * 在目标集合中根据属性映射组聚合查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}})
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyMap 属性映射组
	 * @return
	 */
	public List<T> aggregateFind(String collection, Map<String, ?> propertyMap);
	
	/**
	 * 根据属性映射组聚合分页查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}},{"$skip":start},{"$limit":maxRows})
	 * @author Daniele 
	 * @param propertyMap 属性映射组
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	public List<T> aggregateFind(Map<String, ?> propertyMap, int start, int maxRows);
	
	/**
	 * 在目标集合中根据属性映射组聚合分页查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.aggregate({"$match":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}},{"$skip":start},{"$limit":maxRows})
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyMap 属性映射组
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	public List<T> aggregateFind(String collection, Map<String, ?> propertyMap, int start, int maxRows);
	
	/**
	 * 根据属性映射组聚合查询出满足条件的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]},{$project:{"beanPropertyKeyName1":1,
	 * "beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author Daniele 
	 * @param propertyMap 属性映射组
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(Map<String, ?> propertyMap, Class<R> resultClass);
	
	/**
	 * 在目标集合中根据属性映射组聚合查询出满足条件的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]},{$project:{"beanPropertyKeyName1":1,
	 * "beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyMap 属性映射组
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(String collection, Map<String, ?> propertyMap, Class<R> resultClass);
	
	/**
	 * 根据属性映射组聚合分页查询出满足条件的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}},{"$skip":start},{"$limit":maxRows});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]},{"$skip":start},{"$limit":maxRows},{$project:{"beanPropertyKeyName1":1,
	 * "beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author Daniele 
	 * @param propertyMap 属性映射组
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(Map<String, ?> propertyMap, int start, int maxRows, Class<R> resultClass);
	
	/**
	 * 在目标集合中根据属性映射组聚合分页查询出满足条件的记录列表<P>
	 * 1)当resultClass与当前泛型类管理的实体类型<T>相等，实际执行的语句为:db.collection.aggregate({"$match":{"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}},{"$skip":start},{"$limit":maxRows});
	 * <P>
	 * 2)当resultClass与当前泛型类管理的实体类型<T>不相等，实际执行的语句为:db.collection.aggregate({"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]},{"$skip":start},{"$limit":maxRows},{$project:{"beanPropertyKeyName1":1,
	 * "beanPropertyKeyName2":1,"beanPropertyKeyNameN":1}});
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param propertyMap 属性映射组
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @param resultClass 返回的数据类型
	 * @return
	 */
	public <R> List<R> aggregateFind(String collection, Map<String, ?> propertyMap, int start, int maxRows, Class<R> resultClass);
	
}
