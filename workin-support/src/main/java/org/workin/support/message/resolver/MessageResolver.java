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

package org.workin.support.message.resolver;

/**
 * @description 消息解析器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface MessageResolver {
	
	/**
	 * @description 根据键获取对应的资源信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 资源项的键
	 * @return
	 */
	public String getMessage(String key);
	
	/**
	 * @description 根据键获取对应的资源信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 资源项的键
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public String getMessage(String key, String defaultMessage);
	
	/**
	 * @description 根据键获取具有唯一参数的资源信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 资源信息的键
	 * @param param 参数值
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public String getMessage(String key, Object param, String defaultMessage);
	
	/**
	 * @description 根据键获取具有唯一参数的资源信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 资源项的键
	 * @param param 参数值
	 * @return
	 */
	public String getMessage(String key, Object param);
	
	/**
	 * @description 根据键获取具有多个不同参数的资源信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 资源项的键
	 * @param params 参数值组
	 * @return
	 */
	public String getMessage(String key, Object[] params);
	
	/**
	 * @description 根据键获取具有多个不同参数的资源信息，未获取到时返回默认的信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 资源项的键
	 * @param params 参数值组
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public String getMessage(String key, Object[] params, String defaultMessage);

}
