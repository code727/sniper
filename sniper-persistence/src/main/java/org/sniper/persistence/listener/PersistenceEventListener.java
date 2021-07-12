/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-1-27
 */

package org.sniper.persistence.listener;

/**
 * 持久化事件监听器
 * @author  Daniele
 * @version 1.0
 */
public interface PersistenceEventListener<T> {
	
	/**
	 * 新增实体之前触发onPrePersist方法
	 * @author Daniele 
	 * @param entity
	 */
	public void onPrePersist(T entity);
	
	/**
	 * 更新实体之前触发onPreUpdate方法
	 * @author Daniele 
	 * @param entity
	 */
	public void onPreUpdate(T entity);
	
	/**
	 * 删除实体之前触发onPreRemove方法
	 * @author Daniele 
	 * @param entity
	 */
	public void onPreRemove(T entity); 
	
	/**
	 * 销毁实体之前触发onPreDestroy方法
	 * @author Daniele
	 */
	public void onPreDestroy();

}
