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

package org.workin.persistence.handler;

import org.workin.commons.util.StringUtils;

/**
 * 同时采用"用户名[登录名]"格式作为审核者名称的处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ULPrincipalAuditorHandler extends AbstractPrincipalAuditorHandler {

	@Override
	public String getAuditorName() {
		StringBuffer auditorName = new StringBuffer();
		
		if (StringUtils.isNotBlank(pincipalManager.getCurrentUserName()))
			// 用户名不为空时，格式为"用户名[登录名]"
			auditorName.append(pincipalManager.getCurrentUserName()).append("[")
				.append(pincipalManager.getCurrentLoginName()).append("]");
		else
			// 用户名为空时，则直接得到登录名
			auditorName.append(pincipalManager.getCurrentLoginName());
					
		return auditorName.toString();
	}

}
