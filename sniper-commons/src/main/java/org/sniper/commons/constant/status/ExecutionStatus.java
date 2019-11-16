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
 * Create Date : 2015-11-17
 */

package org.sniper.commons.constant.status;

import java.util.Map;

import org.sniper.commons.constant.AbstractNestedLocaleConstant;

/**
 * 系统执行状态常量类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class ExecutionStatus extends AbstractNestedLocaleConstant<Integer, String> {
	
	private static final long serialVersionUID = -4886626018923699286L;

	/** 成功 */
	public static final ExecutionStatus SUCCESS = new ExecutionStatus(1, BizStatus.SUCCESS);
	
	/** 失败，业务逻辑之类引起的 */
	public static final ExecutionStatus FAILED = new ExecutionStatus(0, BizStatus.FAILED);
	
	/** 异常 */
	public static final ExecutionStatus EXCEPTION = new ExecutionStatus(-1, BizStatus.EXCEPTION);
	
	private static final Map<Integer, ExecutionStatus> mappings;
	
	static {
		mappings = createMapping(ExecutionStatus.class);
	}
	
	protected ExecutionStatus(Integer key, BizStatus value) {
		super(key, value);
	}
	
	/**
	 * 将指定的键解析成ExecutionStatus对象
	 * @param key
	 * @return
	 */
	public static ExecutionStatus resolve(int key) {
		return mappings.get(key);
	}
				
}
