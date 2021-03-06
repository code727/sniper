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
 * Create Date : 2017-5-25
 */

package org.sniper.beans;

/**
 * 类型化接口
 * @author  Daniele
 * @version 1.0
 */
public interface Typed {
	
	/**
	 * 设置目标类型
	 * @author Daniele 
	 * @param type
	 */
	public void setTargetType(Class<?> targetType);
	
	/**
	 * 获取目标类型
	 * @author Daniele 
	 * @return
	 */
	public Class<?> getTargetType();
		
}
