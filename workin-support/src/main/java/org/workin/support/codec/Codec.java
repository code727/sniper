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
 * Create Date : 2016-1-8
 */

package org.workin.support.codec;

/**
 * @description 编解码器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Codec {
	
	/**
	 * @description 将明文按默认字符集进行编码处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext
	 * @return
	 */
	public String encode(String plaintext);
	
	/**
	 * @description 将明文按指定字符集格式进行编码处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext
	 * @param charsetName
	 * @return
	 */
	public String encode(String plaintext, String charsetName);
	
	/**
	 * @description 对字节数组进行编码处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bytes
	 * @return
	 */
	public String encode(byte[] bytes);
	
	/**
	 * @description 将被编码的文本内容解码成默认字符集的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param encoded
	 * @return
	 */
	public String decode(String encoded);
	
	/**
	 * @description 将被编码的文本内容解码成指定字符集的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param encoded
	 * @param charsetName
	 * @return
	 */
	public String decode(String encoded, String charsetName);
	
	/**
	 * @description 将被编码的文本内容解码成字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param encoded
	 * @return
	 */
	public byte[] decodeToBytes(String encoded);

}
