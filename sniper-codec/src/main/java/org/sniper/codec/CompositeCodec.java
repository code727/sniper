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

import java.util.List;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CodecUtils;
import org.sniper.commons.util.CollectionUtils;

/**
 * 混合型编解码器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CompositeCodec extends AbstractCodec {
	
	/** 编解码器成员列表 */
	private List<Codec> members;
	
	public CompositeCodec(List<Codec> members) {
		AssertUtils.assertNotEmpty(members, "Composite codec member must not be null or empty");
		
		this.members = members;
	}
	
	public List<Codec> getmembers() {
		return members;
	}

	@Override
	public String encode(byte[] bytes) {
		return encodeByMemberOrder(bytes);
	}
	
	@Override
	public String encode(String text, String charsetName) {
		return encodeByMemberOrder(text, charsetName, 0);
	}

	@Override
	public String decode(String encodedText, String charsetName) {
		return decodeByMemberInvertedOrder(encodedText, charsetName);
	}
	
	@Override
	public byte[] decodeToBytes(String encodedText) {
		return decodeToBytesByMemberInvertedOrder(encodedText);
	}
	
	/**
	 * 按成员加入的顺序对文本字节数组进行编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bytes
	 * @return
	 */
	private String encodeByMemberOrder(byte[] bytes) {
		/* 第一个编解码器对字节数组编码后，再将结果从列表的第2个成员开始依次再进行编码 */
		String encodedText = CollectionUtils.getFirst(this.members).encode(bytes);
		return encodeByMemberOrder(encodedText, null, 1);
	}
	
	/**
	 * 按成员加入的顺序和指定的字符集对文本进行编码，起始为列表中的第start+1个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @param charsetName
	 * @param start
	 * @return
	 */
	private String encodeByMemberOrder(String text, String charsetName, int start) {
		for (int i = start; i < this.members.size(); i++) {
			text = this.members.get(i).encode(text, charsetName);
		}
		
		return text;
	}
	
	/**
	 * 按成员加入的逆序和指定的字符集对文本进行解码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param encodedText
	 * @param charsetName
	 * @return
	 */
	private String decodeByMemberInvertedOrder(String encodedText, String charsetName) {
		int size = CollectionUtils.size(this.members);
		for (int i = size - 1; i > -1; i--) {
			encodedText = this.members.get(i).decode(encodedText, charsetName);
		}
		
		return encodedText;
	}
	
	/**
	 * 按成员加入的逆序将文本解码为字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param encodedText
	 * @return
	 */
	private byte[] decodeToBytesByMemberInvertedOrder(String encodedText) {
		/* 最后一个编解码器对文本解码后，再对字节数组结果从列表的倒数第2个成员开始逆序进行解码 */
		byte[] textBytes = CollectionUtils.getLast(this.members).decodeToBytes(encodedText);
		int size = CollectionUtils.size(this.members);
		for (int i = size - 2; i > -1; i--) {
			textBytes = this.members.get(i).decodeToBytes(CodecUtils.bytesToString(textBytes));
		}
		
		return textBytes;
	}
	
}
