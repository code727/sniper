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
 * Create Date : 2015-11-5
 */

package org.sniper.resource.fastdfs.support;

import java.util.List;
import java.util.Map;

import org.sniper.resource.fastdfs.file.FastFileSource;

/**
 * FastDFS文件上传器
 * @author  Daniele
 * @version 1.0
 */
public interface FastUploader {
	
	/**
	 * 上传文件资源
	 * @author Daniele 
	 * @param source
	 * @return
	 */
	public <T> String upload(FastFileSource<T> source) throws Exception;
	
	/**
	 * 上传文件资源到指定组
	 * @author Daniele 
	 * @param groupName
	 * @param source
	 * @return
	 */
	public <T> String upload(String groupName, FastFileSource<T> source) throws Exception;
		
	/**
	 * 批量上传文件资源
	 * @author Daniele 
	 * @param sources
	 * @return 上传结果列表
	 */
	public <T> Map<FastFileSource<T>, String> batchUpload(List<FastFileSource<T>> sources) throws Exception;
	
	/**
	 * 批量上传文件资源到指定组
	 * @author Daniele 
	 * @param groupName
	 * @param sources
	 * @return
	 */
	public <T> Map<FastFileSource<T>, String> batchUpload(String groupName, List<FastFileSource<T>> sources) throws Exception;
	
}
