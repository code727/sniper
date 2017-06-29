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
 * Create Date : 2015-2-2
 */

package org.sniper.persistence.hibernate.filter.v4;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;





import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.persistence.OpenEntitySessionManagerInViewFilter;
import org.sniper.persistence.OpenEntitySessionManagerInViewFilterProxy;

/**
 * Hibernate4实体视图管理过滤器。此类的作用在于防止当实体启用了延迟加载策略后，
 * 				加载的数据返回给view层的时候Session已经关闭而导致的访问异常。
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class OpenSessionManagerInViewFilter extends
		org.springframework.orm.hibernate4.support.OpenSessionInViewFilter {
	
	/** 视图管理过滤器代理 */
	private OpenEntitySessionManagerInViewFilterProxy proxy;
	
	/** 代理实现类匹配参数名称 */
	private static final String proxyName = "filterProxy";
	
	@Override
	protected void initFilterBean() throws ServletException {
		FilterConfig config = getFilterConfig();
		String filterProxy = config.getInitParameter(proxyName);
		if (StringUtils.isBlank(filterProxy))
			proxy = new OpenEntitySessionManagerInViewFilter();
		else
			try {
				proxy = ReflectionUtils.newInstance(filterProxy.trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		String exclude = config.getInitParameter(OpenEntitySessionManagerInViewFilterProxy.EXCLUDE_SUFFIXS_NAME);
		String include = config.getInitParameter(OpenEntitySessionManagerInViewFilterProxy.INCLUDE_SUFFIXS_NAME);
		proxy.initFilterBean(exclude, include);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return proxy.shouldNotFilter(request);
	}


}
