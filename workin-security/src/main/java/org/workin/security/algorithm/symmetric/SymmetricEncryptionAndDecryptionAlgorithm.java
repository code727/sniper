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
 * Create Date : 2016-5-3
 */

package org.workin.security.algorithm.symmetric;

import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.Base64Utils;
import org.workin.commons.util.CodecUtils;
import org.workin.security.algorithm.Decryption;
import org.workin.security.algorithm.Encryption;

/**
 * @description 对称加解密算法抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class SymmetricEncryptionAndDecryptionAlgorithm extends
		AbstractSymmetricAlgorithm implements Encryption, Decryption {
	
	protected SymmetricEncryptionAndDecryptionAlgorithm(String privateKey) {
		if (privateKey == null)
			throw new IllegalArgumentException("Private key must not be null.");
		
		this.privateKey = privateKey;
	}
	
	@Override
	public String encrypt(String plaintext) throws Exception {
		return Base64Utils.encode(this.encryptToBytes(plaintext));
	}
	
	@Override
	public String encrypt(byte[] plaintextBytes) throws Exception {
		return Base64Utils.encode(this.encryptToBytes(plaintextBytes));
	}
	
	@Override
	public byte[] encryptToBytes(String plaintext) throws Exception {
		AssertUtils.assertNotNull(plaintext, "Encrypted plaintext must be not null.");
		return this.encryptToBytes(CodecUtils.getBytes(plaintext, this.encoding));
	}
	
	@Override
	public String decrypt(String ciphertext) throws Exception {
		return CodecUtils.byteToString(this.decryptToBytes(ciphertext), this.encoding) ;
	}
	
	@Override
	public String decrypt(byte[] ciphertextBytes) throws Exception {
		// 将字节数组转化为密文字符串
		String ciphertext = CodecUtils.byteToString(ciphertextBytes);
		// 再将密文字符串解密后的明文字节数组转化为最终要得到的明文字符串
		return CodecUtils.byteToString(this.decryptToBytes(ciphertext));
	}
	
	@Override
	public byte[] decryptToBytes(String ciphertext) throws Exception {
		AssertUtils.assertNotNull(ciphertext, "Decrypted ciphertext must be not null.");
		// 先将密文进行Base64解码后得到密文字节数组，再对其进行解密得到明文字节数组
		return this.decryptToBytes(Base64Utils.decodeToBytes(ciphertext));
	}
		
}
