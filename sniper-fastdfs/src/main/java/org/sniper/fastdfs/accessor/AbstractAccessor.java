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

import org.sniper.commons.util.StringUtils;

/**
 * FastDFS访问器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractAccessor extends AccessorSupport {
	
	/**
	 * 根据URL和路径包装后返回完整可访问的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param path
	 * @return
	 */
	protected String getAccessabeURL(String url, String path) {
		return new StringBuffer(url).append(
				(!StringUtils.endsWith(url, "/") && !StringUtils.startsWith(path, "/")) ? 
						"/" : StringUtils.EMPTY).append(path).toString();
	}
	
}
