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

package org.workin.http.handler.response.typed;


import org.workin.commons.response.DataResponse;
import org.workin.commons.response.MessageResponse;
import org.workin.commons.util.ReflectionUtils;

/**
 * Workin JSON响应处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class WorkinJsonResponseHandler extends AbstractJsonNestedResponseHandler {
		
	/**
	 * 实现父类方法，主要是解决当父类处理的响应结果为一个DataResponse对象时，其内部的data值转换问题
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param response
	 * @param nestedMapperRules
	 * @param nestedType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T doResponse(T response, Class<?> nestedType) throws Exception {
		
		if (nestedType != null && response instanceof DataResponse) {
			Object data = ((DataResponse<Object>) response).getData();
			
			//  data值的类型与指定的嵌套类型不一致时，需转换成嵌套类型
			if (data != null && !data.getClass().equals(nestedType)) {
				DataResponse<Object> dataResponse = (DataResponse<Object>) ReflectionUtils.newInstance(response.getClass());
				dataResponse.setCode(((DataResponse<Object>) response).getCode());
				
				if (dataResponse instanceof MessageResponse && response instanceof MessageResponse)
					((MessageResponse) dataResponse).setMessage(((MessageResponse) response).getMessage());
				
				dataResponse.setData(typedSerializer.deserialize(typedSerializer.serialize(data), nestedType));
				return (T) dataResponse;
			}
		}
		
		return response;
	}

}
