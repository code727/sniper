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
 * Create Date : 2017-5-27
 */

package org.workin.http.handler.response.typed;

import org.workin.commons.util.AssertUtils;
import org.workin.serialization.TypedSerializer;

/**
 * 类型化响应处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractTypedResponseHandler implements TypedResponseHandler {
	
	protected final TypedSerializer typedSerializer;
	
	protected AbstractTypedResponseHandler() {
		this(null);
	}
	
	protected AbstractTypedResponseHandler(TypedSerializer typedSerializer) {
		if (typedSerializer != null)
			this.typedSerializer = typedSerializer;
		else
			this.typedSerializer = buildDefaultTypedSerializer();
		
		AssertUtils.assertNotNull(this.typedSerializer, "Typed serializer must not be null");
	}
	
	/**
	 * 构建默认的类型化序列器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected abstract TypedSerializer buildDefaultTypedSerializer();
	
	@Override
	public <T> T handleResponse(String response) throws Exception {
		return handleResponse(response, null);
	}
	
	@Override
	public <T> T handleResponse(String response, Class<T> type) throws Exception {
		return typedSerializer.deserialize(response, type);
	}
	
}
