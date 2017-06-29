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
 * Create Date : 2016-5-9
 */

package org.sniper.security.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.sniper.commons.util.StringUtils;

/**
 * Shiro Web工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ShiroWebUtils extends ShiroUtils {
	
	/**
	 * 获取已保存的请求URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static String getSavedRequestUrl() {
		return getSavedRequestUrl((String) null);
	}
	
	/**
	 * 获取已保存的请求URL，否则返回默认指定的URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fallbackUrl
	 * @return
	 */
	public static String getSavedRequestUrl(String fallbackUrl) {
		return getSavedRequestUrl(getServletRequest(), fallbackUrl);
	}
	
	/**
	 * 获取已保存的请求URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @return
	 */
	public static String getSavedRequestUrl(ServletRequest request) {
		return getSavedRequestUrl(request, null);
	}
	
	/**
	 * 获取已保存的请求URL，否则返回默认指定的URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param fallbackUrl
	 * @return
	 */
	public static String getSavedRequestUrl(ServletRequest request, String fallbackUrl) {
		String savedRequestUrl = null;
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		
		if (savedRequest != null) 
			// 先从SavedRequest对象中获取URL
			savedRequestUrl = savedRequest.getRequestUrl();
		
		if (StringUtils.isBlank(savedRequestUrl)) 
			// 上一步未获取到的URL时，则此步从request对象中获取redirectUrl参数值来作为URL
			savedRequestUrl = request.getParameter(org.sniper.web.WebUtils.REDIRECT_URL_PARAMETER_NAME);
		
		if (StringUtils.isBlank(savedRequestUrl)) 
			// 上两步都未获取到时，则直接使用默认的URL
			savedRequestUrl = (StringUtils.isNotBlank(fallbackUrl) ? fallbackUrl
					: AuthenticationFilter.DEFAULT_SUCCESS_URL);
		
		if (savedRequest == null && savedRequestUrl.startsWith("/") && savedRequestUrl.length() > 1)
			// 没有保存认证前的请求对象时，则说明客户端是在直接进行认证操作，因此返回URL之前需要去掉"/"前缀
			savedRequestUrl = StringUtils.afterFrist(savedRequestUrl, "/");
		
		return savedRequestUrl;
	}
	
	/**
	 * 重定向到已保存的请求URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @throws IOException
	 */
	public static void redirectToSavedRequest() throws IOException {
		redirectToSavedRequest(null);
	}
	
	/**
	 * 重定向到已保存的请求URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fallbackUrl
	 * @throws IOException
	 */
	public static void redirectToSavedRequest(String fallbackUrl) throws IOException {
		redirectToSavedRequest(getServletRequest(), getServletResponse(), fallbackUrl);
	}
	
	/**
	 * 重定向到已保存的请求URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void redirectToSavedRequest(ServletRequest request, ServletResponse response) throws IOException {
		redirectToSavedRequest(request, response, null);
	}
	
	/**
	 * 重定向到已保存的请求URL，否则重定向默认指定的URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @param response
	 * @param fallbackUrl
	 * @throws IOException
	 */
	public static void redirectToSavedRequest(ServletRequest request, ServletResponse response, String fallbackUrl) throws IOException {
		WebUtils.redirectToSavedRequest(request, response, getSavedRequestUrl(request, fallbackUrl));	
	}
	
	/**
	 * 获取javax.servlet.ServletRequest对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static ServletRequest getServletRequest() {
		return ((WebSubject) getCurrentSubject()).getServletRequest();
	}
	
	/**
	 * 获取javax.servlet.ServletResponse对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static ServletResponse getServletResponse() {
		return ((WebSubject) getCurrentSubject()).getServletResponse();
	}

}
