/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016年1月5日
 */

package org.workin.http.httpclient.v4.handler.request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NetUtils;
import org.workin.http.HttpForm;

/**
 * 请求处理器默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefualtRequestHandler implements RequestHandler {

	@Override
	public void handle(HttpEntityEnclosingRequestBase httpRequest, String url, HttpForm form) throws Exception {
		String queryString = NetUtils.getQueryString(url);
		ContentType contentType = ContentType.create(form.getMimeType(), form.getEncoding());
		httpRequest.setEntity(new StringEntity(queryString, contentType));
	}
	
	/**
	 * 根据url中的查询字符串构建NameValuePair对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @return
	 */
	protected List<NameValuePair> buildeNameValuePairByQueryString(String url) {
		Map<String, String> parameterMap = NetUtils.getParameterMap(url);
		List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
		if (MapUtils.isNotEmpty(parameterMap)) {
			Iterator<Entry<String, String>> iterator = parameterMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> parameter = iterator.next();
				nameValueList.add(new BasicNameValuePair(parameter.getKey(), parameter.getValue()));
			}
		}
		return nameValueList;
	}

}
