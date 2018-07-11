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

package org.sniper.resource.fastdfs;

import java.util.List;
import java.util.Set;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.resource.fastdfs.file.FastFileSource;
import org.sniper.resource.fastdfs.support.FastTemplate;

/**
 * FastDFS旧资源清理信息
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class OriginalResourcesDeleteTask<T> implements Runnable {
	
	private boolean deleteAll;
	
	private List<FastFileSource<T>> metas;
	
	private FastTemplate fastTemplate;
	
	public OriginalResourcesDeleteTask(List<FastFileSource<T>> metas, FastTemplate fastTemplate) {
		this.metas = metas;
		this.template = template;
	}
	
	public OriginalResourcesDeleteTask(boolean deleteAll, List<FastFileSource<T>> metas, FastTemplate fastTemplate) {
		this.deleteAll = deleteAll;
		this.metas = metas;
		this.template = template;
	}

	@Override
	public void run() {
		Set<String> pathSet = CollectionUtils.newHashSet();
		if (this.deleteAll) {
			for (FastFileSource<T> meta : this.metas) {
				String originalId = meta.getOriginalId();
				String originalZoomId = meta.getOriginalId();
				if (StringUtils.isNotBlank(originalId))
					pathSet.add(originalId);
				if (StringUtils.isNotBlank(originalZoomId))
					pathSet.add(originalZoomId);
			}
		} else {
			for (FastFileSource<T> meta : this.metas) {
				String originalId = meta.getOriginalId();
				if (StringUtils.isNotBlank(originalId))
					pathSet.add(originalId);
			}
		}
		try {
			this.template.bathDelete(pathSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
