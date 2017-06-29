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
 * Create Date : 2015-6-10
 */

package org.sniper.persistence.handler;

import org.sniper.commons.util.StringUtils;

/**
 * 同时采用"登录名[用户名]"格式作为审核者名称的处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class LUPrincipalAuditorHandler extends AbstractPrincipalAuditorHandler {

	@Override
	public String getAuditorName() {
		StringBuffer auditorName = new StringBuffer();
		auditorName.append(pincipalManager.getCurrentLoginName());
		
		if (StringUtils.isNotBlank(pincipalManager.getCurrentUserName()))
			// 用户名不为空时，则在字符串后追加上"[用户名]"格式
			auditorName.append("[").append(pincipalManager.getCurrentUserName()).append("]");
					
		return auditorName.toString();
	}

}
