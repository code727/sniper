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

package org.workin.security.shiro.filter.authz;

import java.util.Date;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.commons.util.DateUtils;
import org.workin.commons.util.StringUtils;
import org.workin.security.handler.AuthorizationHandler;
import org.workin.security.manager.AuthorizationFilterChainManager;

/**
 * Shiro认证/授权过滤器链管理抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ShiroAuthorizationFilterChainManager implements
		AuthorizationFilterChainManager, InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(ShiroAuthorizationFilterChainManager.class);
	
	private static final Object lock = new Object();
	
	/** 授权处理器 */
	@Autowired
	private AuthorizationHandler authorizationHandlder;
	
	@Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.loadFilterChainDefinitions();
	}

	@Override
	public String loadFilterChainDefinitions() {
		StringBuffer filterChainDefinitions = new StringBuffer();
		logger.info("Start loading shiro filter chain definitions.");
		Date start = new Date();
		filterChainDefinitions.append(StringUtils.safeString(authorizationHandlder.getFixRule()))
			.append(authorizationHandlder.createRoleRule()).append(authorizationHandlder.createPermissionRule());
		logger.info("Sucessful create shiro filter chain definitions in {}ms.\n{}", 
				DateUtils.getIntervalMillis(start, new Date()), filterChainDefinitions);
		
		return filterChainDefinitions.toString();
	}
	
	@Override
	public void reloadFilterChainDefinitions() {
		AbstractShiroFilter shiroFilter = null;
		try {
			shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
		} catch (Exception e) {
			logger.error("getShiroFilter from shiroFilterFactoryBean error!", e);
			throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
		}
		
		PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
		DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
		
		synchronized (lock) {
			/* 清空以前的认证/授权规则后，重新加载新的授权规则 */
			manager.getFilterChains().clear();
			shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
			shiroFilterFactoryBean.setFilterChainDefinitions(this.loadFilterChainDefinitions());
					
			// 重新构建生成
			Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
			for (Map.Entry<String, String> entry : chains.entrySet()) {
				String url = entry.getKey();
				String chainDefinition = StringUtils.trimAll(entry.getValue());
				manager.createChain(url, chainDefinition);
			}
		}
	}
	
}