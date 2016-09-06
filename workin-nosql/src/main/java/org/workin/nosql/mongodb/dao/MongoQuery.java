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

package org.workin.nosql.mongodb.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * MongoDB查询接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface MongoQuery<T, PK extends Serializable> {
	
	/**
	 * 根据主键查询实体对象<P>
	 * 实际执行的语句为:db.collection.findOne({"_id":id})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id 主键ID
	 * @return
	 */
	public T findById(PK id);
	
	/**
	 * 根据主键查询目标集合中的实体对象<P>
	 * 实际执行的语句为:db.collection.findOne({"_id":id})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id 主键ID
	 * @param collection 目标集合
	 * @return
	 */
	public T findById(PK id, String collection);
	
	/**
	 * 根据属性查询出满足条件的唯一记录<P>
	 * 实际执行的语句为:db.collection.findOne({"propertyKey":propertyValue})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return
	 */
	public T findOne(String propertyName, Object propertyValue);
	
	/**
	 * 在目标集合中根据属性查询出满足条件的唯一记录<P>
	 * 实际执行的语句为:db.collection.findOne({"propertyKey":propertyValue})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param collection 目标集合名称
	 * @return
	 */
	public T findOne(String propertyName, Object propertyValue, String collection);
	
	/**
	 * 根据属性映射组查询出满足条件的唯一记录<P>
	 * 实际执行的语句为:db.collection.findOne({"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param propertyMap 属性映射组
	 * @return
	 */
	public T findOne(Map<String, ?> propertyMap);
	
	/**
	 * 在目标集合中根据属性映射组查询出满足条件的唯一记录<P>
	 * 实际执行的语句为:db.collection.findOne({"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyMap 属性映射组
	 * @param collection 目标集合
	 * @return
	 */
	public T findOne(Map<String, ?> propertyMap, String collection);
		
	/**
	 * 查询出当前集合中所有的数据对象<P>
	 * 实际执行的语句为:db.collection.find({});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<T> findAll();
	
	/**
	 * 在目标集合中查询出当前集合中所有的数据对象<P>
	 * 实际执行的语句为:db.collection.find({});
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection 目标集合
	 * @return
	 */
	public List<T> findAll(String collection);
	
	/**
	 * 从起始位置开始执行查询后，得到最大行数的记录列表<P>
	 * 实际执行的语句为:db.collection.find({}).skip(start).limit(maxRows);
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	public List<T> find(int start, int maxRows);
	
	/**
	 * 在目标集合中从起始位置开始执行查询后，得到最大行数的记录列表<P>
	 * 实际执行的语句为:db.collection.find({}).skip(start).limit(maxRows);
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @param collection 目标集合
	 * @return
	 */
	public List<T> find(int start, int maxRows, String collection);
	
	/**
	 * 根据属性查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.find({"propertyKey":propertyValue})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return
	 */
	public List<T> find(String propertyName, Object propertyValue);
	
	/**
	 * 在目标集合中根据属性查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.find({"propertyKey":propertyValue})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param collection 目标集合
	 * @return
	 */
	public List<T> find(String propertyName, Object propertyValue, String collection);
	
	/**
	 * 从起始位置开始，根据属性查询出满足条件的最大行数记录列表<P>
	 * 实际执行的语句为:db.collection.find({"propertyKey":propertyValue}).skip(start).limit(maxRows)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	public List<T> find(String propertyName, Object propertyValue, int start, int maxRows);
	
	/**
	 * 从起始位置开始，在目标集合中根据属性查询出满足条件的最大行数记录列表<P>
	 * 实际执行的语句为:db.collection.find({"propertyKey":propertyValue}).skip(start).limit(maxRows)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @param collection 目标集合
	 * @return
	 */
	public List<T> find(String propertyName, Object propertyValue, int start, int maxRows, String collection);
	
	/**
	 * 根据属性映射组查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.find({"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyMap 属性映射组
	 * @return
	 */
	public List<T> find(Map<String, ?> propertyMap);
	
	/**
	 * 在目标集合中根据属性映射组查询出满足条件的记录列表<P>
	 * 实际执行的语句为:db.collection.find({"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param propertyMap 属性映射组
	 * @param collection 目标集合
	 * @return
	 */
	public List<T> find(Map<String, ?> propertyMap, String collection);
	
	/**
	 * 从起始位置开始，根据属性映射组查询出满足条件的最大行数记录列表<P>
	 * 实际执行的语句为:db.collection.find({"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}).skip(start).limit(maxRows)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyMap 属性映射组
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @return
	 */
	public List<T> find(Map<String, ?> propertyMap, int start, int maxRows);
	
	/**
	 * 从起始位置开始，在目标集合中根据属性映射组查询出满足条件的最大行数记录列表<P>
	 * 实际执行的语句为:db.collection.find({"$and":[{"propertyKey1":propertyValue1},{
	 * "propertyKey2":propertyValue2},{"propertyKeyN":propertyValueN}]}).skip(start).limit(maxRows)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyMap 属性映射组
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @param collection 目标集合
	 * @return
	 */
	public List<T> find(Map<String, ?> propertyMap, int start, int maxRows, String collection);
	
}
