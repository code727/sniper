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
 * Create Date : 2017年5月22日
 */

package org.workin.http.handler.response;


import java.util.Map;
import java.util.Set;

import org.workin.beans.mapper.MapperRule;
import org.workin.commons.response.DataResponse;
import org.workin.commons.response.MessageResponse;
import org.workin.commons.util.ClassUtils;
import org.workin.commons.util.ReflectionUtils;

/**
 * Workin JSON响应处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class WorkinJsonResponseHandler extends AbstractTypedNestedResponseHandler {
	
	public WorkinJsonResponseHandler() {
		super();
	}
	
	public WorkinJsonResponseHandler(TypedResponseHandler typedResponseHandler) {
		super(typedResponseHandler);
	}
			
	@Override
	protected TypedResponseHandler buildDefaultTypedResponseHandler() {
		return new JsonResponseHandler();
	}
	
	/**
	 * 重写父类方法，主要是解决当父类处理的响应结果为一个DataResponse对象时，其内部的data值转换问题
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param response
	 * @param nestedMapperRules
	 * @param nestedType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T doResponse(T response, Set<MapperRule> nestedMapperRules, Class<?> nestedType) throws Exception {
		if (nestedType != null && response instanceof DataResponse) {
			Object data = ((DataResponse<Object>) response).getData();
			
			// 目前只处理响应的data值为map而实际要转换成一个JavaBean的情况
			if (data instanceof Map && !ClassUtils.isJavaType(nestedType)) {
				DataResponse<Object> dataResponse = (DataResponse<Object>) ReflectionUtils.newInstance(response.getClass());
				dataResponse.setCode(((DataResponse<Object>) response).getCode());
				
				if (dataResponse instanceof MessageResponse && response instanceof MessageResponse)
					((MessageResponse) dataResponse).setMessage(((MessageResponse) response).getMessage());
				
				dataResponse.setData(getMapToBeanMapper().mapping((Map<String, Object>) data, nestedMapperRules, nestedType));
				return (T) dataResponse;
			}
		}
		
		return response;
	}

}
