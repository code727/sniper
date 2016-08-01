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
 * Create Date : 2015-11-3
 */

package org.workin.fastdfs.support;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.FileUtils;
import org.workin.commons.util.StringUtils;
import org.workin.fastdfs.cluster.Cluster;
import org.workin.fastdfs.factory.connection.ConnectionFactory;
import org.workin.fastdfs.meta.FastDFSMeta;
import org.workin.fastdfs.task.OriginalResourcesDeleteTask;
import org.workin.support.file.ZoomResource;
import org.workin.support.thread.task.FilesDeleteTask;
import org.workin.web.WebUtils;

/**
 * @description FastDFS模板实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FastDFSTemplet extends FastDFSSupport implements FastDFSOperations {
	
	public FastDFSTemplet() {}
	
	public FastDFSTemplet(Cluster cluster, ConnectionFactory connectionFactory) {
		setConnectionFactory(connectionFactory);
	}

	@Override
	public <T> T execute(FastDFSCallback<T> action) throws Exception {
		return execute(null, action);
	}

	@Override
	public <T> T execute(String groupName, FastDFSCallback<T> action) throws Exception {
		return execute(groupName, action, true);
	}
	
	@Override
	public <T> T execute(String groupName, FastDFSCallback<T> action, boolean autoRelease) throws Exception {
		AssertUtils.assertNotNull(action, "Callback object must not be null");
		
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		try {
			trackerServer = connectionFactory.getTrackerServer();
			storageServer = connectionFactory.getStorageServer(trackerServer, StringUtils.trimToEmpty(groupName));
			return action.doIn(new StorageClient1(trackerServer, storageServer));
		} finally {
			if (autoRelease)
				connectionFactory.release(trackerServer, storageServer);
		}
	}

	@Override
	public <T> String upload(FastDFSMeta<T> meta) throws Exception {
		return upload(null, meta);
	}

	@Override
	public <T> String upload(String groupName, FastDFSMeta<T> meta) throws Exception {
		List<FastDFSMeta<T>> metas = CollectionUtils.newArrayList();
		metas.add(meta);
		return CollectionUtils.get(batchUpload(groupName, metas, false), 0);
	}

	@Override
	public <T> String reupload(FastDFSMeta<T> meta) throws Exception {
		return reupload(null, meta);
	}

	@Override
	public <T> String reupload(String groupName, FastDFSMeta<T> meta) throws Exception {
		List<FastDFSMeta<T>> metas = CollectionUtils.newArrayList();
		metas.add(meta);
		return CollectionUtils.get(batchUpload(groupName, metas, true), 0);
	}

	@Override
	public <T> List<String> batchUpload(List<FastDFSMeta<T>> metas) throws Exception {
		return batchUpload(null, metas);
	}

	@Override
	public <T> List<String> batchUpload(String groupName, List<FastDFSMeta<T>> metas) throws Exception {
		return batchUpload(groupName, metas, false);
	}

	@Override
	public <T> List<String> batchReupload(List<FastDFSMeta<T>> metas) throws Exception {
		return batchReupload(null, metas);
	}

	@Override
	public <T> List<String> batchReupload(String groupName, List<FastDFSMeta<T>> metas) throws Exception {
		return batchUpload(groupName, metas, true);
	}

	@Override
	public <T> ZoomResource zoomUpload(FastDFSMeta<T> meta) throws Exception {
		return zoomUpload(null, meta);
	}

	@Override
	public <T> ZoomResource zoomUpload(String groupName, FastDFSMeta<T> meta) throws Exception {
		List<FastDFSMeta<T>> metas = CollectionUtils.newArrayList();
		metas.add(meta);
		return CollectionUtils.get(batchZoomUpload(groupName, metas, false), 0);
	}

	@Override
	public <T> ZoomResource zoomReupload(FastDFSMeta<T> meta) throws Exception {
		return zoomReupload(null, meta);
	}

	@Override
	public <T> ZoomResource zoomReupload(String groupName, FastDFSMeta<T> meta) throws Exception {
		List<FastDFSMeta<T>> metas = CollectionUtils.newArrayList();
		metas.add(meta);
		return CollectionUtils.get(batchZoomUpload(groupName, metas, true), 0);
	}

	@Override
	public <T> List<ZoomResource> batchZoomUpload(List<FastDFSMeta<T>> metas) throws Exception {
		return batchZoomUpload(null, metas);
	}

	@Override
	public <T> List<ZoomResource> batchZoomUpload(String groupName, List<FastDFSMeta<T>> metas) throws Exception {
		return batchZoomUpload(groupName, metas, false);
	}

	@Override
	public <T> List<ZoomResource> batchZoomReupload(List<FastDFSMeta<T>> metas) throws Exception {
		return batchZoomReupload(null, metas);
	}

	@Override
	public <T> List<ZoomResource> batchZoomReupload(final String groupName, final List<FastDFSMeta<T>> metas) throws Exception {
		return batchZoomUpload(groupName, metas, true);
	}
	
	/**
	 * @description 批量上传资源到指定组，并指定完成后是否删除旧资源
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param metas
	 * @param deleteOriginalResource
	 * @return
	 * @throws Exception
	 */
	private <T> List<String> batchUpload(final String groupName, final List<FastDFSMeta<T>> metas, final boolean deleteOriginalResource) throws Exception {
		return this.execute(groupName, new FastDFSCallback<List<String>>() {

			@Override
			public List<String> doIn(StorageClient1 storageClient) throws Exception {
				List<String> list = doBatchUpload(storageClient, groupName, metas);
				if (deleteOriginalResource) 
					deleteOriginalResources(false, metas);
				return list;
			}
		});
	}
	
	/**
	 * @description 批量上传源以及缩放后的资源到指定组，并指定完成后是否删除旧资源
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param metas
	 * @param deleteOriginalResource
	 * @return
	 * @throws Exception
	 */
	private <T> List<ZoomResource> batchZoomUpload(final String groupName, final List<FastDFSMeta<T>> metas, final boolean deleteOriginalResource) throws Exception {
		return this.execute(groupName, new FastDFSCallback<List<ZoomResource>>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public List<ZoomResource> doIn(StorageClient1 storageClient) throws Exception {
				String targetGroupName = StringUtils.trimToEmpty(groupName);
				Map<String, Object> map = doBatchZoomUpload(storageClient, targetGroupName, metas);
				
				List<File> localTempSources = (List<File>) map.get(LOCAL_TEMPSOURCES_KEY);
				
				if (deleteOriginalResource) 
					deleteOriginalResources(true, metas);
				
				deleteTempFiles(localTempSources);
				return (List<ZoomResource>) map.get(LOCAL_TEMPSOURCES_KEY);
			}
		});
	}
	
	/**
	 * @description FastDFS文件源中指定的旧资源
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param deleteAll
	 * @param metas
	 * @throws Exception 
	 */
	private <T> void deleteOriginalResources(boolean deleteAll, List<FastDFSMeta<T>> metas) throws Exception {
		Runnable task = new OriginalResourcesDeleteTask<T>(deleteAll, metas, this);
		if (this.threadPoolTaskExecutor != null)
			this.threadPoolTaskExecutor.execute(task);
		else
			new Thread(task).start();
	}
	
	/**
	 * @description 清理本地临时文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param files
	 * @throws Exception
	 */
	private void deleteTempFiles(List<File> files) throws Exception {
		Runnable task = new FilesDeleteTask(files);
		if (this.threadPoolTaskExecutor != null)
			this.threadPoolTaskExecutor.execute(task);
		else 
			new Thread(task).start();
	}

	@Override
	public byte[] download(final String path) throws Exception {
		AssertUtils.assertNotBlank(path, "Source path must not be null or blank.");
		
		return this.execute(new FastDFSCallback<byte[]>() {
			@Override
			public byte[] doIn(StorageClient1 storageClient) throws Exception {
				String storagePath = getAccessor().getStoragePath(getCluster(), path);
				return storageClient.download_file1(storagePath);
			}
		});
	}
	
	@Override
	public String download(final String path, final String fileName) throws Exception {
		AssertUtils.assertNotBlank(path, "Source path must not be null or blank.");
		AssertUtils.assertNotBlank(fileName, "Local file name must not be null or blank.");
		
		return this.execute(new FastDFSCallback<String>() {
			@Override
			public String doIn(StorageClient1 storageClient) throws Exception {
				String storagePath = getAccessor().getStoragePath(getCluster(), path);
				String destName = fileName;
				File dest = new File(destName);
				if (dest.isDirectory())
					// 如果下载目标是一个目录，则下载到此目录下，并且文件名称与被下载资源的名称一致
					destName = new StringBuffer(destName).append(File.separator)
						.append(FileUtils.getName(storagePath)).toString();
				
				storageClient.download_file1(storagePath, destName);
				// 返回实际的本地目标文件名
				return StringUtils.replaceAll(destName, "\\", "/");
			}
		});
	}

	@Override
	public void download(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.download(path, null, request, response);
	}

	@Override
	public void download(String path, String attachmentName,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssertUtils.assertNotBlank(path, "Source path must not be null or blank.");
		
		// 如果传入的附件名为空，则从路径中获取
		if (StringUtils.isBlank(attachmentName))
			attachmentName = FileUtils.getName(path);
		
		WebUtils.download(this.download(path), attachmentName, request, response);
	}

	@Override
	public int delete(final String path) throws Exception {
		return this.execute(new FastDFSCallback<Integer>() {

			@Override
			public Integer doIn(StorageClient1 storageClient) throws Exception {
				String storagePath = getAccessor().getStoragePath(getCluster(), path);
				return storageClient.delete_file1(storagePath);
			}
		}); 
	}

	@Override
	public void bathDelete(final Set<String> pathSet) throws Exception {
		this.execute(new FastDFSCallback<Object>() {

			@Override
			public Object doIn(StorageClient1 storageClient) throws Exception {
				for (String path : pathSet) {
					String storagePath = getAccessor().getStoragePath(getCluster(), path);
					storageClient.delete_file1(storagePath);
				}
				return null;
			}
		});
	}
}
