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
 * Create Date : 2017-10-13
 */

package org.sniper.http.httpclient.v4.handler.multipart;

import java.util.List;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.sniper.commons.MultiValueMap;
import org.sniper.commons.util.MapUtils;

/**
 * org.sniper.commons.MultiValueMap类型的Multipart请求体处理器实现类
 * @author  Daniele
 * @version 1.0
 */
public class SniperMultiValueMapMultipartBodyHandler extends AbstractCompositeMultipartBodyHandler<MultiValueMap<String, Object>> {
	
	public SniperMultiValueMapMultipartBodyHandler() {
		this(null);
	}
	
	public SniperMultiValueMapMultipartBodyHandler(List<MultipartBodyHandler> multipartBodyHandlers) {
		super(multipartBodyHandlers, MultiValueMap.class);
	}

	/**
	 * 重写父类方法，兼容org.springframework.http.converter.FormHttpMessageConverter类中isMultipart方法实现逻辑
	 * @author Daniele 
	 * @param requestBody
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean support(Object requestBody) {
		if (requestBody instanceof MultiValueMap) {
			MultiValueMap<String, ?> map = (MultiValueMap<String, ?>) requestBody;
			return !MapUtils.hasTypeValue(map, String.class);
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addBodyPart(MultipartEntityBuilder builder, String name, Object requestBody, ContentType contentType) throws Exception {
		MultipartBodyHandler multipartBodyHandler;
		for (Map.Entry<String, List<Object>> entry : ((MultiValueMap<String, Object>) requestBody).entrySet()) {
			name = entry.getKey();
			for (Object body : entry.getValue()) {
				multipartBodyHandler = getMatchedMultipartBodyHandler(body);
				if (multipartBodyHandler != null)
					multipartBodyHandler.addBodyPart(builder, name, body, contentType);
			}
		}
	}
	
}
