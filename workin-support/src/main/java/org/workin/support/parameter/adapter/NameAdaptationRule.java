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
 * Create Date : 2015年11月13日
 */

package org.workin.support.parameter.adapter;

/**
 * @description 参数名适配规则
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class NameAdaptationRule<K> {
	
	/** 原始参数名称 */
	private K originalName;
	
	/** 适配参数名称 */
	private K adaptationName;
	
	public NameAdaptationRule(){}
	
	public NameAdaptationRule(K originalName, K adaptationName){
		this.originalName = originalName;
		this.adaptationName = adaptationName;
	}

	public K getOriginalName() {
		return this.originalName;
	}

	public void setOriginalName(K originalName) {
		this.originalName = originalName;
	}

	public K getAdaptationName() {
		return this.adaptationName;
	}

	public void setAdaptationName(K adaptationName) {
		this.adaptationName = adaptationName;
	}
	
}
