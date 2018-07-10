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
 * Create Date : 2015-1-16
 */

package org.sniper.resource.file.filter.application;

import java.io.File;

import org.sniper.commons.util.StringUtils;
import org.sniper.resource.file.filter.AbstractFileStringFilter;

/**
 * 文件名过滤器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FileNameFilter extends AbstractFileStringFilter {
	
	public FileNameFilter() {}
	
	public FileNameFilter(String filterValue) {
		this.filterValue = filterValue;
	}
		
	@Override
	public boolean accept(File target) {				
		return StringUtils.isEmpty(this.filterValue)
				|| StringUtils.indexOf(target.getName(), filterValue, ignoreCase) > -1;
	}
	
}
