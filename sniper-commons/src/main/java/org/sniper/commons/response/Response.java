/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-3-16
 */

package org.sniper.commons.response;

import java.io.Serializable;

/**
 * 响应接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Response<C> extends Serializable {
	
	/**
	 * 获取状态编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public C getCode();
	
	/**
	 * 设置状态编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 */
	public void setCode(C code);
	
	/**
	 * 响应是否成功
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean wasSuccess();
	
	/**
	 * 响应是否成功
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param successCode
	 * @return
	 */
	public boolean wasSuccess(C successCode);
	
	/**
	 * 响应是否失败
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean wasFailed();
	
	/**
	 * 响应是否失败
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param failedCode
	 * @return
	 */
	public boolean wasFailed(C failedCode);
	
	/**
	 * 响应是否异常
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean wasException();
	
	/**
	 * 响应是否异常
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param exceptionCode
	 * @return
	 */
	public boolean wasException(C exceptionCode);
			
}
