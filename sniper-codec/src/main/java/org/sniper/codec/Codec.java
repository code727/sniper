/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-5-6
 */

package org.sniper.codec;

/**
 * 编解码处理器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Codec {
	
	/**
	 * 对字节数组进行编码处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bytes
	 * @return
	 */
	public String encode(byte[] bytes);
	
	/**
	 * 将原文本按默认字符集进行编码处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @return
	 */
	public String encode(String text);
	
	/**
	 * 将原文本按指定字符集格式进行编码处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @param charsetName
	 * @return
	 */
	public String encode(String text, String charsetName);
	
	/**
	 * 将被编码的文本内容按默认字符集进行解码处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param encodedText
	 * @return
	 */
	public String decode(String encodedText);
	
	/**
	 * 将被编码的文本内容按指定字符集进行解码处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param encodedText
	 * @param charsetName
	 * @return
	 */
	public String decode(String encodedText, String charsetName);
	
	/**
	 * 将被编码的文本内容解码成字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param encodedText
	 * @return
	 */
	public byte[] decodeToBytes(String encodedText);
	
}
