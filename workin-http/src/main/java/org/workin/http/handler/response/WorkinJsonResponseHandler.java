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


import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.workin.beans.mapper.MapToBeanMapper;
import org.workin.commons.response.BaseFullResponse;
import org.workin.commons.response.DataResponse;
import org.workin.commons.response.MessageResponse;
import org.workin.commons.response.Response;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.ClassUtils;
import org.workin.commons.util.ReflectionUtils;
import org.workin.commons.util.StringUtils;
import org.workin.serialization.json.JsonSerializer;

/**
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class WorkinJsonResponseHandler<T extends Response> extends JsonResponseHandler<T> {
	
	/** 数据Bean类型全路径限定名 */
	private String dataClass;
	
	/** 数据Bean类型 */
	private Class<?> dataType;
	
	public WorkinJsonResponseHandler() throws Exception {
		this((JsonSerializer) null);
	}
	
	public WorkinJsonResponseHandler(String typeClass) throws Exception {
		this(typeClass, null);
	}
	
	public WorkinJsonResponseHandler(Class<T> type) {
		this(type, null);
	}
	
	public WorkinJsonResponseHandler(JsonSerializer jsonSerializer) throws Exception {
		this(BaseFullResponse.class.getName(), jsonSerializer);
	}
	
	@SuppressWarnings("unchecked")
	public WorkinJsonResponseHandler(String typeClass, JsonSerializer jsonSerializer) throws Exception {
		this((Class<T>) Class.forName(typeClass), jsonSerializer);
	}
	
	public WorkinJsonResponseHandler(Class<T> type, JsonSerializer jsonSerializer) {
		super(type, jsonSerializer);
		
		AssertUtils.assertTrue(ClassUtils.isSubClass(type, Response.class),
				"Json type [" + type.getName() + "] must be sub class of [" + Response.class.getName() + "]");
	}
	
	public String getDataClass() {
		return dataClass;
	}

	public void setDataClass(String dataClass) throws Exception {
		if (StringUtils.isNotBlank(dataClass)) {
			this.dataType = Class.forName(dataClass);
			this.dataClass = dataClass;
		} else {
			this.dataType = null;
			this.dataClass = null;
		}
	}

	public Class<?> getDataType() {
		return dataType;
	}

	public void setDataType(Class<?> dataBeanType) {
		if (dataBeanType != null) {
			this.dataType = dataBeanType;
			this.dataClass = dataBeanType.getName();
		} else {
			this.dataType = null;
			this.dataClass = null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Override
	public T handleResponse(String json) throws ClientProtocolException, IOException {
		T result = super.handleResponse(json);
		
		if (dataType != null && result instanceof DataResponse) {
			Object data = ((DataResponse<Object>) result).getData();
			if (data instanceof Map) {
				
				try {
					DataResponse<Object> dataResponse = (DataResponse<Object>) ReflectionUtils.newInstance(result.getClass());
					dataResponse.setCode(result.getCode());
					
					if (dataResponse instanceof MessageResponse && result instanceof MessageResponse) {
						((MessageResponse)dataResponse).setMessage(((MessageResponse)result).getMessage());
					}
					
					dataResponse.setData(new MapToBeanMapper(dataType).mapping((Map) data));
					return (T) dataResponse;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} 
		
		return result;
	}
	
	
	
}
