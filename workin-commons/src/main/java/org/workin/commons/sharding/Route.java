/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-4-18
 */

package org.workin.commons.sharding;

import org.workin.commons.util.StringUtils;

/**
 * 路由对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class Route {
		
	/** 路由目标 */
	private String target;
	
	public Route() {
		this(null);
	}
	
	public Route(String target) {
		this.target = target;
	}
	
	/**
	 * 获取路由目标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 设置路由目标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param target
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	
	/**
	 * 追加目标前缀
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param prefix
	 */
	public Route prefix(String prefix) {
		this.target = new StringBuilder(StringUtils.safeString(prefix)).append(StringUtils.safeString(target)).toString(); 
		return this;
	}
	
	/**
	 * 追加目标后缀
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param suffix
	 */
	public Route suffix(String suffix) {
		this.target = new StringBuilder(StringUtils.safeString(target)).append(StringUtils.safeString(suffix)).toString(); 
		return this;
	}
	
	@Override
	public String toString() {
		return StringUtils.safeString(target);
	}
	
		
}
