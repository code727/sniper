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
 * HTTP响应头枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class HttpResponseHeadersEnum extends HttpHeadersEnum {

	private static final long serialVersionUID = -3918894762166772490L;

	protected HttpResponseHeadersEnum(String key, String description) {
		super(key, description);
	}
	
	/** 可以请求网页实体的一个或者多个子范围字段 */
	public static final HttpResponseHeadersEnum ACCEPT_RANGES = new HttpResponseHeadersEnum("Accept-Ranges",
			"http.response.headers.accept-ranges");
	
	/** 对该请求的响应是否可以暴露给页面 */
	public static final HttpResponseHeadersEnum ACCESS_CONTROL_ALLOW_CREDENTIALS = new HttpResponseHeadersEnum("Access-Control-Allow-Credentials",
			"http.response.headers.access-control-allow-credentials");
	
	/** 用于响应预检请求，在进行实际(跨域)请求时可指示哪些HTTP头可用 */
	public static final HttpResponseHeadersEnum ACCESS_CONTROL_ALLOW_HEADERS = new HttpResponseHeadersEnum("Access-Control-Allow-Headers",
			"http.response.headers.access-control-allow-headers");
	
	/** 指定访问资源以响应预检请求时允许的方法 */
	public static final HttpResponseHeadersEnum ACCESS_CONTROL_ALLOW_METHODS = new HttpResponseHeadersEnum("Access-Control-Allow-Methods",
			"http.response.headers.access-control-allow-methods");
	
	/** 指定该响应的资源是否被允许与指定的域共享 */
	public static final HttpResponseHeadersEnum ACCESS_CONTROL_ALLOW_ORIGIN = new HttpResponseHeadersEnum("Access-Control-Allow-Origin",
			"http.response.headers.access-control-allow-origin");
	
	/** 哪些头可以作为响应的一部分而被公开 */
	public static final HttpResponseHeadersEnum ACCESS_CONTROL_EXPOSE_HEADERS = new HttpResponseHeadersEnum("Access-Control-Expose-Headers",
			"http.response.headers.access-control-expose-headers");
	
	/** 可以缓存预检请求结果（即Access-Control-Allow-Methods和Access-Control-Allow-Headers头中包含的信息）的时间长度 */
	public static final HttpResponseHeadersEnum ACCESS_CONTROL_MAX_AGE = new HttpResponseHeadersEnum("Access-Control-Max-Age",
			"http.response.headers.access-control-max-age");
	
	/** 包含对象在代理缓存中的时间，以秒为单位 */
	public static final HttpResponseHeadersEnum AGE = new HttpResponseHeadersEnum("Age", "http.response.headers.age");
	
	public static final HttpResponseHeadersEnum CONTENT_SECURITY_POLICY = new HttpResponseHeadersEnum("Content-Security-Policy",
			"http.response.headers.content-security-policy");
	
	public static final HttpResponseHeadersEnum CONTENT_SECURITY_POLICY_REPORT_ONLY = new HttpResponseHeadersEnum("Content-Security-Policy-Report-Only",
			"http.response.headers.content-security-policy-report-only");
	
	/** 数据片段在整个文件中的位置 */
	public static final HttpResponseHeadersEnum CONTENT_RANGE = new HttpResponseHeadersEnum("Content-Range", 
			"http.response.headers.content-range");
	
	/** 资源的特定版本的标识符 */
	public static final HttpResponseHeadersEnum ETAG = new HttpResponseHeadersEnum("ETag",
			"http.response.headers.etag");
	
	/** 过期日期/时间 */
	public static final HttpResponseHeadersEnum EXPIRES = new HttpResponseHeadersEnum("Expires",
			"http.response.headers.expires");
	
	public static final HttpResponseHeadersEnum LAST_MODIFIED = new HttpResponseHeadersEnum("Last-Modified",
			"http.response.headers.last-modified");
	
	/** 页面重定向的地址 */
	public static final HttpResponseHeadersEnum LOCATION = new HttpResponseHeadersEnum("Location",
			"http.response.headers.location");
	
	public static final HttpResponseHeadersEnum PROXY_AUTHENTICATE = new HttpResponseHeadersEnum("Proxy-Authenticate",
			"http.response.headers.proxy-authenticate");
	
	/** 用户代理需要等待多长时间之后才能继续发送请求 */
	public static final HttpResponseHeadersEnum RETRY_AFTER = new HttpResponseHeadersEnum("Retry-After",
			"http.response.headers.retry-after");
	
	/** 处理请求的源服务器所用到的软件相关信息 */
	public static final HttpResponseHeadersEnum SERVER = new HttpResponseHeadersEnum("Server",
			"http.response.headers.server");
	
	/** 被服务器用来向客户端发送cookie数据 */
	public static final HttpResponseHeadersEnum SET_COOKIE = new HttpResponseHeadersEnum("Set-Cookie",
			"http.response.headers.set-cookie");
	
	@Deprecated
	/** 被服务器用来向客户端发送cookie数据 */
	public static final HttpResponseHeadersEnum SET_COOKIE2 = new HttpResponseHeadersEnum("Set-Cookie2",
			"http.response.headers.set-cookie2");
	
	public static final HttpResponseHeadersEnum TRAILER = new HttpResponseHeadersEnum("Trailer",
			"http.response.headers.trailer");
	
	public static final HttpResponseHeadersEnum TRANSFER_ENCODING = new HttpResponseHeadersEnum("Transfer-Encoding",
			"http.response.headers.transfer-encoding");
	
	public static final HttpResponseHeadersEnum VARY = new HttpResponseHeadersEnum("Vary",
			"http.response.headers.vary");
	
	public static final HttpResponseHeadersEnum WWW_AUTHENTICATE = new HttpResponseHeadersEnum("WWW-Authenticate",
			"http.response.headers.www-authenticate");
	
}
