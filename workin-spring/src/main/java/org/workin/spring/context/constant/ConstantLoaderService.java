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
 * Create Date : 2015-7-1
 */

package org.workin.spring.context.constant;

import java.util.Map;

/**
 * @description 应用常量配置加载服务接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ConstantLoaderService {
	
	/**
	 * @description 预加载配置项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<Object, Object> preloading();
	
	/**
	 * @description 增量加载到指定的常量配置映射集中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param constantMap
	 */
	public void incrementalLoading(Map<Object, Object> constantMap);

}
