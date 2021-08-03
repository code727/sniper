/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-7-26
 */

package org.sniper.commons.util;

import java.io.IOException;
import java.nio.channels.Channel;

/**
 * NIO工具类
 * @author  Daniele
 * @version 1.0
 */
public abstract class NIOUtils {
	
	/**
	 * 关闭通道
	 * @author Daniele 
	 * @param channel
	 * @throws IOException
	 */
	public static void close(Channel channel) throws IOException {
		if (channel != null)
			channel.close();
	}

}
