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
 * Create Date : 2017-11-17
 */

package org.sniper.commons.entity;

import java.util.Date;

/**
 * 可进行新增/修改的实体抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class CuEntity implements Cuable {
	
	/** 创建时间 */
	private Date createTime;
	
	/** 更新时间 */
	private Date updateTime;

	@Override
	public Date getCreateTime() {
		return this.createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public Date getUpdateTime() {
		return this.updateTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
