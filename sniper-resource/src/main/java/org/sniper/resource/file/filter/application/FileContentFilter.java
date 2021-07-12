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
 * Create Date : 2015-1-19
 */

package org.sniper.resource.file.filter.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.sniper.commons.util.IOUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.commons.util.SystemUtils;
import org.sniper.resource.file.filter.AbstractFileContentFilter;

/**
 * 文件内容过滤器实现类
 * @author  Daniele
 * @version 1.0
 */
public class FileContentFilter extends AbstractFileContentFilter {
	
	@Override
	public boolean accept(File target) {
		
		if (StringUtils.isEmpty(this.filterValue))
			return true;
		
		if (target.isFile()) {
			BufferedReader reader = null;
			try {
				reader = IOUtils.newBufferedReader(target, this.encoding);
				if ((SystemUtils.UNIX_TEXT_NEWLINE.equals(this.filterValue) 
						|| SystemUtils.WINDOWS_TEXT_NEWLINE.equals(this.filterValue) 
						|| SystemUtils.MAC_TEXT_NEWLINE.equals(this.filterValue)) && IOUtils.lines(reader) > 1)
					
					// 匹配换行符
					return true;
					
				String lines;
				while ((lines = reader.readLine()) != null) {
					if (StringUtils.indexOf(lines, this.filterValue, ignoreCase) > -1)
						return true;
				}
				return false;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					IOUtils.close(reader);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

}
