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
 * Create Date : 2017-6-6
 */

package org.workin.http.handler.response;

import java.util.Set;

import org.workin.beans.mapper.MapToBeanMapper;
import org.workin.beans.mapper.MapperRule;

/**
 * 类型化嵌套响应处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractTypedNestedResponseHandler extends AbstractTypedResponseHandler
		implements TypedNestedResponseHandler {
	
	/** Map对象与Java Bean对象之间的映射转换器，
	 *  主要处理最终结果的内部嵌套类型为Map时转换成JavaBean的问题 */
	private MapToBeanMapper<Object> mapToBeanMapper = new MapToBeanMapper<Object>();
	
	/** 类型化响应处理器接口 */
	protected final TypedResponseHandler typedResponseHandler;
	
	protected AbstractTypedNestedResponseHandler() {
		this(null);
	}
	
	protected AbstractTypedNestedResponseHandler(TypedResponseHandler typedResponseHandler) {
		if (typedResponseHandler != null)
			this.typedResponseHandler = typedResponseHandler;
		else
			this.typedResponseHandler = buildDefaultTypedResponseHandler();
	}
	
	public MapToBeanMapper<Object> getMapToBeanMapper() {
		return mapToBeanMapper;
	}

	public void setMapToBeanMapper(MapToBeanMapper<Object> mapToBeanMapper) {
		this.mapToBeanMapper = mapToBeanMapper;
	}
	
	@Override
	public <T> T handleResponse(String response, Class<T> type) throws Exception {
		return handleResponse(response, type, null);
	}

	@Override
	public <T> T handleResponse(String response, Set<MapperRule> nestedMapperRules, Class<?> nestedType) throws Exception {
		return handleResponse(response, null, nestedMapperRules, nestedType);
	}

	@Override
	public <T> T handleResponse(String response, Class<T> type, Class<?> nestedType) throws Exception {
		return handleResponse(response, type, null, nestedType);
	}

	@Override
	public <T> T handleResponse(String response, Class<T> type, Set<MapperRule> nestedMapperRules, Class<?> nestedType) throws Exception {
		T responseEntity = typedResponseHandler.handleResponse(response, type);
		return doResponse(responseEntity, nestedMapperRules, nestedType);
	}
	
	/**
	 *  处理响应实体后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param responseEntity
	 * @param nestedMapperRules 响应实体内部嵌套映射规则
	 * @param nestedType 响应实体内部嵌套类型
	 * @return
	 * @throws Exception
	 */
	protected <T> T doResponse(T responseEntity, Set<MapperRule> nestedMapperRules, Class<?> nestedType) throws Exception {
		return responseEntity;
	}
	
	/**
	 * 构建默认的类型化响应处理器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected abstract TypedResponseHandler buildDefaultTypedResponseHandler();
	
}
