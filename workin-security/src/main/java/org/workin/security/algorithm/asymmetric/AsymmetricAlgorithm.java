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

import org.workin.security.algorithm.symmetric.SymmetricAlgorithm;

/**
 * @description 非对称算法接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface AsymmetricAlgorithm extends SymmetricAlgorithm {
	
	/**
	 * @description 设置公钥
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param publicKey
	 */
	public void setPublicKey(String publicKey);
	
	/**
	 * @description 获取公钥
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getPublicKey();
	
	/**
	 * @description 获取公钥接口对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public PublicKey getPublicKeyInterface();
	
	/**
	 * @description 获取私钥接口对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public PrivateKey getPrivateKeyInterface();

}