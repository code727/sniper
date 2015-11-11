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
 * Create Date : 2015-11-11
 */

package org.workin.fastdfs.task;

import java.util.List;
import java.util.Set;

import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.StringUtils;
import org.workin.fastdfs.meta.FastDFSMeta;
import org.workin.fastdfs.support.FastDFSTemplet;

/**
 * @description FastDFS旧资源清理信息
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class OriginalResourcesDeleteTask<T> implements Runnable {
	
	private boolean deleteAll;
	
	private List<FastDFSMeta<T>> metas;
	
	private FastDFSTemplet templet;
	
	public OriginalResourcesDeleteTask(List<FastDFSMeta<T>> metas, FastDFSTemplet templet) {
		this.metas = metas;
		this.templet = templet;
	}
	
	public OriginalResourcesDeleteTask(boolean deleteAll, List<FastDFSMeta<T>> metas, FastDFSTemplet templet) {
		this.deleteAll = deleteAll;
		this.metas = metas;
		this.templet = templet;
	}

	@Override
	public void run() {
		Set<String> pathSet = CollectionUtils.newHashSet();
		if (this.deleteAll) {
			for (FastDFSMeta<T> meta : this.metas) {
				String originalId = meta.getOriginalId();
				String originalZoomId = meta.getOriginalId();
				if (StringUtils.isNotBlank(originalId))
					pathSet.add(originalId);
				if (StringUtils.isNotBlank(originalZoomId))
					pathSet.add(originalZoomId);
			}
		} else {
			for (FastDFSMeta<T> meta : this.metas) {
				String originalId = meta.getOriginalId();
				if (StringUtils.isNotBlank(originalId))
					pathSet.add(originalId);
			}
		}
		try {
			this.templet.bathDelete(pathSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
