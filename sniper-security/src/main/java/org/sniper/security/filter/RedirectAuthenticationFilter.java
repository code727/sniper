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

package org.sniper.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 重定向认证过滤器
 * @author  Daniele
 * @version 1.0
 */
public interface RedirectAuthenticationFilter {
	
	/**
	 * 设置参数名
	 * @author Daniele 
	 * @param redirectParameterName
	 */
	public void setParameterName(String parameterName);
	
	/**
	 * 获取参数名
	 * @author Daniele 
	 * @return
	 */
	public String getParameterName();
	
	/**
	 * 设置登录认证成功后重定向的URL
	 * @author Daniele 
	 * @param successUrl
	 */
	public void setSuccessUrl(String successUrl);
	
	/**
	 * 获取登录认证成功后重定向的URL
	 * @author Daniele 
	 * @return
	 */
	public String getSuccessUrl();
	
	/**
	 * 获取已保存的请求URL
	 * @author Daniele 
	 * @return
	 */
	public String getSavedRequestUrl();
	
	/**
	 * 获取已保存的请求URL
	 * @author Daniele 
	 * @param request
	 * @return
	 */
	public String getSavedRequestUrl(ServletRequest request);
	
	/**
	 * 重定向到已保存的请求URL
	 * @author Daniele 
	 * @throws Exception
	 */
	public void redirectToSavedRequest() throws Exception;
	
	/**
	 * 重定向到已保存的请求URL
	 * @author Daniele 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void redirectToSavedRequest(ServletRequest request, ServletResponse response) throws Exception;

}
