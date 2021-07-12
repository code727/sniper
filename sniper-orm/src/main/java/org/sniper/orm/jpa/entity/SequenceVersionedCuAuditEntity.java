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

package org.sniper.orm.jpa.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.sniper.commons.entity.Versioned;

/**
 * Sequence主键类型版本化的新增修改审核实体抽象类
 * @author  Daniele
 * @version 1.0
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class SequenceVersionedCuAuditEntity extends
		SequenceCuAuditEntity implements Versioned {
	
	/** 版本号 */
	private long version;

	@Version
	public long getVersion() {
		return this.version;
	}

	@Override
	public void setVersion(long version) {
		this.version = version;
	}

}
