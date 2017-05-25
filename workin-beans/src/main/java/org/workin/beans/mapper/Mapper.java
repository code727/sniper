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
 * Create Date : 2015-11-13
 */

package org.workin.beans.mapper;

import java.util.Set;

/**
 * 参数映射器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Mapper<S, T> {
	
	/**
	 * 设置映射器规则集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mapperRules
	 */
	public void setMapperRules(Set<MapperRule> mapperRules);
	
	/**
	 * 获取映射器规则集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Set<MapperRule> getMapperRules();
	
	/**
	 * 将指定的源对象进行映射处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public T mapping(S source) throws Exception;

}
