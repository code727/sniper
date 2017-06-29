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
 * Create Date : 2016-5-4
 */

package org.sniper.security.algorithm.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sniper.codec.Base64Codec;
import org.sniper.codec.Codec;
import org.sniper.codec.CompositeCodec;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.security.algorithm.symmetric.impl.AESAlgorithm;
import org.sniper.security.algorithm.symmetric.impl.BlowfishAlgorithm;
import org.sniper.security.algorithm.symmetric.impl.DESAlgorithm;
import org.sniper.test.junit.BaseTestCase;

/**
 * 对称加解密算法单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SymmetricAlgorithmTest extends BaseTestCase {
	
	private String privateKey = "12345678";
	
	private String plaintext = "杜斌_dub727@163.com";
	
	private Codec resultCodec;
	
	@Before
	public void before() {
		/* 依次按16进制和Base64对加密结果进行编码，解密时解码的顺序相反 */
		List<Codec> members = CollectionUtils.newArrayList();
//		members.add(new HexCodec());
		members.add(new Base64Codec());
		this.resultCodec = new CompositeCodec(members);
	}
	
	@Test
	public void testDESAlgorithm() throws Exception {
		DESAlgorithm desAlgorithm = new DESAlgorithm();
		desAlgorithm.setPrivateKey(privateKey);
		desAlgorithm.setResultCodec(resultCodec);
		desAlgorithm.afterPropertiesSet();
		
		String ciphertext = desAlgorithm.encrypt(plaintext);
		String decryptedPlaintext = desAlgorithm.decrypt(ciphertext);
		assertEquals(decryptedPlaintext, this.plaintext);
		
		System.out.println("DES密文:" + ciphertext);
		System.out.println("DES明文:" + decryptedPlaintext);
	}
	
//	@Test
	public void testAESAlgorithm() throws Exception {
		AESAlgorithm aesAlgorithm = new AESAlgorithm();
		aesAlgorithm.setPrivateKey(privateKey);
		aesAlgorithm.setResultCodec(resultCodec);
		aesAlgorithm.afterPropertiesSet();
		
		String ciphertext = aesAlgorithm.encrypt(plaintext);
		String decryptedPlaintext = aesAlgorithm.decrypt(ciphertext);
		assertEquals(decryptedPlaintext, this.plaintext);
		
		System.out.println("AES密文:" + ciphertext);
		System.out.println("AES明文:" + decryptedPlaintext);
	}
	
	@Test
	public void testBlowfishAlgorithm() throws Exception {
		BlowfishAlgorithm blowfishAlgorithm = new BlowfishAlgorithm();
		blowfishAlgorithm.setPrivateKey(privateKey);
		blowfishAlgorithm.setResultCodec(resultCodec);
		blowfishAlgorithm.afterPropertiesSet();
		
		String ciphertext = blowfishAlgorithm.encrypt(plaintext);
		String decryptedPlaintext = blowfishAlgorithm.decrypt(ciphertext);
		assertEquals(decryptedPlaintext, this.plaintext);
		
		System.out.println("Blowfish密文:" + ciphertext);
		System.out.println("Blowfish明文:" + decryptedPlaintext);
	}

}
