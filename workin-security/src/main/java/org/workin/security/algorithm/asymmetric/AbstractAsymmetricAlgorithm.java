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

package org.workin.security.algorithm.asymmetric;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.workin.commons.util.CodecUtils;
import org.workin.security.algorithm.symmetric.AbstractSymmetricAlgorithm;
import org.workin.support.codec.Base64Codec;
import org.workin.support.codec.Codec;

/**
 * @description 非对称算法抽象类
 * @author <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractAsymmetricAlgorithm extends
		AbstractSymmetricAlgorithm implements AsymmetricAlgorithm {

	/** 公钥接口对象 */
	protected PublicKey publicKeyInterface;

	/** 私钥接口对象 */
	protected PrivateKey privateKeyInterface;

	/** 公钥 */
	private String publicKey;

	/** 公钥/私钥的编解码器 */
	private Codec keyCodec;

	@Override
	public PublicKey getPublicKeyInterface() {
		return this.publicKeyInterface;
	}

	@Override
	public PrivateKey getPrivateKeyInterface() {
		return this.privateKeyInterface;
	}

	@Override
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public String getPublicKey() {
		return this.publicKey;
	}

	public Codec getKeyCodec() {
		return keyCodec;
	}

	public void setKeyCodec(Codec keyCodec) {
		this.keyCodec = keyCodec;
	}

	@Override
	protected void initCodec() throws Exception {
		super.initCodec();

		if (this.keyCodec == null)
			setKeyCodec(new Base64Codec());
	}

	/**
	 * @description 获取公钥的字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @return
	 */
	protected byte[] getPublicKeyBytes() {
		if (this.publicKey == null)
			throw new IllegalArgumentException("Public key must not be null.");

		return CodecUtils.getBytes(this.publicKey, getEncoding());
	}

}
