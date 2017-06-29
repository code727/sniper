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

package org.sniper.security.shiro;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.springframework.beans.factory.InitializingBean;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.security.AuthorizationModel;
import org.sniper.security.handler.AuthorizationHandlerAdapter;
import org.sniper.security.service.AuthorizationService;

/**
 * Shiro授权处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AuthorizationHandler extends AuthorizationHandlerAdapter implements InitializingBean {
	
	/** Shiro授权过滤器(AuthenticatingFilter)标识 */
	protected static final String AUTHC = "authc";
	
	/** 角色授权过滤器 */
	private AuthorizationFilter roleAuthorizationFilter;
	
	/** 权限授权过滤器 */
	private AuthorizationFilter permissionAuthorizationFilter;
	
	/** 角色授权过滤器标识名称 */
	private String roleAuthorizationFilterName;
	
	/** 权限授权过滤器标识名称 */
	private String permissionAuthorizationFilterName;
	
	public AuthorizationFilter getRoleAuthorizationFilter() {
		return roleAuthorizationFilter;
	}

	public void setRoleAuthorizationFilter(
			AuthorizationFilter roleAuthorizationFilter) {
		this.roleAuthorizationFilter = roleAuthorizationFilter;
	}

	public AuthorizationFilter getPermissionAuthorizationFilter() {
		return permissionAuthorizationFilter;
	}

	public void setPermissionAuthorizationFilter(
			AuthorizationFilter permissionAuthorizationFilter) {
		this.permissionAuthorizationFilter = permissionAuthorizationFilter;
	}

	public String getRoleAuthorizationFilterName() {
		return roleAuthorizationFilterName;
	}

	public String getPermissionAuthorizationFilterName() {
		return permissionAuthorizationFilterName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.roleAuthorizationFilter == null)
			this.roleAuthorizationFilter = new RolesAuthorizationFilter();
		
		if (this.permissionAuthorizationFilter == null)
			this.permissionAuthorizationFilter = new PermissionsAuthorizationFilter();
		
		this.roleAuthorizationFilterName = StringUtils.uncapitalize(this.roleAuthorizationFilterName.getClass().getSimpleName());
		this.permissionAuthorizationFilterName = StringUtils.uncapitalize(this.permissionAuthorizationFilter.getClass().getSimpleName());
	}
		
	@Override
	public String createRoleRule() {
		AuthorizationService authorizationService = getAuthorizationService();
		if (authorizationService != null) {
			List<AuthorizationModel> roleRules = authorizationService.loadRoleRules();
			Map<String, String> authMap = this.createAuthorizationMap(roleRules);
			return buildeAuthString(authMap, this.roleAuthorizationFilterName);
		} else 
			return super.createRoleRule();
	}
	
	@Override
	public String createPermissionRule() {
		AuthorizationService authorizationService = getAuthorizationService();
		if (authorizationService != null) {
			List<AuthorizationModel> roleRules = authorizationService.loadPermissionRules();
			Map<String, String> authMap = this.createAuthorizationMap(roleRules);
			return buildeAuthString(authMap, this.permissionAuthorizationFilterName);
		} else 
			return super.createPermissionRule();
	}
	
	/**
	 * 根据规则列表创建授权映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param rules
	 * @return
	 */
	protected Map<String, String> createAuthorizationMap(List<AuthorizationModel> rules) {
		Map<String, String> authMap = MapUtils.newHashMap();
		if (CollectionUtils.isNotEmpty(rules)) {
			String ruleKey;
			String ruleValue;
			String currentRuleValue;
			for (AuthorizationModel rule : rules) {
				if (rule != null) {
					ruleKey = rule.getRuleKey();
					ruleValue = rule.getRuleValue();
					if (StringUtils.isBlank(ruleKey) && StringUtils.isBlank(ruleValue)) {
						// 当前已有的规则值
						currentRuleValue = authMap.get(ruleKey);
						authMap.put(ruleKey, StringUtils.isNotBlank(currentRuleValue) ? 
								currentRuleValue + "," + ruleValue : ruleValue);
					}
				}
			}
		}
		return authMap;
	}
	
	/**
	 * 根据授权映射集合和与之关联的过滤器名称，构建出最终的授权规则字符串结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param authMap
	 * @param filterName
	 * @return
	 */
	protected String buildeAuthString(Map<String, String> authMap, String filterName) {
		StringBuffer result = new StringBuffer();
		if (MapUtils.isNotEmpty(authMap)) {
			Set<Entry<String, String>> authSet = authMap.entrySet();
			for (Entry<String, String> auth : authSet) {
				result.append(auth.getKey()).append(" = ").append(AUTHC).append(",").append(StringUtils.SPACE_STRING)
					.append(filterName).append("[").append(auth.getValue()).append("]").append(CRLF);
			}
		}
		return result.toString();
	}

}
