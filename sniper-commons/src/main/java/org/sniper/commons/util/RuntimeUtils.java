/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-11-17
 */

package org.sniper.commons.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * 运行时工具类
 * @author  Daniele
 * @version 1.0
 */
public abstract class RuntimeUtils {
	
	public static final RuntimeMXBean RUNTIME_MXBEAN;
	
	static {
		RUNTIME_MXBEAN = ManagementFactory.getRuntimeMXBean();
	}
	
	/**
	 * 获取本机JVM运行时的PID
	 * @author Daniele 
	 * @return
	 */
	public static int jvmPid() {
		String name = RUNTIME_MXBEAN.getName();
		try {
			return Integer.valueOf(name.split("@")[0]);
		} catch (Exception e) {
			return -1;
		}
	}
				
}
