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
 * Create Date : 2015-11-19
 */

package org.workin.support.generator.adapter;

import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.StringUtils;
import org.workin.support.generator.MinLengthIDGenerator;

/**
 * 具有最小长度限制的ID生成适配器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class MinLengthIDGeneratorAdapter extends
		IDGeneratorAdapter implements MinLengthIDGenerator {

	private int minLength = DEFAULT_MIN_LENGTH;
	
	private char supply = '0';
	
	private int supplyPosition;
	
	@Override
	public void setMinLength(int minLength) {
		AssertUtils.assertTrue(minLength >= this.minLength,
				"Serial number length [" + minLength + "] must not less than minimum default value [" + DEFAULT_MIN_LENGTH + "].");
		
		this.minLength = minLength;
	}

	@Override
	public int getMinLength() {
		return this.minLength;
	}

	@Override
	public void setSupply(char supply) {
		this.supply = supply;
	}

	@Override
	public char getSupply() {
		return this.supply;
	}

	@Override
	public void setSupplyPosition(int supplyPosition) {
		this.supplyPosition = supplyPosition;
	}

	@Override
	public int getSupplyPosition() {
		return this.supplyPosition;
	}
	
	@Override
	public String generate(String prefix, String suffix) {
		prefix = StringUtils.safeString(prefix);
		String body = StringUtils.safeString(this.generateBody());
		suffix = StringUtils.safeString(suffix);
		
		int totalLength = prefix.length() + body.length() + suffix.length();
		if (totalLength < minLength) {
			String supplied = supplying(minLength - totalLength);
			switch (supplyPosition) {
			// 后缀之前补充
			case 1:
				suffix = supplied + suffix;
				break;
			// 默认在前缀之后补充
			default:
				prefix = prefix + supplied;
				break;
			}
		}
		return new StringBuffer(prefix).append(body).append(suffix).toString();
	}
	
	/**
	 * 产生指定长度的补充字符序列
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param length
	 * @return
	 */
	private String supplying(int length) {
		StringBuffer supply = new StringBuffer();
		do {
			supply.append(this.supply);
		} while (--length > 0);
		
		return supply.toString();
	}

}
