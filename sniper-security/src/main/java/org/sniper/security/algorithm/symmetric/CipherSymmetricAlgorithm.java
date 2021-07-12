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
 * Create Date : 2016-5-6
 */

package org.sniper.security.algorithm.symmetric;

import javax.crypto.Cipher;

/**
 * 密码器对称算法抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class CipherSymmetricAlgorithm extends AbstractSymmetricAlgorithm {
	
	/** 加密器 */
	protected Cipher encryptModeCipher;
	
	/** 解密器 */
	protected Cipher decryptModeCipher;
	
	@Override
	public byte[] encryptToBytes(byte[] plaintextBytes) throws Exception {
		return this.encryptModeCipher.doFinal(plaintextBytes);
	}

	@Override
	public byte[] decryptToBytes(byte[] ciphertextBytes) throws Exception {
		return this.decryptModeCipher.doFinal(ciphertextBytes);
	}

}
