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

/**
 * 可对新增/修改审计的实体接口
 * @author  Daniele
 * @version 1.0
 */
public interface CuAuditable extends Cuable {
	
	public static final String CREATE_BY = "createBy";
	public static final String UPDATE_BY = "updateBy";
	
	/**
	 * 获取创建者名称
	 * @author Daniele 
	 * @return
	 */
	public String getCreateBy();

	/**
	 * 设置创建者名称
	 * @author Daniele 
	 * @param createBy
	 */
	public void setCreateBy(String createBy);

	/**
	 * 获取更新者名称
	 * @author Daniele 
	 * @return
	 */
	public String getUpdateBy();

	/**
	 * 设置更新者名称
	 * @author Daniele 
	 * @param updateBy
	 */
	public void setUpdateBy(String updateBy);

}
