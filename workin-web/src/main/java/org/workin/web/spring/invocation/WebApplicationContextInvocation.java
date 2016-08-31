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
 * Create Date : 2015-6-12
 */

package org.workin.web.spring.invocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.workin.support.context.ApplicationContextHolder;
import org.workin.web.WebUtils;

/**
 * 基于Web应用上下文的资源拦截器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class WebApplicationContextInvocation extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		/* 当执行请求之前，将request和response对象设置到应用上下文对象 */
		ApplicationContextHolder.setAttribute(WebUtils.HTTP_SERVLET_REQUEST_NAME, request);
		ApplicationContextHolder.setAttribute(WebUtils.HTTP_SERVLET_RESPONSE_NAME, response);
		ApplicationContextHolder.setAttribute(WebUtils.HTTP_SESSION_NAME, request.getSession());
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		/* 当请求执行结束后，将request和response从应用上下文对象中清除 */
		ApplicationContextHolder.removeAttribute(WebUtils.HTTP_SERVLET_REQUEST_NAME);
		ApplicationContextHolder.removeAttribute(WebUtils.HTTP_SERVLET_RESPONSE_NAME);
		ApplicationContextHolder.removeAttribute(WebUtils.HTTP_SESSION_NAME);
		super.afterCompletion(request, response, handler, ex);
	}
	
}
