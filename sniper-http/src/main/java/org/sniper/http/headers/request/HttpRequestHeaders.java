/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-7-5
 */

package org.sniper.http.headers.request;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.sniper.beans.parameter.DefaultParameters;
import org.sniper.beans.parameter.Parameters;
import org.sniper.commons.enums.http.AcceptEncodingEnum;
import org.sniper.commons.enums.http.AuthenticationEnum;
import org.sniper.commons.enums.http.HttpHeaderEnum;
import org.sniper.commons.enums.http.HttpMethodEnum;
import org.sniper.commons.enums.http.HttpProtocolEnum;
import org.sniper.commons.enums.http.HttpRequestHeaderEnum;
import org.sniper.commons.enums.http.TEEnum;
import org.sniper.commons.kv.KeyValuePair;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.http.headers.Forwarded;
import org.sniper.http.headers.HttpHeaders;
import org.sniper.http.headers.MediaType;

/**
 * HTTP请求头
 * @author  Daniele
 * @version 1.0
 */
public class HttpRequestHeaders extends HttpHeaders {
		
	private static final long serialVersionUID = 850698081249888505L;
	
	/**
	 * 设置Accept
	 * @author Daniele 
	 * @param mediaTypes
	 */
	public void setAccept(List<MediaType> mediaTypes) {
		set(HttpRequestHeaderEnum.ACCEPT.getKey(), CollectionUtils.join(mediaTypes, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取Accept
	 * @author Daniele 
	 * @return
	 */
	public List<MediaType> getAccept() {
		String first = getFirst(HttpRequestHeaderEnum.ACCEPT.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			List<MediaType> list = CollectionUtils.newArrayList(values.length);
			MediaType mediaType;
			for (String value : values) {
				mediaType = MediaType.parse(value);
				if (mediaType != null) {
					list.add(mediaType);
				}
			}
			return list;
		}
		
		return null;
	}
			
	/**
	 * 设置客户端可以处理的字符集类型(Accept-Charset)
	 * @author Daniele 
	 * @param charsets
	 */
	public void setAcceptCharset(List<AcceptCharset> charsets) {
		set(HttpRequestHeaderEnum.ACCEPT_CHARSET.getKey(), CollectionUtils.join(charsets, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取客户端可以处理的字符集类型(Accept-Charset)
	 * @author Daniele 
	 * @return
	 */
	public List<AcceptCharset> getAcceptCharset() {
		String first = getFirst(HttpRequestHeaderEnum.ACCEPT_CHARSET.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			List<AcceptCharset> list = CollectionUtils.newArrayList(values.length);
			String charsetName;
			String weight;
			
			for (String value : values) {
				weight = StringUtils.afterLast(value, StringUtils.ASSIGNMENT);
				charsetName = StringUtils.beforeFrist(value, StringUtils.SEMICOLON);
				
				if (StringUtils.isBlank(charsetName)) {
					charsetName = value;
				}
				
				if (StringUtils.isNotBlank(weight)) {
					list.add(new AcceptCharset(charsetName, Double.parseDouble(weight)));
				} else {
					list.add(new AcceptCharset(charsetName));
				}
			}
			return list;
		}
		
		return null;
	}
	
	/**
	 * 设置客户端能够理解的内容编码方式(Accept-Encoding)
	 * @author Daniele 
	 * @param acceptEncoding
	 */
	public void setAcceptEncoding(List<AcceptEncoding> encodings) {
		set(HttpRequestHeaderEnum.ACCEPT_ENCODING.getKey(), CollectionUtils.join(encodings, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取设置客户端能够理解的内容编码方式(Accept-Encoding)
	 * @author Daniele 
	 * @return
	 */
	public List<AcceptEncoding> getAcceptEncoding() {
		String first = getFirst(HttpRequestHeaderEnum.ACCEPT_ENCODING.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(values)) {
			List<AcceptEncoding> list = CollectionUtils.newArrayList();
			
			for (String value : values) {
				
				String encoding = StringUtils.afterLast(value, StringUtils.ASSIGNMENT);
				String weight = StringUtils.beforeFrist(value, StringUtils.SEMICOLON);
				
				AcceptEncodingEnum encodingEnum;
				if (StringUtils.isNotEmpty(encoding)) {
					encodingEnum = AcceptEncodingEnum.resolve(encoding);
				} else {
					encodingEnum = AcceptEncodingEnum.resolve(value);
				}
				
				if (encodingEnum != null) {
					if (StringUtils.isNotBlank(weight)) {
						list.add(new AcceptEncoding(encodingEnum, Double.parseDouble(weight)));
					} else {
						list.add(new AcceptEncoding(encodingEnum));
					}
				}
			}
			return list;
		}
		
		return null;
	}
		
	/**
	 * 设置客户端可以理解的自然语言(Accept-Language)
	 * @author Daniele 
	 * @param acceptLanguages
	 */
	public void setAcceptLanguage(AcceptLanguage acceptLanguage) {
		set(HttpRequestHeaderEnum.ACCEPT_LANGUAGE.getKey(), acceptLanguage != null ? acceptLanguage.toString() : null);
	}
	
	/**
	 * 获取客户端可以理解的自然语言(Accept-Language)
	 * @author Daniele 
	 * @return
	 */
	public String getAcceptLanguage() {
		return getFirst(HttpRequestHeaderEnum.ACCEPT_LANGUAGE.getKey());
	}
	
	/**
	 * 设置通知服务器在真正的请求中采用的请求头列表(Access-Control-Request-Headers)
	 * @author Daniele 
	 * @param requestHeaders
	 */
	public void setAccessControlRequestHeaders(List<String> requestHeaders) {
		set(HttpRequestHeaderEnum.ACCESS_CONTROL_REQUEST_HEADERS.getKey(), CollectionUtils.join(requestHeaders, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取通知服务器在真正的请求中采用的请求头列表(Access-Control-Request-Headers)
	 * @author Daniele 
	 * @return
	 */
	public List<String> getAccessControlRequestHeaders() {
		String first = getFirst(HttpRequestHeaderEnum.ACCESS_CONTROL_REQUEST_HEADERS.getKey());
		return ArrayUtils.toList(StringUtils.split(first, VALUE_SEPARATOR));
	}
	
	/**
	 * 设置服务器在真正的请求中会采用的HTTP方法(Access-Control-Request-Method)
	 * @author Daniele 
	 * @param method
	 */
	public void setAccessControlRequestMethod(HttpMethodEnum method) {
		set(HttpRequestHeaderEnum.ACCESS_CONTROL_REQUEST_METHOD.getKey(), method != null ? method.name() : null);
	}
	
	/**
	 * 获取服务器在真正的请求中会采用的HTTP方法(Access-Control-Request-Method)
	 * @author Daniele 
	 * @return
	 */
	public HttpMethodEnum getAccessControlRequestMethod() {
		return HttpMethodEnum.resolve(getFirst(HttpRequestHeaderEnum.ACCESS_CONTROL_REQUEST_METHOD.getKey()));
	}
		
	/**
	 * 设置服务器用于验证用户代理身份的凭证(Authorization)
	 * @author Daniele 
	 * @param authorization
	 */
	public void setAuthorization(Authorization authorization) {
		set(HttpRequestHeaderEnum.AUTHORIZATION.getKey(), authorization != null ? authorization.toString() : null);
	}
	
	/**
	 * 获取服务器用于验证用户代理身份的凭证(Authorization)
	 * @author Daniele 
	 * @return
	 */
	public Authorization getAuthorization() {
		String first = getFirst(HttpRequestHeaderEnum.AUTHORIZATION.getKey());
		String type = StringUtils.beforeFrist(first, StringUtils.SPACE);
		String credentials = StringUtils.afterFrist(first, StringUtils.SPACE);
		
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(credentials)) {
			AuthenticationEnum authenticationType = AuthenticationEnum.resolve(type);
			if (authenticationType != null) {
				return new Authorization(authenticationType, credentials);
			}
		}
		
		return null;
	}
		
	/**
	 * 设置Content-Length
	 * @author Daniele 
	 * @param contentLength
	 */
	public void setContentLength(long contentLength) {
		set(HttpHeaderEnum.CONTENT_LENGTH.getKey(), Long.toString(contentLength));
	}
	
	/**
	 * 获取Content-Length
	 * @author Daniele 
	 * @return
	 */
	public long getContentLength() {
		String value = getFirst(HttpHeaderEnum.CONTENT_LENGTH.getKey());
		return value != null ? Long.parseLong(value) : -1;
	}
		
	/**
	 * 设置Cookie
	 * @author Daniele 
	 * @param cookies
	 */
	public void setCookie(Parameters<String, Object> cookies) {
		set(HttpRequestHeaderEnum.COOKIE.getKey(), cookies != null
				? MapUtils.join(cookies.getItems(), StringUtils.ASSIGNMENT, NAME_VALUE_PAIR_SEPARATOR) : null);
	}
	
	/**
	 * 设置Cookie
	 * @author Daniele 
	 * @return
	 */
	public Parameters<String, Object> getCookie() {
		String first = getFirst(HttpRequestHeaderEnum.COOKIE.getKey());
		String[] nameValuePairs = StringUtils.split(first, NAME_VALUE_PAIR_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(nameValuePairs)) {
			Parameters<String, Object> cookie = new DefaultParameters<String, Object>();
			KeyValuePair<String, String> keyValuePair;
			
			for (String nameValuePair : nameValuePairs) {
				keyValuePair = StringUtils.splitToKeyValuePair(nameValuePair, StringUtils.ASSIGNMENT);
				if (keyValuePair != null) {
					cookie.add(keyValuePair.getKey(), keyValuePair.getValue());
				}
			}
			return cookie;
		}
		
		return null;
	}
	
	/**
	 * 设置是否禁止目标站点对个人信息进行追踪(更注重个人隐私还是定制化内容)
	 * @author Daniele 
	 * @param dnt 0:允许, 1:禁止
	 */
	public void setDNT(int dnt) {
		set(HttpRequestHeaderEnum.DNT.getKey(), String.valueOf(NumberUtils.rangeLimit(dnt, 0, 1)));
	}
	
	/**
	 * 设置是否禁止目标站点对个人信息进行追踪(更注重个人隐私还是定制化内容)
	 * @author Daniele 
	 * @return
	 */
	public int getDNT() {
		return NumberUtils.rangeLimit(NumberUtils.toIntegerValue(getFirst(HttpRequestHeaderEnum.DNT.getKey())), 0, 1)  ;
	}
	
	/**
	 * 设置期望条件
	 * @author Daniele 
	 * @param expect
	 */
	public void setExpect(String expect) {
		set(HttpRequestHeaderEnum.EXPECT.getKey(), expect);
	}
	
	/**
	 * 获取期望条件
	 * @author Daniele 
	 * @return
	 */
	public String getExpect() {
		return getFirst(HttpRequestHeaderEnum.EXPECT.getKey());
	}
	
	/**
	 * 设置代理转发信息
	 * @author Daniele 
	 * @param forwarded
	 */
	public void setForwarded(Forwarded forwarded) {
		set(HttpRequestHeaderEnum.FORWARDED.getKey(), forwarded != null ? forwarded.toString() : null);
	}
	
	/**
	 * 获取代理转发信息
	 * @author Daniele 
	 * @return
	 */
	public Forwarded getForwarded() {
		String first = getFirst(HttpRequestHeaderEnum.FORWARDED.getKey());
		String[] nameValuePairs = StringUtils.split(first, NAME_VALUE_PAIR_SEPARATOR);
		
		if (ArrayUtils.isNotEmpty(nameValuePairs)) {
			Forwarded forwarded = new Forwarded();
			for (String nameValuePair : nameValuePairs) {
				if (StringUtils.startsWithIgnoreCase(nameValuePair, "by=")) {
					forwarded.setBy(StringUtils.afterFristIgnoreCase(nameValuePair, "by="));
					continue;
				}
				
				if (StringUtils.startsWithIgnoreCase(nameValuePair, "for=")) {
					String[] forItems = nameValuePair.split(VALUE_SEPARATOR);
					for (String forItem : forItems) {
						forwarded.addFor(StringUtils.afterFristIgnoreCase(forItem, "for="));
					}
					continue;
				}
				
				if (StringUtils.startsWithIgnoreCase(nameValuePair, "host=")) {
					forwarded.setHost(StringUtils.afterFristIgnoreCase(nameValuePair, "host="));
					continue;
				}
				
				if (StringUtils.startsWithIgnoreCase(nameValuePair, "proto=")) {
					forwarded.setProtocol(HttpProtocolEnum.resolve(StringUtils.afterFristIgnoreCase(nameValuePair, "proto=")));
					continue;
				}
			}
			
			return forwarded;
		}
		
		return null;
	}
	
	/**
	 * 设置来源(属于发送请求的用户电子邮箱地址)
	 * @author Daniele 
	 * @param expect
	 */
	public void setFrom(String from) {
		set(HttpRequestHeaderEnum.FROM.getKey(), from);
	}
	
	/**
	 * 获取来源(属于发送请求的用户电子邮箱地址)
	 * @author Daniele 
	 * @return
	 */
	public String getFrom() {
		return getFirst(HttpRequestHeaderEnum.FROM.getKey());
	}
	
	/**
	 * 设置服务器域名
	 * @author Daniele 
	 * @param host
	 */
	public void setHost(String host) {
		set(HttpRequestHeaderEnum.HOST.getKey(), host);
	}
	
	/**
	 * 获取服务器域名
	 * @author Daniele 
	 * @return
	 */
	public String gethost() {
		return getFirst(HttpRequestHeaderEnum.HOST.getKey());
	}
	
	/**
	 * 设置条件请求(当资源的eTag属性值与列表中的值相匹配时返回该资源)
	 * @author Daniele 
	 * @param eTagValues
	 */
	public void setIfMatch(List<String> eTagValues) {
		set(HttpRequestHeaderEnum.IF_MATCH.getKey(), CollectionUtils.join(eTagValues, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取条件请求(当资源的eTag属性值与列表中的值相匹配时返回该资源)
	 * @author Daniele 
	 * @return
	 */
	public List<String> getIfMatch() {
		String first = getFirst(HttpRequestHeaderEnum.IF_MATCH.getKey());
		return ArrayUtils.toList(StringUtils.split(first, VALUE_SEPARATOR));
	}
	
	/**
	 * 设置资源返回的日期时间条件
	 * @author Daniele 
	 * @param date
	 */
	public void setIfModifiedSince(Date date) {
		set(HttpRequestHeaderEnum.IF_MODIFIED_SINCE.getKey(), date != null ? DateUtils.getGMTDateFormat(Locale.US).format(date) : null);
	}
	
	/**
	 * 获取资源返回的日期时间条件
	 * @author Daniele 
	 * @return
	 */
	public Date getIfModifiedSince() {
		String first = getFirst(HttpRequestHeaderEnum.IF_MODIFIED_SINCE.getKey());
		return StringUtils.isNotBlank(first) ? DateUtils.getGMTDateFormat(Locale.US).parse(first, new ParsePosition(0)) : null;
	}
	
	/**
	 * 设置条件请求(当资源的eTag属性值与列表中的任何值不匹配时返回该资源)
	 * @author Daniele 
	 * @param eTagValues
	 */
	public void setIfNoneMatch(List<String> eTagValues) {
		set(HttpRequestHeaderEnum.IF_NONE_MATCH.getKey(), CollectionUtils.join(eTagValues, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取条件请求(当资源的eTag属性值与列表中的任何值不匹配时返回该资源)
	 * @author Daniele 
	 * @return
	 */
	public List<String> getIfNoneMatch() {
		String first = getFirst(HttpRequestHeaderEnum.IF_NONE_MATCH.getKey());
		return ArrayUtils.toList(StringUtils.split(first, VALUE_SEPARATOR));
	}
	
	/**
	 * 设置条件请求(当字段值中的条件得到满足时，Range头字段才会起作用，同时服务器回复206 部分内容状态码，以及Range头字段请求的相应部分；
	 * 如果字段值中的条件没有得到满足，服务器将会返回 200 OK 状态码，并返回完整的请求资源)
	 * @author Daniele 
	 * @param range
	 */
	public void setIfRange(Object range) {
		if (range instanceof Date) {
			set(HttpRequestHeaderEnum.IF_RANGE.getKey(), DateUtils.getGMTDateFormat(Locale.US).format(range));
		} else {
			set(HttpRequestHeaderEnum.IF_RANGE.getKey(), range != null ? range.toString() : null);
		}
	}
	
	/**
	 * 获取条件请求(当字段值中的条件得到满足时，Range头字段才会起作用，同时服务器回复206 部分内容状态码，以及Range 头字段请求的相应部分；
	 * 如果字段值中的条件没有得到满足，服务器将会返回 200 OK 状态码，并返回完整的请求资源)
	 * @author Daniele 
	 * @return
	 */
	public Object getIfRange() {
		String first = getFirst(HttpRequestHeaderEnum.IF_RANGE.getKey());
		if (StringUtils.isNotBlank(first)) {
			Date date = DateUtils.getGMTDateFormat(Locale.US).parse(first, new ParsePosition(0));
			if (date != null) {
				return date;
			}
		}
		
		return first;
	}
	
	/**
	 * 设置条件请求(只有当资源在指定的时间之后没有进行过修改的情况下，服务器才会返回请求的资源，或是接受 POST或其他non-safe方法的请求)
	 * @author Daniele 
	 * @param date
	 */
	public void setIfUnmodifiedSince(Date date) {
		set(HttpRequestHeaderEnum.IF_UNMODIFIED_SINCE.getKey(),
				date != null ? DateUtils.getGMTDateFormat(Locale.US).format(date) : null);
	}
	
	/**
	 * 获取条件请求(只有当资源在指定的时间之后没有进行过修改的情况下，服务器才会返回请求的资源，或是接受 POST或其他non-safe方法的请求)
	 * @author Daniele 
	 * @return
	 */
	public Date getIfUnmodifiedSince() {
		String first = getFirst(HttpRequestHeaderEnum.IF_UNMODIFIED_SINCE.getKey());
		return StringUtils.isNotBlank(first) ? DateUtils.getGMTDateFormat(Locale.US).parse(first, new ParsePosition(0)) : null;
	}
	
	/**
	 * 设置请求来自于哪个站点
	 * @author Daniele 
	 * @param url
	 */
	public void setOrigin(URL url) {
		set(HttpRequestHeaderEnum.ORIGIN.getKey(), url != null ? url.toString() : null);
	}
	
	/**
	 * 获取请求来自于哪个站点
	 * @author Daniele 
	 * @return
	 */
	public URL getOrigin() {
		String first = getFirst(HttpRequestHeaderEnum.ORIGIN.getKey());
		try {
			return StringUtils.isNotBlank(first) ? new URL(first) : null;
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	/**
	 * 设置提供给代理服务器的用于验证用户身份的凭证(Proxy-Authorization)
	 * @author Daniele 
	 * @param authorization
	 */
	public void setProxyAuthorization(Authorization authorization) {
		set(HttpRequestHeaderEnum.PROXY_AUTHORIZATION.getKey(), authorization != null ? authorization.toString() : null);
	}
		
	/**
	 * 获取提供给代理服务器的用于验证用户身份的凭证(Proxy-Authorization)
	 * @author Daniele 
	 * @return
	 */
	public Authorization getProxyAuthorization() {
		String first = getFirst(HttpRequestHeaderEnum.PROXY_AUTHORIZATION.getKey());
		String type = StringUtils.beforeFrist(first, StringUtils.SPACE);
		String credentials = StringUtils.afterFrist(first, StringUtils.SPACE);
		
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(credentials)) {
			AuthenticationEnum authenticationType = AuthenticationEnum.resolve(type);
			if (authenticationType != null) {
				return new Authorization(authenticationType, credentials);
			}
		}
		
		return null;
	}
	
	/**
	 * 设置服务器返回的文件范围
	 * @author Daniele 
	 * @param range
	 */
	public void setRange(Range range) {
		set(HttpRequestHeaderEnum.RANGE.getKey(), range != null ? range.toString() : null);
	}
	
	/**
	 * 获取服务器返回的文件范围
	 * @author Daniele 
	 * @return
	 */
	public Range getRange() {
		String first = getFirst(HttpRequestHeaderEnum.RANGE.getKey());
		String unit = StringUtils.beforeFrist(first, StringUtils.ASSIGNMENT);
		String[] rangeItems = StringUtils.split(StringUtils.afterFrist(first, StringUtils.ASSIGNMENT), VALUE_SEPARATOR);  
		
		if (StringUtils.isNotBlank(unit) && ArrayUtils.isNotEmpty(rangeItems)) {
			List<RangeValue> rangeValues = CollectionUtils.newArrayList(rangeItems.length);
			for (String rangeItem : rangeItems) {
				Long start = NumberUtils.toLong(StringUtils.beforeFrist(rangeItem, StringUtils.CONNECTION_LINE));
				if (start != null) {
					rangeValues.add(new RangeValue(start, NumberUtils.toLong(StringUtils.afterFrist(rangeItem, StringUtils.CONNECTION_LINE))));
				}
			}
			return new Range(unit, rangeValues);
		}
		
		return null;
	}
	
	/**
	 * 设置当前页面的来源页面的地址
	 * @author Daniele 
	 * @param url
	 */
	public void setReferer(URL url) {
		set(HttpRequestHeaderEnum.REFERER.getKey(), url != null ? url.toString() : null);
	}
	
	/**
	 * 获取当前页面的来源页面的地址
	 * @author Daniele 
	 * @return
	 */
	public URL getReferer() {
		String first = getFirst(HttpRequestHeaderEnum.REFERER.getKey());
		try {
			return StringUtils.isNotBlank(first) ? new URL(first) : null;
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	/**
	 * 设置用户代理希望使用的传输编码类型
	 * @author Daniele 
	 * @param te
	 */
	public void setTE(List<TE> tes) {
		set(HttpRequestHeaderEnum.TE.getKey(), CollectionUtils.join(tes, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取用户代理希望使用的传输编码类型
	 * @author Daniele 
	 * @return
	 */
	public List<TE> getTE() {
		String first = getFirst(HttpRequestHeaderEnum.TE.getKey());
		String[] values = StringUtils.split(first, VALUE_SEPARATOR);

		if (ArrayUtils.isNotEmpty(values)) {
			List<TE> list = CollectionUtils.newArrayList();
			String weight;
			String encoding;
			TEEnum algorithm;
			
			for (String value : values) {
				weight = StringUtils.afterLast(value, StringUtils.ASSIGNMENT);
				encoding = StringUtils.beforeFrist(value, StringUtils.SEMICOLON);

				if (StringUtils.isNotEmpty(encoding)) {
					algorithm = TEEnum.resolve(encoding);
				} else {
					algorithm = TEEnum.resolve(value);
				}

				if (algorithm != null) {
					if (StringUtils.isNotBlank(weight)) {
						list.add(new TE(algorithm, Double.parseDouble(weight)));
					} else {
						list.add(new TE(algorithm));
					}
				}
			}
			return list;
		}
		
		return null;
	}
		
	/**
	 * 设置客户端优先选择加密及带有身份验证的响应(Upgrade-Insecure-Requests)
	 * @author Daniele 
	 * @param value
	 */
	public void setUpgradeInsecureRequests(String value) {
		set(HttpRequestHeaderEnum.UPGRADE_INSECURE_REQUESTS.getKey(), value);
	}
	
	/**
	 * 获取客户端优先选择加密及带有身份验证的响应(Upgrade-Insecure-Requests)
	 * @author Daniele 
	 * @return
	 */
	public String getUpgradeInsecureRequests() {
		return getFirst(HttpRequestHeaderEnum.UPGRADE_INSECURE_REQUESTS.getKey());
	}
	
	/**
	 * 设置用户代理
	 * @author Daniele 
	 * @param userAgent
	 */
	public void setUserAgent(String userAgent) {
		set(HttpRequestHeaderEnum.USER_AGENT.getKey(), userAgent);
	}
	
	/**
	 * 获取用户代理
	 * @author Daniele 
	 * @return
	 */
	public String getUserAgent() {
		return getFirst(HttpRequestHeaderEnum.USER_AGENT.getKey());
	}
	
	/**
	 * 设置X-Forwarded-For请求消息头 
	 * @author Daniele 
	 * @param ips
	 */
	public void setXForwardedFor(List<String> ips) {
		set(HttpRequestHeaderEnum.X_FORWARDED_FOR.getKey(), CollectionUtils.join(ips, VALUE_SEPARATOR));
	}
	
	/**
	 * 获取X-Forwarded-For请求消息头
	 * @author Daniele 
	 * @return
	 */
	public List<String> getXForwardedFor() {
		String first = getFirst(HttpRequestHeaderEnum.X_FORWARDED_FOR.getKey());
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
	 * 设置X-Forwarded-Host请求消息头
	 * @author Daniele 
	 * @param host
	 */
	public void setXForwardedHost(String host) {
		set(HttpRequestHeaderEnum.X_FORWARDED_HOST.getKey(), host);
	}
	
	/**
	 * 获取X-Forwarded-Host请求消息头
	 * @author Daniele 
	 * @return
	 */
	public String getXForwardedHost() {
		return getFirst(HttpRequestHeaderEnum.X_FORWARDED_HOST.getKey());
	}
	
	/**
	 * 设置X-Forwarded-Proto请求消息头
	 * @author Daniele 
	 * @param protocol
	 */
	public void setXForwardedProto(HttpProtocolEnum protocol) {
		set(HttpRequestHeaderEnum.X_FORWARDED_PROTO.getKey(), protocol != null ? protocol.name().toLowerCase() : null);
	}
	
	/**
	 * 获取X-Forwarded-Proto请求消息头
	 * @author Daniele 
	 * @return
	 */
	public HttpProtocolEnum getXForwardedProto() {
		return HttpProtocolEnum.resolve(getFirst(HttpRequestHeaderEnum.X_FORWARDED_PROTO.getKey()));
	}
	
}
