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
 * Create Date : 2015-11-13
 */

package org.sniper.security.signature;


/**
 * 签名接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Signature<T> {
	
	/**
	 * 设置类型标识
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 */
	public void setType(String type);
	
	/**
	 * 获取类型标识
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getType();
	
	/**
	 * 执行签名处理，并返回结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameters
	 * @param secretKey
	 * @return
	 */
	public String excute(T parameters);
	
}
