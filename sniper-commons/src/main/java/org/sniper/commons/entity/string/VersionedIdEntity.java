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

package org.sniper.commons.entity.string;

import org.sniper.commons.entity.Versioned;

/**
 * 字符串主键类型的版本化实体抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class VersionedIdEntity extends IdEntity implements Versioned {
	
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
