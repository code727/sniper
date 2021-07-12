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
 * Create Date : 2015-2-3
 */

package org.sniper.orm.hibernate.dao;

import java.io.Serializable;

import org.sniper.beans.GenericBean;
import org.sniper.persistence.FilterQuery;
import org.sniper.orm.jpa.dao.JpaNamedQuery;
import org.sniper.orm.jpa.dao.JpaNativePersistence;
import org.sniper.orm.jpa.dao.JpaNativeQuery;

/**
 * Hibernate持久化数据访问接口
 * @author  Daniele
 * @version 1.0
 */
public interface HibernateDao<T, Pk extends Serializable> extends
		GenericBean<T>, HibernatePersistence<T, Pk>, JpaNativePersistence,
		HibernateQuery<T, Pk>, JpaNamedQuery<T>, JpaNativeQuery<T>,
		HibernateCriteriaQuery<T>, HibernateExampleQuery<T>, FilterQuery<T> {
		
}
