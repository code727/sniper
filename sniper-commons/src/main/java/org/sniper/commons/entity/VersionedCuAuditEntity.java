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
 * Create Date : 2015-1-27
 */

package org.sniper.commons.entity;

import org.sniper.commons.entity.Versioned;

/**
 * 版本化新增/修改审核实体抽象类
 * @author Daniele
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class VersionedCuAuditEntity extends CuAuditEntity implements Versioned {
		
	/** 版本号 */
	private long version;

	@Override
	public long getVersion() {
		return this.version;
	}

	@Override
	public void setVersion(long version) {
		this.version = version;
	}

}
