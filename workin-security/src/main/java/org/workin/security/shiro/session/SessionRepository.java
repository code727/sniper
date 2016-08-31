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
 * Create Date : 2015-5-11
 */

package org.workin.security.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;

/**
 * Shiro会话库接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SessionRepository {
	
	/**
	 * 保存会话
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param session
	 */
	public void saveSession(Session session);  
	
	/**
	 * 删除一个指定标识的会话
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sessionId
	 */
	public void deleteSession(Serializable sessionId);  
	
	/**
	 * 获取一个指定标识的会话
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sessionId
	 * @return
	 */
	public Session getSession(Serializable sessionId);
	
	/**
	 * 获取所有的会话
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Collection<Session> getAllSessions();  

}
