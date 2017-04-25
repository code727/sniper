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

package org.workin.sharding;

import java.math.BigInteger;

import org.workin.commons.sharding.Route;
import org.workin.commons.util.RegexUtils;

/**
 * 哈希分片器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HashSharding extends AbstractSharding {
	
	/** 模数 */
	private BigInteger model = new BigInteger("1");
	
	public int getModel() {
		return model.intValue();
	}

	public void setModel(int model) {
		if (model != 0)
			this.model = new BigInteger(String.valueOf(Math.abs(model)));
	}
	
	@Override
	protected <T> void doSharded(T parameter, Route route) {
		String target = "";
		if (parameter == null)
			target = "0";
		else {
			String str = parameter.toString();
			BigInteger integer;
			if (RegexUtils.isInteger(str)) 
				// 如果参数是整数，则直接利用整数绝对值取模
				integer = new BigInteger(str).abs();
			else 
				// 如果是小数或其他非数字型对象，则直接利用该对象的哈希绝对值取模
				integer = new BigInteger(String.valueOf(Math.abs(parameter.hashCode())));
				
			target = integer.mod(model).toString();
		}
		
		route.suffix(target);
	}
		
}
