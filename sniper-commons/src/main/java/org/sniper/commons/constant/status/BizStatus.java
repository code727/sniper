/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-11-16
 */

package org.sniper.commons.constant.status;

import org.sniper.commons.constant.AbstractNestedLocaleConstant;

/**
 * 业务状态常量类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BizStatus extends AbstractNestedLocaleConstant<String, Integer> {
	
	private static final long serialVersionUID = -2847353128385702823L;
	
	protected BizStatus(String key, ExecutionStatus value) {
		super(key, value);
	}
	
	/** 业务执行成功 */
	public static final BizStatus SUCCESS = new BizStatus("success", ExecutionStatus.SUCCESS);
	
	/** 异常，系统故障之类引起的 */
	public static final BizStatus EXCEPTION = new BizStatus("exception", ExecutionStatus.EXCEPTION);
	
	/** 业务执行失败，业务逻辑之类引起的 */
	public static final BizStatus FAILED = new BizStatus("failed", ExecutionStatus.FAILED);
		
}
