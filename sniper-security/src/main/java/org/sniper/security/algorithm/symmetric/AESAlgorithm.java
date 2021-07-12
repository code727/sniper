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

package org.sniper.security.algorithm.symmetric;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密算法实现类
 * @author  Daniele
 * @version 1.0
 */
public class AESAlgorithm extends CipherSymmetricAlgorithm {
	
	@Override
	protected void init() throws Exception {
		SecureRandom random = new SecureRandom(getPrivateKeyBytes());
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance(this.algorithm);   
		keyGenerator.init(128, random); 
		SecretKey secretKey = keyGenerator.generateKey(); 
		SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), this.algorithm);   
		
        this.encryptModeCipher = Cipher.getInstance(this.algorithm);
		this.encryptModeCipher.init(Cipher.ENCRYPT_MODE, keySpec);
		
		this.decryptModeCipher = Cipher.getInstance(this.algorithm);
		this.decryptModeCipher.init(Cipher.DECRYPT_MODE, keySpec);
	}

}
