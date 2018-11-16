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

package org.sniper.commons.enums.status;

import org.sniper.commons.enums.AbstractLocaleEnum;

/**
 * 在线离线/线上到线下状态枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class O2OStatus extends AbstractLocaleEnum<Integer> {

	private static final long serialVersionUID = -1790893708912629703L;

	private O2OStatus(Integer key, String value) {
		super(key, value);
	}
	
	/** 线下/离线 */
	public static final O2OStatus OFFLINE = new O2OStatus(0, "o2o.status.offline");
	
	/** 线上/在线 */
	public static final O2OStatus ONLINE = new O2OStatus(1, "o2o.status.online");
	
}
