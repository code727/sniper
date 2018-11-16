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

package org.sniper.commons.enums.http.headers;

/**
 * HTTP请求头枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class HttpRequestHeadersEnum extends HttpHeadersEnum {

	private static final long serialVersionUID = -7371490587451512766L;

	protected HttpRequestHeadersEnum(String key, String description) {
		super(key, description);
	}
	
	/** 浏览器端可以接受的媒体类型 */
	public static final HttpRequestHeadersEnum ACCEPT = new HttpRequestHeadersEnum("Accept",
			"http.request.headers.accept");
	
	/** 服务器处理表单数据所接受的字符集 */
	public static final HttpRequestHeadersEnum ACCEPT_CHARSET = new HttpRequestHeadersEnum("Accept-Charset",
			"http.request.headers.accept-charset");
	
	/** 声明浏览器支持的编码类型 */
	public static final HttpRequestHeadersEnum ACCEPT_ENCODING = new HttpRequestHeadersEnum("Accept-Encoding",
			"http.request.headers.accept-encoding");
	
	/** 告知服务器客户端(浏览器)可以支持的语言 */
	public static final HttpRequestHeadersEnum ACCEPT_LANGUAGE = new HttpRequestHeadersEnum("Accept-Language", 
			"http.request.headers.accept-language");
	
	/** 当发出预检请求时，使用Access-Control-Request-Headers请求头来让服务器知道在实际请求时将使用哪个HTTP头 */
	public static final HttpRequestHeadersEnum ACCESS_CONTROL_REQUEST_HEADERS = new HttpRequestHeadersEnum("Access-Control-Request-Headers",
			"http.request.headers.access-control-request-headers");
	
	/** 当发出预检请求时，使用Access-Control-Request-Method请求头来让服务器知道在实际请求时将使用哪种HTTP方法 */
	public static final HttpRequestHeadersEnum ACCESS_CONTROL_REQUEST_METHOD = new HttpRequestHeadersEnum("Access-Control-Request-Method",
			"http.request.headers.access-control-request-method");
	
	/** 用于通过服务器对服务器进行身份验证的凭据，通常在服务器以401未授权状态响应并且 WWW-Authenticate之后 */
	public static final HttpRequestHeadersEnum AUTHORIZATION = new HttpRequestHeadersEnum("Authorization",
			"http.request.headers.authorization");
	
	/** HTTP cookies */
	public static final HttpRequestHeadersEnum COOKIE = new HttpRequestHeadersEnum("Cookie",
			"http.request.headers.cookie");
	
	/** HTTP cookies */
	@Deprecated
	public static final HttpRequestHeadersEnum COOKIE2 = new HttpRequestHeadersEnum("Cookie2",
			"http.request.headers.cookie2");
	
	public static final HttpRequestHeadersEnum DNT = new HttpRequestHeadersEnum("DNT",
			"http.request.headers.dnt");
	
	/** 期望条件，表示服务器只有在满足此期望条件的情况下才能妥善地处理请求  */
	public static final HttpRequestHeadersEnum EXPECT = new HttpRequestHeadersEnum("Expect",
			"http.request.headers.expect");
	
	public static final HttpRequestHeadersEnum FORWARDED = new HttpRequestHeadersEnum("Forwarded",
			"http.request.headers.forwarded");
	
	/** 电子邮箱地址 */
	public static final HttpRequestHeadersEnum FROM = new HttpRequestHeadersEnum("From",
			"http.request.headers.from");
	
	/** 服务器的域名 */
	public static final HttpRequestHeadersEnum HOST = new HttpRequestHeadersEnum("Host",
			"http.request.headers.host");
	
	/** 条件请求 */
	public static final HttpRequestHeadersEnum IF_MATCH = new HttpRequestHeadersEnum("If-Match",
			"http.request.headers.if-match");
	
	public static final HttpRequestHeadersEnum IF_MODIFIED_SINCE = new HttpRequestHeadersEnum("If-Modified-Since",
			"http.request.headers.if-modified-since");
	
	public static final HttpRequestHeadersEnum IF_NONE_MATCH = new HttpRequestHeadersEnum("If-None-Match",
			"http.request.headers.if-none-match");
	
	public static final HttpRequestHeadersEnum IF_RANGE = new HttpRequestHeadersEnum("If-Range",
			"http.request.headers.if-range");
	
	public static final HttpRequestHeadersEnum IF_UNMODIFIED_SINCE = new HttpRequestHeadersEnum("If-Unmodified-Since",
			"http.request.headers.if-unmodified-since");
	
	public static final HttpRequestHeadersEnum ORIGIN = new HttpRequestHeadersEnum("Origin",
			"http.request.headers.origin");
	
	public static final HttpRequestHeadersEnum PROXY_AUTHORIZATION = new HttpRequestHeadersEnum("Proxy-Authorization",
			"http.request.headers.proxy-authorization");
	
	public static final HttpRequestHeadersEnum RANGE = new HttpRequestHeadersEnum("Range",
			"http.request.headers.range");
	
	public static final HttpRequestHeadersEnum REFERER = new HttpRequestHeadersEnum("Referer",
			"http.request.headers.referer");
	
	public static final HttpRequestHeadersEnum TE = new HttpRequestHeadersEnum("TE",
			"http.request.headers.te");
	
	public static final HttpRequestHeadersEnum UPGRADE_INSECURE_REQUESTS = new HttpRequestHeadersEnum("Upgrade-Insecure-Requests",
			"http.request.headers.upgrade-insecure-requests");
	
	public static final HttpRequestHeadersEnum USER_AGENT = new HttpRequestHeadersEnum("User-Agent",
			"http.request.headers.user-agent");
	
	public static final HttpRequestHeadersEnum X_FORWARDED_FOR = new HttpRequestHeadersEnum("X-Forwarded-For",
			"http.request.headers.x-forwarded-for");
	
	public static final HttpRequestHeadersEnum X_FORWARDED_HOST = new HttpRequestHeadersEnum("X-Forwarded-Host",
			"http.request.headers.x-forwarded-host");
	
	public static final HttpRequestHeadersEnum X_FORWARDED_PROTO = new HttpRequestHeadersEnum("X-Forwarded-Proto",
			"http.request.headers.x-forwarded-proto");
	
	
}
