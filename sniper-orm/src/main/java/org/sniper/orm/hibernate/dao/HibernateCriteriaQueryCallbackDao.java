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
 * Create Date : 2015-3-12
 */

package org.sniper.orm.hibernate.dao;

/**
 * Hibernate标准查询回调DAO抽象类。</P>
 * 它在HibernateCriteriaQueryCallback的基础上可以设置上层对象(如Service)传入的查询参数，
 * 可以真正完全独立的在DAO中处理属于JPA的业务，而不必混杂在上层对象中来处理。
 * @author  Daniele
 * @version 1.0
 */
public abstract class HibernateCriteriaQueryCallbackDao<P> implements HibernateCriteriaQueryCallback {
	
	/** 查询参数对象 */
	protected P parameter;
	
	/**
	 * 设置查询参数对象
	 * @author Daniele 
	 * @param parameter
	 */
	public void setParameter(P parameter) {
		this.parameter = parameter;
	}
	
	/**
	 * 获取查询参数对象
	 * @author Daniele 
	 * @return
	 */
	public P getParameter() {
		return this.parameter;
	}

}
