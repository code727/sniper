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
 * Create Date : 2015-12-29
 */

package org.workin.captcha.repository;

import java.util.Map;

/**
 * @description 可分组的文本库接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface GroupRepository {
	
	/**
	 * @description 设置分组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param group
	 */
	public void setGroup(Map<String, String> group);
	
	/**
	 * @description 获取指定组的文本内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public String getContent(String name);

}
