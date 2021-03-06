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
 * 可新增/更新的实体接口
 * @author  Daniele
 * @version 1.0
 */
public interface Cuable {
	
	public static final String CREATE_TIME = "createTime";
	public static final String UPDATE_TIME = "updateTime";
	
	/**
	 * 获取创建时间
	 * @author Daniele 
	 * @return
	 */
	public Date getCreateTime();

	/**
	 * 设置创建时间
	 * @author Daniele 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime);
	
	/**
	 * 获取更新时间
	 * @author Daniele 
	 * @return
	 */
	public Date getUpdateTime();

	/**
	 * 设置更新时间
	 * @author Daniele 
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime);

}
