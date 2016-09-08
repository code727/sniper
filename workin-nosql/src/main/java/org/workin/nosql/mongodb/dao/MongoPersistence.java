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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.mongodb.WriteResult;

/**
 * MongoDB持久化接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface MongoPersistence<T, PK extends Serializable> {
	
	/**
	 * 新增实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void insert(T entity);
	
	/**
	 * 新增实体对象到目标集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 * @param collection
	 */
	public void insert(T entity, String collection);
	
	/**
	 * 批量新增实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entities
	 */
	public void batchInsert(Collection<T> entities);
	
	/**
	 * 批量新增实体对象到目标集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entities
	 * @param collection
	 */
	public void batchInsert(Collection<T> entities, String collection);
		
	/**
	 * 新增/更新实体对象<p>
	 * 1.当entity对象的id在数据库中找不到记录，则新增<p>
	 * 2.当entity对象的id在数据库中找得到记录，则更新<p>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void save(T entity);
	
	/**
	 * 新增/更新实体对象到目标集合中<p>
	 * 1.当entity对象的id在数据库中找不到记录，则新增<p>
	 * 2.当entity对象的id在数据库中找得到记录，则更新<p>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 * @param collection
	 */
	public void save(T entity, String collection);
		
	/**
	 * 删除实体对象    
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 * @return
	 */
	public WriteResult remove(T entity);
	
	/**
	 * 在目标集合中删除实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 * @param collection
	 * @return
	 */
	public WriteResult remove(T entity, String collection);
	
	/**
	 * 删除主键对应的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public WriteResult remove(PK id);
	
	/**
	 * 在目标集合中删除主键对应的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param collection
	 * @return
	 */
	public WriteResult remove(PK id, String collection);
	
	/**
	 * 根据ID删除查询结果集返回的第一条记录，并返回删除前的数据对象<p>
	 * 实际执行的语句为:db.collection.findAndModify({"_id":id,"remove":true})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public T findAndRemove(PK id);
	
	/**
	 * 在目标集合中根据ID删除查询结果集返回的第一条记录，并返回删除前的数据对象<p>
	 * 实际执行的语句为:db.collection.findAndModify({"_id":id,"remove":true})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param collection
	 * @return
	 */
	public T findAndRemove(PK id, String collection);
	
	 /**
	  * 根据属性删除查询结果集返回的第一条记录，并返回删除前的数据对象<p>
	  * 实际执行的语句为:db.collection.findAndModify({"propertyKey":propertyValue,"remove":true})
	  * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	  * @param propertyName 属性名
	  * @param propertyValue 属性值
	  * @return
	  */
	public T findAndRemove(String propertyName, Object propertyValue);
	
	/**
	 * 在目标集合中根据属性删除查询结果集返回的第一条记录，并返回删除前的数据对象<p>
	 * 实际执行的语句为:db.collection.findAndModify({"propertyKey":propertyValue,"remove":true})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @param collection 目标集合
	 * @return
	 */
	public T findAndRemove(String propertyName, Object propertyValue, String collection);
	
	/**
	 * 根据属性映射组删除查询结果集返回的第一条记录，并返回删除前的数据对象<p>
	 * 实际执行的语句为:db.collection.findAndModify({"query":{"$and":[{propertyKey1:
	 * propertyValue1},{propertyKey2:propertyValue2},{propertyKeyN:propertyValueN}]},"remove":true})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param propertyMap 属性映射组
	 * @return
	 */
	public T findAndRemove(Map<String, ?> propertyMap);
	
	/**
	 * 在目标集合中根据属性映射组删除查询结果集返回的第一条记录，并返回删除前的数据对象<p>
	 * 实际执行的语句为:db.collection.findAndModify({"query":{"$and":[{propertyKey1:
	 * propertyValue1},{propertyKey2:propertyValue2},{propertyKeyN:propertyValueN}]},"remove":true})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyMap 属性映射组
	 * @param collection 目标集合
	 * @return
	 */
	public T findAndRemove(Map<String, ?> propertyMap, String collection);
	
	/**
	 * 根据属性删除查询结果集返回的所有记录，并返回删除前的数据对象列表<p>
	 * 实际执行的语句为:db.collection.findAndModify({"propertyKey":propertyValue,"remove":true})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @return
	 */
	public List<T> findAllAndRemove(String propertyName, Object propertyValue);
	
	/**
	 * 在目标集合中根据属性删除查询结果集返回的所有记录，并返回删除前的数据对象列表<p>
	 * 实际执行的语句为:db.collection.findAndModify({"propertyKey":propertyValue,"remove":true})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @param collection 目标集合
	 * @return
	 */
	public List<T> findAllAndRemove(String propertyName, Object propertyValue, String collection);
	
	/**
	 * 根据属性映射组删除查询结果集返回的所有记录，并返回删除前的数据对象列表<p>
	 * 实际执行的语句为:db.collection.findAndModify({"query":{"$and":[{propertyKey1:
	 * propertyValue1},{propertyKey2:propertyValue2},{propertyKeyN:propertyValueN}]},"remove":true})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyMap 属性映射组
	 * @return
	 */
	public List<T> findAllAndRemove(Map<String, ?> propertyMap);
	
	/**
	 * 在目标集合中根据属性映射组删除查询结果集返回的所有记录，并返回删除前的数据对象列表
	 * 实际执行的语句为:db.collection.findAndModify({"query":{"$and":[{propertyKey1:
	 * propertyValue1},{propertyKey2:propertyValue2},{propertyKeyN:propertyValueN}]},"remove":true})
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyMap 属性映射组
	 * @param collection 目标集合
	 * @return
	 */
	public List<T> findAllAndRemove(Map<String, ?> propertyMap, String collection);
	
}
