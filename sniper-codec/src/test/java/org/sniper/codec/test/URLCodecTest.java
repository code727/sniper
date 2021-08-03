/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-8-2
 */

package org.sniper.codec.test;

import org.junit.Test;
import org.sniper.codec.decoder.RawURLDecoder;
import org.sniper.codec.decoder.URLDecoder;
import org.sniper.codec.encoder.RawURLEncoder;
import org.sniper.codec.encoder.URLEncoder;
import org.sniper.test.junit.BaseTestCase;

/**
 * URL编解码单元测试
 * @author  Daniele
 * @version 1.0
 */
public class URLCodecTest extends BaseTestCase {
	
	private String url = "http://test.com?name=te st&nickname=阿波罗";
	
	@Test
	public void testURLCodec() throws Exception {
		String encodedURL = new URLEncoder().encode(url);
		String decodedURL = new URLDecoder().decode(encodedURL);	
				
		assertEquals(url, decodedURL);
		System.out.println("URLEncoder:" + encodedURL);
		System.out.println("URLDecoder:" + decodedURL);
	}
	
	@Test
	public void testRawURLCodec() throws Exception {
		String encodedURL = new RawURLEncoder().encode(url);
		String decodedURL = new RawURLDecoder().decode(encodedURL);	
				
		assertEquals(url, decodedURL);
		System.out.println("RawURLEncoder:" + encodedURL);
		System.out.println("RawURLDecoder:" + decodedURL);
	}

}
