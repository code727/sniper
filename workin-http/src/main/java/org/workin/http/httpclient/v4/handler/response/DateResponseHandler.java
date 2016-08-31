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
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.workin.commons.util.DateUtils;
import org.workin.commons.util.StringUtils;

/**
 * Date类型响应处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0j
 */
public class DateResponseHandler extends AbstractResponseHandler<Date> {
	
	/** 日期格式 */
	private String pattern;
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public Date handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		String result = super.doResponse(response);
		if (StringUtils.isNotBlank(result))
			return DateUtils.stringToDate(result, this.pattern);
		
		String defaultValue = super.getDefaultValue();
		return StringUtils.isNotBlank(defaultValue) ? DateUtils.stringToDate(defaultValue, this.pattern) : null;
	}

}
