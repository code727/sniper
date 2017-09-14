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

package org.sniper.http.headers;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.StringUtils;

/**
 * Q因子抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractQualityFactor implements QualityFactor {
	
	private static final long serialVersionUID = -3158184340281312062L;
			
	/** 质量价值，默认为1.0 */
	private double qualityValue = MAX_DEFAULT;
	
	public double getQualityValue() {
		return qualityValue;
	}

	public void setQualityValue(double qualityValue) {
		AssertUtils.assertTrue(qualityValue >= QualityFactor.MIN_DEFAULT && qualityValue <= QualityFactor.MAX_DEFAULT,
				"Invalid http quality value '" + qualityValue + "',should be between 0 and 1");
		
		this.qualityValue = qualityValue;
	}

	@Override
	public String toString() {
		return (this.qualityValue >= MIN_DEFAULT && this.qualityValue < MAX_DEFAULT) ? 
				(new StringBuilder(StringUtils.SEMICOLON).append(PARAM_QUALITY_FACTOR)
						.append(StringUtils.ASSIGNMENT).append(this.qualityValue).toString()) : StringUtils.EMPTY;
	}
		
}
