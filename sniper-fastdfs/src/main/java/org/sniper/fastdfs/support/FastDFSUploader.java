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

package org.sniper.fastdfs.support;

import java.util.List;

import org.sniper.fastdfs.source.FastFileSource;
import org.sniper.support.file.ZoomResource;

/**
 * FastDFS文件上传器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface FastDFSUploader {
	
	/**
	 * 上传文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param meta
	 * @return
	 */
	public <T> String upload(FastFileSource<T> meta) throws Exception;
	
	/**
	 * 上传文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param meta
	 * @return
	 */
	public <T> String upload(String groupName, FastFileSource<T> meta) throws Exception;
	
	/**
	 * 重传文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param meta
	 * @return
	 */
	public <T> String reupload(FastFileSource<T> meta) throws Exception;
	
	/**
	 * 重传文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param meta
	 * @return
	 */
	public <T> String reupload(String groupName, FastFileSource<T> meta) throws Exception;
	
	/**
	 * 批量上传文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param metas
	 * @return
	 */
	public <T> List<String> batchUpload(List<FastFileSource<T>> metas) throws Exception;
	
	/**
	 * 批量上传文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param metas
	 * @return
	 */
	public <T> List<String> batchUpload(String groupName, List<FastFileSource<T>> metas) throws Exception;
	
	/**
	 * 批量重传
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public <T> List<String> batchReupload(List<FastFileSource<T>> metas) throws Exception;
	
	/**
	 * 批量重传到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param metas
	 * @return
	 */
	public <T> List<String> batchReupload(String groupName, List<FastFileSource<T>> metas) throws Exception;
	
	/**
	 * 上传源以及缩放后的文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param meta
	 * @return
	 */
	public <T> ZoomResource zoomUpload(FastFileSource<T> meta) throws Exception;
	
	/**
	 * 上传源以及缩放后的文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param meta
	 * @return
	 */
	public <T> ZoomResource zoomUpload(String groupName, FastFileSource<T> meta) throws Exception;
	
	/**
	 * 重传源以及缩放后的文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param meta
	 * @return
	 */
	public <T> ZoomResource zoomReupload(FastFileSource<T> meta) throws Exception;
	
	/**
	 * 重传源以及缩放后的文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param meta
	 * @return
	 */
	public <T> ZoomResource zoomReupload(String groupName, FastFileSource<T> meta) throws Exception;
	
	/**
	 * 批量上传源以及缩放后的文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param metas
	 * @return
	 */
	public <T> List<ZoomResource> batchZoomUpload(List<FastFileSource<T>> metas) throws Exception;
	
	/**
	 * 批量上传源以及缩放后的文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param metas
	 * @return
	 */
	public <T> List<ZoomResource> batchZoomUpload(String groupName, List<FastFileSource<T>> metas) throws Exception;
	
	/**
	 * 批量重传源以及缩放后的文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param metas
	 * @return
	 */
	public <T> List<ZoomResource> batchZoomReupload(List<FastFileSource<T>> metas) throws Exception;
	
	/**
	 * 批量重传源以及缩放后的文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param metas
	 * @return
	 */
	public <T> List<ZoomResource> batchZoomReupload(String groupName, List<FastFileSource<T>> metas) throws Exception;
	
}
