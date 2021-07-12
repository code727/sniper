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
 * Create Date : 2016年8月27日
 */

package org.sniper.orm.jpa.dao.v1;

import org.sniper.beans.GenericBean;
import org.sniper.commons.util.ClassUtils;

/**
 * JPA标准的DAO支持抽象类
 * @author  Daniele
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public abstract class JpaDaoSupport<T> extends
	org.springframework.orm.jpa.support.JpaDaoSupport implements GenericBean<T> {
		
	/** 当前DAO所关联的实体类型 */
	private Class<T> targetType;
	
	@Override
	public Class<T> getTargetType() {
		return targetType;
	}

	@Override
	public void setTargetType(Class<T> targetType) {
		this.targetType = targetType;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initDao() throws Exception {
		if (this.targetType == null)
			this.targetType = (Class<T>) ClassUtils.getSuperclassGenricType(getClass());
	}

}
