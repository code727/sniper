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

package org.workin.nosql.mongodb.spring;

import java.util.Map;

import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * Spring MongoDB分组接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SpringMongoGroup<T> {
	
	/**
	 * 按指定的方式进行分组<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupBy 分组方式
	 * @return
	 */
	public GroupByResults<T> group(GroupBy groupBy);
	
	/**
	 * 在目标集合中按指定的方式进行分组<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param groupBy 分组方式
	 * @return
	 */
	public GroupByResults<T> group(String collection, GroupBy groupBy);
	
	/**
	 * 按指定的方式进行分组，并以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupBy 分组方式
	 * @param resultClass 分组结果数据类型
	 * @return
	 */
	public <R> GroupByResults<R> group(GroupBy groupBy, Class<R> resultClass);
	
	/**
	 * 在目标集合中按指定的方式进行分组，并以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param groupBy 分组方式
	 * @param resultClass 分组结果数据类型
	 * @return
	 */
	public <R> GroupByResults<R> group(String collection, GroupBy groupBy, Class<R> resultClass);
	
	/**
	 * 根据属性查询出文档后按指定的方式进行分组<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{"propertyKey":propertyValue}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @param groupBy 分组方式
	 * @return
	 */
	public GroupByResults<T> group(String propertyName, Object propertyValue, GroupBy groupBy);
	
	/**
	 * 在目标集合中根据属性查询出文档后按指定的方式进行分组<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{"propertyKey":propertyValue}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @param groupBy 分组方式
	 * @return
	 */
	public GroupByResults<T> group(String collection, String propertyName,
			Object propertyValue, GroupBy groupBy);
	
	/**
	 * 根据属性查询出文档后按指定的方式进行分组，并以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{"propertyKey":propertyValue}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @param groupBy 分组方式
	 * @param resultClass 分组结果数据类型
	 * @return
	 */
	public <R> GroupByResults<R> group(String propertyName,
			Object propertyValue, GroupBy groupBy, Class<R> resultClass);
	
	/**
	 * 在目标集合中根据属性查询出文档后按指定的方式进行分组，并以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{"$and":[{
	 * "propertyKey1":propertyValue1,"propertyKey2":propertyValue2,"propertyKeyN":propertyValueN}]}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param collection 目标集合
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @param groupBy 分组方式
	 * @param resultClass 分组结果数据类型
	 * @return
	 */
	public <R> GroupByResults<R> group(String collection, String propertyName,
			Object propertyValue, GroupBy groupBy, Class<R> resultClass);
	
	/**
	 * 根据属性组查询出文档后按指定的方式进行分组<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{"$and":[{
	 * "propertyKey1":propertyValue1,"propertyKey2":propertyValue2,"propertyKeyN":propertyValueN}]}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param properties 属性组
	 * @param groupBy 分组方式
	 * @return
	 */
	public GroupByResults<T> group(Map<String, ?> properties, GroupBy groupBy);
	
	/**
	 * 在目标集合中根据属性组查询出文档后按指定的方式进行分组<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{"$and":[{
	 * "propertyKey1":propertyValue1,"propertyKey2":propertyValue2,"propertyKeyN":propertyValueN}]}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param properties 属性组
	 * @param groupBy 分组方式
	 * @return
	 */
	public GroupByResults<T> group(String collection,
			Map<String, ?> properties, GroupBy groupBy);
		
	/**
	 * 根据属性组查询出文档后按指定的方式进行分组，并以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{"$and":[{
	 * "propertyKey1":propertyValue1,"propertyKey2":propertyValue2,"propertyKeyN":propertyValueN}]}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param properties 属性组
	 * @param groupBy 分组方式
	 * @param resultClass 分组结果数据类型
	 * @return
	 */
	public <R> GroupByResults<R> group(Map<String, ?> properties,
			GroupBy groupBy, Class<R> resultClass);
	
	/**
	 * 在目标集合中根据属性组查询出文档后按指定的方式进行分组，并以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{"$and":[{
	 * "propertyKey1":propertyValue1,"propertyKey2":propertyValue2,"propertyKeyN":propertyValueN}]}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param properties 属性组
	 * @param groupBy 分组方式
	 * @param resultClass 分组结果数据类型
	 * @return
	 */
	public <R> GroupByResults<R> group(String collection,
			Map<String, ?> properties, GroupBy groupBy, Class<R> resultClass);
	
	/**
	 * 根据条件查询出文档后按指定的方式进行分组<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{criteria}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param criteria 查询条件
	 * @param groupBy 分组方式
	 * @return
	 */
	public GroupByResults<T> group(Criteria criteria, GroupBy groupBy);
	
	/**
	 * 在目标集合中根据条件查询出文档后按指定的方式进行分组<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{criteria}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param criteria 查询条件
	 * @param groupBy 分组方式
	 * @return
	 */
	public GroupByResults<T> group(String collection, Criteria criteria, GroupBy groupBy);
	
	/**
	 * 根据条件查询出文档后按指定的方式进行分组，并以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{criteria}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param criteria 查询条件
	 * @param groupBy 分组方式
	 * @param resultClass 分组结果数据类型
	 * @return
	 */
	public <R> GroupByResults<R> group(Criteria criteria, GroupBy groupBy, Class<R> resultClass);
	
	/**
	 * 在目标集合中根据条件查询出文档后按指定的方式进行分组，并以指定的数据类型返回结果<P>
	 * 实际执行的语句为:db.collection.group({groupBy:groupBy,"cond":{criteria}});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @param criteria 查询条件
	 * @param groupBy 分组方式
	 * @param resultClass 分组结果数据类型
	 * @return
	 */
	public <R> GroupByResults<R> group(String collection, Criteria criteria, GroupBy groupBy, Class<R> resultClass);
	
}
