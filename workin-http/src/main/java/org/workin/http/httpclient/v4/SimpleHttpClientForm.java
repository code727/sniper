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
 * Create Date : 2015-7-8
 */

package org.workin.http.httpclient.v4;

import org.apache.http.client.ResponseHandler;
import org.workin.http.SimpleHttpForm;
import org.workin.http.httpclient.v4.handler.request.RequestHandler;

/**
 * @description HttpClient4.x表单实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 * @param <T>
 */
public class SimpleHttpClientForm extends SimpleHttpForm implements HttpClientForm {
	
	/** 当前表单绑定的请求处理器 */
	private RequestHandler requestHandler;
	
	/** 当前表单绑定的响应处理器 */
	private ResponseHandler<?> responseHandler;
	
	@Override
	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public RequestHandler getRequestHandler() {
		return this.requestHandler;
	}

	@Override
	public void setResponseHandler(ResponseHandler<?> responseHandler) {
		this.responseHandler = responseHandler;
	}

	@Override
	public ResponseHandler<?> getResponseHandler() {
		return this.responseHandler;
	}

}
