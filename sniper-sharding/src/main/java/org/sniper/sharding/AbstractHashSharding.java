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

/**
 * 哈希分片器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractHashSharding<T> extends AbstractSharding<T> {
	
	/** 模数 */
	protected BigInteger model;
	
	/** 路由目标格式化长度 */
	protected int formatLength;
	
	protected AbstractHashSharding() {
		this(1);
	}
	
	protected AbstractHashSharding(int model) {
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

}
