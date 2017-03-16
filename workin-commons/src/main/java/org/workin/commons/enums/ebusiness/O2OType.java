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
 * Create Date : 2015-11-20
 */

package org.workin.commons.enums.ebusiness;

import org.workin.commons.enums.AbstractLocaleEnums;

/**
 * O2O类型枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class O2OType extends AbstractLocaleEnums<Integer> {

	private O2OType(Integer key, String messageKey) {
		super(key, messageKey);
	}
	
	/** 线上 */
	public static final O2OType ONLINE = new O2OType(0, "o2o.type.online");
	
	/** 线下 */
	public static final O2OType OFFLINE = new O2OType(1, "o2o.type.offline");

}
