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

package org.sniper.sharding.route;

import java.io.Serializable;

import org.sniper.commons.util.StringUtils;

/**
 * 路由对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class Route implements Serializable {
		
	private static final long serialVersionUID = 391358322168130780L;
	
	/** 前缀 */
	private String prefix;
	
	/** 目标 */
	private String target;
	
	/** 后缀 */
	private String suffix;
	
	public Route() {
		this(null);
	}
	
	public Route(String prefix) {
		this(prefix, null);
	}
	
	public Route(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(StringUtils.safeString(prefix));
		builder.append(StringUtils.safeString(target));
		builder.append(StringUtils.safeString(suffix));
		return builder.toString();
	}
		
}
