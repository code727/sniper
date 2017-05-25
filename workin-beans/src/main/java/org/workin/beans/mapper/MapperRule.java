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
 * Create Date : 2015-11-13
 */

package org.workin.beans.mapper;

/**
 * 映射器规则
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapperRule {
	
	/** 原始参数名称 */
	private String originalName;
	
	/** 被映射后的参数名称 */
	private String mappedName;
	
	public MapperRule(String originalName, String mappedName) {
		this.originalName = originalName;
		this.mappedName = mappedName;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getMappedName() {
		return mappedName;
	}

	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
	}

}