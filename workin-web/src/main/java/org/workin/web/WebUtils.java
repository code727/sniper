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
 * Create Date : 2015-6-19
 */

package org.workin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description Web工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class WebUtils {
	
	/** HttpServletRequest对象的属性名 */
	public static final String HTTP_SERVLET_REQUEST_NAME = HttpServletRequest.class.getName();
	
	/** HttpServletResponse对象的属性名 */
	public static final String HTTP_SERVLET_RESPONSE_NAME = HttpServletResponse.class.getName();
	
	/** HttpSession对象的属性名 */
	public static final String HTTP_SESSION_NAME = HttpSession.class.getName();
	
}
