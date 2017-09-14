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
 * Create Date : 2017-8-29
 */

package org.sniper.http.headers;

import java.io.Serializable;

/**
 * Q因子接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface QualityFactor extends Serializable {
	
	/** Q因子参数名 */
	public static final String PARAM_QUALITY_FACTOR = "q";
	
	/** 最小默认值 */
	public static final double MIN_DEFAULT = 0.0d;
	
	/** 最大默认值 */
	public static final double MAX_DEFAULT = 1.0d;
	
	/**
	 * 获取质量价值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public double getQualityValue();
	
	/**
	 * 设置质量价值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qualityValue
	 */
	public void setQualityValue(double qualityValue);

}
