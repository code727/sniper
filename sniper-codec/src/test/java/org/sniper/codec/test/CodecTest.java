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

package org.sniper.codec.test;

import java.util.List;

import org.junit.Test;
import org.sniper.codec.Base64Codec;
import org.sniper.codec.Codec;
import org.sniper.codec.CompositeCodec;
import org.sniper.codec.HexCodec;
import org.sniper.commons.util.CodecUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.test.junit.BaseTestCase;

/**
 * 编码/解码器单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class CodecTest extends BaseTestCase {
	
	private Codec codec;
	
	private String text = "Daniele.De.Rossi";
	
	@Test
	public void testBase64Codec() {
		codec = new Base64Codec();
		String encodedText = codec.encode(text);
		String decodedText = codec.decode(encodedText);
		
		assertEquals(text, decodedText);
		System.out.println("Base64编码:" + encodedText);
		System.out.println("Base64解码:" + decodedText);
	}
	
	@Test
	public void testHexCodec() {
		codec = new HexCodec();
		String encodedText = codec.encode(text, CodecUtils.GBK_ENCODING);
		String decodedText = codec.decode(encodedText, CodecUtils.GBK_ENCODING);
		
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
