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
 * Create Date : 2016-3-30
 */

package org.sniper.security.algorithm;

import org.sniper.codec.Codecable;

/**
 * 解密算法接口
 * @author  Daniele
 * @version 1.0
 */
public interface Decryption extends Codecable {
	
	/**
	 * 将密文解密为明文
	 * @author Daniele 
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String ciphertext) throws Exception;
	
	/**
	 * 将密文字节数组解密成明文
	 * @author Daniele 
	 * @param ciphertextBytes
	 * @return
	 * @throws Exception
	 */
	public String decrypt(byte[] ciphertextBytes) throws Exception;
	
	/**
	 * 将密文解密为明文字节数组
	 * @author Daniele 
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptToBytes(String ciphertext) throws Exception;
	
	/**
	 * 将密文字节数组解密为明文字节数组
	 * @author Daniele 
	 * @param ciphertextBytes
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptToBytes(byte[] ciphertextBytes) throws Exception;
			
}
