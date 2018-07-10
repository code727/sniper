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

import java.util.List;

import org.csource.common.NameValuePair;
import org.sniper.support.file.source.FileSource;

/**
 * FastDFS文件资源接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface FastFileSource<T> extends FileSource<T> {
	
	/**
	 * 设置原有的资源标识
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param originalId
	 */
	public void setOriginalId(String originalId);
	
	/**
	 * 获取原有的资源标识
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getOriginalId();
	
	/**
	 * 设置原有的缩放资源标识
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param originalZoomId
	 */
	public void setOriginalZoomId(String originalZoomId);
	
	/**
	 * 获取原有的缩放资源标识
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getOriginalZoomId();
		
	/**
	 * 添加目标文件元数据名值对
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param nameValuePair
	 */
	public void addNameValuePair(NameValuePair nameValuePair);
	
	/**
	 * 批量添加目标文件元数据名值对
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param NameValuePairs
	 */
	public void addNameValuePaires(NameValuePair[] nameValuePairs);
	
	/**
	 * 批量添加目标文件元数据名值对
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param nameValuePairs
	 */
	public void addNameValuePaires(List<NameValuePair> nameValuePairs);
	
	/**
	 * 获取所有的元数据名值对
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<NameValuePair> getNameValuePaires();
	
	/**
	 * 获取指定索引位上的元数据名值对
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @return
	 */
	public NameValuePair getNameValuePair(int index);
	
	/**
	 * 获取第一个元数据名值对
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public NameValuePair getFirstNameValuePair();
	
	/**
	 * 获取最后一个元数据名值对
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public NameValuePair getLastNameValuePair();
	
	/**
	 * 获取匹配名称对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public String getValue(String name);
	
	/**
	 * 按忽略大小写的方式获取匹配名称对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public String getValueIgnoreCase(String name);

}
