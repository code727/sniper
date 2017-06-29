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
 * Create Date : 2015-11-10
 */

package org.sniper.fastdfs.accessor;

import java.util.Iterator;
import java.util.Map.Entry;

import org.sniper.commons.util.StringUtils;
import org.sniper.fastdfs.cluster.Cluster;
import org.sniper.fastdfs.cluster.StorageGroup;

/**
 * FastDFS访问器支持类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AccessorSupport implements Accessor {

	@Override
	public String getStoragePath(Cluster cluster, String url) {
		String path = url;
		Iterator<Entry<String, StorageGroup>> iterator = cluster.getStorageGroups().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, StorageGroup> groupEntry = iterator.next();
			String pathPrefix = groupEntry.getKey();
			if (StringUtils.indexOf(path, pathPrefix) > -1) {
				path = pathPrefix + StringUtils.afterFrist(path, pathPrefix);
				break;
			}
		}
		return path;
	}

}
