/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-8-26
 */

package org.sniper.beans;

/**
 * 泛型Bean接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface GenericBean<T> {
	
	/**
	 * 设置当前泛型类所管理的目标类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param targetType
	 */
	public void setTargetType(Class<T> targetType);
	
	/**
	 * 获取当前泛型类所管理的目标类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Class<T> getTargetType();

}
