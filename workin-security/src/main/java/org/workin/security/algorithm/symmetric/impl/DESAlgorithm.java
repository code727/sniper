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

package org.workin.security.algorithm.symmetric.impl;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.workin.security.algorithm.symmetric.AbstractSymmetricAlgorithm;

/**
 * @description DES加解密算法实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DESAlgorithm extends AbstractSymmetricAlgorithm {
	
	private Cipher encryptModeCipher;
	
	private Cipher decryptModeCipher;
	
	protected void init() throws Exception {
		SecureRandom random = new SecureRandom();
		
		DESKeySpec desKey = new DESKeySpec(getPrivateKeyBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(desKey);
		
		this.encryptModeCipher = Cipher.getInstance("DES");
		this.encryptModeCipher.init(Cipher.ENCRYPT_MODE, securekey, random);
		
		this.decryptModeCipher = Cipher.getInstance("DES");
		this.decryptModeCipher.init(Cipher.DECRYPT_MODE, securekey, random);
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
