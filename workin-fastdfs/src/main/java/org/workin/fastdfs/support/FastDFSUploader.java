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

package org.workin.fastdfs.support;

import java.util.List;

import org.workin.fastdfs.meta.FastDFSMeta;
import org.workin.support.file.ZoomResource;

/**
 * @description FastDFS文件上传器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface FastDFSUploader {
	
	/**
	 * @description 上传文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param meta
	 * @return
	 */
	public <T> String upload(FastDFSMeta<T> meta) throws Exception;
	
	/**
	 * @description 上传文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param meta
	 * @return
	 */
	public <T> String upload(String groupName, FastDFSMeta<T> meta) throws Exception;
	
	/**
	 * @description 重传文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param meta
	 * @return
	 */
	public <T> String reupload(FastDFSMeta<T> meta) throws Exception;
	
	/**
	 * @description 重传文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param meta
	 * @return
	 */
	public <T> String reupload(String groupName, FastDFSMeta<T> meta) throws Exception;
	
	/**
	 * @description 批量上传文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param metas
	 * @return
	 */
	public <T> List<String> batchUpload(List<FastDFSMeta<T>> metas) throws Exception;
	
	/**
	 * @description 批量上传文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param metas
	 * @return
	 */
	public <T> List<String> batchUpload(String groupName, List<FastDFSMeta<T>> metas) throws Exception;
	
	/**
	 * @description 批量重传
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public <T> List<String> batchReupload(List<FastDFSMeta<T>> metas) throws Exception;
	
	/**
	 * @description 批量重传到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param metas
	 * @return
	 */
	public <T> List<String> batchReupload(String groupName, List<FastDFSMeta<T>> metas) throws Exception;
	
	/**
	 * @description 上传源以及缩放后的文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param meta
	 * @return
	 */
	public <T> ZoomResource zoomUpload(FastDFSMeta<T> meta) throws Exception;
	
	/**
	 * @description 上传源以及缩放后的文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param meta
	 * @return
	 */
	public <T> ZoomResource zoomUpload(String groupName, FastDFSMeta<T> meta) throws Exception;
	
	/**
	 * @description 重传源以及缩放后的文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param meta
	 * @return
	 */
	public <T> ZoomResource zoomReupload(FastDFSMeta<T> meta) throws Exception;
	
	/**
	 * @description 重传源以及缩放后的文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param meta
	 * @return
	 */
	public <T> ZoomResource zoomReupload(String groupName, FastDFSMeta<T> meta) throws Exception;
	
	/**
	 * @description 批量上传源以及缩放后的文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param metas
	 * @return
	 */
	public <T> List<ZoomResource> batchZoomUpload(List<FastDFSMeta<T>> metas) throws Exception;
	
	/**
	 * @description 批量上传源以及缩放后的文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param metas
	 * @return
	 */
	public <T> List<ZoomResource> batchZoomUpload(String groupName, List<FastDFSMeta<T>> metas) throws Exception;
	
	/**
	 * @description 批量重传源以及缩放后的文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param metas
	 * @return
	 */
	public <T> List<ZoomResource> batchZoomReupload(List<FastDFSMeta<T>> metas) throws Exception;
	
	/**
	 * @description 批量重传源以及缩放后的文件到指定组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param metas
	 * @return
	 */
	public <T> List<ZoomResource> batchZoomReupload(String groupName, List<FastDFSMeta<T>> metas) throws Exception;
	
}
