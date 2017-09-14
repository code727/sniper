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
 * Create Date : 2017-9-13
 */

package org.sniper.http.headers.request;

import org.sniper.commons.enums.http.TEAlgorithm;
import org.sniper.commons.util.AssertUtils;
import org.sniper.http.headers.AbstractQualityFactor;

/**
 * 传输编码类型
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TE extends AbstractQualityFactor {

	private static final long serialVersionUID = 8993761533716387260L;
	
	private TEAlgorithm algorithm;
	
	public TE(TEAlgorithm algorithm) {
		this(algorithm, MAX_DEFAULT);
	}
	
	public TE(TEAlgorithm algorithm, double qualityValue) {
		setAlgorithm(algorithm);
		setQualityValue(qualityValue);
	}

	public void setAlgorithm(TEAlgorithm algorithm) {
		AssertUtils.assertNotNull(algorithm, "Transfer encoding algorithm must not be null");
		this.algorithm = algorithm;
	}

	public TEAlgorithm getAlgorithm() {
		return algorithm;
	}
	
	@Override
	public String toString() {
		String qualityValue = super.toString();
		
		if (qualityValue.length() > 0) 
			return this.algorithm.getEncoding() + qualityValue;
		
		return this.algorithm.getEncoding();
	}
		
}
