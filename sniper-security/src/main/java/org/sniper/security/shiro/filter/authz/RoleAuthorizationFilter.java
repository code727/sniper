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
 * Create Date : 2015-7-27
 */

package org.sniper.security.shiro.filter.authz;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.sniper.commons.util.ArrayUtils;

/**
 * 自定义角色授权过滤器。
 * @author  Daniele
 * @version 1.0
 */
public class RoleAuthorizationFilter extends AuthorizationFilter {
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		
		Subject subject = getSubject(request, response);
		String[] roles = (String[]) mappedValue;
		
		if (ArrayUtils.isEmpty(roles)) 
			return true;
		
		/* 
		 * 当前用户只要有一个角色匹配时，此过滤器就认为授权成功。
		 * 而Shiro自带的RolesAuthorizationFilter则必须要求所有的角色都匹配时，授权才会成功
		 */
		for (String role : roles) {
			if (subject.hasRole(role)) 
				return true;
		}
		
		return false;
		
	}

}
