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
 * Create Date : 2015-7-21
 */

package org.sniper.security.manager;

/**
 * 自定义授权过滤器链管理器接口
 * @author  Daniele
 * @version 1.0
 */
public interface AuthorizationFilterChainManager {
	
	/**
	 * 加载过滤链中配置的认证/授权规则信息
	 * @author Daniele 
	 * @return
	 */
	public String loadFilterChainDefinitions();
	
	/**
	 * 重新加载过滤链中配置的认证/授权规则信息。
	 * @author Daniele
	 */
	public void reloadFilterChainDefinitions();

}
