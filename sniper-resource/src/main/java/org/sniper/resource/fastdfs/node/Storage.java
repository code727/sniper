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
 * Create Date : 2015-10-30
 */

package org.sniper.resource.fastdfs.node;

import org.sniper.resource.server.IntranetServer;

/**
 * Storage接口
 * @author  Daniele
 * @version 1.0
 */
public interface Storage extends IntranetServer {
	
	/** Storage默认的服务端口 */
	public static final int DEFAULT_PORT = 23000;
	
}
