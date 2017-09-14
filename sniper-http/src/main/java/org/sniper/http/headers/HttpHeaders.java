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

import org.sniper.commons.LinkedMultiValueMap;
import org.sniper.commons.enums.http.ContentDispositionType;
import org.sniper.commons.enums.http.ContentEncodingAlgorithm;
import org.sniper.commons.enums.http.HttpConnection;
import org.sniper.commons.enums.http.HttpMethod;
import org.sniper.commons.enums.http.headers.HttpHeadersEnum;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;

/**
 * HTTP头
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param allowedMethods
	 */
	public void setAllow(Set<HttpMethod> allowedMethods) {
		set(HttpHeadersEnum.ALLOW.getKey(), CollectionUtils.join(allowedMethods, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取资源所支持的HTTP方法的集合(Allow)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Set<HttpMethod> getAllow() {
		String first = getFirst(HttpHeadersEnum.ALLOW.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			Set<HttpMethod> set = CollectionUtils.newLinkedHashSet();
			HttpMethod method;
			for (String value : values) {
				method = HttpMethod.resolve(value);
				if (method != null)
					set.add(method);
			}
			
			return set;
		}
		
		return null;
	}
	
	/**
	 * 设置HTTP请求和响应中通过指定指令来实现的缓存机制
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param list
	 */
	public void setCacheControl(List<String> list) {
		set(HttpHeadersEnum.CACHE_CONTROL.getKey(), CollectionUtils.join(list, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取HTTP请求和响应中通过指定指令来实现的缓存机制
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<String> getCacheControl() {
		String first = getFirst(HttpHeadersEnum.CACHE_CONTROL.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			List<String> list = CollectionUtils.newArrayList();
			for (String value : values) {
				if (StringUtils.isNotBlank(value))
					list.add(value);
			}
			
			return list;
		}
		
		return null;
	}
	
	/**
	 * 设置是否关闭HTTP网络连接
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 */
	public void setConnection(HttpConnection connection) {
		set(HttpHeadersEnum.CONNECTION.getKey(), connection != null ? connection.getValue() : null);
	}
	
	/**
	 * 获取是否关闭HTTP网络连接
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public HttpConnection getConnection() {
		return HttpConnection.resolve(getFirst(HttpHeadersEnum.CONNECTION.getKey()));
	}
	
	/**
	 * 设置回复的内容该以何种形式展示(Content-Disposition)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param contentDisposition
	 */
	public void setContentDisposition(ContentDisposition contentDisposition) {
		set(HttpHeadersEnum.CONTENT_DISPOSITION.getKey(), contentDisposition != null ? contentDisposition.toString() : null);
	}
	
	/**
	 * 获取回复的内容该以何种形式展示(Content-Disposition)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public ContentDisposition getContentDisposition() {
		String first = getFirst(HttpHeadersEnum.CONTENT_DISPOSITION.getKey());
		String[] values = StringUtils.split(first, StringUtils.SEMICOLON);
		
		ContentDispositionType type = ContentDispositionType.resolve(ArrayUtils.get(values, 0));
		if (type != null) {
			ContentDisposition disposition = new ContentDisposition(type);
			
			String item = ArrayUtils.get(values, 1);
			if (StringUtils.simpleMatch("name=*", item)) {
				setContentDispositionName(disposition, item);
				
				item = ArrayUtils.get(values, 2);
				if (StringUtils.simpleMatch("filename=*", item)) 
					setContentDispositionFilename(disposition, item);
				
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
	 * 设置HTTP Content-Disposition消息头对象的表单名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param disposition
	 * @param name
	 */
	private void setContentDispositionName(ContentDisposition disposition, String name) {
		disposition.setName(StringUtils.replaceAll(StringUtils.afterFrist(name, StringUtils.ASSIGNMENT),
				StringUtils.DOUBLE_QUOTES, StringUtils.EMPTY));
	}
	
	/**
	 * 设置HTTP Content-Disposition消息头对象的文件名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param disposition
	 * @param name
	 */
	private void setContentDispositionFilename(ContentDisposition disposition, String name) {
		disposition.setFilename(StringUtils.replaceAll(StringUtils.afterFrist(name, StringUtils.ASSIGNMENT),
				StringUtils.DOUBLE_QUOTES, StringUtils.EMPTY));
	}
	
	/**
	 * 设置对特定媒体类型的数据进行压缩的算法(Content-Encoding)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param algorithm
	 */
	public void setContentEncoding(Set<ContentEncodingAlgorithm> algorithms) {
		set(HttpHeadersEnum.CONTENT_ENCODING.getKey(), CollectionUtils.join(algorithms, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取对特定媒体类型的数据进行压缩的算法(Content-Encoding)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Set<ContentEncodingAlgorithm> getContentEncoding() {
		String first = getFirst(HttpHeadersEnum.CONTENT_ENCODING.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			Set<ContentEncodingAlgorithm> set = CollectionUtils.newLinkedHashSet();
			ContentEncodingAlgorithm algorithm;
			for (String value : values) {
				algorithm = ContentEncodingAlgorithm.resolve(value);
				if (algorithm != null)
					set.add(algorithm);
			}
			
			return set;
		}
		
		return null;
	}
	
	/**
	 * 设置访问者希望采用的语言或语言组合(Content-Language)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param languages
	 */
	public void setContentLanguage(Set<String> languages) {
		set(HttpHeadersEnum.CONTENT_LANGUAGE.getKey(), CollectionUtils.join(languages, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取访问者希望采用的语言或语言组合(Content-Language)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Set<String> getContentLanguage() {
		String first = getFirst(HttpHeadersEnum.CONTENT_LANGUAGE.getKey());
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param contentLength
	 */
	public void setContentLength(long contentLength) {
		set(HttpHeadersEnum.CONTENT_LENGTH.getKey(), Long.toString(NumberUtils.minLimit(contentLength, -1)));
	}

	/**
	 * 获取发送给接收方的消息的大小(Content-Length)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public long getContentLength() {
		String value = getFirst(HttpHeadersEnum.CONTENT_LENGTH.getKey());
		return value != null ? Long.parseLong(value) : -1;
	}
	
	/**
	 * 设置要返回的数据的地址选项(Content-Location)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 */
	public void setContentLocation(String url) {
		set(HttpHeadersEnum.CONTENT_LOCATION.getKey(), url);
	}
	
	/**
	 * 获取要返回的数据的地址选项(Content-Location)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getContentLocation() {
		return getFirst(HttpHeadersEnum.CONTENT_LOCATION.getKey());
	}
	
	/**
	 * 设置用于指示资源的MIME类型(MediaType)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mediaType
	 */
	public void setContentType(MediaType mediaType) {
		set(HttpHeadersEnum.CONTENT_TYPE.getKey(), mediaType != null ? mediaType.toString() : null);
	}
	
	/**
	 * 获取用于指示资源的MIME类型(MediaType)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public MediaType getContentType() {
		return MediaType.parse(getFirst(HttpHeadersEnum.CONTENT_TYPE.getKey()));
	}
	
	/**
	 * 设置消息生成的日期和时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 */
	public void setDate(Date date) {
		set(HttpHeadersEnum.DATE.getKey(), date != null ? DateUtils.getGMTDateFormat(Locale.US).format(date) : null);
	}
	
	/**
	 * 获取消息生成的日期和时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Date getDate() {
		String first = getFirst(HttpHeadersEnum.DATE.getKey());
		return StringUtils.isNotBlank(first) ? DateUtils.getGMTDateFormat(Locale.US).parse(first, new ParsePosition(0)) : null;
	}
	
	/**
	 * 设置Keep-Alive消息头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keepAlive
	 */
	public void setKeepAlive(KeepAlive keepAlive) {
		set(HttpHeadersEnum.KEEP_ALIVE.getKey(), keepAlive != null ? keepAlive.toString() : null);
	}
	
	/**
	 * 获取Keep-Alive消息头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public KeepAlive getKeepAlive() {
		String first = getFirst(HttpHeadersEnum.KEEP_ALIVE.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			long timeout = 0;
			int max = 0;
			for (String value : values) {
				if (StringUtils.startsWithIgnoreCase(value, "timeout=")) 
					timeout = NumberUtils.toLongValue(StringUtils.afterFristIgnoreCase(value, "timeout="));
				else if (StringUtils.startsWithIgnoreCase(value, "max="))
					max = NumberUtils.toIntegerValue(StringUtils.afterFristIgnoreCase(value, "max="));
			}
			
			if (timeout > 0 && max > 0)
				return new KeepAlive(timeout, max);
		}
		
		return null;
	}
	
	/**
	 * 设置Pragma通用消息头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pragma
	 */
	public void setPragma(String pragma) {
		set(HttpHeadersEnum.PRAGMA.getKey(), pragma);
	}
	
	/**
	 * 获取Pragma通用消息头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getPragma() {
		return getFirst(HttpHeadersEnum.PRAGMA.getKey());
	}
	
	/**
	 * 设置Via通用消息头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param via
	 */
	public void setVia(String via) {
		set(HttpHeadersEnum.VIA.getKey(), via);
	}
	
	/**
	 * 获取Via通用消息头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getVia() {
		return getFirst(HttpHeadersEnum.VIA.getKey());
	}
	
	/**
	 * 设置当前消息状态可能存在的问题
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param warning
	 */
	public void setWarning(String warning) {
		set(HttpHeadersEnum.WARNING.getKey(), warning);
	}
	
	/**
	 * 获取当前消息状态可能存在的问题
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getWarning() {
		return getFirst(HttpHeadersEnum.WARNING.getKey());
	}
					
}
