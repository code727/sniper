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
 * Create Date : 2017年10月11日
 */

package org.sniper.http.httpclient.v4.handler;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.sniper.commons.util.MapUtils;
import org.springframework.core.io.Resource;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultMultipartFormHandler implements MultipartFormHandler {
	
	protected static final Map<Class<?>, String> BASE_MULTIPART = MapUtils.newHashMap();
	protected static final Map<Class<?>, String> FULL_MULTIPART = MapUtils.newHashMap();
	
	static {
		BASE_MULTIPART.put(File.class, "");
		BASE_MULTIPART.put(File[].class, "");
		BASE_MULTIPART.put(Resource.class, "");
		BASE_MULTIPART.put(Resource[].class, "");
		BASE_MULTIPART.put(MultipartFile.class, "");
		BASE_MULTIPART.put(MultipartFile[].class, "");
		
		FULL_MULTIPART.putAll(BASE_MULTIPART);
		FULL_MULTIPART.put(MultiValueMap.class, "");
		FULL_MULTIPART.put(org.sniper.commons.MultiValueMap.class, "");
		FULL_MULTIPART.put(Map.class, "");
	}

	@Override
	public boolean isMultipart(Object requestBody) {
		if (requestBody == null)
			return false;
		
		return isBaseBody(requestBody) || isSpringMultiValueMapBody(requestBody)
				|| isSniperMultiValueMapBody(requestBody);
	}
	
	protected boolean isBaseBody(Object requestBody) {
		Class<?> bodyType = requestBody.getClass();
		Set<Class<?>> baseMultipartTypes = BASE_MULTIPART.keySet();
		
		for (Class<?> baseMultipartType : baseMultipartTypes) {
			if (bodyType.isAssignableFrom(baseMultipartType)) 
				return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	protected boolean isMapBody(Object requestBody) {
		if (requestBody instanceof Map) {
			Iterator<Object> iterator = ((Map<String, Object>) requestBody).values().iterator();
			while(iterator.hasNext()) {
				Object body = iterator.next();
				if (isBaseBody(body))
					return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	protected boolean isSpringMultiValueMapBody(Object requestBody) {
		if (requestBody instanceof MultiValueMap) {
			/* 兼容org.springframework.http.converter.FormHttpMessageConverter类中isMultipart方法实现逻辑 */
			MultiValueMap<String, ?> map = (MultiValueMap<String, ?>) requestBody;
			return !MapUtils.hasTypeValue(map, String.class);
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	protected boolean isSniperMultiValueMapBody(Object requestBody) {
		if (requestBody instanceof org.sniper.commons.MultiValueMap) {
			/* 兼容org.springframework.http.converter.FormHttpMessageConverter类中isMultipart方法实现逻辑 */
			org.sniper.commons.MultiValueMap<String, ?> map = (org.sniper.commons.MultiValueMap<String, ?>) requestBody;
			return !MapUtils.hasTypeValue(map, String.class);
		}
		
		return false;
	}

}
