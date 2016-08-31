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
 * Create Date : 2016-5-20
 */

package org.workin.security.shiro.filter.authc;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.workin.commons.util.StringUtils;
import org.workin.security.filter.RedirectAuthenticationFilter;
import org.workin.security.shiro.ShiroWebUtils;

/**
 * Shiro 重定向认证过滤器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ShiroRedirectAuthenticationFilter extends
		PassThruAuthenticationFilter implements RedirectAuthenticationFilter {
	
	private String parameterName = org.workin.web.WebUtils.REDIRECT_URL_PARAMETER_NAME;

	@Override
	public void setParameterName(String parameterName) {
		if (StringUtils.isNotBlank(parameterName))
			this.parameterName = parameterName.trim();
	}

	@Override
	public String getParameterName() {
		return this.parameterName ;
	}

	@Override
	public void setSuccessUrl(String successUrl) {
		if (StringUtils.isNotBlank(successUrl))
			super.setSuccessUrl(successUrl.trim());
	}

	@Override
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}

	@Override
	public String getSavedRequestUrl() {
		return getSavedRequestUrl(ShiroWebUtils.getServletRequest());
	}

	@Override
	public String getSavedRequestUrl(ServletRequest request) {
		String savedRequestUrl = null;
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		
		if (savedRequest != null) 
			// 先从SavedRequest对象中获取URL
			savedRequestUrl = savedRequest.getRequestUrl();
		
		if (StringUtils.isBlank(savedRequestUrl)) 
			// 上一步未获取到的URL时，则此步从request对象中获取redirectUrl参数值来作为URL
			savedRequestUrl = request.getParameter(this.parameterName);
		
		if (StringUtils.isBlank(savedRequestUrl)) 
			// 上两步都未获取到时，则直接使用默认的URL
			savedRequestUrl = (StringUtils.isNotBlank(getSuccessUrl()) ? getSuccessUrl() : DEFAULT_SUCCESS_URL);
		
		if (savedRequest == null && savedRequestUrl.startsWith("/") && savedRequestUrl.length() > 1)
			// 没有保存认证前的请求对象时，则说明客户端是在直接进行认证操作，因此返回URL之前需要去掉"/"前缀
			savedRequestUrl = StringUtils.afterFrist(savedRequestUrl, "/");
		
		return savedRequestUrl;
	}

	@Override
	public void redirectToSavedRequest() throws Exception {
		redirectToSavedRequest(ShiroWebUtils.getServletRequest(), ShiroWebUtils.getServletResponse());
	}

	@Override
	public void redirectToSavedRequest(ServletRequest request, ServletResponse response) throws Exception {
		WebUtils.redirectToSavedRequest(request, response, getSavedRequestUrl(request));	
	}

}
