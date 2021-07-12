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
 * Create Date : 2015-3-10
 */

package org.sniper.orm.jpa.service;

import java.io.Serializable;

import org.sniper.orm.jpa.dao.JpaDao;

/**
 * JPA对象服务接口
 * @author  Daniele
 * @version 1.0
 */
public interface JpaBeanService<T, PK extends Serializable> {
	
	/**
	 * 设置JAP数据访问接口(DAO)
	 * @author Daniele 
	 * @param jpaDao
	 */
	public void setJpaDao(JpaDao<T, PK> jpaDao);
	
	/**
	 * 获取JAP数据访问接口(DAO)
	 * @author Daniele 
	 * @return
	 */
	public JpaDao<T, PK> getJpaDao();

}
