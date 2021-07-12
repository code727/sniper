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

/**
 * HTTP请求头常量类
 * @author  Daniele
 * @version 1.0
 */
public class HttpRequestHeader extends HttpHeader {

	private static final long serialVersionUID = -7371490587451512766L;

	protected HttpRequestHeader(String key, String value) {
		super(key, value);
	}
	
	/** 浏览器端可以接受的媒体类型 */
	public static final HttpRequestHeader ACCEPT = new HttpRequestHeader("Accept",
			"http.request.header.accept");
	
	/** 服务器处理表单数据所接受的字符集 */
	public static final HttpRequestHeader ACCEPT_CHARSET = new HttpRequestHeader("Accept-Charset",
			"http.request.header.accept-charset");
	
	/** 声明浏览器支持的编码类型 */
	public static final HttpRequestHeader ACCEPT_ENCODING = new HttpRequestHeader("Accept-Encoding",
			"http.request.header.accept-encoding");
	
	/** 告知服务器客户端(浏览器)可以支持的语言 */
	public static final HttpRequestHeader ACCEPT_LANGUAGE = new HttpRequestHeader("Accept-Language", 
			"http.request.header.accept-language");
	
	/** 当发出预检请求时，使用Access-Control-Request-Headers请求头来让服务器知道在实际请求时将使用哪个HTTP头 */
	public static final HttpRequestHeader ACCESS_CONTROL_REQUEST_HEADERS = new HttpRequestHeader("Access-Control-Request-Headers",
			"http.request.header.access-control-request-headers");
	
	/** 当发出预检请求时，使用Access-Control-Request-Method请求头来让服务器知道在实际请求时将使用哪种HTTP方法 */
	public static final HttpRequestHeader ACCESS_CONTROL_REQUEST_METHOD = new HttpRequestHeader("Access-Control-Request-Method",
			"http.request.header.access-control-request-method");
	
	/** 用于通过服务器对服务器进行身份验证的凭据，通常在服务器以401未授权状态响应并且 WWW-Authenticate之后 */
	public static final HttpRequestHeader AUTHORIZATION = new HttpRequestHeader("Authorization",
			"http.request.header.authorization");
	
	/** HTTP cookies */
	public static final HttpRequestHeader COOKIE = new HttpRequestHeader("Cookie",
			"http.request.header.cookie");
	
	/** HTTP cookies */
	@Deprecated
	public static final HttpRequestHeader COOKIE2 = new HttpRequestHeader("Cookie2",
			"http.request.header.cookie2");
	
	public static final HttpRequestHeader DNT = new HttpRequestHeader("DNT",
			"http.request.header.dnt");
	
	/** 期望条件，表示服务器只有在满足此期望条件的情况下才能妥善地处理请求  */
	public static final HttpRequestHeader EXPECT = new HttpRequestHeader("Expect",
			"http.request.header.expect");
	
	public static final HttpRequestHeader FORWARDED = new HttpRequestHeader("Forwarded",
			"http.request.header.forwarded");
	
	/** 电子邮箱地址 */
	public static final HttpRequestHeader FROM = new HttpRequestHeader("From",
			"http.request.header.from");
	
	/** 服务器的域名 */
	public static final HttpRequestHeader HOST = new HttpRequestHeader("Host",
			"http.request.header.host");
	
	/** 条件请求 */
	public static final HttpRequestHeader IF_MATCH = new HttpRequestHeader("If-Match",
			"http.request.header.if-match");
	
	public static final HttpRequestHeader IF_MODIFIED_SINCE = new HttpRequestHeader("If-Modified-Since",
			"http.request.header.if-modified-since");
	
	public static final HttpRequestHeader IF_NONE_MATCH = new HttpRequestHeader("If-None-Match",
			"http.request.header.if-none-match");
	
	public static final HttpRequestHeader IF_RANGE = new HttpRequestHeader("If-Range",
			"http.request.header.if-range");
	
	public static final HttpRequestHeader IF_UNMODIFIED_SINCE = new HttpRequestHeader("If-Unmodified-Since",
			"http.request.header.if-unmodified-since");
	
	public static final HttpRequestHeader ORIGIN = new HttpRequestHeader("Origin",
			"http.request.header.origin");
	
	public static final HttpRequestHeader PROXY_AUTHORIZATION = new HttpRequestHeader("Proxy-Authorization",
			"http.request.header.proxy-authorization");
	
	public static final HttpRequestHeader RANGE = new HttpRequestHeader("Range",
			"http.request.header.range");
	
	public static final HttpRequestHeader REFERER = new HttpRequestHeader("Referer",
			"http.request.header.referer");
	
	public static final HttpRequestHeader TE = new HttpRequestHeader("TE",
			"http.request.header.te");
	
	public static final HttpRequestHeader UPGRADE_INSECURE_REQUESTS = new HttpRequestHeader("Upgrade-Insecure-Requests",
			"http.request.header.upgrade-insecure-requests");
	
	public static final HttpRequestHeader USER_AGENT = new HttpRequestHeader("User-Agent",
			"http.request.header.user-agent");
	
	public static final HttpRequestHeader X_FORWARDED_FOR = new HttpRequestHeader("X-Forwarded-For",
			"http.request.header.x-forwarded-for");
	
	public static final HttpRequestHeader X_FORWARDED_HOST = new HttpRequestHeader("X-Forwarded-Host",
			"http.request.header.x-forwarded-host");
	
	public static final HttpRequestHeader X_FORWARDED_PROTO = new HttpRequestHeader("X-Forwarded-Proto",
			"http.request.header.x-forwarded-proto");
	
	
}
