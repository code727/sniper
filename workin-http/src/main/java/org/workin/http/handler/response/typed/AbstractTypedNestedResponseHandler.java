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

package org.workin.http.handler.response.typed;

import org.workin.serialization.TypedSerializer;

/**
 * 类型化嵌套响应处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractTypedNestedResponseHandler extends AbstractTypedResponseHandler
		implements TypedNestedResponseHandler {
	
	protected AbstractTypedNestedResponseHandler() {
		super();
	}
	
	protected AbstractTypedNestedResponseHandler(TypedSerializer typedSerializer) {
		super(typedSerializer);
	}
			
	@Override
	public <T> T handleResponse(String response, Class<T> type) throws Exception {
		return handleResponse(response, type, null);
	}

	@Override
	public <T> T handleResponse(String response, Class<T> type, Class<?> nestedType) throws Exception {
		T responseEntity = typedSerializer.deserialize(response, type);
		return doResponse(responseEntity, nestedType);
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
	protected <T> T doResponse(T response, Class<?> nestedType) throws Exception {
		return response;
	}
	
}
