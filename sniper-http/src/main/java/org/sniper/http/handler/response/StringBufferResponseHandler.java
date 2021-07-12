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
 * Create Date : 2017-6-14
 */
package org.sniper.http.handler.response;

/**
 * StringBuffer响应结果处理器实现类
 * @author  Daniele
 * @version 1.0
 */
public class StringBufferResponseHandler extends StringResponseHandler {
	
	public StringBufferResponseHandler() {
		super();
	}
	
	public StringBufferResponseHandler(boolean allowEmpty) {
		super(allowEmpty);
	}
	
	public StringBufferResponseHandler(String defaultValue) {
		super(defaultValue);
	}
	
	public StringBufferResponseHandler(boolean allowEmpty, String defaultValue) {
		super(allowEmpty, defaultValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T handle(String response) {
		return (T) new StringBuffer(response);
	}
}
