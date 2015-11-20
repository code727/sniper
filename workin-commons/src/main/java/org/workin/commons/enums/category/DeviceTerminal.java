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

package org.workin.commons.enums.category;

import org.workin.commons.enums.AbstractLocaleEnums;

/**
 * @description 设备终端类型枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DeviceTerminal extends AbstractLocaleEnums<Integer> {

	protected DeviceTerminal(Integer key, String messageKey) {
		super(key, messageKey);
	}
	
	public static final DeviceTerminal PC = new DeviceTerminal(0, "ms.device.terminal.pc");
	public static final DeviceTerminal ANDROID = new DeviceTerminal(1, "ms.device.terminal.android");
	public static final DeviceTerminal IOS = new DeviceTerminal(2, "ms.device.terminal.ios");
	public static final DeviceTerminal WINPHONE = new DeviceTerminal(3, "ms.device.terminal.winphone");

}
