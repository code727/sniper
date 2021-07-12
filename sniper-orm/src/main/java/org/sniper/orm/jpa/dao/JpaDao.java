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
 * Create Date : 2015-2-2
 */

package org.sniper.orm.jpa.dao;

import java.io.Serializable;

import org.sniper.beans.GenericBean;
import org.sniper.persistence.FilterQuery;

/**
 * JPA持久化数据访问接口
 * @author Daniele
 * @version 1.0
 */
public interface JpaDao<T, PK extends Serializable> extends GenericBean<T>,
		JpaPersistence<T, PK>, JpaNativePersistence, JpaQuery<T, PK>,
		JpaNamedQuery<T>, JpaNativeQuery<T>, JpaCriteriaQuery<T>,
		JpaPagingQuery<T>, FilterQuery<T> {
	
}
