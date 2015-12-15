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
 * Create Date : 2015年12月15日
 */

package org.workin.persistence.jpa.entity;

import javax.persistence.MappedSuperclass;

import org.workin.commons.entity.string.StringIdable;
import org.workin.persistence.jpa.entity.JpaCuAuditableEntity;

/**
 * @description 字符串主键类型的可进行新增修改审核的实体抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class StringCuAuditableEntity extends JpaCuAuditableEntity implements StringIdable {
	
	/** 主键ID */
	private String id;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}
