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

package org.workin.persistence;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description 实体/会话视图管理过滤器代理实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class OpenEntitySessionManagerInViewFilter implements OpenEntitySessionManagerInViewFilterProxy {
	
	/** 默认需要过滤的资源后缀名称组 */
	private static final String[] DEFAULT_INCLUDE_SUFFIXS = { ".action", ".do",
			".jsf", ".jsp" };
	
	/** 默认不需要过滤的资源后缀名称组 */
	private static final String[] DEFAULT_EXCLUDE_SUFFIXS = { ".js", ".css",
			".jpg", ".gif", ".png", ".icon" };
	
	/** 通配符 */
	private static final String WILDCARD = "*";
	
	/** 最终需要过滤的资源后缀名称组 */
	private static String[] includeSuffixs = DEFAULT_INCLUDE_SUFFIXS;
	
	/** 最终不需要过滤的资源后缀名称组 */
	private static String[] excludeSuffixs = DEFAULT_EXCLUDE_SUFFIXS;
	
	/**
	 * @description 初始化过滤器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @throws ServletException
	 */
	public void initFilterBean(String excludeSuffix, String includeSuffix) throws ServletException {
		if (StringUtils.isNotBlank(excludeSuffix)) {
			excludeSuffixs = excludeSuffix.split(",");
			excludeSuffixs = ArrayUtils.trim(excludeSuffixs);
			
			if (excludeSuffixs.length == 0)
				throw new ServletException("Exclusion suffixs can not be blank.");
				
			// exclude组存在通配符时，则忽略掉其它配置
			if (ArrayUtils.contains(excludeSuffixs, WILDCARD) && excludeSuffixs.length > 1)
				excludeSuffixs = new String[] { WILDCARD };
			
		} else
			excludeSuffixs = DEFAULT_EXCLUDE_SUFFIXS;
		
		if (StringUtils.isNotBlank(excludeSuffix)) {
			includeSuffixs = includeSuffix.split(",");
			includeSuffixs = ArrayUtils.trim(includeSuffixs);
			if (includeSuffixs.length == 0)
				throw new ServletException("Inclusion suffixs can not be blank.");
			
			// include组存在通配符时，则忽略掉其它配置
			if (ArrayUtils.contains(includeSuffixs, WILDCARD) && includeSuffixs.length > 1)
				includeSuffixs = new String[] { WILDCARD };
		} else 
			includeSuffixs = DEFAULT_INCLUDE_SUFFIXS;	
		
		for (String include : includeSuffixs) {
			// 任意一个后缀只能存在于其中一个组中，否则抛出异常
			if (ArrayUtils.contains(excludeSuffixs, include))
				throw new ServletException("Duplicate suffix:" + include + 
						",can not has same suffix value in includeSuffixs and excludeSuffixs.");
		}
	}
	
	/**
	 * @description 排除不需要过滤的资源
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param request
	 * @return
	 */
	public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();
		
		/* 若include组包含统配符，则忽略掉此组其它配置后检测是否属于exclude组 */
		if (ArrayUtils.contains(includeSuffixs, WILDCARD)) 
			return isExcludeGroup(path);
				
		/* 若exclude组包含统配符，则忽略掉此组其它配置后检测是否不属于include组 */
		if (ArrayUtils.contains(excludeSuffixs, WILDCARD)) 
			return !isIncludeGroup(path);
		
		/* 两组都不包含通配符时，只需判断是否属于exclude组，若不属于则一概认为需要过滤 */
		return isExcludeGroup(path);
	}
					
	/**
	 * @description 检测指定的路径是否属于exclude组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param path
	 * @return
	 */
	private static boolean isExcludeGroup(String path) {
		for (String exclude : excludeSuffixs) {
			if (path.endsWith(exclude))
				return true;
		}
		return false;
	}
	
	/**
	 * @description 检测指定的路径是否属于include组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param path
	 * @return
	 */
	private static boolean isIncludeGroup(String path) {
		for (String include : includeSuffixs) {
			if (path.endsWith(include))
				return true;
		}
		return false;
	}
		
}
