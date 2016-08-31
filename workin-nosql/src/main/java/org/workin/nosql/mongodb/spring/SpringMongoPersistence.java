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

package org.workin.nosql.mongodb.spring;

import java.io.Serializable;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.workin.nosql.mongodb.dao.MongoPersistence;

import com.mongodb.WriteResult;

/**
 * Spring MongoDB持久化接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SpringMongoPersistence<T, PK extends Serializable> extends
		MongoPersistence<T, PK> {
	
	/**
	 * 更新满足查询条件的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询条件
	 * @param update 更新操作和数据
	 * @return
	 */
	public WriteResult update(Query query, Update update);
	
	/**
	 * 更新满足查询条件的实体对象到目标集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询条件
	 * @param update 更新操作和数据
	 * @param collection 目标集合
	 * @return
	 */
	public WriteResult update(Query query, Update update, String collection);
	
	/**
	 * 批量更新满足查询条件的实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询条件
	 * @param update 更新操作和数据
	 * @return
	 */
	public WriteResult batchUpdate(Query query, Update update);
	
	/**
	 * 批量更新满足查询条件的实体对象到目标集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询条件
	 * @param update 更新操作和数据
	 * @param collection 目标集合名称
	 * @return
	 */
	public WriteResult batchUpdate(Query query, Update update, String collection);
	
	/**
	 * 更新满足查询条件或插入未满足查询条件的实体对象</br> 
	 * 				1）query如果查找到符合条件的行，则修改这些行</br>
	 *              2）query如果未查找到符合条件的行，则连同update中的数据键值组合成新的一行插入到集合中</br>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询条件
	 * @param update 更新/插入操作和数据
	 * @return
	 */
	public WriteResult upsert(Query query, Update update);
	
	/**
	 * 更新满足查询条件或插入未满足查询条件的实体对象到目标集合中</br>
	 * 				1）query如果查找到符合条件的行，则修改这些行</br>
	 *              2）query如果未查找到符合条件的行，则连同update中的数据键值组合成新的一行插入到集合中</br>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询条件
	 * @param update 更新/插入操作和数据
	 * @param collection 目标集合名称
	 * @return
	 */
	public WriteResult upsert(Query query, Update update, String collection);
	
	/**
	 * 更新满足查询条件的实体对象，并且返回操作前的实体数据对象</br>
	 * 				执行的语句为</br>
	 *             		db.test.findAndModify({</br>
     *                 		"query":{查询},</br>
     *                  	"update":{更新}</br>
     *                 	})</br>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询条件
	 * @param update 更新操作和数据
	 * @return
	 */
	public T findAndModify(Query query, Update update);
	
	/**
	 * 在目标集合中更新/删除满足查询条件的实体对象，并且返回操作前的实体数据对象</br>
	 * 				执行的语句为</br>
	 *             		db.test.findAndModify({</br>
     *                 		"query":{查询},</br>
     *                  	"update":{更新}</br>
     *                 	})</br>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询条件
	 * @param update 更新操作和数据
	 * @param collection 目标集合名称
	 * @return
	 */
	public T findAndModify(Query query, Update update, String collection);
	
	/**
	 * 删除满足查询条件的实体对象，并且返回操作前的实体数据对象
	 * 				执行的语句为</br>
	 *             		db.test.findAndModify({</br>
     *                 		"query":{查询},</br>
     *                  	"remove":true</br>
     *                 	})</br>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询条件
	 * @return
	 */
	public T findAndRemove(Query query);
	
	/**
	 * 在目标集合中删除满足查询条件的实体对象，并且返回操作前的实体数据对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询条件
	 * @param collection 目标集合名称
	 * @return
	 */
	public T findAndRemove(Query query, String collection);

}
