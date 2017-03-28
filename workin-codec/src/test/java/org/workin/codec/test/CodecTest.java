/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016年5月6日
 */

package org.workin.codec.test;

import java.util.List;

import org.junit.Test;
import org.workin.codec.Base64Codec;
import org.workin.codec.Codec;
import org.workin.codec.CompositeCodec;
import org.workin.codec.HexCodec;
import org.workin.commons.util.CodecUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.test.junit.BaseTestCase;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CodecTest extends BaseTestCase {
	
	private Codec codec;
	
	private String text = "dub727@163.com";
	
	@Test
	public void testBase64Codec() {
		codec = new Base64Codec();
		String encodedText = codec.encode(text);
		String decodedText = codec.decode(encodedText);
		
		assertEquals(text, decodedText);
		System.out.println("Base编码:" + encodedText);
		System.out.println("Base解码:" + decodedText);
	}
	
	@Test
	public void testHexCodec() {
		codec = new HexCodec();
		String encodedText = codec.encode(text, CodecUtils.GBK_ENCODING);
		String decodedText = codec.decode(encodedText,CodecUtils.GBK_ENCODING);
		
		assertEquals(text, decodedText);
		System.out.println("16进制编码:" + encodedText);
		System.out.println("16进制解码:" + decodedText);
	}
	
	@Test
	public void testCompositeCodec() {
		/* 依次按16进制和Base64编码，解码时顺序相反 */
		List<Codec> codecMembers = CollectionUtils.newArrayList();
		codecMembers.add(new HexCodec());
		codecMembers.add(new Base64Codec());
		
		CompositeCodec compositeCodec = new CompositeCodec(codecMembers);
		String encodedText = compositeCodec.encode(text);
		String decodedText = compositeCodec.decode(encodedText);
		
		assertEquals(text, decodedText);
		System.out.println("复合编码:" + encodedText);
		System.out.println("复合解码:" + decodedText);
	}
	
}
