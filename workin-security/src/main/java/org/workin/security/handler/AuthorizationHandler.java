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

package org.workin.security.handler;

import org.workin.commons.util.SystemUtils;

/**
 * @description 授权处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface AuthorizationHandler {
	
	/** 换行标志 */
	public static final String CRLF = SystemUtils.getTextNewline();
	
	/**
	 * @description 创建角色授权规则字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String createRoleRule();
	
	/**
	 * @description 创建角色授权规则字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String createPermissionRule();
	
	/**
	 * @description 设置固定规则字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fixRule
	 */
	public void setFixRule(String fixRule);
	
	/**
	 * @description 获取固定规则字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getFixRule();

}
