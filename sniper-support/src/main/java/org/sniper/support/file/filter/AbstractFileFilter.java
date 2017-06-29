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
 * Create Date : 2015-1-15
 */

package org.sniper.support.file.filter;

import java.io.File;
import java.util.List;

import org.sniper.commons.util.CollectionUtils;

/**
 * 本地文件过滤器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractFileFilter implements SniperFileFilter {
	
	/** 开始过滤的根目录 */
	private File root;
			
	private List<File> files = CollectionUtils.newArrayList();
	
	/** 根文件/目录是否参与检索，默认为参与 */
	private boolean containsRoot = true;
	
	public boolean isContainsRoot() {
		return containsRoot;
	}

	public void setContainsRoot(boolean containsRoot) {
		this.containsRoot = containsRoot;
	}

	@Override
	public File getRoot() {
		return this.root;
	}

	@Override
	public void setRoot(File root) {
		this.root = root;
	}
	
	@Override
	public void clear() {
		this.files.clear();
	}
	
	@Override
	public List<File> list() {
		return this.files;
	}
	
	@Override
	public void doFileter() {
		if (root == null)
			return;
		
		if (this.containsRoot && this.accept(root))
			files.add(root);
		
		doFileter(root);
	}
	
	/**
	 * 过滤下级文件/目录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 */
	private void doFileter(File file) {
		File[] subFiles = file.listFiles();
		for (File sub : subFiles) {
			if (this.accept(sub)) 
				files.add(sub);
			if (sub.isDirectory())
				doFileter(sub);
		}
	}
	
}
