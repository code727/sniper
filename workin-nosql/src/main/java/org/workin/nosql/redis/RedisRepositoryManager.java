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
 * Create Date : 2015-3-26
 */

package org.workin.nosql.redis;

import java.util.Map;

/**
 * @description Redis库管理接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisRepositoryManager {
	
	/**
	 * @description 设置库配置映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param repositories
	 */
	public void setRepositories(Map<Integer, RedisRepository> repositories);
	
	/**
	 * @description 获取所有库的配置映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<Integer, RedisRepository> getRepositories();
	
	/**
	 * @description 根据键获取映射集合里的某个库配置项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @return
	 */
	public RedisRepository getRepository(int index);

}
