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
 * Create Date : 2015年11月25日
 */

package org.workin.commons.enums.category;

import org.workin.commons.enums.AbstractLocaleEnums;

/**
 * @description 业务状态枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BizStatus extends AbstractLocaleEnums<String> {

	protected BizStatus(String key, String value) {
		super(key, value);
	}
	
	/** 失败 */
	public static final SystemStatus FAILED = new SystemStatus("-biz0001", "msg.biz.failed.status");
	
	/** 未知 */
	public static final SystemStatus UNKNOW = new SystemStatus("biz0000", "msg.biz.unknow.status");
	
	/** 成功 */
	public static final SystemStatus SUCCESS = new SystemStatus("biz0001", "msg.biz.success.status");

}
