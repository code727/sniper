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
 * Create Date : 2015-6-23
 */

package org.sniper.web.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.sniper.context.ThreadLocalHolder;
import org.sniper.web.WebUtils;
import org.sniper.web.spring.invocation.WebApplicationContextInvocation;

/**
 * Spring Web应用上下文帮助类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class WebContextHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(WebContextHelper.class);
	
	/**
	 * 获取HttpServletRequest对象。
	 * 需要配置org.springframework.web.context.request.RequestContextListener过滤器或
	 * org.sniper.web.spring.invocation.WebApplicationContextInvocation拦截器后才能获取到不为空的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
		HttpServletRequest request = null;
		if (requestAttributes == null) {
			request = (HttpServletRequest) ThreadLocalHolder.getAttribute(WebUtils.HTTP_SERVLET_REQUEST_NAME);
			if (request == null) {
				logger.warn("HttpServletRequest is null,please configure '{}' or '{}'", 
						RequestContextListener.class, WebApplicationContextInvocation.class);
			}
		} else {
			request = requestAttributes.getRequest();
		}
		
		return request;
	}
	
	/**
	 * 获取HttpServletResponse对象。
	 * 需要配置org.sniper.web.spring.invocation.WebApplicationContextInvocation拦截器后才能获取到不为空的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static HttpServletResponse getHttpServletResponse() {
		HttpServletResponse response = (HttpServletResponse) ThreadLocalHolder.getAttribute(WebUtils.HTTP_SERVLET_RESPONSE_NAME);
		
		if (response == null) {
			logger.warn("HttpServletResponse is null,please configure '{}'", WebApplicationContextInvocation.class);
		}
					
		return response;
	}
	
	/**
	 * 获取HttpSession对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static HttpSession getHttpSession() {
		return getHttpSession(false);
	}
	
	/**
	 * 获取HttpSession对象，为空时选择是否自动创建一个新对象后返回。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param create
	 * @return
	 */
	public static HttpSession getHttpSession(boolean create) {
		HttpServletRequest request = getHttpServletRequest();
		return request != null ? WebUtils.getSession(request, create) : null;
	}
	
}
