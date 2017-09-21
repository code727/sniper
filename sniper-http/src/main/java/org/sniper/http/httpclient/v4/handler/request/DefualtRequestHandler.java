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

package org.sniper.http.httpclient.v4.handler.request;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.enums.http.Media;
import org.sniper.commons.enums.http.headers.HttpRequestHeadersEnum;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.NetUtils;
import org.sniper.http.headers.MediaType;
import org.sniper.http.headers.request.HttpRequestHeaders;
import org.sniper.serialization.json.JsonSerializer;
import org.sniper.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;

/**
 * 请求处理器默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefualtRequestHandler implements RequestHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(DefualtRequestHandler.class);
	
	private JsonSerializer jsonSerializer = new FasterxmlJacksonSerializer();

	@Override
	public void handle(HttpEntityEnclosingRequestBase httpRequest, String url, HttpRequestHeaders requestHeaders, Object requestBody, String encoding) throws Exception {
		ContentType contentType;
		if (requestBody == null) {
			contentType = ContentType.create(Media.APPLICATION_FORM_URLENCODED.getType(), encoding); 
			httpRequest.setEntity(new StringEntity(NetUtils.getQueryString(url), contentType));
		}
		
//		ContentType contentType = (hasContentType(requestHeaders) ? createCustomizeContentType(requestHeaders, encoding) : null);
//		if (requestBody == null ) {
//			/* 如果HttpRequestHeaders对象不包含有Content-Type，
//			 * 则建默认的ContentType对象(application/x-www-form-urlencoded) */
//			if (contentType == null)
//				contentType = ContentType.create(Media.APPLICATION_FORM_URLENCODED.getType(), encoding); 
//			
//			httpRequest.setEntity(new StringEntity(NetUtils.getQueryString(url), contentType));
//		} else {
//			if (contentType == null)
//				contentType = ContentType.create(Media.APPLICATION_JSON.getType(), encoding); 
//			
//			httpRequest.setEntity(new StringEntity(CodecUtils.bytesToString(jsonSerializer.serialize(requestBody), encoding), contentType));
//			
////			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
////			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE); 
////			/* 如果HttpRequestHeaders对象不包含有Content-Type，
////			 * 则建默认的ContentType对象(multipart/form-data) */
////			if (contentType == null)
////				contentType = ContentType.create(Media.APPLICATION_FORM_URLENCODED.getType(), encoding); 
////			
////			addParameteres(builder, url, contentType);
////			addRequestBody(builder, requestBody, contentType);
////			
////			httpRequest.setEntity(builder.build());
//		}
	}
	
	/**
	 * 判断请求头内是否有Content-Type(
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param requestHeaders
	 * @return
	 */
	protected boolean hasContentType(HttpRequestHeaders requestHeaders) {
		return requestHeaders != null && requestHeaders.containsKey(HttpRequestHeadersEnum.CONTENT_TYPE.getKey());
	}
	
	/**
	 * 创建自定义类型的ContentType对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param requestHeaders
	 * @param encoding
	 * @return
	 */
	protected ContentType createCustomizeContentType(HttpRequestHeaders requestHeaders, String encoding) {
		MediaType mediaType = requestHeaders.getContentType();
		Charset charset = mediaType.getCharset();
		return charset != null ? ContentType.create(mediaType.getType(), mediaType.getCharset())
				: ContentType.create(mediaType.getType(), encoding);
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
	
	/**
	 * 添加查询参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param url
	 * @param contentType
	 */
	protected void addParameteres(MultipartEntityBuilder builder, String url, ContentType contentType) {
		Map<String, String> parameterMap = NetUtils.getParameterMap(url);
		if (MapUtils.isNotEmpty(parameterMap)) {
			Iterator<Entry<String, String>> iterator = parameterMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> parameter = iterator.next();
				builder.addPart(parameter.getKey(), new StringBody(parameter.getValue(), contentType));
			}
		}
	}
	
	/**
	 * 添加RequestBody
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param requestBody
	 * @param contentType
	 */
	@SuppressWarnings("unchecked")
	protected void addRequestBody(MultipartEntityBuilder builder, Object requestBody, ContentType contentType) {
		if (requestBody instanceof Map) {
			Map<String, Object> mapBody = (Map<String, Object>) requestBody;
			if (MapUtils.isNotEmpty(mapBody)) {
				Iterator<Entry<String, Object>> iterator = mapBody.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> parameter = iterator.next();
					builder.addPart(parameter.getKey(), new StringBody(parameter.getValue().toString(), contentType));
				}
			}
		}
		
//		if (requestBody instanceof File) {
//			File file = (File) requestBody;
//			builder.addPart("file", new FileBody(file, contentType, file.getName()));
//		} else if (requestBody instanceof InputStream) {
//			InputStream input = (InputStream) requestBody;
//			builder.addPart("file", new InputStreamBody(input, contentType, "input"));
//		} else {
//			
//		}
	}
	
}
