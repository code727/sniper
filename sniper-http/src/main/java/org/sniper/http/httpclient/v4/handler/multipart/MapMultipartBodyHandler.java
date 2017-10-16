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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

/**
 * Map类型的Multipart请求体处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapMultipartBodyHandler extends AbstractCompositeMultipartBodyHandler<Map<String, Object>> {
	
	public MapMultipartBodyHandler() {
		this(null);
	}
	
	public MapMultipartBodyHandler(List<MultipartBodyHandler> multipartBodyHandlers) {
		super(multipartBodyHandlers, Map.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean support(Object requestBody) {
		if (requestBody instanceof Map) {
			Iterator<Object> iterator = ((Map<String, Object>) requestBody).values().iterator();
			while (iterator.hasNext()) {
				Object body = iterator.next();
				for (MultipartBodyHandler multipartBodyHandler : multipartBodyHandlers) {
					if (multipartBodyHandler.support(body))
						return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addBodyPart(MultipartEntityBuilder builder, String name, Object requestBody, ContentType contentType) throws Exception {
		MultipartBodyHandler multipartBodyHandler;
		Object body;
		for (Map.Entry<String, Object> entry : ((Map<String, Object>) requestBody).entrySet()) {
			name = entry.getKey();
			body = entry.getValue();
			multipartBodyHandler = getMatchedMultipartBodyHandler(body);
			if (multipartBodyHandler != null)
				multipartBodyHandler.addBodyPart(builder, name, body, contentType);
		}
	}
	
}
