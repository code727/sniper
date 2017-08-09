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
 * Create Date : 2015-7-7
 */

package org.sniper.http.form;

import java.util.Map;

/**
 * HTTP表单注册器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface HttpFormRegister {
		
	/**
	 * 设置表单映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param formMap
	 */
	public void setFormMap(Map<String, HttpForm> formMap);
	
	/**
	 * 获取表单映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<String, HttpForm> getFormMap();
	
	/**
	 * 根据名称查找到对应的表单对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public HttpForm find(String name);
	
	/**
	 * 根据名称查找到对应表单的URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public String findURL(String name);
		
}
