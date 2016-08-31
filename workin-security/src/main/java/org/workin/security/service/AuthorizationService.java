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

package org.workin.security.service;

import java.util.List;

import org.workin.security.AuthorizationModel;

/**
 * 授权服务接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface AuthorizationService {

	/** 
	 * 加载角色授权规则列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>  
	 */
	public List<AuthorizationModel> loadRoleRules();
	
	/**
	 * 加载权限授权规则列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<AuthorizationModel> loadPermissionRules();

}
