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
 * Create Date : 2016-5-12
 */

package org.workin.security.algorithm.asymmetric.impl;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.workin.security.algorithm.asymmetric.CipherAsymmetricAlgorithm;

/**
 * RSA加解密算法实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>	
 * @version 1.0
 */
public class RSAAlgorithm extends CipherAsymmetricAlgorithm {
	
	@Override
	protected void init() throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.algorithm);
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		
		buildePublicKey(keyPair, keyFactory);
		buildePrivateKey(keyPair, keyFactory);
		
		this.encryptModeCipher = Cipher.getInstance(this.algorithm);
		this.encryptModeCipher.init(Cipher.ENCRYPT_MODE, getPublicKeyInterface());
		
		this.decryptModeCipher = Cipher.getInstance(this.algorithm);
		this.decryptModeCipher.init(Cipher.DECRYPT_MODE, getPrivateKeyInterface());
	}
	
	/**
	 * 构建公钥
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keyPair
	 * @param keyFactory
	 * @throws Exception
	 */
	protected void buildePublicKey(KeyPair keyPair, KeyFactory keyFactory) throws Exception {
		String publicKey = getPublicKey();
		/* 未设置公钥字符串时，则使用keyPair生成的公钥。
		 * 否则，利用设置的公钥字符串来创建一个java.security.PublicKey接口对象 */
		if (publicKey == null) {
			this.publicKeyInterface = keyPair.getPublic();
			publicKey = getKeyCodec().encode(this.publicKeyInterface.getEncoded());
			setPublicKey(publicKey);
		} else {
			X509EncodedKeySpec x509ek = new X509EncodedKeySpec(getKeyCodec().decodeToBytes(getPublicKey()));
			this.publicKeyInterface = keyFactory.generatePublic(x509ek);
		}
	}
	
	/**
	 * 构建私钥
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keyPair
	 * @param keyFactory
	 * @throws Exception
	 */
	protected void buildePrivateKey(KeyPair keyPair, KeyFactory keyFactory) throws Exception {
		String privateKey = getPrivateKey();
		if (privateKey == null) {
			this.privateKeyInterface = keyPair.getPrivate();
			privateKey = getKeyCodec().encode(this.privateKeyInterface.getEncoded());
			setPrivateKey(privateKey);
		} else {
			PKCS8EncodedKeySpec s8ek = new PKCS8EncodedKeySpec(getKeyCodec().decodeToBytes(getPrivateKey()));
			this.privateKeyInterface = keyFactory.generatePrivate(s8ek);
		}
	}

}
