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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.workin.commons.util.IOUtils;
import org.workin.security.algorithm.symmetric.AbstractSymmetricAlgorithm;

/**
 * @description Blowfish加解密算法实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BlowfishAlgorithm extends AbstractSymmetricAlgorithm {

	private Cipher encryptModeCipher;

	private Cipher decryptModeCipher;
	
	private byte[] bytes;

	@Override
	protected void init() throws Exception {
		SecureRandom random = new SecureRandom();
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");  
		keyGenerator.init(128); 
		SecretKey secretKey = keyGenerator.generateKey(); 
		
		this.bytes = new byte[8];
		random.nextBytes(this.bytes);
		IvParameterSpec spec = new IvParameterSpec(bytes);
		
		this.encryptModeCipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
		this.encryptModeCipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

		this.decryptModeCipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
		this.decryptModeCipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
	}

	@Override
	public byte[] encryptToBytes(byte[] plaintextBytes) throws Exception {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(plaintextBytes);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		CipherOutputStream cipherStream = null;
		try {
			outputStream.write(this.bytes); 
			cipherStream = new CipherOutputStream(outputStream, this.encryptModeCipher);
			for (int i = inputStream.read(); i != -1; i = inputStream.read())
	            cipherStream.write(i);
		} catch(IOException e) {
			throw new IOException(e);
		} finally {
			IOUtils.close(inputStream);
			IOUtils.close(cipherStream);
			IOUtils.close(outputStream);
		}
        return outputStream.toByteArray();
	}

	@Override
	public byte[] decryptToBytes(byte[] ciphertextBytes) throws Exception {
		 ByteArrayInputStream inputStream = new ByteArrayInputStream(ciphertextBytes);
		 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		 CipherInputStream cipherStream = null;
		 try {
			 inputStream.read(this.bytes);
			 cipherStream = new CipherInputStream(inputStream, this.decryptModeCipher);
			 for (int i = cipherStream.read(); i != -1; i = cipherStream.read())
				 outputStream.write(i);
		 } catch(IOException e) {
			 IOUtils.close(inputStream);
			 IOUtils.close(cipherStream);
			 IOUtils.close(outputStream);
		 }
         return outputStream.toByteArray();
	}

}
