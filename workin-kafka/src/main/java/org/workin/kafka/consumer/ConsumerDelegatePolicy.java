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
 * Create Date : 2017-3-14
 */

package org.workin.kafka.consumer;

/**
 * 消费者委派策略枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum DelegatePolicy {
	
	/** 当找不到委派时用默认的来执行 */
	USE_DEFAULT_WHEN_DELEGATE_NOTFOUND("default"),
	
	/** 当找不到委派时直接报错 */
	ERROR_WHEN_DELEGATE_NOTFOUND("error");
	
	private String name;
	
	private DelegatePolicy(String name) {
        this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
