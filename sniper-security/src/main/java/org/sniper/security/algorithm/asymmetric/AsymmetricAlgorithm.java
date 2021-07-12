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

package org.sniper.security.algorithm.asymmetric;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.sniper.security.algorithm.symmetric.SymmetricAlgorithm;

/**
 * 非对称算法接口
 * @author  Daniele
 * @version 1.0
 */
public interface AsymmetricAlgorithm extends SymmetricAlgorithm {
	
	/**
	 * 设置公钥
	 * @author Daniele 
	 * @param publicKey
	 */
	public void setPublicKey(String publicKey);
	
	/**
	 * 获取公钥
	 * @author Daniele 
	 * @return
	 */
	public String getPublicKey();
	
	/**
	 * 获取公钥接口对象
	 * @author Daniele 
	 * @return
	 */
	public PublicKey getPublicKeyInterface();
	
	/**
	 * 获取私钥接口对象
	 * @author Daniele 
	 * @return
	 */
	public PrivateKey getPrivateKeyInterface();

}
