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
 * Create Date : 2017-6-2
 */

package org.sniper.beans.mapper;

import java.util.Set;

import org.sniper.beans.GenericBean;

/**
 * Java Bean映射器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface BeanMapper<S> extends ConfigurableMapper, GenericBean<S> {
	
	/**
	 * 将源对象映射成一个全局类型的JavaBean
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param source 源对象
	 * @return
	 * @throws Exception
	 */
	public <T> T mapping(S source) throws Exception;
	
	/**
	 * 将源对象映射成一个指定目标类型的JavaBean
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param source 源对象
	 * @param targetType 目标JavaBean类型
	 * @return
	 * @throws Exception
	 */
	public <T> T mapping(S source, Class<T> targetType) throws Exception;
	
	/**
	 * 将源对象按照指定规则映射成一个全局类型的JavaBean
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param source 源对象
	 * @param mapperRules 映射规则集
	 * @return
	 * @throws Exception
	 */
	public <T> T mapping(S source, Set<MapperRule> mapperRules) throws Exception;
	
	/**
	 *  将源对象按照指定规则映射成一个指定目标类型的JavaBean
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param source 源对象
	 * @param mapperRules 映射规则集
	 * @param targetType 目标JavaBean类型
	 * @return
	 * @throws Exception
	 */
	public <T> T mapping(S source, Set<MapperRule> mapperRules, Class<T> targetType) throws Exception;

}
