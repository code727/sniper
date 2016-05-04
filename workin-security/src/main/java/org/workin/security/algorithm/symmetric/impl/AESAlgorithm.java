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

package org.workin.security.algorithm.symmetric.impl;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.workin.security.algorithm.symmetric.AbstractSymmetricAlgorithm;

/**
 * @description AES加解密算法实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AESAlgorithm extends AbstractSymmetricAlgorithm {
	
	private Cipher encryptModeCipher;
	
	private Cipher decryptModeCipher;

	@Override
	protected void init() throws Exception {
		SecureRandom random = new SecureRandom(getPrivateKeyBytes());
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");   
		keyGenerator.init(128, random); 
		SecretKey secretKey = keyGenerator.generateKey(); 
		SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");   
		
        this.encryptModeCipher = Cipher.getInstance("AES");
		this.encryptModeCipher.init(Cipher.ENCRYPT_MODE, keySpec);
		
		this.decryptModeCipher = Cipher.getInstance("AES");
		this.decryptModeCipher.init(Cipher.DECRYPT_MODE, keySpec);
	}

	@Override
	public byte[] encryptToBytes(byte[] plaintextBytes) throws Exception {
		return this.encryptModeCipher.doFinal(plaintextBytes);
	}

	@Override
	public byte[] decryptToBytes(byte[] ciphertextBytes) throws Exception {
		return this.decryptModeCipher.doFinal(ciphertextBytes);
	}

}
