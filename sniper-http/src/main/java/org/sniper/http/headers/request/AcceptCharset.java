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
 * Create Date : 2017-8-14
 */

package org.sniper.http.headers.request;

import java.nio.charset.Charset;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CodecUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.http.headers.AbstractQualityFactor;

/**
 * 客户端可以处理的字符集类型
 * @author  Daniele
 * @version 1.0
 */
public class AcceptCharset extends AbstractQualityFactor {
	
	private static final long serialVersionUID = 5448698826832761985L;
	
	/** 字符集名称 */
	private String name;
	
	public AcceptCharset() {
		this(CodecUtils.DEFAULT_ENCODING);
	}
		
	public AcceptCharset(String charsetName) {
		this(charsetName, MAX_DEFAULT_VALUE);
	}
	
	public AcceptCharset(double qualityValue) {
		this(CodecUtils.DEFAULT_ENCODING, qualityValue);
	}
	
	public AcceptCharset(String charsetName, double qualityValue) {
		setName(charsetName);
		setQualityValue(qualityValue);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		AssertUtils.assertNotBlank(name, "Charset name must not be null or blank");
		
		if (StringUtils.ANY.equals(name)) {
			this.name = name;
		} else {
			this.name = Charset.forName(name).name();
		}
	}
	
	@Override
	public String toString() {
		String qualityValue = super.toString();
		
		if (qualityValue.length() > 0) 
			return this.name + qualityValue;
		
		return this.name.toString();
	}
						
}
