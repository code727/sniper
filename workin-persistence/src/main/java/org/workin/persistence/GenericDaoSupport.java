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
 * Create Date : 2016-8-26
 */

package org.workin.persistence;

import org.springframework.beans.factory.InitializingBean;
import org.workin.commons.util.ClassUtils;

/**
 * @description 泛型DAO支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class GenericDaoSupport<T> implements GenericDao<T>, InitializingBean {
	
	/** 当前DAO所关联的实体类型 */
	private Class<T> entityClass;
	
	@Override
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.entityClass == null)
			this.entityClass = (Class<T>) ClassUtils.getSuperClassGenricType(getClass());
		
	}

}
