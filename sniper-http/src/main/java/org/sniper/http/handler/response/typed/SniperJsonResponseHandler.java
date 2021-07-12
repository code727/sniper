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
 * Create Date : 2017-5-22
 */

package org.sniper.http.handler.response.typed;


import org.sniper.commons.response.DatamationResponse;
import org.sniper.commons.util.ReflectionUtils;

/**
 * Sniper JSON响应处理器实现类
 * @author  Daniele
 * @version 1.0
 */
public class SniperJsonResponseHandler extends AbstractJsonNestedResponseHandler {
		
	/**
	 * 实现父类方法，主要是解决当父类处理的响应结果为一个DataResponse对象时，其内部的data值转换问题
	 * @author Daniele 
	 * @param response
	 * @param nestedMapperRules
	 * @param nestedType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T doResponse(T response, Class<?> nestedType) throws Exception {
		
		if (nestedType != null && response instanceof DatamationResponse) {
			Object data = ((DatamationResponse<?, ?>) response).getData();
			
			//  data值的类型与指定的嵌套类型不一致时，需转换成嵌套类型
			if (data != null && !data.getClass().equals(nestedType)) {
				DatamationResponse<Object, Object> dataResponse = (DatamationResponse<Object, Object>) 
						ReflectionUtils.newInstance(response.getClass());
				dataResponse.setCode(((DatamationResponse<Object, Object>) response).getCode());				
				dataResponse.setData(typedSerializer.deserialize(typedSerializer.serialize(data), nestedType));
				return (T) dataResponse;
			}
		}
		
		return response;
	}

}
