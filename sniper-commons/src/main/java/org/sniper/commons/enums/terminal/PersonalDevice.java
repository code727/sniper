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

package org.sniper.commons.enums.terminal;

import org.sniper.commons.enums.AbstractLocaleEnum;

/**
 * 个人设备类型枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class PersonalDevice extends AbstractLocaleEnum<Integer> {

	private static final long serialVersionUID = 2143744347288417229L;
	
	private PersonalDevice(Integer key, String value) {
		super(key, value);
	}
	
	public static final PersonalDevice PC = new PersonalDevice(0, "personal.device.type.pc");
	public static final PersonalDevice ANDROID = new PersonalDevice(1, "personal.device.type.android");
	public static final PersonalDevice IOS = new PersonalDevice(2, "personal.device.type.ios");
	public static final PersonalDevice WINPHONE = new PersonalDevice(3, "personal.device.type.winphone");
	
}
