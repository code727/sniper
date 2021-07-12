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

import java.io.Serializable;

/**
 * 主键ID实体类
 * @author  Daniele
 * @version 1.0
 */
@SuppressWarnings("serial")
public class IdEntity<PK extends Serializable> implements Idable<PK>, Deletable {
	
	/** 主键ID */
	private PK id;
	
	/** "是否已删除"状态标识(0:未删除,1:已删除)*/
	private int deleted;

	@Override
	public PK getId() {
		return id;
	}

	@Override
	public void setId(PK id) {
		this.id = id;
	}

	@Override
	public int getDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
}
