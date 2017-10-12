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
 * Create Date : 2017-9-28
 */

package org.sniper.http.httpclient.v4.builder;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.http.headers.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * multipart/form-data表单消息构建器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MultipartFormMessageBuilder extends AbstractHttpClientMessageBuilder {
	
	private static final String SINGLE_FILE_PARAMETER_NAME = "file";
	private static final String MULTI_FILE_PARAMETER_NAME = "files";
	
	protected static final Map<Class<?>, String> SOURCE_MULTIPART = MapUtils.newHashMap();
	protected static final Map<Class<?>, String> MAP_MULTIPART = MapUtils.newHashMap();
	
	static {
		SOURCE_MULTIPART.put(File.class, "addBodyByFile");
		SOURCE_MULTIPART.put(File[].class, "addBodyByFiles");
		
		/* Apache FileItem */
		SOURCE_MULTIPART.put(FileItem.class, "addBodyByFileItem");
		SOURCE_MULTIPART.put(FileItem[].class, "addBodyByFileItems");
		
		/* Spring Resource and MultipartFile */
		SOURCE_MULTIPART.put(Resource.class, "addBodyByResource");
		SOURCE_MULTIPART.put(Resource[].class, "addBodyByResources");
		SOURCE_MULTIPART.put(MultipartFile.class, "addBodyByMultipartFile");
		SOURCE_MULTIPART.put(MultipartFile[].class, "addBodyByMultipartFiles");
		
		MAP_MULTIPART.put(Map.class, "addBodyByMap");
		MAP_MULTIPART.put(MultiValueMap.class, "addBodyBySpringMultiValueMap");
		MAP_MULTIPART.put(org.sniper.commons.MultiValueMap.class, "addBodyBySniperMultiValueMap");
	}
		
	public MultipartFormMessageBuilder() {
		super(MediaType.MULTIPART_FORM_DATA);
	}
	
	@Override
	public boolean support(Object requestBody, MediaType mediaType) {
		return isMultipart(requestBody) && super.support(requestBody, mediaType);
	}
	
	/**
	 * 判断是否为Multipart类型的请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param requestBody
	 * @return
	 */
	protected boolean isMultipart(Object requestBody) {
		if (requestBody == null)
			return false;
		
		return isSourceBody(requestBody) || isMapSourceBody(requestBody) 
				|| isSpringMultiValueMapBody(requestBody) || isSniperMultiValueMapBody(requestBody);
	}
	
	/**
	 * 判断是否为携带有资源对象的请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param requestBody
	 * @return
	 */
	protected boolean isSourceBody(Object requestBody) {
		if (requestBody != null) {
			Class<?> bodyType = requestBody.getClass();
			Set<Class<?>> baseMultipartTypes = SOURCE_MULTIPART.keySet();
			
			for (Class<?> baseMultipartType : baseMultipartTypes) {
				if (baseMultipartType.isAssignableFrom(bodyType)) 
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 判断是否为携带有资源对象的Map请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param requestBody
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean isMapSourceBody(Object requestBody) {
		if (requestBody instanceof Map) {
			Iterator<Object> iterator = ((Map<String, Object>) requestBody).values().iterator();
			while(iterator.hasNext()) {
				Object body = iterator.next();
				if (isSourceBody(body))
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 判断是否为org.springframework.util.MultiValueMap请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param requestBody
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean isSpringMultiValueMapBody(Object requestBody) {
		if (requestBody instanceof MultiValueMap) {
			/* 兼容org.springframework.http.converter.FormHttpMessageConverter类中isMultipart方法实现逻辑 */
			MultiValueMap<String, ?> map = (MultiValueMap<String, ?>) requestBody;
			return !MapUtils.hasTypeValue(map, String.class);
		}
		
		return false;
	}
	
	/**
	 * 判断是否为org.sniper.commons.MultiValueMap请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param requestBody
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean isSniperMultiValueMapBody(Object requestBody) {
		if (requestBody instanceof org.sniper.commons.MultiValueMap) {
			/* 兼容org.springframework.http.converter.FormHttpMessageConverter类中isMultipart方法实现逻辑 */
			org.sniper.commons.MultiValueMap<String, ?> map = (org.sniper.commons.MultiValueMap<String, ?>) requestBody;
			return !MapUtils.hasTypeValue(map, String.class);
		}
		
		return false;
	}
	
	/**
	 * 获取资源请求体执行的方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param requestBody
	 * @return
	 */
	protected String getSourceRequestBodyExecuteMethod(Object requestBody) {
		if (requestBody == null)
			return null;
		
		Class<?> bodyType = requestBody.getClass();
		Set<Entry<Class<?>, String>> entrySet = SOURCE_MULTIPART.entrySet();
		for (Entry<Class<?>, String> entry : entrySet) {
			if (entry.getKey().isAssignableFrom(bodyType)) 
				return entry.getValue();
		}
		
		return null;
	}
	
	/**
	 * 获取Map请求体执行的方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param requestBody
	 * @return
	 */
	protected String getMapRequestBodyExecuteMethod(Object requestBody) {
		if (requestBody == null)
			return null;
		
		Class<?> bodyType = requestBody.getClass();
		Set<Entry<Class<?>, String>> entrySet = MAP_MULTIPART.entrySet();
		for (Entry<Class<?>, String> entry : entrySet) {
			if (entry.getKey().isAssignableFrom(bodyType)) 
				return entry.getValue();
		}
		
		return null;
	}
				
	@Override
	public HttpEntity build(String url, Object requestBody, MediaType mediaType, String encoding) throws Exception {
		ContentType contentType = createContentType(mediaType, encoding);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setCharset(contentType.getCharset());
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		addBodyPart(builder, requestBody, contentType);
		return builder.build();
	}

	/** 
	 * 添加Body部分
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param requestBody
	 * @param contentType 
	 * @throws IOException 
	 */
	protected void addBodyPart(MultipartEntityBuilder builder, Object requestBody, ContentType contentType) throws Exception {
		String method = getSourceRequestBodyExecuteMethod(requestBody);
		Class<?>[] pTypes;
		Object[] pValues;
		if (method != null) {
			/* 这里的参数类型传Object.class而不是请求体的实际类型(requestBody.getClass())，目的在于当反射调用目标方法时，
			      如果目标方法的requestBody参数类型定义为接口(例如addBodyByResource方法的requestBody参数类型定义为org.springframework.core.io.Resource)
			      而调用方却传入的是该接口的实现类(例如org.springframework.core.io.FileSystemResource)，则发生反射调用找不到目标方法的错误。
			      因此为避免上述错误，如下所有的addBodyByXXX目标方法中，统一的将requestBody参数类型定义为Object */
			pTypes = new Class<?>[] { MultipartEntityBuilder.class, String.class, Object.class, ContentType.class };
			
			pValues = new Object[] { builder, null, requestBody, contentType };
			ReflectionUtils.invokeMethod(this, method, pTypes, pValues);
		} else {
			method = getMapRequestBodyExecuteMethod(requestBody);
			pTypes = new Class<?>[] { MultipartEntityBuilder.class, Object.class, ContentType.class };
			pValues = new Object[] { builder, requestBody, contentType };
			ReflectionUtils.invokeMethod(this, method, pTypes, pValues);
		}
	}
	
	/**
	 * 添加本地文件请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param requestBody
	 * @param contentType
	 */
	protected void addBodyByFile(MultipartEntityBuilder builder, String name, Object requestBody, ContentType contentType) {
		File file = (File) requestBody;
		builder.addPart(StringUtils.isNotBlank(name) ? name : SINGLE_FILE_PARAMETER_NAME,
				new FileBody(file, contentType, file.getName()));
	}
	
	/**
	 * 添加多个本地文件请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param requestBody
	 * @param contentType
	 */
	protected void addBodyByFiles(MultipartEntityBuilder builder, String name, Object requestBody, ContentType contentType) {
		File[] files = (File[]) requestBody;
		if (StringUtils.isNotBlank(name)) {
			for (File file : files ) {
				builder.addPart(name, new FileBody(file, contentType, file.getName()));
			}
		} else {
			for (File file : files) {
				builder.addPart(MULTI_FILE_PARAMETER_NAME, new FileBody(file, contentType, file.getName()));
			}
		}
	}
	
	/**
	 * 添加org.apache.commons.fileupload.FileItem请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param requestBody
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByFileItem(MultipartEntityBuilder builder, String name, Object requestBody,
			ContentType contentType) throws IOException {
		
		FileItem fileItem = (FileItem) requestBody;
		if (StringUtils.isNotBlank(name)) {
			builder.addBinaryBody(name, fileItem.getInputStream(), contentType, fileItem.getName());
		} else {
			/* 从fileItem对象中获取域名，设置为文件参数名，未获取到时设置为全局默认的参数名 */
			String fieldName = fileItem.getFieldName();
			builder.addBinaryBody(StringUtils.isNotBlank(fieldName) ? fieldName : SINGLE_FILE_PARAMETER_NAME,
					fileItem.getInputStream(), contentType, fileItem.getName());
		}
	}
	
	/**
	 * 添加多个org.apache.commons.fileupload.FileItem请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param requestBody
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByFileItems(MultipartEntityBuilder builder, String name, Object requestBody,
			ContentType contentType) throws IOException {
		
		FileItem[] fileItems = (FileItem[]) requestBody;
		if (StringUtils.isNotBlank(name)) {
			for (FileItem fileItem : fileItems) {
				builder.addBinaryBody(name, fileItem.getInputStream(), contentType, fileItem.getName());
			}
		} else {
			String fieldName;
			for (FileItem fileItem : fileItems) {
				/* 从fileItem对象中获取域名，设置为文件参数名，未获取到时设置为全局默认的参数名 */
				fieldName = fileItem.getFieldName();
				builder.addBinaryBody(StringUtils.isNotBlank(fieldName) ? fieldName : MULTI_FILE_PARAMETER_NAME,
						fileItem.getInputStream(), contentType, fileItem.getName());
			}
		}
	}
	
	/**
	 * 添加org.springframework.core.io.Resource请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param requestBody
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByResource(MultipartEntityBuilder builder, String name, Object requestBody,
			ContentType contentType) throws IOException {
		
		Resource resource = (Resource) requestBody;
		builder.addBinaryBody(name, resource.getInputStream(), contentType, resource.getFilename());
	}
	
	/**
	 * 添加多个org.springframework.core.io.Resource请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param requestBody
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByResources(MultipartEntityBuilder builder, String name, Object requestBody,
			ContentType contentType) throws IOException {
		
		Resource[] resources = (Resource[]) requestBody;
		if (StringUtils.isNotBlank(name)) {
			for (Resource resource : resources) {
				builder.addBinaryBody(name, resource.getInputStream(), contentType, resource.getFilename());
			}
		} else {
			for (Resource resource : resources) {
				builder.addBinaryBody(MULTI_FILE_PARAMETER_NAME, resource.getInputStream(), 
						contentType, resource.getFilename());
			}
		}
	}
	
	/**
	 * 添加org.springframework.web.multipart.MultipartFile请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param requestBody
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByMultipartFile(MultipartEntityBuilder builder, String name, Object requestBody,
			ContentType contentType) throws IOException {
		
		MultipartFile multipartFile = (MultipartFile) requestBody;
		if (StringUtils.isNotBlank(name)) {
			builder.addBinaryBody(name, multipartFile.getInputStream(), contentType,
					multipartFile.getOriginalFilename());
		} else {
			/* 从multipartFile对象中获取域名，设置为文件参数名，未获取到时设置为全局默认的参数名 */
			name = multipartFile.getName();
			builder.addBinaryBody(StringUtils.isNotBlank(name) ? name : SINGLE_FILE_PARAMETER_NAME, 
					multipartFile.getInputStream(), contentType, multipartFile.getOriginalFilename());
		}
	}
	
	/**
	 * 添加多个org.springframework.web.multipart.MultipartFile请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param requestBody
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByMultipartFiles(MultipartEntityBuilder builder, String name, Object requestBody,
			ContentType contentType) throws IOException {
		
		MultipartFile[] multipartFiles = (MultipartFile[]) requestBody;
		if (StringUtils.isNotBlank(name)) {
			for (MultipartFile multipartFile : multipartFiles) {
				builder.addBinaryBody(name, multipartFile.getInputStream(), contentType,
						multipartFile.getOriginalFilename());
			}
		} else {
			for (MultipartFile multipartFile : multipartFiles) {
				/* 从multipartFile对象中获取域名，设置为文件参数名，未获取到时设置为全局默认的参数名 */
				name = multipartFile.getName();
				builder.addBinaryBody(StringUtils.isNotBlank(name) ? name : MULTI_FILE_PARAMETER_NAME,
						multipartFile.getInputStream(), contentType, multipartFile.getOriginalFilename());
			}
		}
	}
	
	/**
	 * 添加Map请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param requestBody
	 * @param contentType
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected void addBodyByMap(MultipartEntityBuilder builder, Object requestBody, ContentType contentType) throws Exception {
		Map<String, Object> map = (Map<String, Object>) requestBody;
		for (Map.Entry<String, Object> entry : map .entrySet()) {
			addBodyByNameAndValue(builder, entry.getKey(), entry.getValue(), contentType);
		}
	}
	
	/**
	 * 添加org.springframework.util.MultiValueMap请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param requestBody
	 * @param contentType
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected void addBodyBySpringMultiValueMap(MultipartEntityBuilder builder, Object requestBody, ContentType contentType) throws Exception {
		MultiValueMap<String, Object> multiValueMap = (MultiValueMap<String, Object>) requestBody;
		for (Map.Entry<String, List<Object>> entry : multiValueMap.entrySet()) {
			String name = entry.getKey();
			for (Object value : entry.getValue()) {
//				String method = getSourceRequestBodyExecuteMethod(value);
//				if (method != null) {
//					Class<?>[] pTypes = new Class<?>[]{MultipartEntityBuilder.class, String.class, Object.class, ContentType.class};
//					Object[] pValues = new Object[]{builder, name, value, contentType};	
//					ReflectionUtils.invokeMethod(this, method, pTypes, pValues);
//				} else {
//					String text = ObjectUtils.toString(value);
//					if (text != null)
//						builder.addBinaryBody(name, text.getBytes(contentType.getCharset()));
//				}
				addBodyByNameAndValue(builder, name, value, contentType);
			}
		}
	}

	/**
	 * 添加org.sniper.commons.MultiValueMap请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param requestBody
	 * @param contentType
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected void addBodyBySniperMultiValueMap(MultipartEntityBuilder builder, Object requestBody, ContentType contentType) throws Exception {
		org.sniper.commons.MultiValueMap<String, Object> multiValueMap = (org.sniper.commons.MultiValueMap<String, Object>) requestBody;
		for (Map.Entry<String, List<Object>> entry : multiValueMap.entrySet()) {
			String name = entry.getKey();
			for (Object value : entry.getValue()) {
//				String method = getSourceRequestBodyExecuteMethod(value);
//				if (method != null) {
//					Class<?>[] pTypes = new Class<?>[]{MultipartEntityBuilder.class, String.class, Object.class, ContentType.class};
//					Object[] pValues = new Object[]{builder, name, value, contentType};	
//					ReflectionUtils.invokeMethod(this, method, pTypes, pValues);
//				} else {
//					String text = ObjectUtils.toString(value);
//					if (text != null)
//						builder.addBinaryBody(name, text.getBytes(contentType.getCharset()));
//				}
				addBodyByNameAndValue(builder, name, value, contentType);
			}
		}
	}
	
	/**
	 * 根据名值添加请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param value
	 * @param contentType
	 * @throws Exception
	 */
	private void addBodyByNameAndValue(MultipartEntityBuilder builder, String name, Object value, ContentType contentType) throws Exception {
		String method = getSourceRequestBodyExecuteMethod(value);
		if (method != null) {
			Class<?>[] pTypes = new Class<?>[]{MultipartEntityBuilder.class, String.class, Object.class, ContentType.class};
			Object[] pValues = new Object[]{builder, name, value, contentType};	
			ReflectionUtils.invokeMethod(this, method, pTypes, pValues);
		} else {
			String text = ObjectUtils.toString(value);
			if (text != null)
				builder.addBinaryBody(name, text.getBytes(contentType.getCharset()));
		}
	}
	
}
