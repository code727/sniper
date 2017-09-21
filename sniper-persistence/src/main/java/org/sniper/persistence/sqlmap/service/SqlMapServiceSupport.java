/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-3-21
 */

package org.sniper.persistence.sqlmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.FileUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.persistence.sqlmap.SqlMapOperations;
import org.sniper.persistence.sqlmap.dao.SqlMapDao;
import org.sniper.spring.beans.CheckableInitializingBeanAdapter;

/**
 * SQL映射持久化服务支持类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SqlMapServiceSupport<T> extends CheckableInitializingBeanAdapter
		implements SqlMapBeanService<T>, SqlMapOperations<T> {
	
	@Autowired
	protected SqlMapDao<T> sqlMapDao;
	
	/** 当前Service所关联的目标实体类型 */
	private Class<T> targetType;
	
	/** 是否自动构建命名空间 */
	private boolean autoBuildNamespace = true;
	
	/** 命名空间 */
	protected String namespace;
	
	@Override
	public void setSqlMapDao(SqlMapDao<T> sqlMapDao) {
		this.sqlMapDao = sqlMapDao;
	}

	@Override
	public SqlMapDao<T> getSqlMapDao() {
		return this.sqlMapDao;
	}
	
	@Override
	public Class<T> getTargetType() {
		return targetType;
	}

	@Override
	public void setTargetType(Class<T> targetType) {
		this.targetType = targetType;
	}

	@Override
	public void setAutoBuildNamespace(boolean autoBuildNamespace) {
		this.autoBuildNamespace = autoBuildNamespace;
	}

	@Override
	public boolean isAutoBuildNamespace() {
		return autoBuildNamespace;
	}

	@Override
	public void setNamespace(String namespace) {
		this.namespace = StringUtils.safeString(namespace);
	}

	@Override
	public String getNamespace() {
		return namespace;
	}
	
	@Override
	protected void checkProperties() {
		if (this.sqlMapDao == null)
			throw new IllegalArgumentException("Property 'sqlMapDao' is required");
	}
	
	@Override
	protected void init() throws Exception {
		initTargetType();
		initNamespace();
	}
	
	@SuppressWarnings("unchecked")
	protected void initTargetType() {
		if (this.targetType == null)
			this.targetType = ((Class<T>) ClassUtils.getSuperClassGenricType(getClass()));
	}
	
	/**
	 * 初始化命名空间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void initNamespace() {
		/* 如果DAO设置有命名空间，则以DAO的为准 */
		if (StringUtils.isNotBlank(sqlMapDao.getNamespace())) {
			namespace = "";
			return;
		}
		
		/* 在需要自动构建命名空间且没有手动设置命名空间的情况下，
		 * 当前SqlMapSerivce中的命名空间为非Object实体对象类型的名称 */
		if (autoBuildNamespace && StringUtils.isBlank(namespace) && targetType != Object.class) 
			namespace = targetType.getName() + FileUtils.EXTENSION_SEPERATOR;
		else {
			if (StringUtils.isNotBlank(namespace) && !namespace.endsWith(FileUtils.EXTENSION_SEPERATOR))
				namespace += FileUtils.EXTENSION_SEPERATOR;
		}
	}
	
}
