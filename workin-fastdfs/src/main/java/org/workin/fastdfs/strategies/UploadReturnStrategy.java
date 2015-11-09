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

package org.workin.fastdfs.strategies;

import org.workin.fastdfs.cluster.Cluster;

/**
 * @description 上传返回策略接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface UploadReturnStrategy {
	
	/**
	 * @description 根据FastDFS集群族和路径返回最终的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cluster
	 * @param path
	 * @return
	 */
	public String returnResult(Cluster cluster, String path);

}
