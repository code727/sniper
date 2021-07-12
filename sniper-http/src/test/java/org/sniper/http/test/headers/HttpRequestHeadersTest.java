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
 * Create Date : 2017-9-6
 */

package org.sniper.http.test.headers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.sniper.beans.parameter.DefaultParameters;
import org.sniper.beans.parameter.Parameters;
import org.sniper.commons.enums.http.AuthenticationEnum;
import org.sniper.commons.enums.http.TEEnum;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.http.headers.Forwarded;
import org.sniper.http.headers.MediaType;
import org.sniper.http.headers.request.Authorization;
import org.sniper.http.headers.request.HttpRequestHeaders;
import org.sniper.http.headers.request.Range;
import org.sniper.http.headers.request.RangeValue;
import org.sniper.http.headers.request.TE;
import org.sniper.test.junit.BaseTestCase;

/**
 * HTTP请求头单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class HttpRequestHeadersTest extends BaseTestCase {
	
	HttpRequestHeaders headers = new HttpRequestHeaders();
	
//	@Test
	public void testAccept() {
		List<MediaType> mediaTypes = CollectionUtils.newArrayList();
		mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
		mediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(mediaTypes);
		
		List<MediaType> list = headers.getAccept();
		assertTrue(CollectionUtils.isNotEmpty(list));
		System.out.println(list);
	}
	
//	@Test
	public void testAuthorization() {
		Authorization authorization = new Authorization(AuthenticationEnum.BASIC, "YWxhZGRpbjpvcGVuc2VzYW1l");
		headers.setAuthorization(authorization);
		System.out.println(headers.getAuthorization());
	}
	
//	@Test
	public void testCookie() {
		Parameters<String, Object> cookies = new DefaultParameters<String, Object>();
		cookies.add("name", "dubin");
		cookies.add("age", 34);
		cookies.add("date", new Date());
		
		headers.setCookie(cookies);
		Parameters<String, Object> cookie = headers.getCookie();
		System.out.println(cookie);
		System.out.println(cookie.getString("name"));
		System.out.println(cookie.getInteger("age"));
	}
	
//	@Test
	public void testDNT() {
		headers.setDNT(-1);
		System.out.println(headers.getDNT());
	}
	
//	@Test
	public void testForwarded() {
		Forwarded forwarded = new Forwarded();
//		forwarded.setBy("   ");
//		forwarded.setHost("127.0.0.1");
//		forwarded.setProtocol(HttpProtocol.HTTPS);
		
		List<String> multiFor = CollectionUtils.newArrayList();
		multiFor.add("192.0.2.43");
		multiFor.add("\"[2001:db8:cafe::17]\"");
//		forwarded.setMultiFor(multiFor);
		
		headers.setForwarded(forwarded);
		System.out.println(forwarded.getBy());
		System.out.println(headers.getForwarded());
	}
	
//	@Test
	public void testIfMatch() {
		List<String> eTagValues = CollectionUtils.newArrayList();
		eTagValues.add(  "W/\"67ab43\"");
		eTagValues.add("\"54ed21\"");
		eTagValues.add("\"7892dd\"");
		headers.setIfMatch(eTagValues);
		System.out.println(headers.getIfMatch());
	}
	
//	@Test
	public void testIfRange() {
		headers.setIfRange(StringUtils.appendDoubleQuotes("675af34563dc-tr34"));
		System.out.println(headers.getIfRange());
	}
	
//	@Test
	public void testOrigin() throws MalformedURLException {
		URL url = new URL("https://developer.mozilla.org");
		headers.setOrigin(url);
		System.out.println(headers.getOrigin());
	}
	
//	@Test
	public void testProxyAuthorization() {
		Authorization authorization = new Authorization(AuthenticationEnum.BASIC, "YWxhZGRpbjpvcGVuc2VzYW1l");
		headers.setProxyAuthorization(authorization);
		System.out.println(headers.getProxyAuthorization());
	}
	
//	@Test 
	public void testRange() {
		List<RangeValue> rangeValues = CollectionUtils.newArrayList();
		rangeValues.add(new RangeValue(0L, 99L));
		rangeValues.add(new RangeValue(100L, 199L));
		rangeValues.add(new RangeValue(200L, 299L));
		
		Range range = new Range(rangeValues);
		headers.setRange(range);
		System.out.println(headers.getRange());
	}
	
//	@Test 
	public void testTE() {
		List<TE> tes = CollectionUtils.newArrayList();
		tes.add(new TE(TEEnum.TRAILERS));
		tes.add(new TE(TEEnum.DEFLATE, 0.5));
		
		headers.setTE(tes);
		System.out.println(headers.getTE());
	}
	
	@Test 
	public void testUserAgent() {
		String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36";
		headers.setUserAgent(userAgent);
		System.out.println(headers.getUserAgent());
	}
	
}
