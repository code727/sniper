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

package org.workin.security.algorithm.symmetric;

import org.springframework.beans.factory.InitializingBean;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.Base64Utils;
import org.workin.commons.util.CodecUtils;

/**
 * @description 对称算法抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractSymmetricAlgorithm implements SymmetricAlgorithm, InitializingBean {
	
	/** 字符集编码 */
	protected String encoding;
	
	/** 私钥 */
	protected String privateKey;
	
	@Override
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public String getEncoding() {
		return this.encoding;
	}

	@Override
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	@Override
	public String getPrivateKey() {
		return this.privateKey;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
	
	@Override
	public String encrypt(String plaintext) throws Exception {
//		return Base64Utils.encode(CodecUtils.bytesToHex(this.encryptToBytes(plaintext)));
		return Base64Utils.encode(this.encryptToBytes(plaintext));
	}
	
	@Override
	public String encrypt(byte[] plaintextBytes) throws Exception {
//		return Base64Utils.encode(CodecUtils.bytesToHex(this.encryptToBytes(plaintextBytes)));
		return Base64Utils.encode(this.encryptToBytes(plaintextBytes));
	}
	
	@Override
	public byte[] encryptToBytes(String plaintext) throws Exception {
		AssertUtils.assertNotNull(plaintext, "Encrypted plaintext must be not null.");
		return this.encryptToBytes(CodecUtils.getBytes(plaintext, this.encoding));
	}
	
	@Override
	public String decrypt(String ciphertext) throws Exception {
		return CodecUtils.bytesToString(this.decryptToBytes(ciphertext), this.encoding) ;
	}
	
	@Override
	public String decrypt(byte[] ciphertextBytes) throws Exception {
		// 将字节数组转化为密文字符串
		String ciphertext = CodecUtils.bytesToString(ciphertextBytes);
		// 再将密文字符串解密后的明文字节数组转化为最终要得到的明文字符串
		return CodecUtils.bytesToString(this.decryptToBytes(ciphertext));
	}
	
	@Override
	public byte[] decryptToBytes(String ciphertext) throws Exception {
		AssertUtils.assertNotNull(ciphertext, "Decrypted ciphertext must be not null.");
		// 先将密文进行Base64解码后得到密文字节数组，再对其进行解密得到明文字节数组
		return this.decryptToBytes(Base64Utils.decodeToBytes(ciphertext));
	}
	
	/**
	 * @description 获取密钥的字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected byte[] getPrivateKeyBytes() {
		if (this.privateKey == null)
			throw new IllegalArgumentException("Private key must not be null.");
		
		return CodecUtils.getBytes(this.privateKey, this.encoding);
	}
	
	/**
	 * @description 初始化操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @throws Exception
	 */
	protected abstract void init() throws Exception;

}
