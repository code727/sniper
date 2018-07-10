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
 * 泛型响应对象接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface GenericResponse<C> extends Serializable {
	
	/**
	 * 获取响应码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public C getCode();
	
	/**
	 * 设置响应码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 */
	public void setCode(C code);
	
	/**
	 * 判断响应是否成功。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return 如果默认的状态码与当前状态码匹配，则表明响应成功，否则未成功。
	 */
	public boolean wasSuccess();
	
	/**
	 * 判断响应是否成功。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code 指定的状态码
	 * @return 如果指定的状态码与当前状态码匹配，则表明响应成功，否则未成功。
	 */
	public boolean wasSuccess(C code);
	
	/**
	 * 判断响应是否失败。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return 如果默认的状态码与当前状态码匹配，则表明响应失败，否则未失败。
	 */
	public boolean wasFailed();
	
	/**
	 * 判断响应是否失败。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code 指定的状态码
	 * @return 如果指定的状态码与当前状态码匹配，则表明响应失败，否则未失败。
	 */
	public boolean wasFailed(C code);
	
	/**
	 * 判断响应是否出现异常。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return 如果默认的状态码与当前状态码匹配，则表明响应出现异常，否则未出现异常。
	 */
	public boolean wasException();
	
	/**
	 * 判断响应是否出现异常。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code 指定的状态码
	 * @return 如果指定的状态码与当前状态码匹配，则表明响应出现异常，否则未出现异常。
	 */
	public boolean wasException(C code);
			
}
