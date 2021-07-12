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
 * Create Date : 2015-6-11
 */

package org.sniper.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet接口
 * @author  Daniele
 * @version 1.0
 */
public interface ServletAware {
	
	/**
	 * 获取HttpServletRequest对象
	 * @author Daniele 
	 * @return
	 */
	public HttpServletRequest getHttpServletRequest();
	
	/**
	 * 获取HttpServletResponse对象
	 * @author Daniele 
	 * @return
	 */
	public HttpServletResponse getHttpServletResponse();
	
	/**
	 * 获取HttpSession对象
	 * @author Daniele 
	 * @return
	 */
	public HttpSession getHttpSession();
	
	/**
	 * 获取HttpSession对象，并制定为空时是否创建一个新的对象后再返回
	 * @author Daniele 
	 * @param create
	 * @return
	 */
	public HttpSession getHttpSession(boolean create);

}
