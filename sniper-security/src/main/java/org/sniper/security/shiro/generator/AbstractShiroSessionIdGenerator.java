/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-1-5
 */

package org.sniper.security.shiro.generator;

/**
 * Shiro SessionID生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractShiroSessionIdGenerator implements ShiroSessionIdGenerator {
	
	/** 前缀 */
	private String prefix;
	
	/** 后缀 */
	private String suffix;
	
	@Override
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String getPrefix() {
		return this.prefix;
	}

	@Override
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String getSuffix() {
		return this.suffix;
	}

}
