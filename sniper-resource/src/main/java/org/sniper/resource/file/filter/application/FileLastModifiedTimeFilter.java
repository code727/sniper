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
 * Create Date : 2015-1-20
 */

package org.sniper.resource.file.filter.application;

import java.io.File;

import org.sniper.resource.file.filter.AbstractFileNumberFilter;

/**
 * 文件最后修改时间过滤器实现类
 * @author  Daniele
 * @version 1.0
 */
public class FileLastModifiedTimeFilter extends AbstractFileNumberFilter {

	/**
	 * 判断目标文件/目录的最后修改时间是否满足逻辑运算条件
	 * @author Daniele 
	 * @param pathname
	 * @return 
	 */
	@Override
	public boolean accept(File target) {
		if (logicOperation == null || filterValue == null)
			return true;
		
		return logicOperation.execute(target.lastModified(), filterValue);
	}

}
