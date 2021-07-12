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
 * Create Date : 2015-1-27
 */

package org.sniper.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 * @author  Daniele
 * @version 1.0
 */
public class ShiroUtils {
	
	/**
	 * 获取当前认证主题
	 * @author Daniele 
	 * @return
	 */
	public static Subject getCurrentSubject() {
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前认证通过后的会话对象
	 * @author Daniele 
	 * @return
	 */
	public static Session getCurrentSession() {
		return getCurrentSubject().getSession();
	}
	
	/**
	 * 在当前认证通过后的会话对象中设置属性
	 * @author Daniele 
	 * @param key
	 * @param value
	 */
	public static void setSessionAttribute(Object key, Object value) {
		getCurrentSession().setAttribute(key, value);
	}
	
	/**
	 * 获取当前用户
	 * @author Daniele 
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public static <T> T getCurrentUser() {
		return (T) ShiroUtils.getCurrentSubject().getPrincipal();
	}
	
	/**
	 * 获取当前用户
	 * @author Daniele 
	 * @param userType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getCurrentUser(Class<T> userType) {
		return (T) ShiroUtils.getCurrentSubject().getPrincipal();
	}
	
	/**
	 * 注销
	 * @author Daniele
	 */
	public static void logout() {
		Subject subject = ShiroUtils.getCurrentSubject();
		if (subject != null)
			subject.logout();
	}
	
}
