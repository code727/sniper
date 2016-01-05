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

package org.workin.http.httpclient.v4.handler.response;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.workin.commons.util.StringUtils;

/**
 * @description 整型响应处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class IntegerResponseHandler extends AbstractResponseHandler<Integer> {

	@Override
	public Integer handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		String result = super.doResponse(response);
		if (result != null)
			return new Integer(result);
		
		String defaultValue = super.getDefaultValue();
		return StringUtils.isNotBlank(defaultValue) ? new Integer(defaultValue) : null;
	}

}
