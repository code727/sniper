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
	
	/** 前缀标志 */
	private String prefix;
	
	/** 后缀标志 */
	private String suffix;
	
	/** 目标 */
	private String target;
	
	public Route() {
		this(null, null, null);
	}
	
	public Route(String prefix) {
		this(prefix, null, null);
	}
	
	public Route(String prefix, String target) {
		this(prefix, target, null);
	}
	
	public Route(String prefix, String target, String suffix) {
		this.prefix = prefix;
		this.target = target;
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getTarget() {
		return new StringBuilder(StringUtils.safeString(prefix)).append(StringUtils.safeString(target))
				.append(StringUtils.safeString(suffix)).toString();
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	@Override
	public String toString() {
		return getTarget();
	}
	
	
	
}
