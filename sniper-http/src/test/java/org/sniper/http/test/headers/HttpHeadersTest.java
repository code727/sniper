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

import java.util.Date;

import org.sniper.commons.util.DateUtils;
import org.sniper.http.headers.HttpHeaders;
import org.sniper.http.headers.KeepAlive;
import org.sniper.http.headers.MediaType;
import org.sniper.test.junit.BaseTestCase;

/**
 * HTTP通用头单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HttpHeadersTest extends BaseTestCase {
	
	private HttpHeaders headers = new HttpHeaders();
	
//	@Test
	public void testContentType() {
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		System.out.println(headers.getContentType());
	}
	
//	@Test
	public void testDate() {
		Date date = new Date();
		headers.setDate(date);
		System.out.println(headers.getDate());
		headers.setDate(DateUtils.addDays(date, 1));
		System.out.println(headers.getDate());
	}
	
//	@Test
	public void testKeepAlive() {
		KeepAlive keepAlive = new KeepAlive(60, 1024);
		headers.setKeepAlive(keepAlive);
		System.out.println(headers.getKeepAlive());
	}
	
}
