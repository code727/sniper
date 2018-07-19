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

package org.sniper.resource.fastdfs.file;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.csource.common.NameValuePair;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.resource.file.FileSource;

/**
 * FastDFS文件资源默认类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultFastFileSource<T> implements FastFileSource<T> {
	
	/** 文件源 */
	private final FileSource<T> source;
	
	/** 原有的资源标识 */
	private String originalId;
	
	/** 目标文件元数据名值对列表 */
	private final List<NameValuePair> nameValuePaires;
	
	public DefaultFastFileSource(FileSource<T> source) throws IOException {
		this(source, null);
	}
	
	public DefaultFastFileSource(FileSource<T> source, List<NameValuePair> nameValuePaires) throws IOException {
		this.source = source;
		this.nameValuePaires = CollectionUtils.newArrayList(nameValuePaires);
	}
		
	@Override
	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	@Override
	public String getOriginalId() {
		return this.originalId;
	}
	
	@Override
	public void addNameValuePair(NameValuePair nameValuePair) {	
		AssertUtils.assertNotNull(nameValuePair, 
				MessageFormat.format("Fast file '{0}' name value pair must not be null", getFile()));
		this.nameValuePaires.add(nameValuePair);
	}
	
	@Override
	public void addNameValuePaires(NameValuePair[] nameValuePaires) {
		AssertUtils.assertNotEmpty(nameValuePaires,
				MessageFormat.format("Fast file '{0}' name value pair array must not be empty", getFile()));
		
		this.addNameValuePaires(Arrays.asList(nameValuePaires));
	}
	
	@Override
	public void addNameValuePaires(List<NameValuePair> nameValuePairs) {
		AssertUtils.assertNotEmpty(nameValuePaires, 
				MessageFormat.format("Fast file '{0}' name value pair list must not be empty", getFile()));
						
		this.nameValuePaires.addAll(nameValuePairs);
	}
	
	@Override
	public List<NameValuePair> getNameValuePaires() {
		return this.nameValuePaires;
	}

	@Override
	public NameValuePair getNameValuePair(int index) {
		return CollectionUtils.get(this.nameValuePaires, index);
	}

	@Override
	public NameValuePair getFirstNameValuePair() {
		return CollectionUtils.getFirst(this.nameValuePaires);
	}

	@Override
	public NameValuePair getLastNameValuePair() {
		return CollectionUtils.getLast(this.nameValuePaires);
	}

	@Override
	public String getValue(String name) {
		return getValue(name, false);
	}

	@Override
	public String getValueIgnoreCase(String name) {
		return getValue(name, true);
	}
	
	@Override
	public T getFile() {
		return this.source.getFile();
	}

	@Override
	public String getName() {
		return this.source.getName();
	}

	@Override
	public String getMainName() {
		return this.source.getMainName();
	}

	@Override
	public String getExtName() {
		return this.source.getExtName();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.source.getInputStream();
	}
	
	@Override
	public byte[] getBytes() throws IOException {
		return this.source.getBytes();
	}

	@Override
	public void close() throws IOException {
		this.source.close();
	}
	
	/**
	 * 选择是否按忽略大小写的方式获取匹配名称对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param ignoreCase
	 * @return
	 */
	protected String getValue(String name, boolean ignoreCase) {
		if (ignoreCase) {
			for (NameValuePair pair : nameValuePaires) {
				if (StringUtils.equalsIgnoreCase(name, pair.getName())) {
					return pair.getValue();
				}
			}
		} else {
			for (NameValuePair pair : nameValuePaires) {
				if (StringUtils.equals(name, pair.getName())) {
					return pair.getValue();
				}
			}
		}
		return null;
	}

}
