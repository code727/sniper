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

import org.workin.commons.util.CodecUtils;

/**
 * @description 对称算法抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractSymmetricAlgorithm implements SymmetricAlgorithm {
	
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
	
	protected byte[] getPrivateKeyBytes() {
		if (this.privateKey == null)
			throw new IllegalArgumentException("Private key must not be null.");
		
		return CodecUtils.getBytes(this.privateKey, this.encoding);
	}

}
