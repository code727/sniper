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

package org.workin.fastdfs.support;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.FileUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;
import org.workin.commons.util.SystemUtils;
import org.workin.fastdfs.accessor.Accessor;
import org.workin.fastdfs.accessor.DefaultAccessor;
import org.workin.fastdfs.cluster.Cluster;
import org.workin.fastdfs.factory.connection.ConnectionFactory;
import org.workin.fastdfs.meta.FastDFSMeta;
import org.workin.image.zoom.ImageZoomHandler;
import org.workin.spring.beans.CheckableInitializingBean;
import org.workin.support.file.ZoomResource;

/**
 * @description FastDFS支持服务类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class FastDFSSupport extends CheckableInitializingBean {
	
	/** 缩放资源标识键 */
	protected final static String ZOOM_RESOURCES_KEY = "zoomResources";
	
	/** 本地临时文件资源标识键 */
	protected final static String LOCAL_TEMPSOURCES_KEY = "localTempSources";
	
	/** FastDFS集群族对象 */
	private Cluster cluster;
	
	/** 连接工厂 */
	protected ConnectionFactory connectionFactory;
	
	/** 图像缩放处理器 */
	protected ImageZoomHandler imageZoomHandler;
	
	/** FastDFS访问器 */
	protected Accessor accessor;
	
	/** 线程池任务执行器 */
	protected ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public ImageZoomHandler getImageZoomHandler() {
		return imageZoomHandler;
	}

	public void setImageZoomHandler(ImageZoomHandler imageZoomHandler) {
		this.imageZoomHandler = imageZoomHandler;
	}
	
	public Accessor getAccessor() {
		return accessor;
	}

	public void setAccessor(Accessor accessor) {
		this.accessor = accessor;
	}
	
	public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		return threadPoolTaskExecutor;
	}

	public void setThreadPoolTaskExecutor(
			ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}

	@Override
	protected void checkProperties() {
		if (this.cluster == null)
			throw new IllegalArgumentException("FastDFSTemplet property 'cluster' must not be null.");
		
		if (this.connectionFactory == null)
			throw new IllegalArgumentException("FastDFSTemplet property 'connectionFactory' must not be null.");
	}
	
	@Override
	protected void init() throws Exception {
		if (this.accessor == null)
			this.accessor = new DefaultAccessor();
	}
	
	/**
	 * @description 根据文件源创建本地临时文件对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param meta
	 * @return
	 */
	protected <T> File createTempFile(FastDFSMeta<T> meta) {
		String tempPathName = new StringBuilder(SystemUtils.getTempDir())
			.append(File.separator).append("temp_").append(new Date().getTime())
			.append("_").append(meta.getName()).toString();
		return new File(tempPathName);
	}
	
	/**
	 * @description 执行批量上传资源到指定组的操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param storageClient
	 * @param groupName
	 * @param metas
	 * @return
	 * @throws Exception
	 */
	protected <T> List<String> doBatchUpload(StorageClient1 storageClient, String groupName, List<FastDFSMeta<T>> metas) throws Exception { 
		List<String> list = CollectionUtils.newArrayList();
		String targetGroupName = StringUtils.trimToEmpty(groupName);
		for (FastDFSMeta<T> meta : metas) {
			NameValuePair[] nameValuePaires = CollectionUtils.isNotEmpty(meta.getNameValuePaires()) ? 
					CollectionUtils.toArray(meta.getNameValuePaires()) : null;
			
			/* 上传源和缩放文件后设置返回结果 */
			String result = accessor.getAccessableURL(getCluster(), 
					storageClient.upload_file1(targetGroupName, meta.getBytes(), meta.getExtName(), nameValuePaires));
			list.add(result);
		}
		return list;
	}
	
	/**
	 * @description 执行批量上传源以及缩放后的资源到指定组的操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param storageClient
	 * @param groupName
	 * @param metas
	 * @return
	 * @throws Exception
	 */
	protected <T> Map<String, Object> doBatchZoomUpload(StorageClient1 storageClient, String groupName, List<FastDFSMeta<T>> metas) throws Exception { 
		AssertUtils.assertNotNull(imageZoomHandler, "ImageZoomHandler must not be null.");
		
		List<ZoomResource> zoomResources = CollectionUtils.newArrayList();
		String targetGroupName = StringUtils.trimToEmpty(groupName);
		List<File> localTempSources = CollectionUtils.newArrayList();
		
		for (FastDFSMeta<T> meta : metas) {
			ZoomResource zoomResource = new ZoomResource();
			NameValuePair[] nameValuePaires = CollectionUtils.isNotEmpty(meta.getNameValuePaires()) ? 
					CollectionUtils.toArray(meta.getNameValuePaires()) : null;
			
			File localTempSource = createTempFile(meta);
			// 将源文件缩放后生成一个新的文件资源在本地的临时目录中
			imageZoomHandler.handle(meta.getInputStream(), meta.getExtName(), localTempSource);
			
			/* 上传源和缩放文件后设置返回结果 */
			String srcURL = accessor.getAccessableURL(getCluster(), 
					storageClient.upload_file1(targetGroupName, meta.getBytes(), meta.getExtName(), nameValuePaires));
			String zoomURL = accessor.getAccessableURL(getCluster(), 
					storageClient.upload_file1(targetGroupName, localTempSource.getCanonicalPath(), FileUtils.getExtensionName(localTempSource), nameValuePaires));
			zoomResource.setSrcURL(srcURL);
			zoomResource.setZoomURL(zoomURL);
		
			zoomResources.add(zoomResource);
			localTempSources.add(localTempSource);
		}
		
		/* 返回执行结果:
		 * 1)zoomResources:缩放资源结果对象 
		 * 2)tempImageSources:进行缩放操作时所创建的本地临时图片资源，对于上一层调用方来说，可根据此值来做本地临时文件的清理工作
		 */
		Map<String, Object> map = MapUtils.newHashMap();
		map.put(ZOOM_RESOURCES_KEY, zoomResources);
		map.put(LOCAL_TEMPSOURCES_KEY, localTempSources);
		return map;
	}
	
}
