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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FastDFS文件下载器
 * @author  Daniele
 * @version 1.0
 */
public interface FastDownloader {
	
	/**
	 * 从指定的Storage路径下载
	 * @author Daniele 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public byte[] download(String path) throws Exception;
	
	/**
	 * 从指定的Storage路径下载到指定名称的本地文件中
	 * @author Daniele 
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String download(String path, String fileName) throws Exception;
	
	/**
	 * 从指定的Storage路径下载资源，并写入HttpServletResponse对象
	 * @author Daniele 
	 * @param path
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void download(String path, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 从指定的Storage路径下载资源，并写入HttpServletResponse对象
	 * @author Daniele 
	 * @param path
	 * @param attachmentName
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void download(String path, String attachmentName, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
