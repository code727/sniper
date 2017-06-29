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
 * 加密算法接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Encryption extends Codecable {
	
	/**
	 * 将明文加密成密文
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String plaintext) throws Exception;
	
	/**
	 * 将明文字节数组加密成密文
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintextBytes
	 * @return
	 * @throws Exception
	 */
	public String encrypt(byte[] plaintextBytes) throws Exception;
	
	/**
	 * 将明文加密成密文字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptToBytes(String plaintext) throws Exception;
	
	/**
	 * 将明文字节数组加密成密文字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintextBytes
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptToBytes(byte[] plaintextBytes) throws Exception;
	
}
