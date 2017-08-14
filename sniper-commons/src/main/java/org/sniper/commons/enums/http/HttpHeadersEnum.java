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
 * Create Date : 2017-8-10
 */

package org.sniper.commons.enums.http;

import org.sniper.commons.enums.AbstractLocaleEnums;

/**
 * HTTP头枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HttpHeadersEnum extends AbstractLocaleEnums<String> {

	protected HttpHeadersEnum(String key, String description) {
		super(key, description);
	}
	
	/** 列出资源支持的一组方法 */
	public static final HttpHeadersEnum ALLOW = new HttpHeadersEnum("Allow", "http.entity.headers.allow");
	
	/** 用于指定缓存机制的指令，请求和响应中通过此指令来实现缓存机制 */
	public static final HttpHeadersEnum CACHE_CONTROL = new HttpHeadersEnum("Cache-Control",
			"http.general.headers.cache-control");
	
	/** 控制当前事务完成后网络连接是否保持打开状态。 
	 *  如果发送的值保持活动，则该连接是持久的而不是关闭的，允许对相同服务器的后续请求完成 */
	public static final HttpHeadersEnum CONNECTION = new HttpHeadersEnum("Connection",
			"http.general.headers.connection");
	
	/** 用于压缩媒体类型，让客户端知道如何解码以获取Content-Type头引用的媒体类型 */
	public static final HttpHeadersEnum CONTENT_ENCODING = new HttpHeadersEnum("Content-Encoding",
			"http.entity.headers.content-encoding");
	
	/** 指定内容是否期望在浏览器中内联显示，
	 *  即作为网页或作为网页的一部分，或作为附件显示，在本地下载并保存 */
	public static final HttpHeadersEnum CONTENT_DISPOSITION = new HttpHeadersEnum("Content-Disposition",
			"http.general.headers.content-disposition");
	
	/** 用于定义页面所使用的语言代码 */
	public static final HttpHeadersEnum CONTENT_LANGUAGE = new HttpHeadersEnum("Content-Language",
			"http.entity.headers.content-language");
	
	/** 指定发送到接收方的消息主体的大小，以十进制数字为单位 */
	public static final HttpHeadersEnum CONTENT_LENGTH = new HttpHeadersEnum("Content-Length",
			"http.entity.headers.content-length");

	/** 指定要返回的数据的地址选项。最主要的用途是用来指定要访问的资源经过内容协商后的结果的URL */
	public static final HttpHeadersEnum CONTENT_LOCATION = new HttpHeadersEnum("Content-Location",
			"http.entity.headers.content-location");
	
	/** 指定资源的MIME类型(mediaType) */
	public static final HttpHeadersEnum CONTENT_TYPE = new HttpHeadersEnum("Content-Type",
			"http.entity.headers.content-type");
	
	/** 消息生成的日期和时间 */
	public static final HttpHeadersEnum DATE = new HttpHeadersEnum("Date", "http.general.headers.date");
	
	public static final HttpHeadersEnum PRAGMA = new HttpHeadersEnum("Pragma", "http.general.headers.pragma");
	
	public static final HttpHeadersEnum VIA = new HttpHeadersEnum("Via", "http.general.headers.via");
	
	public static final HttpHeadersEnum WARNING = new HttpHeadersEnum("Warning", "http.general.headers.warning");
			
	
}
