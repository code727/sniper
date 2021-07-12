/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-12-10
 */

package org.sniper.security.signature;

/**
 * 对称签名抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class SymmetricSignature<T> implements Signature<T> {
	
	/** 类型标识 */
	private String type;
	
	/** 私钥 */
	private String privateKey;
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 设置私钥
	 * @author Daniele 
	 * @param privateKey
	 */
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
	/**
	 * 获取私钥
	 * @author Daniele 
	 * @return
	 */
	public String getPrivateKey() {
		return this.privateKey;
	}

}
