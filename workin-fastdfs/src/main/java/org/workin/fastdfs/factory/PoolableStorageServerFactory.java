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
 * Create Date : 2015-11-4
 */

package org.workin.fastdfs.factory;

import org.apache.commons.pool.PoolableObjectFactory;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;

/**
 * @description 可池化的StorageServer对象工厂接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface PoolableStorageServerFactory extends PoolableObjectFactory<StorageServer> {
	
	public TrackerClient getTrackerClient();
	
	public void setTrackerClient(TrackerClient trackerClient);

}
