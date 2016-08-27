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

import com.mongodb.WriteResult;

/**
 * @description MongoDB持久化接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface MongoPersistence<T, PK extends Serializable> {
	
	/**
	 * @description 保存对象到泛型实体类型对应的集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 */
	public void save(T entity);
	
	/**
	 * @description 保存对象到指定名称的集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entity
	 * @param collection
	 */
	public void save(T entity, String collection);
	
	/**
	 * @description 在泛型实体类型对应的集合中删除指定对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public WriteResult remove(Object object);
	
	/**
	 * @description 在指定名称的集合中删除对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param collection
	 * @return
	 */
	public WriteResult remove(Object object, String collection);
	
	
	
}
