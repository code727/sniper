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
 * Create Date : 2015-1-28
 */

package org.sniper.persistence;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * 会话/实体视图管理过滤器代理接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface OpenEntitySessionManagerInViewFilterProxy {
	
	/** 过滤器参数名称includeSuffixs，表示需要过滤的资源后缀名称组 */
	public static final String INCLUDE_SUFFIXS_NAME = "includeSuffixs";
	
	/** 过滤器参数名称excludeSuffixs，表示不需要过滤的资源后缀名称组 */
	public static final String EXCLUDE_SUFFIXS_NAME = "excludeSuffixs";
	
	/**
	 * 根据后缀名称组初始化过滤器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param excludeSuffix 不需要过滤的资源后缀名称组
	 * @param includeSuffix 需要过滤的资源后缀名称组
	 * @throws ServletException
	 */
	public void initFilterBean(String excludeSuffix, String includeSuffix) throws ServletException;
	
	/**
	 * 排除不需要过滤的资源
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @return true:不需要过滤 , false:需要过滤
	 * @throws ServletException
	 */
	public boolean shouldNotFilter(HttpServletRequest request) throws ServletException;

}
