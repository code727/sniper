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

package org.sniper.support.thread.task;

import java.io.File;
import java.util.List;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.FileUtils;

/**
 * 本地文件列表清理任务
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FilesDeleteTask implements Runnable {
	
	private List<File> files;
	
	public FilesDeleteTask(File file) {
		if (this.files == null) {
			this.files = CollectionUtils.newArrayList();
		}
		
		this.files.add(file);
	}
	
	public FilesDeleteTask(List<File> files) {
		this.files = files;
	}

	@Override
	public void run() {
		for (File file : files)
			FileUtils.delete(file);
		this.files.clear();
	}

}
