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

package org.sniper.commons.constant.terminal;

import org.sniper.commons.constant.AbstractLocaleConstant;

/**
 * 个人设备类型常量类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PersonalDevice extends AbstractLocaleConstant<Integer> {
	
	private static final long serialVersionUID = 2143744347288417229L;
	
	protected PersonalDevice(Integer key, String value) {
		super(key, value);
	}
	
	/** PC终端 */
	public static final PersonalDevice PC = new PersonalDevice(0, "personal.device.pc");
	
	/** Android终端 */
	public static final PersonalDevice ANDROID = new PersonalDevice(1, "personal.device.android");
	
	/** IOS终端 */
	public static final PersonalDevice IOS = new PersonalDevice(2, "personal.device.ios");
	
	/** WinPhone终端 */
	public static final PersonalDevice WINPHONE = new PersonalDevice(3, "personal.device.winphone");
	
}
