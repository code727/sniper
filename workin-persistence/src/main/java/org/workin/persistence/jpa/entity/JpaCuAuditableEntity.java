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
 * Create Date : 2015-12-15
 */

package org.workin.persistence.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.workin.commons.entity.CuAuditableEntity;
import org.workin.persistence.CuAuditEventListener;

/**
 * JPA可进行新增修改审核的实体抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@MappedSuperclass
@EntityListeners(value = { CuAuditEventListener.class })
public abstract class JpaCuAuditableEntity extends CuAuditableEntity {
	
	@Column(length = 30, updatable = false)
	public Date getCreateTime() {
		return super.getCreateTime();
	}
	
	@Column(length = 30, insertable = false)
	public Date getUpdateTime() {
		return super.getUpdateTime();
	}
	
	@Column(length = 32, updatable = false)
	public String getCreateBy() {
		return super.getCreateBy();
	}
	
	@Column(length = 32, updatable = false)
	public String getUpdateBy() {
		return super.getUpdateBy();
	}
	
}
