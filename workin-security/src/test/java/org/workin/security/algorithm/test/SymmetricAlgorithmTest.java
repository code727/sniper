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
 * Create Date : 2016年5月4日
 */

package org.workin.security.algorithm.test;

import org.junit.Test;
import org.workin.security.algorithm.symmetric.impl.AESAlgorithm;
import org.workin.security.algorithm.symmetric.impl.BlowfishAlgorithm;
import org.workin.security.algorithm.symmetric.impl.DESAlgorithm;
import org.workin.test.junit.BaseTestCase;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SymmetricAlgorithmTest extends BaseTestCase {
	
	private String privateKey = "12345678";
	
	private String plaintext = "杜斌_dub727@163.com";
	
	@Test
	public void testDESAlgorithm() throws Exception {
		DESAlgorithm desAlgorithm = new DESAlgorithm();
		desAlgorithm.setPrivateKey(privateKey);
		desAlgorithm.afterPropertiesSet();
		
		String ciphertext = desAlgorithm.encrypt(plaintext);
		String decryptedPlaintext = desAlgorithm.decrypt(ciphertext);
		assertEquals(decryptedPlaintext, this.plaintext);
		
		System.out.println("DES密文:" + ciphertext);
		System.out.println("DES明文:" + decryptedPlaintext);
	}
	
	@Test
	public void testAESAlgorithm() throws Exception {
		AESAlgorithm aesAlgorithm = new AESAlgorithm();
		aesAlgorithm.setPrivateKey(privateKey);
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
		blowfishAlgorithm.afterPropertiesSet();
		
		String ciphertext = blowfishAlgorithm.encrypt(plaintext);
		String decryptedPlaintext = blowfishAlgorithm.decrypt(ciphertext);
		assertEquals(decryptedPlaintext, this.plaintext);
		
		System.out.println("Blowfish密文:" + ciphertext);
		System.out.println("Blowfish明文:" + decryptedPlaintext);
	}

}
