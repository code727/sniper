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

package org.sniper.resource.fastdfs.support;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.FileUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.resource.fastdfs.cluster.Cluster;
import org.sniper.resource.fastdfs.factory.connection.ConnectionFactory;
import org.sniper.resource.fastdfs.file.FastFileSource;
import org.sniper.web.WebUtils;

/**
 * FastDFS模板实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FastTemplate extends FastSupport implements FastOperations {
	
	public FastTemplate() {}
	
	public FastTemplate(Cluster cluster, ConnectionFactory connectionFactory) {
		this.cluster = cluster;
		this.connectionFactory = connectionFactory;
	}

	@Override
	public <T> T execute(FastCallback<T> action) throws Exception {
		return execute(null, action);
	}

	@Override
	public <T> T execute(String groupName, FastCallback<T> action) throws Exception {
		return execute(groupName, action, true);
	}
	
	@Override
	public <T> T execute(String groupName, FastCallback<T> action, boolean autoRelease) throws Exception {
		AssertUtils.assertNotNull(action, "Callback object must not be null");
		
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		try {
			trackerServer = connectionFactory.getTrackerServer();
			storageServer = connectionFactory.getStorageServer(trackerServer, StringUtils.trimToEmpty(groupName));
			return action.doIn(new StorageClient1(trackerServer, storageServer));
		} finally {
			if (autoRelease) {
				connectionFactory.release(trackerServer, storageServer);
			}
		}
	}

	@Override
	public <T> String upload(FastFileSource<T> source) throws Exception {
		return upload(null, source);
	}

	@Override
	public <T> String upload(final String groupName, final FastFileSource<T> source) throws Exception {
		AssertUtils.assertNotNull(source, "Uploaded file source must not be null");
		return this.execute(groupName, new FastCallback<String>() {

			@Override
			public String doIn(StorageClient1 storageClient) throws Exception {
				String targetGroupName = StringUtils.trimToEmpty(groupName);
				String path = storageClient.upload_file1(targetGroupName, source.getBytes(), source.getExtName(),
						CollectionUtils.toArray(source.getNameValuePaires(), NameValuePair.class));
				return accessor.getAccessableURL(cluster, path);
			}
		});
	}

	@Override
	public <T> Map<FastFileSource<T>, String> batchUpload(List<FastFileSource<T>> sources) throws Exception {
		return batchUpload(null, sources);
	}

	@Override
	public <T> Map<FastFileSource<T>, String> batchUpload(final String groupName, final List<FastFileSource<T>> sources) throws Exception {
		AssertUtils.assertNotEmpty(sources, "Uploaded file sources must not be empty");
		return this.execute(groupName, new FastCallback<Map<FastFileSource<T>, String>>() {

			@Override
			public Map<FastFileSource<T>, String> doIn(StorageClient1 storageClient) throws Exception {
				String targetGroupName = StringUtils.trimToEmpty(groupName);
				Map<FastFileSource<T>, String> result = MapUtils.newLinkedHashMap();
				String path;
				for (FastFileSource<T> source : sources) {
					/* 文件资源列表内的成员可能有空的，为空的文件资源不进行上传操作，过滤掉，
					 * 因此最终结果的个数可能会与文件资源列表的个数不一致 */
					if (source != null) {
						path = storageClient.upload_file1(targetGroupName, source.getBytes(), source.getExtName(),
								CollectionUtils.toArray(source.getNameValuePaires(), NameValuePair.class));
						result.put(source, accessor.getAccessableURL(cluster, path));
					}
				}
				return result;
			}
		});
	}
	@Override
	public byte[] download(final String path) throws Exception {
		AssertUtils.assertNotBlank(path, "Source path must not be blank");
		
		return this.execute(new FastCallback<byte[]>() {
			@Override
			public byte[] doIn(StorageClient1 storageClient) throws Exception {
				String storagePath = accessor.getStoragePath(cluster, path);
				return storageClient.download_file1(storagePath);
			}
		});
	}
	
	@Override
	public String download(final String path, final String fileName) throws Exception {
		AssertUtils.assertNotBlank(path, "Source path must not be blank");
		AssertUtils.assertNotBlank(fileName, "Local file name must not be blank");
		
		return this.execute(new FastCallback<String>() {
			@Override
			public String doIn(StorageClient1 storageClient) throws Exception {
				String storagePath = accessor.getStoragePath(cluster, path);
				
				String destFileName = fileName;
				File dest = new File(destFileName);
				if (dest.isDirectory()) {
					// 如果下载目标是一个目录，则下载到此目录下，并且文件名称与被下载资源的名称一致
					destFileName = new StringBuffer(destFileName).append(File.separator).append(FileUtils.getName(storagePath)).toString();
				}
				
				storageClient.download_file1(storagePath, destFileName);
				// 返回实际的本地目标文件名
				return StringUtils.replaceAll(destFileName, "\\", "/");
			}
		});
	}

	@Override
	public void download(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.download(path, null, request, response);
	}

	@Override
	public void download(String path, String attachmentName,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		AssertUtils.assertNotBlank(path, "Source path must not be blank");
		
		// 如果传入的附件名为空，则从路径中获取
		if (StringUtils.isBlank(attachmentName))
			attachmentName = FileUtils.getName(path);
		
		WebUtils.download(this.download(path), attachmentName, request, response);
	}

	@Override
	public int delete(final String path) throws Exception {
		AssertUtils.assertNotBlank(path, "Source path must not be blank");
		return this.execute(new FastCallback<Integer>() {

			@Override
			public Integer doIn(StorageClient1 storageClient) throws Exception {
				String storagePath = getAccessor().getStoragePath(getCluster(), path);
				return storageClient.delete_file1(storagePath);
			}
		}); 
	}

	@Override
	public Map<String, Integer> bathDelete(final Set<String> paths) throws Exception {
		return this.execute(new FastCallback<Map<String, Integer>>() {

			@Override
			public Map<String, Integer> doIn(StorageClient1 storageClient) throws Exception {
				Map<String, Integer> result = MapUtils.newLinkedHashMap();
				String storagePath;
				for (String path : paths) {
					/* 文件存储路径可能为空，为空时不进行删除操作，过滤掉，
					 * 因此最终结果的个数可能会与资源路径列表的个数不一致 */
					if (StringUtils.isNotBlank(path)) {
						storagePath = accessor.getStoragePath(cluster, path);
						result.put(path, storageClient.delete_file1(storagePath));
					}
				}
				return null;
			}
		});
	}
}
