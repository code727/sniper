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
 * @author  Daniele
 * @version 1.0
 */
public interface GenericResponse<C> extends Serializable {
	
	/**
	 * 获取响应码
	 * @author Daniele 
	 * @return
	 */
	public C getCode();
	
	/**
	 * 设置响应码
	 * @author Daniele 
	 * @param code
	 */
	public void setCode(C code);
		
	/**
	 * 判断响应是否成功
	 * @author Daniele 
	 * @return 
	 */
	public boolean successed();
	
	/**
	 * 判断响应是否错误
	 * @author Daniele 
	 * @return 
	 */
	public boolean errored();
	
}
