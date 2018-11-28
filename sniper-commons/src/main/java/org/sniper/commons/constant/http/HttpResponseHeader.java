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
 * HTTP响应头常量类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HttpResponseHeader extends HttpHeader {

	private static final long serialVersionUID = -3918894762166772490L;

	protected HttpResponseHeader(String key, String value) {
		super(key, value);
	}
		
	/** 可以请求网页实体的一个或者多个子范围字段 */
	public static final HttpResponseHeader ACCEPT_RANGES = new HttpResponseHeader("Accept-Ranges",
			"http.response.header.accept-ranges");
	
	/** 对该请求的响应是否可以暴露给页面 */
	public static final HttpResponseHeader ACCESS_CONTROL_ALLOW_CREDENTIALS = new HttpResponseHeader("Access-Control-Allow-Credentials",
			"http.response.header.access-control-allow-credentials");
	
	/** 用于响应预检请求，在进行实际(跨域)请求时可指示哪些HTTP头可用 */
	public static final HttpResponseHeader ACCESS_CONTROL_ALLOW_HEADERS = new HttpResponseHeader("Access-Control-Allow-Headers",
			"http.response.header.access-control-allow-headers");
	
	/** 指定访问资源以响应预检请求时允许的方法 */
	public static final HttpResponseHeader ACCESS_CONTROL_ALLOW_METHODS = new HttpResponseHeader("Access-Control-Allow-Methods",
			"http.response.header.access-control-allow-methods");
	
	/** 指定该响应的资源是否被允许与指定的域共享 */
	public static final HttpResponseHeader ACCESS_CONTROL_ALLOW_ORIGIN = new HttpResponseHeader("Access-Control-Allow-Origin",
			"http.response.header.access-control-allow-origin");
	
	/** 哪些头可以作为响应的一部分而被公开 */
	public static final HttpResponseHeader ACCESS_CONTROL_EXPOSE_HEADERS = new HttpResponseHeader("Access-Control-Expose-Headers",
			"http.response.header.access-control-expose-headers");
	
	/** 可以缓存预检请求结果（即Access-Control-Allow-Methods和Access-Control-Allow-Headers头中包含的信息）的时间长度 */
	public static final HttpResponseHeader ACCESS_CONTROL_MAX_AGE = new HttpResponseHeader("Access-Control-Max-Age",
			"http.response.header.access-control-max-age");
	
	/** 包含对象在代理缓存中的时间，以秒为单位 */
	public static final HttpResponseHeader AGE = new HttpResponseHeader("Age", "http.response.header.age");
	
	public static final HttpResponseHeader CONTENT_SECURITY_POLICY = new HttpResponseHeader("Content-Security-Policy",
			"http.response.header.content-security-policy");
	
	public static final HttpResponseHeader CONTENT_SECURITY_POLICY_REPORT_ONLY = new HttpResponseHeader("Content-Security-Policy-Report-Only",
			"http.response.header.content-security-policy-report-only");
	
	/** 数据片段在整个文件中的位置 */
	public static final HttpResponseHeader CONTENT_RANGE = new HttpResponseHeader("Content-Range", 
			"http.response.header.content-range");
	
	/** 资源的特定版本的标识符 */
	public static final HttpResponseHeader ETAG = new HttpResponseHeader("ETag",
			"http.response.header.etag");
	
	/** 过期日期/时间 */
	public static final HttpResponseHeader EXPIRES = new HttpResponseHeader("Expires",
			"http.response.header.expires");
	
	public static final HttpResponseHeader LAST_MODIFIED = new HttpResponseHeader("Last-Modified",
			"http.response.header.last-modified");
	
	/** 页面重定向的地址 */
	public static final HttpResponseHeader LOCATION = new HttpResponseHeader("Location",
			"http.response.header.location");
	
	public static final HttpResponseHeader PROXY_AUTHENTICATE = new HttpResponseHeader("Proxy-Authenticate",
			"http.response.header.proxy-authenticate");
	
	/** 用户代理需要等待多长时间之后才能继续发送请求 */
	public static final HttpResponseHeader RETRY_AFTER = new HttpResponseHeader("Retry-After",
			"http.response.header.retry-after");
	
	/** 处理请求的源服务器所用到的软件相关信息 */
	public static final HttpResponseHeader SERVER = new HttpResponseHeader("Server",
			"http.response.header.server");
	
	/** 被服务器用来向客户端发送cookie数据 */
	public static final HttpResponseHeader SET_COOKIE = new HttpResponseHeader("Set-Cookie",
			"http.response.header.set-cookie");
	
	@Deprecated
	public static final HttpResponseHeader SET_COOKIE2 = new HttpResponseHeader("Set-Cookie2",
			"http.response.header.set-cookie2");
	
	public static final HttpResponseHeader TRAILER = new HttpResponseHeader("Trailer",
			"http.response.header.trailer");
	
	public static final HttpResponseHeader TRANSFER_ENCODING = new HttpResponseHeader("Transfer-Encoding",
			"http.response.header.transfer-encoding");
	
	public static final HttpResponseHeader VARY = new HttpResponseHeader("Vary",
			"http.response.header.vary");
	
	public static final HttpResponseHeader WWW_AUTHENTICATE = new HttpResponseHeader("WWW-Authenticate",
			"http.response.header.www-authenticate");
		
}
