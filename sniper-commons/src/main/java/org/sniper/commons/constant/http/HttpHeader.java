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

package org.sniper.commons.constant.http;

import org.sniper.commons.constant.AbstractLocaleConstant;

/**
 * HTTP头常量类
 * @author  Daniele
 * @version 1.0
 */
public class HttpHeader extends AbstractLocaleConstant<String> {

	private static final long serialVersionUID = 7469645351098963000L;
	
	protected HttpHeader(String key, String value) {
		super(key, value);
	}

	/** 列出资源支持的一组方法 */
	public static final HttpHeader ALLOW = new HttpHeader("Allow", "http.entity.header.allow");
	
	/** 用于指定缓存机制的指令，请求和响应中通过此指令来实现缓存机制 */
	public static final HttpHeader CACHE_CONTROL = new HttpHeader("Cache-Control",
			"http.general.header.cache-control");
	
	/** 控制当前事务完成后网络连接是否保持打开状态。 
	 *  如果发送的值保持活动，则该连接是持久的而不是关闭的，允许对相同服务器的后续请求完成 */
	public static final HttpHeader CONNECTION = new HttpHeader("Connection",
			"http.general.header.connection");
	
	/** 用于压缩媒体类型，让客户端知道如何解码以获取Content-Type头引用的媒体类型 */
	public static final HttpHeader CONTENT_ENCODING = new HttpHeader("Content-Encoding",
			"http.entity.header.content-encoding");
	
	/** 指定内容是否期望在浏览器中内联显示，
	 *  即作为网页或作为网页的一部分，或作为附件显示，在本地下载并保存 */
	public static final HttpHeader CONTENT_DISPOSITION = new HttpHeader("Content-Disposition",
			"http.general.header.content-disposition");
	
	/** 用于定义页面所使用的语言代码 */
	public static final HttpHeader CONTENT_LANGUAGE = new HttpHeader("Content-Language",
			"http.entity.header.content-language");
	
	/** 指定发送到接收方的消息主体的大小，以十进制数字为单位 */
	public static final HttpHeader CONTENT_LENGTH = new HttpHeader("Content-Length",
			"http.entity.header.content-length");

	/** 指定要返回的数据的地址选项。最主要的用途是用来指定要访问的资源经过内容协商后的结果的URL */
	public static final HttpHeader CONTENT_LOCATION = new HttpHeader("Content-Location",
			"http.entity.header.content-location");
	
	/** 指定资源的MIME类型(mediaType) */
	public static final HttpHeader CONTENT_TYPE = new HttpHeader("Content-Type",
			"http.entity.header.content-type");
	
	/** 消息生成的日期和时间 */
	public static final HttpHeader DATE = new HttpHeader("Date", "http.general.header.date");
	
	public static final HttpHeader KEEP_ALIVE = new HttpHeader("Keep-Alive", "http.general.header.keep-alive");
	
	public static final HttpHeader PRAGMA = new HttpHeader("Pragma", "http.general.header.pragma");
	
	public static final HttpHeader VIA = new HttpHeader("Via", "http.general.header.via");
	
	public static final HttpHeader WARNING = new HttpHeader("Warning", "http.general.header.warning");
	
}
