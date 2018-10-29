/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-11-9
 */

package org.sniper.generator.application;

import java.util.List;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.SecurityUtils;
import org.sniper.generator.AbstractParameterizeGenerator;

/**
 * 短链接生成器</p>
 * 算法思路：</p>
 * 1.将给定的字符串（长链接）先转换为32位的一个MD5字符串；</p>
 * 2.将上面的MD5字符串分为4段处理，每段的长度为8，4段分别为M、N、O、P；</p>
 * 3.可以将M字符串当作一个16进制格式的数字来处理，将其转换为一个Long类型。比如转换为L；</p>
 * 4.此时L的二进制有效长度为32位，需要将前面两位去掉，留下30位，可以进行位操作(&0x3fffffff)得到想要的结果；</p>
 * 5.此时L的二进制有效长度为30位，分为6段处理，每段的长度为5；</p>
 * 6.依次取出L的每一段（5位），进行位操作(&0x0000003D)得到一个<=61的数字，来当做index。
 *   再根据index去预定义的字符表里面去取一个字符， 最后能取出6个字符，此时就能用这6个字符拼接成一个字符串，作为短链接；</p>
 * 7.根据2重复3、4、5、6，总共能得到6个第六步生成的字符串，取其中任意一个字符串当作短链接都是可以的。
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ShortLinkGenerator extends AbstractParameterizeGenerator<Object, String> {
	
	protected final char[] chars;
	
	public ShortLinkGenerator() {
		this(false);
	}
	
	public ShortLinkGenerator(boolean allNumber) {
		if (allNumber) {
			chars = new char[62];
			int length = chars.length;
			for (int i = 0; i < length; i++) {
				chars[i] = String.valueOf(i % 9).charAt(0);
			}
		} else {
			chars = new char[] { 
					'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
					'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
					'8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 
					'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		}
	}
	
	@Override
	public String generateByParameter(Object parameter) {
		String md5 = SecurityUtils.md5(ObjectUtils.toSafeString(parameter));
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 4; i++) {  
			/* 把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算，目的是去掉最前面的2位，只留下30位 */
			String sub = md5.substring(i << 3, (i << 3) + 8);  
			long sub16 = Long.parseLong(sub, 16) & 0X3FFFFFFF;
			
			/* 将剩下的30位分6段处理，每段5位 */
			for (int j = 0; j < 6; j++) {  
				builder.append(chars[(int) (sub16 & 0x0000003D)]);
				// 右移5位
				sub16 = sub16 >> 5; 
			}  
		}  
		return builder.toString();
	}

	/**
	 * 由于短链接生成器是针对参数生成的，因此针对于同一个参数批量生成的结果都是一样的，将生成count个相同的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @param count
	 * @return
	 */
	@Override
	public List<String> batchGenerateByParameter(Object parameter, int count) {
		checkBatchCount(count);
		
		List<String> results = CollectionUtils.newArrayList(count);
		String element = this.generateByParameter(parameter);
		for (int i = 0; i < count; i++) {
			results.add(element);
		}
		
		return results;
	}
		
}
