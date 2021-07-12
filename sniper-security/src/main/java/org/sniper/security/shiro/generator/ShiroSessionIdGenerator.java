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
 * Create Date : 2016-1-5
 */

package org.sniper.security.shiro.generator;

import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

/**
 * Shiro SessionID生成器接口
 * @author  Daniele
 * @version 1.0
 */
public interface ShiroSessionIdGenerator extends SessionIdGenerator {
	
	/**
	 * 设置前缀
	 * @author Daniele 
	 * @param prefix
	 */
	public void setPrefix(String prefix);
	
	/**
	 * 获取前缀
	 * @author Daniele 
	 * @return
	 */
	public String getPrefix();
	
	/**
	 * 设置后缀
	 * @author Daniele 
	 * @param suffix
	 */
	public void setSuffix(String suffix);
	
	/**
	 * 获取后缀
	 * @author Daniele 
	 * @return
	 */
	public String getSuffix();
	
}
