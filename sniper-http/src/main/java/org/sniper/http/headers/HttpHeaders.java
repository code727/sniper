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
 * Create Date : 2017-8-15
 */

package org.sniper.http.headers;

import java.text.ParsePosition;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.sniper.commons.enums.http.ContentDispositionEnum;
import org.sniper.commons.enums.http.ContentEncodingEnum;
import org.sniper.commons.enums.http.HttpConnectionEnum;
import org.sniper.commons.enums.http.HttpHeaderEnum;
import org.sniper.commons.enums.http.HttpMethodEnum;
import org.sniper.commons.kv.LinkedMultiValueMap;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;

/**
 * HTTP头
 * @author  Daniele
 * @version 1.0
 */
public class HttpHeaders extends LinkedMultiValueMap<String, String> {
	
	private static final long serialVersionUID = -4210698174542258790L;
	
	/** 值分隔符 */
	public static final String VALUE_SEPARATOR = ", ";
	
	/** 名值对分隔分 */
	public static final String NAME_VALUE_PAIR_SEPARATOR = "; ";
	
	/**
	 * 设置资源所支持的HTTP方法的集合(Allow)
	 * @author Daniele 
	 * @param allowedMethods
	 */
	public void setAllow(Set<HttpMethodEnum> allowedMethods) {
		set(HttpHeaderEnum.ALLOW.getKey(), CollectionUtils.join(allowedMethods, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取资源所支持的HTTP方法的集合(Allow)
	 * @author Daniele 
	 * @return
	 */
	public Set<HttpMethodEnum> getAllow() {
		String first = getFirst(HttpHeaderEnum.ALLOW.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			Set<HttpMethodEnum> methods = CollectionUtils.newLinkedHashSet();
			HttpMethodEnum method;
			
			for (String value : values) {
				method = HttpMethodEnum.resolve(value);
				if (method != null) {
					methods.add(method);
				}
			}
			
			return methods;
		}
		
		return null;
	}
	
	/**
	 * 设置HTTP请求和响应中通过指定指令来实现的缓存机制
	 * @author Daniele 
	 * @param list
	 */
	public void setCacheControl(List<String> list) {
		set(HttpHeaderEnum.CACHE_CONTROL.getKey(), CollectionUtils.join(list, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取HTTP请求和响应中通过指定指令来实现的缓存机制
	 * @author Daniele 
	 * @return
	 */
	public List<String> getCacheControl() {
		String first = getFirst(HttpHeaderEnum.CACHE_CONTROL.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			List<String> list = CollectionUtils.newArrayList();
			for (String value : values) {
				if (StringUtils.isNotBlank(value)) {
					list.add(value);
				}
			}
			
			return list;
		}
		
		return null;
	}
	
	/**
	 * 设置是否关闭HTTP网络连接
	 * @author Daniele 
	 * @param connection
	 */
	public void setConnection(HttpConnectionEnum connection) {
		set(HttpHeaderEnum.CONNECTION.getKey(), connection != null ? connection.getStatus() : null);
	}
	
	/**
	 * 获取是否关闭HTTP网络连接
	 * @author Daniele 
	 * @return
	 */
	public HttpConnectionEnum getConnection() {
		return HttpConnectionEnum.resolve(getFirst(HttpHeaderEnum.CONNECTION.getKey()));
	}
	
	/**
	 * 设置回复的内容该以何种形式展示(Content-Disposition)
	 * @author Daniele 
	 * @param contentDisposition
	 */
	public void setContentDisposition(ContentDisposition contentDisposition) {
		set(HttpHeaderEnum.CONTENT_DISPOSITION.getKey(), contentDisposition != null ? contentDisposition.toString() : null);
	}
	
	/**
	 * 获取回复的内容该以何种形式展示(Content-Disposition)
	 * @author Daniele 
	 * @return
	 */
	public ContentDisposition getContentDisposition() {
		String first = getFirst(HttpHeaderEnum.CONTENT_DISPOSITION.getKey());
		String[] values = StringUtils.split(first, StringUtils.SEMICOLON);
		
		ContentDispositionEnum type = ContentDispositionEnum.resolve(ArrayUtils.get(values, 0));
		if (type != null) {
			ContentDisposition disposition = new ContentDisposition(type);
			
			String item = ArrayUtils.get(values, 1);
			if (StringUtils.simpleMatch("name=*", item)) {
				setContentDispositionName(disposition, item);
				item = ArrayUtils.get(values, 2);
				
				if (StringUtils.simpleMatch("filename=*", item)) {
					setContentDispositionFilename(disposition, item);
				}
			} else if (StringUtils.simpleMatch("filename=*", item)) {
				setContentDispositionFilename(disposition, item);
				
				item = ArrayUtils.get(values, 2);
				if (StringUtils.simpleMatch("name=*", item)) 
					setContentDispositionName(disposition, item);
			}
			
			return disposition;
		}
		
		return null;
	}
	
	/**
	 * 设置对特定媒体类型的数据进行压缩的算法(Content-Encoding)
	 * @author Daniele 
	 * @param algorithm
	 */
	public void setContentEncoding(Set<ContentEncodingEnum> algorithms) {
		set(HttpHeaderEnum.CONTENT_ENCODING.getKey(), CollectionUtils.join(algorithms, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取对特定媒体类型的数据进行压缩的算法(Content-Encoding)
	 * @author Daniele 
	 * @return
	 */
	public Set<ContentEncodingEnum> getContentEncoding() {
		String first = getFirst(HttpHeaderEnum.CONTENT_ENCODING.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			Set<ContentEncodingEnum> algorithms = CollectionUtils.newLinkedHashSet();
			for (String value : values) {
				ContentEncodingEnum algorithm = ContentEncodingEnum.resolve(value);
				if (algorithm != null) {
					algorithms.add(algorithm);
				}
			}
			
			return algorithms;
		}
		
		return null;
	}
	
	/**
	 * 设置访问者希望采用的语言或语言组合(Content-Language)
	 * @author Daniele 
	 * @param languages
	 */
	public void setContentLanguage(Set<String> languages) {
		set(HttpHeaderEnum.CONTENT_LANGUAGE.getKey(), CollectionUtils.join(languages, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取访问者希望采用的语言或语言组合(Content-Language)
	 * @author Daniele 
	 * @return
	 */
	public Set<String> getContentLanguage() {
		String first = getFirst(HttpHeaderEnum.CONTENT_LANGUAGE.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			Set<String> set = CollectionUtils.newLinkedHashSet();
			for (String value : values) {
				if (StringUtils.isNotBlank(value))
					set.add(value);
			}
			
			return set;
		}
		
		return null;
	}
	
	/**
	 * 设置发送给接收方的消息的大小(Content-Length)
	 * @author Daniele 
	 * @param contentLength
	 */
	public void setContentLength(long contentLength) {
		set(HttpHeaderEnum.CONTENT_LENGTH.getKey(), Long.toString(NumberUtils.minLimit(contentLength, -1)));
	}

	/**
	 * 获取发送给接收方的消息的大小(Content-Length)
	 * @author Daniele 
	 * @return
	 */
	public long getContentLength() {
		String value = getFirst(HttpHeaderEnum.CONTENT_LENGTH.getKey());
		return value != null ? Long.parseLong(value) : -1;
	}
	
	/**
	 * 设置要返回的数据的地址选项(Content-Location)
	 * @author Daniele 
	 * @param url
	 */
	public void setContentLocation(String url) {
		set(HttpHeaderEnum.CONTENT_LOCATION.getKey(), url);
	}
	
	/**
	 * 获取要返回的数据的地址选项(Content-Location)
	 * @author Daniele 
	 * @return
	 */
	public String getContentLocation() {
		return getFirst(HttpHeaderEnum.CONTENT_LOCATION.getKey());
	}
	
	/**
	 * 设置用于指示资源的MIME类型(MediaType)
	 * @author Daniele 
	 * @param mediaType
	 */
	public void setContentType(MediaType mediaType) {
		set(HttpHeaderEnum.CONTENT_TYPE.getKey(), mediaType != null ? mediaType.toString() : null);
	}
	
	/**
	 * 获取用于指示资源的MIME类型(MediaType)
	 * @author Daniele 
	 * @return
	 */
	public MediaType getContentType() {
		return MediaType.parse(getFirst(HttpHeaderEnum.CONTENT_TYPE.getKey()));
	}
	
	/**
	 * 设置消息生成的日期和时间
	 * @author Daniele 
	 * @param date
	 */
	public void setDate(Date date) {
		set(HttpHeaderEnum.DATE.getKey(), date != null ? DateUtils.getGMTDateFormat(Locale.US).format(date) : null);
	}
	
	/**
	 * 获取消息生成的日期和时间
	 * @author Daniele 
	 * @return
	 */
	public Date getDate() {
		String first = getFirst(HttpHeaderEnum.DATE.getKey());
		return StringUtils.isNotBlank(first) ? DateUtils.getGMTDateFormat(Locale.US).parse(first, new ParsePosition(0)) : null;
	}
	
	/**
	 * 设置Keep-Alive消息头
	 * @author Daniele 
	 * @param keepAlive
	 */
	public void setKeepAlive(KeepAlive keepAlive) {
		set(HttpHeaderEnum.KEEP_ALIVE.getKey(), keepAlive != null ? keepAlive.toString() : null);
	}
	
	/**
	 * 获取Keep-Alive消息头
	 * @author Daniele 
	 * @return
	 */
	public KeepAlive getKeepAlive() {
		String first = getFirst(HttpHeaderEnum.KEEP_ALIVE.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			long timeout = 0;
			int max = 0;
			
			for (String value : values) {
				if (StringUtils.startsWithIgnoreCase(value, "timeout=")) {
					timeout = NumberUtils.toLongValue(StringUtils.afterFristIgnoreCase(value, "timeout="));
				} else if (StringUtils.startsWithIgnoreCase(value, "max=")) {
					max = NumberUtils.toIntegerValue(StringUtils.afterFristIgnoreCase(value, "max="));
				}
			}
			
			if (timeout > 0 && max > 0) {
				return new KeepAlive(timeout, max);
			}
		}
		
		return null;
	}
	
	/**
	 * 设置Pragma通用消息头
	 * @author Daniele 
	 * @param pragma
	 */
	public void setPragma(String pragma) {
		set(HttpHeaderEnum.PRAGMA.getKey(), pragma);
	}
	
	/**
	 * 获取Pragma通用消息头
	 * @author Daniele 
	 * @return
	 */
	public String getPragma() {
		return getFirst(HttpHeaderEnum.PRAGMA.getKey());
	}
	
	/**
	 * 设置Via通用消息头
	 * @author Daniele 
	 * @param via
	 */
	public void setVia(String via) {
		set(HttpHeaderEnum.VIA.getKey(), via);
	}
	
	/**
	 * 获取Via通用消息头
	 * @author Daniele 
	 * @return
	 */
	public String getVia() {
		return getFirst(HttpHeaderEnum.VIA.getKey());
	}
	
	/**
	 * 设置当前消息状态可能存在的问题
	 * @author Daniele 
	 * @param warning
	 */
	public void setWarning(String warning) {
		set(HttpHeaderEnum.WARNING.getKey(), warning);
	}
	
	/**
	 * 获取当前消息状态可能存在的问题
	 * @author Daniele 
	 * @return
	 */
	public String getWarning() {
		return getFirst(HttpHeaderEnum.WARNING.getKey());
	}
	
	/**
	 * 设置HTTP Content-Disposition消息头对象的表单名
	 * @author Daniele 
	 * @param disposition
	 * @param name
	 */
	private void setContentDispositionName(ContentDisposition disposition, String name) {
		disposition.setName(StringUtils.replaceAll(StringUtils.afterFrist(name, StringUtils.ASSIGNMENT),
				StringUtils.DOUBLE_QUOTES, StringUtils.EMPTY));
	}
	
	/**
	 * 设置HTTP Content-Disposition消息头对象的文件名
	 * @author Daniele 
	 * @param disposition
	 * @param name
	 */
	private void setContentDispositionFilename(ContentDisposition disposition, String name) {
		disposition.setFilename(StringUtils.replaceAll(StringUtils.afterFrist(name, StringUtils.ASSIGNMENT),
				StringUtils.DOUBLE_QUOTES, StringUtils.EMPTY));
	}
					
}
