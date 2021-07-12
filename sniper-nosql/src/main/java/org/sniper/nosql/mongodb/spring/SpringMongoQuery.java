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
 * Create Date : 2016-8-30
 */

package org.sniper.nosql.mongodb.spring;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.sniper.nosql.mongodb.dao.MongoQuery;

/**
 * Spring MongoDB查询接口
 * @author  Daniele
 * @version 1.0
 */
public interface SpringMongoQuery<T, PK extends Serializable> extends
		MongoQuery<T, PK> {
	
	/**
	 * 查询出满足条件的唯一记录<p>
	 * 实际执行的语句为:db.collection.findOne({查询}})
	 * @author Daniele 
	 * @param query 查询条件
	 * @return
	 */
	public T findOne(Query query);
	
	/**
	 * 在目标集合中查询出满足条件的唯一记录<p>
	 * 实际执行的语句为:db.collection.findOne({查询}})
	 * @author Daniele 
	 * @param collection 目标集合
	 * @param query 查询条件
	 * @return
	 */
	public T findOne(String collection, Query query);
	
	/**
	 * 查询出满足条件的记录列表<p>
	 * 实际执行的语句为:db.collection.find({查询}})
	 * @author Daniele 
	 * @param query 查询条件
	 * @return
	 */
	public List<T> find(Query query);
	
	/**
	 * 在目标集合中查询出满足条件的记录列表<p>
	 * 实际执行的语句为:db.collection.find({查询}})
	 * @author Daniele 
	 * @param query 查询条件
	 * @param collection 目标集合
	 * @return
	 */
	public List<T> find(String collection, Query query);

}
