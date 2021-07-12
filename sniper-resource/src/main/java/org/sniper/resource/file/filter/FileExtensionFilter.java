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

package org.sniper.resource.file.filter;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * 文件扩展过滤器
 * @author  Daniele
 * @version 1.0
 */
public interface FileExtensionFilter extends FileFilter {
	
	/**
	 * 判断过滤操作是否包含根文件/目录
	 * @author Daniele 
	 * @return
	 */
	public boolean isContainsRoot();
	
	/** 
	 * 设置过滤操作是否包含根文件/目录
	 * @author Daniele 
	 * @param containsRoot
	 */
	public void setContainsRoot(boolean containsRoot);
		
	/**
	 * 设置开始过滤的根目录
	 * @author Daniele 
	 * @param root
	 */
	public void setRoot(File root);
	
	/**
	 * 获取开始过滤的根目录
	 * @author Daniele 
	 * @return
	 */
	public File getRoot();
	
	/**
	 * 执行过滤操作
	 * @author Daniele
	 */
	public void execute();
	
	/**
	 * 清空结果集
	 * @author Daniele
	 */
	public void clear();
	
	/**
	 * 获取满足过滤条件的结果集
	 * @author Daniele 
	 * @return
	 */
	public List<File> list();
	
}
