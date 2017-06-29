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
 * Create Date : 2015-11-9
 */

package org.sniper.fastdfs.accessor;

import org.sniper.fastdfs.cluster.Cluster;

/**
 * 默认访问器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultAccessor extends AccessorSupport {

	@Override
	public String getAccessableURL(Cluster cluster, String path) {
		// 按传入的路径直接返回，严格的说此实现类返回的结果不是一个可直接访问的完整URL
		return path;
	}

}
