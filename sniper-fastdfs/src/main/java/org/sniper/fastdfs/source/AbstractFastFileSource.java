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

package org.sniper.fastdfs.source;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.csource.common.NameValuePair;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.support.file.source.AbstaractFileSource;

/**
 * FastDFS文件资源抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractFastFileSource<T> extends AbstaractFileSource<T> implements FastFileSource<T> {
	
	/** 原有的资源标识 */
	private String originalId;
	
	/** 原有的缩放资源标识 */
	private String originalZoomId;
		
	/** 目标文件元数据名值对列表 */
	private List<NameValuePair> nameValuePaires;
	
	public AbstractFastFileSource(T source) throws IOException {
		super(source);
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
	public void setOriginalZoomId(String originalZoomId) {
		this.originalZoomId = originalZoomId;
	}

	@Override
	public String getOriginalZoomId() {
		return this.originalZoomId;
	}

	@Override
	public void addNameValuePair(NameValuePair nameValuePair) {
		AssertUtils.assertNotNull(nameValuePair, "NameValuePair must not be null.");
		
		if (this.nameValuePaires == null) 
			this.nameValuePaires = CollectionUtils.newArrayList();
		
		this.nameValuePaires.add(nameValuePair);
	}
	
	@Override
	public void addNameValuePaires(NameValuePair[] nameValuePaires) {
		AssertUtils.assertTrue(ArrayUtils.isNotEmpty(nameValuePaires), 
				"NameValuePair array must not be null or empty.");
		
		addNameValuePaires(Arrays.asList(nameValuePaires));
	}
	
	@Override
	public void addNameValuePaires(List<NameValuePair> nameValuePairs) {
		AssertUtils.assertTrue(CollectionUtils.isNotEmpty(nameValuePaires), 
				"NameValuePair list must not be null or empty.");
		
		if (this.nameValuePaires == null) 
			this.nameValuePaires = CollectionUtils.newArrayList();
				
		this.nameValuePaires.addAll(nameValuePairs);
	}
	
	@Override
	public List<NameValuePair> getNameValuePaires() {
		return this.nameValuePaires;
	}

	@Override
	public NameValuePair getNameValuePair(int index) {
		return this.nameValuePaires.get(index);
	}

	@Override
	public NameValuePair getFirstNameValuePair() {
		return getNameValuePair(0);
	}

	@Override
	public NameValuePair getLastNameValuePair() {
		return getNameValuePair(CollectionUtils.size(this.nameValuePaires) - 1);
	}

	@Override
	public String getValue(String name) {
		if (ArrayUtils.length(nameValuePaires) == 0)
			return null;
		
		for (NameValuePair pair : nameValuePaires) {
			if (StringUtils.equals(name, pair.getName()))
				return pair.getValue();
		}
		
		return null;
	}

	@Override
	public String getValueIgnoreCase(String name) {
		if (ArrayUtils.length(nameValuePaires) == 0)
			return null;
		
		for (NameValuePair pair : nameValuePaires) {
			if (StringUtils.equalsIgnoreCase(name, pair.getName()))
				return pair.getValue();
		}
		
		return null;
	}

}
