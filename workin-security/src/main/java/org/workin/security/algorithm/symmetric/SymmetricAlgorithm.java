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

package org.workin.security.algorithm.symmetric;

import org.workin.support.codec.Codecable;

/**
 * @description 对称算法标识接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SymmetricAlgorithm extends Codecable {
	
	/**
	 * @description 设置私钥
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param privateKey
	 */
	public void setPrivateKey(String privateKey);
	
	/**
	 * @description 获取私钥
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getPrivateKey();

}
