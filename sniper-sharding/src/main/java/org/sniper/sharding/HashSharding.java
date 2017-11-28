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
 * Create Date : 2017-4-19
 */

package org.sniper.sharding;

import java.math.BigInteger;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.sharding.route.Route;

/**
 * 哈希分片器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HashSharding extends AbstractSharding {
	
	/** 模数 */
	private BigInteger model;
	
	/** 路由目标格式化长度 */
	private int formatLength;
	
	public HashSharding() {
		this(1);
	}
	
	public HashSharding(int model) {
		setModel(model);
	}
	
	public int getModel() {
		return model.intValue();
	}

	public void setModel(int model) {
		AssertUtils.assertTrue(model != 0, "Hash model must not be equals 0");
		this.model = new BigInteger(String.valueOf(Math.abs(model)));
	}
	
	public int getFormatLength() {
		return formatLength;
	}

	public void setFormatLength(int formatLength) {
		this.formatLength = formatLength;
	}

	@Override
	protected <T> void doSharding(T parameter, Route route) {
		String target = "";
		if (parameter == null)
			target = "0";
		else {
			BigInteger integer;
			try {
				integer = new BigInteger(parameter.toString()).abs();
			} catch (NumberFormatException e) {
				// 如果是小数或其他非数字型对象，则直接利用该对象的哈希绝对值取模
				integer = new BigInteger(String.valueOf(parameter.hashCode())).abs();
			}
			
			target = integer.mod(model).toString();
		}
		
		if (formatLength > 0)
			route.setTarget(NumberUtils.format(new BigInteger(target), formatLength));
		else
			route.setTarget(target);
	}
	
}
